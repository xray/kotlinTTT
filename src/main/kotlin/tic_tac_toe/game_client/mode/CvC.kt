package tic_tac_toe.game_client.mode

import tic_tac_toe.game_client.mode.cpu.ai.AIPlayer
import tic_tac_toe.game_client.user_interface.UserInterface
import tic_tac_toe.game_manager.State

class CvC(private val io: UserInterface, private val mi: ManagerInterface, private val ai: AIPlayer) : GameMode {
    override fun play(): State {
        return play(mi.getNewState(), false)
    }

    private fun play(state: State, comp: Boolean): State {
        io.showBoard(state)
        val move = ai.winGame(state)
        val (success, message, returnState) = mi.makeMove(state.gameId, move, state.currentPlayer)
        if (success && returnState.gameComplete) { io.showBoard(returnState); return returnState }
        if (success) return play(returnState, !comp)
        io.displayMessage(message)
        io.displayMessage("Please try again!")
        return play(returnState, comp)
    }
}