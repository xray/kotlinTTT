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
        val x = "\u001b[36m✕\u001b[0m"
        val o = "\u001b[35m◯\u001B[0m"
        fun n(i: Int) : String {return "\u001B[30;1m$i\u001B[0m"}

        return "╭───┰───┰───╮" +
               "│ $x ┃ $o ┃ $x │" +
               "┝━━━╋━━━╋━━━┥" +
               "│ ${n(4)} ┃ $x ┃ ${n(6)} │" +
               "┝━━━╋━━━╋━━━┥" +
               "│ ${n(7)} ┃ $o ┃ ${n(9)} │" +
               "╰───┸───┸───╯"
    }
}