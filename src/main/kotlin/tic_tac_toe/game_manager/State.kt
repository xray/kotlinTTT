package tic_tac_toe.game_manager

class State (val board: Map<Int, Int>, val currentPlayer: Int, val gameComplete: Boolean, val gameId: Int) {
    constructor(newGameId : Int) : this(
            mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0),
            1,
            false,
            newGameId
    )

    override fun equals(other: Any?): Boolean {
        return if (other !is State) false
        else (hashCode() == other.hashCode())
    }

    override fun hashCode(): Int {
        var result = board.hashCode()
        result = 31 * result + currentPlayer
        result = 31 * result + gameComplete.hashCode()
        result = 31 * result + gameId
        return result
    }
}
