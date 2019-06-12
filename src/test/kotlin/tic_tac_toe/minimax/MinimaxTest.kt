package tic_tac_toe.minimax

import kotlin.test.Test

class MinimaxTest {
    @Test fun testItReturnsTheScoreOfANodeWithNoChildren() {
        val node = TreeNode(-15)

        assert(score(node) == -15)
    }

    @Test fun testItReturnsTheScoreOfANodeWithOneChild() {
        val child = TreeNode(-207)
        val parent = TreeNode()
                .addChild(child)

        assert(score(parent) == -207)
    }

    @Test fun testItReturnsTheScoreOfALinkedList() {
        val child = TreeNode(10)
        val parent = TreeNode()
                .addChild(child)
        val grandparent = TreeNode()
                .addChild(parent)

        assert(score(grandparent) == 10)
    }

    @Test fun testItReturnsTheLargestOfTwoChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)

        assert(score(parent) == 20)
    }

    @Test fun testItReturnsTheSmallestGrandchild() {
        val child1 = TreeNode(20)
        val child2 = TreeNode(10)
        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)
        val grandparent = TreeNode().addChild(parent)

        assert(score(grandparent) == 10)
    }

    @Test fun testItReturnsTheValueOfAGameWithFourMovesAndOnlyTheMinimizerGetsToMove() {
        val child1 = TreeNode(1)
        val child2 = TreeNode(0)
        val child3 = TreeNode(2)
        val child4 = TreeNode(-5)

        val parent1 = TreeNode()
                .addChild(child1)
                .addChild(child2)
        val parent2 = TreeNode()
                .addChild(child3)
                .addChild(child4)

        val grandparent1 = TreeNode().addChild(parent1)
        val grandparent2 = TreeNode().addChild(parent2)

        val greatgrandparent = TreeNode()
                .addChild(grandparent1)
                .addChild(grandparent2)

        val greatgreatgrandparent = TreeNode()
                .addChild(greatgrandparent)


        assert(score(greatgreatgrandparent) == -5)
    }
}
