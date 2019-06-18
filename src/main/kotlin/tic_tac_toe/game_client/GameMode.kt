package tic_tac_toe.game_client

import tic_tac_toe.game_manager.State

interface GameMode {
    fun play() : State
}
