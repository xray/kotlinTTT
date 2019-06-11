package tic_tac_toe.minimax

import kotlin.test.Test

class MinimaxTest {
    @Test fun testItReturnsTheScoreOfANodeWithNoChildren() {
        val node = TreeNode(-15)

        assert(score(node) == -15)
    }
}