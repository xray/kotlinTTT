package tic_tac_toe.game_client.user_interface.fixtures

import tic_tac_toe.game_client.GameMode
import tic_tac_toe.game_client.fixtures.TestGameMode
import tic_tac_toe.game_client.user_interface.UserInterface

class TestUserInterface : UserInterface {
    override fun confirm(message: String): Boolean {
        return false
    }

    override fun displayMessage(message: String): Pair<Boolean, String> {
        return Pair(true, message)
    }

    override fun getGameMode(modes: Map<String, GameMode>): GameMode {
        return modes.getOrDefault("Test", TestGameMode())
    }
}