package tic_tac_toe.game_manager.repo

import tic_tac_toe.game_manager.RepoInterface
import tic_tac_toe.game_manager.Game
import tic_tac_toe.game_manager.Turn
import tic_tac_toe.game_manager.repo.schema.games.Games
import tic_tac_toe.game_manager.repo.schema.turns.Turns
import tic_tac_toe.game_manager.repo.schema.boards.Boards
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tic_tac_toe.game_manager.repo.schema.boards.BoardEntry
import tic_tac_toe.game_manager.repo.schema.games.GameEntry
import tic_tac_toe.game_manager.repo.schema.turns.TurnEntry

class PostgresRepo(env : String = "") : RepoInterface {
    init {
        Database.connect("jdbc:postgresql://localhost:5432/tictactoe$env",
                driver = "org.postgresql.Driver",
                user = "postgres",
                password = ""
        )

        transaction {
            SchemaUtils.create (Games, Turns, Boards)
        }
    }

    override fun createGame(): Game {
        val testGame = transaction {
            GameEntry.new {
                complete = false
            }
        }

        transaction {
            TurnEntry.new {
                player = 0
                move = 0
                game = testGame
                board = BoardEntry.new {
                    one = 0
                    two = 0
                    three = 0
                    four = 0
                    five = 0
                    six = 0
                    seven = 0
                    eight = 0
                    nine = 0
                }
            }
        }
        val (_, _, game) = readGame(testGame.id.value)
        return game
    }

    override fun readGame(id: Int): Triple<Boolean, String, Game> {
        val game = transaction { GameEntry.findById(id) }
        val turns = transaction { TurnEntry.find { Turns.game eq id }.sortedByDescending{ it.id } }

        if (game != null) {
            val turnList = mutableSetOf<Turn>()
            for (dbTurn in turns) {
                val dbBoard = transaction { BoardEntry.findById(dbTurn.board.id) }
                if (dbBoard != null) {
                    val board = mapOf(
                            1 to dbBoard.one,
                            2 to dbBoard.two,
                            3 to dbBoard.three,
                            4 to dbBoard.four,
                            5 to dbBoard.five,
                            6 to dbBoard.six,
                            7 to dbBoard.seven,
                            8 to dbBoard.eight,
                            9 to dbBoard.nine
                    )
                    val newTurn = Turn(board, id, dbTurn.move, dbTurn.player)
                    turnList.add(newTurn)
                }
            }
            val gameTurns = turnList.toTypedArray()
            val constructedGame = Game(game.complete, game.id.value, gameTurns)
            return Triple(true, "", constructedGame)
        } else {
            val placeholderGame = Game(true, 0, arrayOf())
            return Triple(false, "Game ID does not exist.", placeholderGame)
        }
    }

    override fun writeTurn(turn: Turn): Triple<Boolean, String, Game> {
        val dbGame = transaction { GameEntry.findById(turn.gameId) }
        if (dbGame != null){
            transaction {
                TurnEntry.new {
                    player = turn.player
                    move = turn.move
                    game = dbGame
                    board = BoardEntry.new {
                        one = turn.board.getValue(1)
                        two = turn.board.getValue(2)
                        three = turn.board.getValue(3)
                        four = turn.board.getValue(4)
                        five = turn.board.getValue(5)
                        six = turn.board.getValue(6)
                        seven = turn.board.getValue(7)
                        eight = turn.board.getValue(8)
                        nine = turn.board.getValue(9)
                    }
                }
            }
            val (gameComplete, winner) = checkCompleteBoard(turn.board)
            if (gameComplete){
                transaction {
                    val endGame = GameEntry.findById(turn.gameId)
                    if (endGame != null) {
                        endGame.complete = true
                    }
                }
            }
            val (_, _, game) = readGame(dbGame.id.value)
            return Triple(true, "", game)
        } else {
            return Triple(false, "Error making move, please try again later.", createGame())
        }
    }

    override fun checkCompleteBoard(board: Map<Int, Int>): Pair<Boolean, Int> {
        val allWinGroups = getDiagonals(board, 3) + getRowsAndColumns(board, 3)
        var allFilled = true
        var winner = 0

        groupLoop@ for (group in allWinGroups){
            if (group.contains(0)) {
                allFilled = false
                continue@groupLoop
            }
            val matcher = group[0]
            for (space in group) if (space != matcher) continue@groupLoop
            winner = matcher
            break
        }

        if (winner != 0) return Pair(true, winner)
        if (allFilled) return Pair(true, winner)
        else return Pair(false, winner)
    }

    private fun getDiagonals(board: Map<Int, Int>, bs: Int) : Array<Array<Int>> {
        val winGroups = mutableListOf<Array<Int>>()
        var optionsIncrement = 1
        while (optionsIncrement <= 2) {
            val singularGroup = mutableListOf<Int>()
            var increment = 1
            while (increment <= bs){
                val location: Int = if (optionsIncrement == 1){
                    (increment * bs) - (bs - increment)
                } else {
                    ((increment * bs) + 1) - increment
                }
                singularGroup.add(board.getValue(location))
                increment++
            }
            winGroups.add(singularGroup.toTypedArray())
            optionsIncrement++
        }
        return winGroups.toTypedArray()
    }

    private fun getRowsAndColumns(board: Map<Int, Int>, bs: Int) : Array<Array<Int>> {
        val winGroups = mutableListOf<Array<Int>>()
        var optionsIncrement = 1
        while (optionsIncrement <= 2) {
            var externalIncrement = 1
            while (externalIncrement <= bs) {
                val singularGroup = mutableListOf<Int>()
                var internalIncrement = 1
                while (internalIncrement <= bs){
                    val location: Int = if (optionsIncrement == 1){
                        (bs - (bs - internalIncrement)) + ((externalIncrement * bs) - bs)
                    } else {
                        ((internalIncrement * bs) - (bs - 1)) + (bs - externalIncrement)
                    }
                    singularGroup.add(board.getValue(location))
                    internalIncrement++
                }
                winGroups.add(singularGroup.toTypedArray())
                externalIncrement++
            }
            optionsIncrement++
        }
        return winGroups.toTypedArray()
    }
}