package tic_tac_toe.game_manager

interface RepoIntermediary {
    fun createGame() : Game
    fun readGame(id : Int) : Triple<Boolean, String, Game>
    fun writeTurn(turn : Turn) : Triple<Boolean, String, Game>
}
