package tic_tac_toe.game_manager

class GameManager(private val repo: RepoIntermediary) {
    fun newGame() : State {
        val game = repo.createGame()
        return State(game.id)
    }

    fun makeMove(gameId: Int, position: Int, player: Int) : Triple<Boolean, String, State> {
        val (readSuccess, readMessage, readResultGame) = repo.readGame(gameId)
        if (!readSuccess) return Triple(readSuccess, readMessage, newGame())

        val (valid, message) = isValidMove(
                readResultGame.turns[0].board,
                position,
                player,
                getNextPlayer(readResultGame)
        )

        if (!valid) return Triple(false, message, createState(readResultGame, getNextPlayer(readResultGame)))

        val (_, _, writeResultGame) = repo.writeTurn(createTurn(readResultGame, position, player))

        return Triple(true, "", createState(writeResultGame, switchPlayer(player)))
    }

    fun isValidMove(board: Map<Int, Int>, position: Int, player: Int, turn: Int) : Pair<Boolean, String> {
        if (player != turn) return Pair(false, "It is not Player $player's turn to play.")
        if (board[position] != 0) return Pair(false, "This position is already populated.")
        return Pair(true, "")
    }

    private fun createTurn(game: Game, playerPosition: Int, player: Int) : Turn {
        val mostRecentBoard = game.turns[0].board
        val mutableBoard = mutableMapOf<Int, Int>()

        for (boardPosition in mostRecentBoard){
            if (boardPosition.key == playerPosition) {
                mutableBoard[boardPosition.key] = player
            } else {
                mutableBoard[boardPosition.key] = boardPosition.value
            }
        }

        return Turn(mutableBoard.toMap(), game.id, playerPosition, player)
    }

    private fun createState(game: Game, player: Int) : State {

        return State(game.turns[0].board, player, game.complete, game.id)
    }

    private fun switchPlayer(player: Int) : Int {
        return if (player == 1) 2
        else 1
    }

    private fun getNextPlayer(game: Game) : Int {
        val lastPlayer = game.turns[0].player
        return switchPlayer(lastPlayer)
    }
}
