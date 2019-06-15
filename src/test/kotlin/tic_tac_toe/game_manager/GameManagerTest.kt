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
}