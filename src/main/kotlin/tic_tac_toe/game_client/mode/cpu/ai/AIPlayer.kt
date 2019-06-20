package tic_tac_toe.game_client.mode.cpu.ai

import tic_tac_toe.game_manager.State

interface AIPlayer {
    fun winGame(state: State): Int
}