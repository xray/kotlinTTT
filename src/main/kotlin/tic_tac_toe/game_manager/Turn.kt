package tic_tac_toe.game_manager

class Turn(val board: Map<Int, Int>, val gameId: Int, val move: Int, val player: Int) {
    override fun equals(other: Any?): Boolean {
        return if (other !is Turn) false
        else (board == other.board && gameId == other.gameId && move == other.move && player == other.player)
    }

    override fun hashCode(): Int {
        var result = board.hashCode()
        result = 31 * result + gameId
        result = 31 * result + move
        result = 31 * result + player
        return result
    }
}