package tic_tac_toe.minimax

import kotlin.test.Test

class TreeNodeTest {
    @Test fun testItCreatesANodeWithNoPoints() {
        val node = TreeNode()
        assert(!node.hasPoints)
    }

    @Test fun testItCreatesANodeWithPoints() {
        val node = TreeNode(5)
        assert(node.hasPoints)
        assert(node.points == 5)
    }

    @Test fun testItHasZeroChildrenByDefault() {
        val node = TreeNode()
        assert(node.children.isEmpty())
    }

    @Test fun testItCanHaveOneChild() {
        val child = TreeNode(20)
        val parent = TreeNode(40, arrayOf(child))

        assert(parent.children.contentEquals(arrayOf(child)))
    }

    @Test fun testItCanHaveOneChildAndNoPoints() {
        val child = TreeNode(10)
        val parent = TreeNode(arrayOf(child))

        assert(parent.children.contentEquals(arrayOf(child)))
    }

    @Test fun testItCanHaveAParentWithThreeChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val child3 = TreeNode(30)
        val parent = TreeNode(60, arrayOf(child1, child2, child3))

        assert(parent.children.contentEquals(arrayOf(child1, child2, child3)))
    }

    @Test fun testItCanCreateALinkedList() {
        val child = TreeNode(10)
        val parent = TreeNode(20, arrayOf(child))
        val grandparent = TreeNode(30, arrayOf(parent))

        assert(grandparent.children.contentEquals(arrayOf(parent)))
        assert(parent.children.contentEquals(arrayOf(child)))
    }
}