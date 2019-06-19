package tic_tac_toe.game_client.mode.fixtures

import tic_tac_toe.game_client.mode.ManagerInterface
import tic_tac_toe.game_manager.State

class TestManagerInterface : ManagerInterface {
    override fun getNewState(): State {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        return State(board, 1, false, 1)
    }

    override fun makeMove(gameId: Int, location: Int, player: Int): Triple<Boolean, String, State> {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2)
        val state = State(board, 1, true, 1)
        return Triple(true, "", state)
    }

    override fun isValidMove(player: Int, location: Int, state: State): Pair<Boolean, String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isGameComplete(state: State): Pair<Boolean, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}