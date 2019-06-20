package tic_tac_toe.game_client.mode.local

import tic_tac_toe.game_client.mode.ManagerInterface
import tic_tac_toe.game_manager.GameManager
import tic_tac_toe.game_manager.State

class LocalMI(val gm: GameManager) : ManagerInterface {
    override fun getNewState(): State {
        return gm.newGame()
    }

    override fun isBoardComplete(board: Map<Int, Int>): Pair<Boolean, Int> {
        return gm.isGameComplete(board)
    }

    override fun isGameComplete(state: State): Pair<Boolean, Int> {
        return isBoardComplete(state.board)
    }

    override fun isValidMove(player: Int, location: Int, state: State): Pair<Boolean, String> {
        return gm.isValidMove(state.board, location, player, state.currentPlayer)
    }

    override fun makeMove(gameId: Int, location: Int, player: Int): Triple<Boolean, String, State> {
        return gm.makeMove(gameId, location, player)
    }
}