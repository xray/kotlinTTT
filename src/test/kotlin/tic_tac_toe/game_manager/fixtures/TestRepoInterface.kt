package tic_tac_toe.game_manager.fixtures

import tic_tac_toe.game_manager.Game
import tic_tac_toe.game_manager.RepoInterface
import tic_tac_toe.game_manager.Turn

class TestRepoInterface : RepoInterface {
    override fun createGame(): Game {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn = Turn(board, 1, 0, 0)
        return Game(false, 1, arrayOf(turn))
    }

    override fun readGame(id: Int) : Triple<Boolean, String, Game> {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn = Turn(board, 1, 0, 0)
        val game = Game(false, 1, arrayOf(turn))
        return Triple(true, "", game)
    }

    override fun writeTurn(turn: Turn): Triple<Boolean, String, Game> {
        val board0 = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn0 = Turn(board0, 1, 0, 0)
        val board1 = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val turn1 = Turn(board1, 1, 1, 1)
        val writeGame = Game(false, 1, arrayOf(turn1, turn0))
        return Triple(true, "", writeGame)
    }

    override fun checkCompleteBoard(board: Map<Int, Int>): Pair<Boolean, Int> {
        return Pair(true, 0)
    }
}