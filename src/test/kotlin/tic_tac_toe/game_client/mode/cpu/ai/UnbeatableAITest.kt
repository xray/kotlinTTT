package tic_tac_toe.game_client.mode.cpu.ai

import tic_tac_toe.game_client.mode.local.LocalMI
import tic_tac_toe.game_manager.GameManager
import tic_tac_toe.game_manager.State
import tic_tac_toe.game_manager.repo.PostgresRepo
import kotlin.test.Test

class UnbeatableAITest {
    private val gm = GameManager(PostgresRepo())
    private val mi = LocalMI(gm)

    @Test fun testGetRemainingSpaceReturnsAnArrayOfPositionsThatHaveYetToBeFilled() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)


        val ai = UnbeatableAI(mi)

        assert(ai.getRemainingSpaces(state).contentEquals(arrayOf(4, 6, 7)))
    }

    @Test fun testSimulateMoveReturnsUpdatedState() {
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val initState = State(board, 1, false, 1)
        val updatedBoard = mapOf(1 to 1, 2 to 2, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val updatedState = State(updatedBoard, 1, false, 1)

        val ai = UnbeatableAI(mi)

        assert(ai.simulateMove(initState, 2, 2) == updatedState)
    }

    @Test fun testScoreOutcomeReturnsTenWhenCurrentPlayerWins() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)

        val ai = UnbeatableAI(mi)

        assert(ai.scoreOutcome(state, 1) == 10)
    }

    @Test fun testScoreOutcomeReturnsNegativeTenWhenOtherPlayerWins() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)

        val ai = UnbeatableAI(mi)

        assert(ai.scoreOutcome(state, 2) == -10)
    }

    @Test fun testScoreOutcomeReturnsZeroWhenThereIsATie() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 2, 5 to 2, 6 to 1, 7 to 1, 8 to 1, 9 to 2)
        val state = State(board, 0, true, 1)

        val ai = UnbeatableAI(mi)

        assert(ai.scoreOutcome(state, 2) == 0)
    }

    @Test fun testGetBestMoveReturnsTheBestMove() {
        val moveSet = mapOf(1 to -10, 2 to 0, 3 to 10)
        val ai = UnbeatableAI(mi)

        assert(ai.getBestMove(moveSet) == 3)

    }

    @Test fun testNoOverflowPlz() {
        val testBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 2, 5 to 2, 6 to 0, 7 to 0, 8 to 1, 9 to 0)
        val testState = State(testBoard, 1, false, 1)
        val gameMode = UnbeatableAI(mi)

        val theMove = gameMode.winGame(testState)

        assert(theMove == 6)
    }

    @Test fun testMinimaxPicksTheBestMove() {
        val testBoard = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val testState = State(testBoard, 2, false, 1)
        val gameMode = UnbeatableAI(mi)

        val theMove = gameMode.winGame(testState)

        assert(theMove == 5)
    }

    @Test fun testMinimaxMakesGoodLifeDescisions() {
        val testBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 2, 6 to 0, 7 to 0, 8 to 1, 9 to 0)
        val testState = State(testBoard, 2, false, 1)
        val gameMode = UnbeatableAI(mi)

        val theMove = gameMode.winGame(testState)

        assert(theMove == 4)
    }

    @Test fun testMinimaxContinuestoMakeGoodLifeDescisions() {
        val testBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 2, 5 to 2, 6 to 0, 7 to 0, 8 to 1, 9 to 0)
        val testState = State(testBoard, 1, false, 1)
        val gameMode = UnbeatableAI(mi)

        val theMove = gameMode.winGame(testState)

        assert(theMove == 6)
    }
}