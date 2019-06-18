package tic_tac_toe.game_client.fixtures

import tic_tac_toe.game_client.GameMode
import tic_tac_toe.game_manager.State

class TestGameMode : GameMode {
    override fun play(): State {
        return State(
                mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2),
                1,
                true,
                1
        )
    }

}