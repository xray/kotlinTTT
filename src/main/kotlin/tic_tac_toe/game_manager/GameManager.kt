package tic_tac_toe.game_manager

class GameManager(private val repo: RepoIntermediary) {
    fun newGame() : State {
        val game = repo.createGame()
        return State(game.id)
    }

    fun makeMove(gameId: Int, location: Int, player: Int) : Triple<Boolean, String, State> {
        val (_, _, readResultGame) = repo.readGame(gameId)
        val (_, _, writeResultGame) = repo.writeTurn(createTurn(readResultGame, location, player))
        return Triple(true, "", createState(writeResultGame, player))
    }

    private fun createTurn(game: Game, location: Int, player: Int) : Turn {
        val mostRecentBoard = game.turns[0].board
        val mutableBoard = mutableMapOf<Int, Int>()

        for (position in mostRecentBoard){
            if (position.key == location) {
                mutableBoard[position.key] = player
            } else {
                mutableBoard[position.key] = position.value
            }
        }

        return Turn(mutableBoard.toMap(), game.id, location, player)
    }

    private fun createState(game: Game, player: Int) : State {
        return State(game.turns[0].board, player, game.complete, game.id)
    }
}
