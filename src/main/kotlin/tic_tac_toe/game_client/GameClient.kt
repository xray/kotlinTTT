package tic_tac_toe.game_client

import tic_tac_toe.game_client.mode.GameMode
import tic_tac_toe.game_client.user_interface.UserInterface

class GameClient(private val io: UserInterface) {
    fun start(gameModes: Map<String, GameMode>) {
        val selectedMode = io.getGameMode(gameModes)
        val endState = selectedMode.play()
        val winner = endState.currentPlayer
        io.displayMessage("Congrats Player $winner, you win!")
        val playAgain = io.confirm("Would you like to play again?")
        if (playAgain) start(gameModes)
    }
}