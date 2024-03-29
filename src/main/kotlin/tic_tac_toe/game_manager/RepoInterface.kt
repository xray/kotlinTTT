package tic_tac_toe.game_manager

interface RepoInterface {
    fun createGame() : Game
    fun readGame(id : Int) : Triple<Boolean, String, Game>
    fun writeTurn(turn : Turn) : Triple<Boolean, String, Game>
    fun checkCompleteBoard(board : Map<Int, Int>) : Pair<Boolean, Int>
}
