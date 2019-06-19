package tic_tac_toe.game_client.user_interface

import tic_tac_toe.game_client.mode.GameMode
import tic_tac_toe.game_manager.State

interface UserInterface {
    fun confirm(message: String) : Boolean
    fun displayMessage(message: String) : Pair<Boolean, String>
    fun getGameMode(modes: Map<String, GameMode>) : GameMode
    fun showBoard(state: State) : String
    fun getMove(state: State) : Int
}