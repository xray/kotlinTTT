package tic_tac_toe.game_client.mode

import tic_tac_toe.game_client.user_interface.UserInterface
import tic_tac_toe.game_manager.State

class PvP(private val io: UserInterface, private val gm: ManagerInterface) : GameMode {
    override fun play(): State {
        return play(gm.getNewState())
    }

    private fun play(state: State): State {
        io.showBoard(state)
        val move = io.getMove(state)
        val (success, message, returnState) = gm.makeMove(state.gameId, move, state.currentPlayer)
        if (success && returnState.gameComplete) { io.showBoard(returnState); return returnState }
        if (success) return play(returnState)
        io.displayMessage(message)
        io.displayMessage("Please try again!")
        return play(returnState)
    }
}