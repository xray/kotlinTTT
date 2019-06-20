package tic_tac_toe.game_client.mode

import tic_tac_toe.game_manager.State

interface ManagerInterface {
    fun getNewState(): State
    fun makeMove(gameId: Int, location: Int, player: Int) : Triple<Boolean, String, State>
    fun isValidMove(player: Int, location: Int, state: State) : Pair<Boolean, String>
    fun isGameComplete(state: State) : Pair<Boolean, Int>
    fun isBoardComplete(board: Map<Int, Int>) : Pair<Boolean, Int>
}