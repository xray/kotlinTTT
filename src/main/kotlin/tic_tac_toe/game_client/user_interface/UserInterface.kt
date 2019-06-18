package tic_tac_toe.game_client.user_interface

import tic_tac_toe.game_client.GameMode

interface UserInterface {
    fun confirm(message: String) : Boolean
    fun displayMessage(message: String) : Pair<Boolean, String>
    fun getGameMode(modes: Map<String, GameMode>) : GameMode
}