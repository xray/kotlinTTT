package tic_tac_toe.game_client

import io.mockk.*
import tic_tac_toe.game_manager.State
import tic_tac_toe.game_client.fixtures.TestGameMode
import tic_tac_toe.game_client.user_interface.fixtures.TestUserInterface
import kotlin.test.Test

class GameClientTest {
    private val mockIO = spyk<TestUserInterface>()
    private val mockGameMode = spyk<TestGameMode>()
    private val gameModes = mapOf("Test" to mockGameMode)

    @Test fun testStartCallsGetGameMode(){
        val game = GameClient(mockIO)
        val gameModes = mapOf("Test" to TestGameMode())

        game.start(gameModes)

        verify { mockIO.getGameMode(gameModes) }
    }

    @Test fun testStartCallsPlayOnSelectedGameMode() {
        val game = GameClient(mockIO)

        game.start(gameModes)

        verify { mockGameMode.play() }
    }

    @Test fun testStartCallDisplayMessage() {
        val game = GameClient(mockIO)

        game.start(gameModes)

        verify { mockIO.displayMessage("Congrats Player 1, you win!") }
    }

    @Test fun testStartCallsConfirm() {
        val game = GameClient(mockIO)

        game.start(gameModes)

        verify { mockIO.confirm("Would you like to play again?") }
    }

    @Test fun testStartWillDisplayDifferentMessageWhenGameEndsInTie() {
        val mockGameMode = spyk<TestGameMode>()
        val tieState = State(
                mapOf(1 to 2, 2 to 1, 3 to 2, 4 to 1, 5 to 2, 6 to 1, 7 to 1, 8 to 2, 9 to 1),
                0,
                true,
                1
        )
        every {mockGameMode.play()} returns tieState
        val gameModes = mapOf("Test" to mockGameMode)
        val game = GameClient(mockIO)

        game.start(gameModes)

        verify { mockIO.displayMessage("The game ended in a draw!") }
    }

    @Test fun testStartWillRecurseIfUserChoosesToPlayAgain() {
        val confirmMessage = "Would you like to play again?"
        every { mockIO.confirm(confirmMessage) } returns true andThen false
        val game = GameClient(mockIO)
        val state1 = State(
                mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2),
                1,
                true,
                1
        )
        val state2 = State(
                mapOf(1 to 2, 2 to 1, 3 to 2, 4 to 0, 5 to 2, 6 to 0, 7 to 2, 8 to 1, 9 to 1),
                2,
                true,
                1
        )
        every { mockGameMode.play() } returns state1 andThen state2
        val gameModes = mapOf("Test" to mockGameMode)

        game.start(gameModes)

        verifyOrder {
            mockIO.displayMessage("Congrats Player 1, you win!")
            mockIO.displayMessage("Congrats Player 2, you win!")
        }
    }
}