package tic_tac_toe.game_client.user_interface

import tic_tac_toe.game_client.GameMode

class ConsoleUI(private val input: InputMethod, private val output: OutputMethod) : UserInterface {
    override fun confirm(message: String): Boolean {
        output.send("$message (Y/n)")
        val response = input.receive().toLowerCase()
        if (response == "y") return true
        if (response == "n") return false
        output.send("\"$response\" is not \"Y\", \"N\" or blank...")
        output.send("Please try again!")
        return confirm(message)
    }

    override fun displayMessage(message: String): Pair<Boolean, String> {
        output.send(message)
        return Pair(true, message)
    }

    override fun getGameMode(modes: Map<String, GameMode>): GameMode {
        if (modes.size == 1) return modes.toList()[0].second
        output.send("Please choose a game mode:")
        val modeNumbers = mutableMapOf<Int, String>()
        var numberOfModes = 0
        for (mode in modes) {
            numberOfModes++
            modeNumbers[numberOfModes] = mode.key
            output.send("$numberOfModes.) ${mode.key}")
        }
        output.send("\nEnter a number between 1 and $numberOfModes")
        val choice = input.receive()
        for (option in modeNumbers){
            if (choice == "${option.key}") return modes[modeNumbers[option.key]]!!
        }

        output.send("\"${choice.toLowerCase()}\" is not a number between 1 and $numberOfModes...")
        output.send("Please try again!")
        return getGameMode(modes)
    }
}