package tic_tac_toe.game_client.user_interface

import io.mockk.*
import tic_tac_toe.game_client.mode.GameMode
import tic_tac_toe.game_client.fixtures.TestGameMode
import tic_tac_toe.game_client.user_interface.console.ConsoleUI
import tic_tac_toe.game_client.user_interface.fixtures.TestInputMethod
import tic_tac_toe.game_client.user_interface.fixtures.TestOutputMethod
import kotlin.test.Test

class ConsoleUITest {
    private val mockInput = spyk<TestInputMethod>()
    private val mockOutput = spyk<TestOutputMethod>()

    @Test fun testDisplayMessageCallsSendOnOutputMethod() {
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Test 123..."
        ui.displayMessage(message)

        verify { mockOutput.send(message) }
    }

    @Test fun testDisplayMessageSendsAMessageToTheUser() {
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Test 123..."
        val (result, sent) = ui.displayMessage(message)

        assert(result)
        assert(sent == message)
    }

    @Test fun testConfirmCallsSendOnOutputMethod() {
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Yes or No?"
        ui.confirm(message)

        val expectedInput = "$message (Y/n)"
        verify { mockOutput.send(expectedInput) }
    }

    @Test fun testConfirmCallsReceiveOnInputMethod() {
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Yes or No?"
        ui.confirm(message)

        verify { mockInput.receive() }
    }

    @Test fun testConfirmReturnsTrueWhenInputIsY() {
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Yes or No?"
        val response = ui.confirm(message)

        assert(response)
    }

    @Test fun testConfirmReturnsFalseWhenInputIsN() {
        every { mockInput.receive() } returns "N"
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Yes or No?"
        val response = ui.confirm(message)

        assert(!response)
    }

    @Test fun testConfirmWillRecurseIfInputIsInvalid() {
        every { mockInput.receive() } returns "BAD INPUT" andThen "Y"
        val ui = ConsoleUI(mockInput, mockOutput)

        val message = "Yes or No?"
        ui.confirm(message)

        verifySequence {
            mockOutput.send("$message (Y/n)")
            mockInput.receive()
            mockOutput.send("\"bad input\" is not \"Y\", \"N\" or blank...")
            mockOutput.send("Please try again!")
            mockOutput.send("$message (Y/n)")
            mockInput.receive()
        }
    }

    @Test fun testGetGameModeCallsSendOnOutputMethod() {
        every { mockInput.receive() } returns "1"
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM1 = spyk<TestGameMode>()
        val mockGM2 = spyk<TestGameMode>()

        val modes = mapOf<String, GameMode>(
                "Mode 1" to mockGM1,
                "Mode 2" to mockGM2
        )

        ui.getGameMode(modes)

        verifySequence {
            mockOutput.send("Please choose a game mode:")
            mockOutput.send("1.) Mode 1")
            mockOutput.send("2.) Mode 2")
            mockOutput.send("\nEnter a number between 1 and 2")
        }
    }

    @Test fun testGetGameModeCallsReceiveOnInputMethod() {
        every { mockInput.receive() } returns "1"
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM1 = spyk<TestGameMode>()
        val mockGM2 = spyk<TestGameMode>()

        val modes = mapOf<String, GameMode>(
                "Mode 1" to mockGM1,
                "Mode 2" to mockGM2
        )

        ui.getGameMode(modes)

        verify { mockInput.receive() }
    }

    @Test fun testGetGameModeReturnsFirstModeWhenInputIs1() {
        every { mockInput.receive() } returns "1"
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM1 = spyk<TestGameMode>()
        val mockGM2 = spyk<TestGameMode>()

        val modes = mapOf<String, GameMode>(
                "Mode 1" to mockGM1,
                "Mode 2" to mockGM2
        )

        val result = ui.getGameMode(modes)

        assert(result == mockGM1)
    }

    @Test fun testGetGameModeReturnsSecondModeWhenInputIs2() {
        every { mockInput.receive() } returns "2"
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM1 = spyk<TestGameMode>()
        val mockGM2 = spyk<TestGameMode>()

        val modes = mapOf<String, GameMode>(
                "Mode 1" to mockGM1,
                "Mode 2" to mockGM2
        )

        val result = ui.getGameMode(modes)

        assert(result == mockGM2)
    }

    @Test fun testGetGameModeWillRecurseWhenInputIsNotValid() {
        every { mockInput.receive() } returns "BAD INPUT" andThen "1"
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM1 = spyk<TestGameMode>()
        val mockGM2 = spyk<TestGameMode>()

        val modes = mapOf<String, GameMode>(
                "Mode 1" to mockGM1,
                "Mode 2" to mockGM2
        )

        val result = ui.getGameMode(modes)

        verifySequence {
            mockOutput.send("Please choose a game mode:")
            mockOutput.send("1.) Mode 1")
            mockOutput.send("2.) Mode 2")
            mockOutput.send("\nEnter a number between 1 and 2")
            mockInput.receive()
            mockOutput.send("\"bad input\" is not a number between 1 and 2...")
            mockOutput.send("Please try again!")
            mockOutput.send("Please choose a game mode:")
            mockOutput.send("1.) Mode 1")
            mockOutput.send("2.) Mode 2")
            mockOutput.send("\nEnter a number between 1 and 2")
            mockInput.receive()
        }
        assert(result == mockGM1)
    }

    @Test fun testGetGameModeReturnsOnlyModeIfGivenOne() {
        val ui = ConsoleUI(mockInput, mockOutput)
        val mockGM = spyk<TestGameMode>()
        val mode = mapOf<String, GameMode>("Mode" to mockGM)

        val result = ui.getGameMode(mode)

        verify { mockInput wasNot Called }
        verify { mockOutput wasNot Called }
        assert(result == mockGM)
    }
}