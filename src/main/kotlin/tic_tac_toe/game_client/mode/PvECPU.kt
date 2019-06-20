package tic_tac_toe.game_client.mode

import tic_tac_toe.game_client.user_interface.UserInterface
import tic_tac_toe.game_manager.State

class PvECPU(private val io: UserInterface, private val gm: ManagerInterface) : GameMode {
    override fun play(): State {
        return play(gm.getNewState(), false)
    }

    private fun play(state: State, comp: Boolean): State {
        io.showBoard(state)
        val move = if (comp) {
            val availableSpaces = mutableListOf<Int>()
            for (space in state.board){
                if (space.value == 0) availableSpaces.add(space.key)
            }
            availableSpaces.random()
        } else {
            io.getMove(state)

        }
        val (success, message, returnState) = gm.makeMove(state.gameId, move, state.currentPlayer)
        if (success && returnState.gameComplete) { io.showBoard(returnState); return returnState }
        if (success) return play(returnState, !comp)
        io.displayMessage(message)
        io.displayMessage("Please try again!")
        return play(returnState, comp)
    }
}