package tic_tac_toe.game_client.user_interface.fixtures

import tic_tac_toe.game_client.user_interface.InputMethod

class TestInputMethod : InputMethod {
    override fun receive(): String {
        return "Y"
    }
}