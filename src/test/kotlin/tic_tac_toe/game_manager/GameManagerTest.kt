package tic_tac_toe.game_manager

import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import tic_tac_toe.game_manager.fixtures.TestRepoIntermediary
import kotlin.test.Test

class GameManagerTest {
    private val mockRepo = spyk<TestRepoIntermediary>()

    @Test fun testNewGameCallsCreateGame() {
        val testGame = Game(false, 1, arrayOf())
        every { mockRepo.createGame() } returns testGame

        val sm = GameManager(mockRepo)
        sm.newGame()

        verify { mockRepo.createGame() }
    }

    @Test fun testNewGameReturnsANewState() {
        val testGame = Game(false, 1, arrayOf())
        every { mockRepo.createGame() } returns testGame
        val expectedBoard = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)

        val sm = GameManager(mockRepo)
        val state = sm.newGame()

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

        val sm = GameManager(mockRepo)
        sm.makeMove(1, 1, 1)

        verify { mockRepo.readGame(1) }
    }

    @Test fun testMakeMoveCallsWriteTurn() {
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)

        val sm = GameManager(mockRepo)
        sm.makeMove(1, 1, 1)

        verify { mockRepo.writeTurn(Turn(board, 1, 1, 1)) }
    }

    @Test fun testMakeMoveReturnsUpdatedStateWithValidMove() {
        val sm = GameManager(mockRepo)
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 2, false, 1)

        val (success, message, state) = sm.makeMove(1, 1, 1)

        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsUpdatedStateWhenPlayerOneIsNext() {
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

        val sm = GameManager(mockRepo)
        val expectedState = State(boardW3, 1, false, 2)


        val (success, message, state) = sm.makeMove(2, 2, 2)

        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

}