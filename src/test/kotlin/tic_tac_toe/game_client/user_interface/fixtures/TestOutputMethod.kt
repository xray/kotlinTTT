package tic_tac_toe.game_client.user_interface.fixtures

import tic_tac_toe.game_client.user_interface.OutputMethod

class TestOutputMethod : OutputMethod {
    override fun send(output: String): String {
        return output
    }
}