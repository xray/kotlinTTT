package tic_tac_toe.game_manager

class GameManager(private val repo: RepoIntermediary) {
    fun newGame() : State {
        val game = repo.createGame()
        return State(game.id)
    }

    fun makeMove(gameId: Int, position: Int, player: Int) : Triple<Boolean, String, State> {
        val (_, _, readResultGame) = repo.readGame(gameId)

        val (valid, message) = isValidMove(readResultGame.turns[0].board, position)
        if (!valid) return Triple(false, message, createState(readResultGame, player))

        val (_, _, writeResultGame) = repo.writeTurn(createTurn(readResultGame, position, player))

        return Triple(true, "", createState(writeResultGame, getNextPlayer(player)))
    }

    fun isValidMove(board: Map<Int, Int>, position: Int) : Pair<Boolean, String> {
        return if (board[position] == 0) {
            Pair(true, "")
        } else {
            Pair(false, "This position is already populated...")
        }
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

    private fun getNextPlayer(player: Int) : Int {
        return if (player == 1) 2
        else 1
    }
}
