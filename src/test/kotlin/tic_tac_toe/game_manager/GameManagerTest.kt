package tic_tac_toe.game_manager

import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import tic_tac_toe.game_manager.fixtures.TestRepoInterface
import kotlin.test.Test

class GameManagerTest {
    private val mockRepo = spyk<TestRepoInterface>()

    @Test fun testNewGameCallsCreateGame() {
        val testGame = Game(false, 1, arrayOf())
        every { mockRepo.createGame() } returns testGame

        val gm = GameManager(mockRepo)
        gm.newGame()

        verify { mockRepo.createGame() }
    }

    @Test fun testNewGameReturnsANewState() {
        val expectedBoard = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)

        val gm = GameManager(mockRepo)
        val state = gm.newGame()

        assert(state.board == expectedBoard)
        assert(state.currentPlayer == 1)
        assert(!state.gameComplete)
        assert(state.gameId == 1)
    }

    @Test fun testMakeMoveCallsReadGame() {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn = Turn(board, 1, 0, 0)
        val game = Game(false, 1, arrayOf(turn))
        val response = Triple(true, "", game)
        every { mockRepo.readGame(1) } returns response

        val gm = GameManager(mockRepo)
        gm.makeMove(1, 1, 1)

        verify { mockRepo.readGame(1) }
    }

    @Test fun testMakeMoveCallsWriteTurn() {
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)

        val gm = GameManager(mockRepo)
        gm.makeMove(1, 1, 1)

        verify { mockRepo.writeTurn(Turn(board, 1, 1, 1)) }
    }

    @Test fun testMakeMoveReturnsUpdatedStateWithValidMove() {
        val gm = GameManager(mockRepo)
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 2, false, 1)

        val (success, message, state) = gm.makeMove(1, 1, 1)

        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsUpdatedStateWhenPlayerOneIsNext() {
        // Arrange
        val boardR1 = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turnR1 = Turn(boardR1, 2, 0, 0)
        val boardR2 = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turnR2 = Turn(boardR2, 2, 1, 1)
        val gameR = Game(false, 2, arrayOf(turnR2, turnR1))
        val responseR = Triple(true, "", gameR)
        every { mockRepo.readGame(2) } returns responseR

        val boardW1 = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turnW1 = Turn(boardW1, 2, 0, 0)
        val boardW2 = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turnW2 = Turn(boardW2, 2, 1, 1)
        val boardW3 = mapOf(1 to 1, 2 to 2, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turnW3 = Turn(boardW3, 2, 2, 2)
        val gameW = Game(false, 2, arrayOf(turnW3, turnW2, turnW1))
        val responseW = Triple(true, "", gameW)
        every { mockRepo.writeTurn(turnW3) } returns responseW

        val gm = GameManager(mockRepo)
        val expectedState = State(boardW3, 1, false, 2)

        // Act
        val (success, message, state) = gm.makeMove(2, 2, 2)

        // Assert
        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsSameStateIfMoveIsInvalid() {
        // Arrange
        val board1 = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn1 = Turn(board1, 3, 0, 0)
        val board2 = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn2 = Turn(board2, 3, 1, 1)
        val game = Game(false, 3, arrayOf(turn2, turn1))
        val response = Triple(true, "", game)
        every { mockRepo.readGame(3) } returns response

        val gm = GameManager(mockRepo)
        val expectedState = State(board2, 2, false, 3)

        // Act
        val (success, message, state) = gm.makeMove(3, 1, 2)

        // Assert
        assert(!success)
        assert(message == "This position is already populated.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsSameStateIfNotPlayersTurn() {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(mockRepo)

        val (success, message, state) = gm.makeMove(1, 1, 2)

        assert(!success)
        assert(message == "It is not Player 2's turn to play.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsBlankStateWhenReadIdDoesNotExist() {
        val game = Game(true, 0, arrayOf())
        val response = Triple(false, "Game ID does not exist.", game)
        every { mockRepo.readGame(0) } returns response
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(mockRepo)

        val (success, message, state) = gm.makeMove(0, 7, 1)

        assert(!success)
        assert(message == "Game ID does not exist.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsBlankStateWhenReadFails() {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val game = Game(true, 0, arrayOf())
        val response = Triple(false, "Error making move, please try again later.", game)
        every { mockRepo.readGame(5) } returns response
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(mockRepo)

        val (success, message, state) = gm.makeMove(5, 3, 2)

        assert(!success)
        assert(message == "Error making move, please try again later.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsUnchangedStateWhenWriteFails() {
        val board0 = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn0 = Turn(board0, 6, 0, 0)
        val board1 = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn1 = Turn(board1, 6, 1, 1)
        val game = Game(false, 6, arrayOf(turn1, turn0))
        val readResponse = Triple(true, "", game)
        every { mockRepo.readGame(6) } returns readResponse

        val board2 = mapOf(1 to 1, 2 to 0, 3 to 2, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn2 = Turn(board2, 6, 3, 2)
        val resGame = Game(true, 0, arrayOf())
        val writeResponse = Triple(false, "Error making move, please try again later.", resGame)
        every { mockRepo.writeTurn(turn2) } returns writeResponse

        val expectedState = State(board1, 2, false, 6)
        val gm = GameManager(mockRepo)

        val (success, message, state) = gm.makeMove(6, 3, 2)

        assert(!success)
        assert(message == "Error making move, please try again later.")
        assert(state == expectedState)
    }
    
    @Test fun testIsGameCompleteCallsCheckGameComplete() {
        val completeBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2)
        every { mockRepo.checkCompleteBoard(completeBoard) } returns Pair(true, 1)
        val gm = GameManager(mockRepo)

        gm.isGameComplete(completeBoard)

        verify { mockRepo.checkCompleteBoard(completeBoard) }
    }
}