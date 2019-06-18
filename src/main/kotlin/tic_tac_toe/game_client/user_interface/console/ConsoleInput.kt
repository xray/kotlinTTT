package tic_tac_toe.game_client.user_interface.console

import tic_tac_toe.game_client.user_interface.InputMethod

class ConsoleInput : InputMethod {
    override fun receive(): String {
        return "${readLine()}"
    }
}
