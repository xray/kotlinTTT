package tic_tac_toe.game_client

class GameClient(private val io: UserIntermediary) {
    fun start(gameModes: Array<GameMode>) {
        val selectedMode = io.getGameMode(gameModes)
        val endState = selectedMode.play()
        val winner = endState.currentPlayer
        io.displayMessage("Congrats Player $winner, you win!")
        val playAgain = io.confirm("Would you like to play again?")
        if (playAgain) start(gameModes)
    }
}