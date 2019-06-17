package tic_tac_toe.game_manager

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.drop
import org.jetbrains.exposed.sql.transactions.transaction
import tic_tac_toe.game_manager.repo.PostgresRepo
import tic_tac_toe.game_manager.repo.schema.boards.Boards
import tic_tac_toe.game_manager.repo.schema.games.Games
import tic_tac_toe.game_manager.repo.schema.turns.Turns
import kotlin.test.AfterTest
import kotlin.test.Test

class ProdGameManagerTest {
    val pg = PostgresRepo("_test")

    @Test fun testNewGameReturnsANewState() {
        val expectedBoard = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)

        val gm = GameManager(pg)
        val state = gm.newGame()

        assert(state.board == expectedBoard)
        assert(state.currentPlayer == 1)
        assert(!state.gameComplete)
        assert(state.gameId == 1)
    }

    @Test fun testMakeMoveReturnsUpdatedStateWithValidMove() {
        val gm = GameManager(pg)
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 2, false, 1)

        val game = gm.newGame()
        val (success, message, state) = gm.makeMove(game.gameId, 1, 1)

        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsUpdatedStateWhenPlayerOneIsNext() {
        // Arrange
        val board = mapOf(1 to 1, 2 to 2, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(pg)

        // Act
        val game = gm.newGame()
        gm.makeMove(game.gameId, 1, 1)
        val (success, message, state) = gm.makeMove(1, 2, 2)

        // Assert
        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsSameStateIfMoveIsInvalid() {
        // Arrange
        val board = mapOf(1 to 1, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 2, false, 1)
        val gm = GameManager(pg)

        // Act
        val game = gm.newGame()
        gm.makeMove(game.gameId, 1, 1)
        val (success, message, state) = gm.makeMove(1, 1, 2)

        // Assert
        assert(!success)
        assert(message == "This position is already populated.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsSameStateIfNotPlayersTurn() {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(pg)

        val game = gm.newGame()
        val (success, message, state) = gm.makeMove(game.gameId, 1, 2)

        assert(!success)
        assert(message == "It is not Player 2's turn to play.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsBlankStateWhenReadIdDoesNotExist() {
        val board = mapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        val expectedState = State(board, 1, false, 1)
        val gm = GameManager(pg)

        val (success, message, state) = gm.makeMove(0, 7, 1)

        assert(!success)
        assert(message == "Game ID does not exist.")
        assert(state == expectedState)
    }

    @Test fun testMakeMoveReturnsACompletedGameWhenGameIsOver() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2)
        val expectedState = State(board, 2, true, 1)
        val gm = GameManager(pg)

        val game = gm.newGame()
        gm.makeMove(game.gameId, 1, 1)
        gm.makeMove(1, 8, 2)
        gm.makeMove(1, 3, 1)
        gm.makeMove(1, 2, 2)
        gm.makeMove(1, 5, 1)
        gm.makeMove(1, 9, 2)
        val (success, message, state) = gm.makeMove(1, 7, 1)

        assert(success)
        assert(message == "")
        assert(state == expectedState)
    }

    @AfterTest fun clearDB(){
        Database.connect("jdbc:postgresql://localhost:5432/tictactoe_test",
                driver = "org.postgresql.Driver",
                user = "postgres",
                password = ""
        )

        transaction {
            drop(Games, Turns, Boards)
        }
    }
}