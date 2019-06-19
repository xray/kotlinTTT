package tic_tac_toe.game_client.user_interface.fixtures

import tic_tac_toe.game_client.mode.GameMode
import tic_tac_toe.game_client.fixtures.TestGameMode
import tic_tac_toe.game_client.user_interface.UserInterface
import tic_tac_toe.game_manager.State

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

    override fun getMove(state: State): Int {
        return 7
    }

    override fun showBoard(state: State): String {
        return """
            ╭───┰───┰───╮
            │ 1 ┃ 2 ┃ 3 │
            ┝━━━╋━━━╋━━━┥
            │ 4 ┃ 5 ┃ 6 │
            ┝━━━╋━━━╋━━━┥
            │ 7 ┃ 8 ┃ 9 │
            ╰───┸───┸───╯
        """.trimIndent()
    }
}