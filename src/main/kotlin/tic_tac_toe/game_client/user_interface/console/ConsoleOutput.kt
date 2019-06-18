package tic_tac_toe.game_client.user_interface.console

import tic_tac_toe.game_client.user_interface.OutputMethod

class ConsoleOutput : OutputMethod {
    override fun send(output: String): String {
        println(output)
        return output
    }
}
