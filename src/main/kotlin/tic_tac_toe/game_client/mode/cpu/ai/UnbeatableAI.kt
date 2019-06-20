package tic_tac_toe.game_client.mode.cpu.ai

import tic_tac_toe.game_client.mode.ManagerInterface
import tic_tac_toe.game_manager.State

class UnbeatableAI(private val mi: ManagerInterface) : AIPlayer {
    override fun winGame(state: State) : Int {
        val whoAmI = state.currentPlayer
        val moves = mutableMapOf<Int, Int>()
        val remainingMoves = getRemainingSpaces(state)
        for (remainingMove in remainingMoves) {
            val stateToScore = getStateToScore(state, remainingMove, whoAmI)
            val score = scoreOutcome(stateToScore, whoAmI)
            moves[remainingMove] = score
        }
        val broiledMoves = moves.toMap()
        return getBestMove(broiledMoves)
    }

    fun getRemainingSpaces(state: State) : Array<Int> {
        val availableSpaces = mutableListOf<Int>()
        for (space in state.board){ if (space.value == 0) availableSpaces.add(space.key) }
        return availableSpaces.toTypedArray()
    }

    fun simulateMove(state: State, moveLoc: Int, player: Int) : State {
        val newBoard = mutableMapOf<Int, Int>()
        for (location in state.board) {
            if (location.key == moveLoc) { newBoard[location.key] = player }
            else { newBoard[location.key] = location.value }
        }
        val easyBakedBoard = newBoard.toMap()
        val nextPlayer = if (player == 1) { 2 } else { 1 }
        val (complete, winner) = mi.isBoardComplete(easyBakedBoard)
        if (complete) return State(easyBakedBoard, winner, true, state.gameId)
        return State(easyBakedBoard, nextPlayer, false, state.gameId)
    }

    fun scoreOutcome(state: State, me: Int): Int {
        if (state.currentPlayer == me) return 10
        if (state.currentPlayer == 0) return 0
        return -10
    }

    fun getStateToScore(state: State, move: Int, player: Int) : State {
        val updatedState = simulateMove(state, move, player)
        if (updatedState.gameComplete) return updatedState
        val nextMove = winGame(updatedState)
        return getStateToScore(updatedState, nextMove, updatedState.currentPlayer)
    }

    fun getBestMove(options: Map<Int, Int>) : Int {
        var selectionSet = false
        var selection = Pair(0, 0)
        for (option in options) {
            if (!selectionSet) selection = Pair(option.key, option.value); selectionSet = true
            if (option.value > selection.second) selection = Pair(option.key, option.value)
        }
        return selection.first
    }
}