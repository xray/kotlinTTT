package tic_tac_toe.game_client.mode.local

import tic_tac_toe.game_client.mode.ManagerInterface
import tic_tac_toe.game_manager.GameManager
import tic_tac_toe.game_manager.State

class LocalMI(val gm: GameManager) : ManagerInterface {
    override fun makeMove(gameId: Int, location: Int, player: Int): Triple<Boolean, String, State> {
        return gm.makeMove(gameId, location, player)
    }

    override fun isValidMove(player: Int, location: Int, state: State): Pair<Boolean, String> {
        return gm.isValidMove(state.board, location, player, state.currentPlayer)
    }

    override fun isGameComplete(state: State): Pair<Boolean, Int> {
        return gm.isGameComplete(state.board)
    }

    override fun getNewState(): State {
        return gm.newGame()
    }

}