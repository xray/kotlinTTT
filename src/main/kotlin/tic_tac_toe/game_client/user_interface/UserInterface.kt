package tic_tac_toe.game_client.user_interface

import tic_tac_toe.game_client.GameMode

interface UserInterface {
    fun getGameMode(modes: Array<GameMode>) : GameMode
    fun displayMessage(message: String) : Pair<Boolean, String>
    fun confirm(message: String) : Boolean
}