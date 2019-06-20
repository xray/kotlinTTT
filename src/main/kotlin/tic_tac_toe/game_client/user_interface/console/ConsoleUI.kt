package tic_tac_toe.game_client.user_interface.console

import tic_tac_toe.game_client.mode.GameMode
import tic_tac_toe.game_client.user_interface.InputMethod
import tic_tac_toe.game_client.user_interface.OutputMethod
import tic_tac_toe.game_client.user_interface.UserInterface
import tic_tac_toe.game_manager.State

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

    override fun getMove(state: State): Int {
        val selections = mapOf("1" to 1, "2" to 2, "3" to 3, "4" to 4, "5" to 5,
                "6" to 6, "7" to 7, "8" to 8, "9" to 9)
        output.send("Player ${state.currentPlayer}, you're up!")
        output.send("Please select a number between 1 and 9")
        val choice = input.receive()
        if (selections.containsKey(choice)) return selections.getValue(choice)
        output.send("\"${choice.toLowerCase()}\" is not a number between 1 and 9...")
        output.send("Please try again!")
        return getMove(state)
    }

    override fun showBoard(state: State): String {
        fun colorNumber(i: Int) : String {return "\u001B[30;1m$i\u001B[0m"}
        val x = "\u001b[36m✕\u001b[0m"
        val o = "\u001b[35m◯\u001B[0m"

        val convertedBoard = mutableMapOf<Int, String>()
        for (spaces in state.board) {
            when {
                spaces.value == 1 -> convertedBoard[spaces.key] = x
                spaces.value == 2 -> convertedBoard[spaces.key] = o
                else -> {
                    val num = colorNumber(spaces.key)
                    convertedBoard[spaces.key] = num
                }
            }
        }

        val boardTop =     "╭───┰───┰───╮"
        val boardDivider = "┝━━━╋━━━╋━━━┥"
        val boardBottom =  "╰───┸───┸───╯"

        val boardLine1 = "│ ${convertedBoard[1]} ┃ ${convertedBoard[2]} ┃ ${convertedBoard[3]} │"
        val boardLine2 = "│ ${convertedBoard[4]} ┃ ${convertedBoard[5]} ┃ ${convertedBoard[6]} │"
        val boardLine3 = "│ ${convertedBoard[7]} ┃ ${convertedBoard[8]} ┃ ${convertedBoard[9]} │"

        output.send(boardTop)
        output.send(boardLine1)
        output.send(boardDivider)
        output.send(boardLine2)
        output.send(boardDivider)
        output.send(boardLine3)
        output.send(boardBottom)

        return "$boardTop\n$boardLine1\n$boardDivider\n$boardLine2\n$boardDivider\n$boardLine3\n$boardBottom\n"
    }
}