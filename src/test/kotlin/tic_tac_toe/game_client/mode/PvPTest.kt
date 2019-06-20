package tic_tac_toe.game_client.mode

import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifySequence
import tic_tac_toe.game_client.mode.fixtures.TestManagerInterface
import tic_tac_toe.game_client.user_interface.fixtures.TestUserInterface
import tic_tac_toe.game_manager.State
import kotlin.test.Test

class PvPTest {
    private val mockIO = spyk<TestUserInterface>()
    private val mockMI = spyk<TestManagerInterface>()

    @Test fun testPlayCallsGetNewState() {
        val mode = PvP(mockIO, mockMI)

        mode.play()

        verify { mockMI.getNewState() }
    }

    @Test fun testPlayCallsShowBoard() {
        val mode = PvP(mockIO, mockMI)
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)

        mode.play()

        verify { mockIO.showBoard(state) }
    }

    @Test fun testPlayCallsGetMove() {
        val mode = PvP(mockIO, mockMI)
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)

        mode.play()

        verify { mockIO.getMove(state) }
    }

    @Test fun testPlayCallsMakeMove() {
        val mode = PvP(mockIO, mockMI)

        mode.play()

        verify { mockMI.makeMove(1, 7, 1) }
    }

    @Test fun testPlayWillRecurseWhenMoveIsInvalid() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val state = State(board, 1, false, 1)
        every { mockMI.getNewState() } returns state
        every { mockIO.getMove(state) } returns 1 andThen 7
        val invalidResponse = Triple(false, "This position is already populated.", state)
        every { mockMI.makeMove(1, 1, 1) } returns invalidResponse
        val updatedBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2)
        val updatedState = State(updatedBoard, 1, true, 1)
        val validResponse = Triple(true, "", updatedState)
        every { mockMI.makeMove(1, 7, 1) } returns validResponse
        val mode = PvP(mockIO, mockMI)

        mode.play()

        verifySequence {
            mockMI.getNewState()
            mockIO.showBoard(state)
            mockIO.getMove(state)
            mockMI.makeMove(1, 1, 1)
            mockIO.displayMessage("This position is already populated.")
            mockIO.displayMessage("Please try again!")
            mockIO.showBoard(state)
            mockIO.getMove(state)
            mockMI.makeMove(1, 7, 1)
            mockIO.showBoard(updatedState)
        }
    }

    @Test fun testPlayWillRecurseIfGameIsNotComplete() {
        val board = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 0)
        val initState = State(board, 2, false, 1)
        every { mockMI.getNewState() } returns initState
        every { mockIO.getMove(initState) } returns 9
        val updatedBoard = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 0, 8 to 2, 9 to 2)
        val updatedState = State(updatedBoard, 1, false, 1)
        val firstResponse = Triple(true, "", updatedState)
        every { mockMI.makeMove(1, 9, 2) } returns firstResponse
        every { mockIO.getMove(updatedState) } returns 7
        val updatedBoardTwo = mapOf(1 to 1, 2 to 2, 3 to 1, 4 to 0, 5 to 1, 6 to 0, 7 to 1, 8 to 2, 9 to 2)
        val updatedStateTwo = State(updatedBoardTwo, 1, true, 1)
        val secondResponse = Triple(true, "", updatedStateTwo)
        every { mockMI.makeMove(1, 7, 1) } returns secondResponse
        val mode = PvP(mockIO, mockMI)

        mode.play()

        verifySequence {
            mockMI.getNewState()
            mockIO.showBoard(initState)
            mockIO.getMove(initState)
            mockMI.makeMove(1, 9, 2)
            mockIO.showBoard(updatedState)
            mockIO.getMove(updatedState)
            mockMI.makeMove(1, 7, 1)
            mockIO.showBoard(updatedStateTwo)
        }
    }
}