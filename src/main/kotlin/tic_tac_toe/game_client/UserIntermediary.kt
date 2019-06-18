package tic_tac_toe.game_client

interface UserIntermediary {
    fun getGameMode(modes: Array<GameMode>) : GameMode
    fun displayMessage(message: String) : Pair<Boolean, String>
    fun confirm(message: String) : Boolean
}