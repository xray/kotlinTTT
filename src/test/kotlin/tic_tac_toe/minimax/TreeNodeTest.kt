package tic_tac_toe.minimax

import kotlin.test.Test

class TreeNodeTest {
    @Test fun testItCreatesANodeWithNoValue() {
        val node = TreeNode()

        assert(!node.hasValue)
    }

    @Test fun testItReturnsANodeWithAValue() {
        val node = TreeNode(5)

        assert(node.hasValue)
        assert(node.value == 5)
    }

    @Test fun testItHasZeroChildrenByDefault() {
        val node = TreeNode()

        assert(node.isEmpty())
    }

    @Test fun testItCanHaveOneChild() {
        val child = TreeNode(20)
        val parent = TreeNode(40).addChild(child)

        assert(parent.contains(child))
    }

    @Test fun testItCanHaveOneChildAndNoValue() {
        val child = TreeNode(10)
        val parent = TreeNode().addChild(child)

        assert(!parent.hasValue)
        assert(parent.contains(child))
    }

    @Test fun testItReturnsANodeWithThreeChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val child3 = TreeNode(30)

        val parent = TreeNode(60)
                .addChild(child1)
                .addChild(child2)
                .addChild(child3)

        assert(parent.contains(child1))
        assert(parent.contains(child2))
        assert(parent.contains(child3))
    }

    @Test fun testItReturnsALinkedList() {
        val child = TreeNode(10)
        val parent = TreeNode(10).addChild(child)
        val grandparent = TreeNode(20).addChild(parent)

        assert(grandparent.contains(parent))
    }

    @Test fun testContainsReturnsTrueWhenNodeIsChild() {
        val child = TreeNode(10)
        val parent = TreeNode().addChild(child)

        assert(parent.contains(child))
    }

    @Test fun testContainsReturnsFalseWhenNodeIsNotChild() {
        val unrelatedNode = TreeNode(10)
        val node = TreeNode(20)

        assert(!node.contains(unrelatedNode))
    }

    @Test fun testContainsAllReturnsTrueWhenAllNodesAreChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val child3 = TreeNode(30)

        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)
                .addChild(child3)

        assert(parent.containsAll(listOf(child1, child2, child3)))
    }

    @Test fun testContainsAllReturnsFalseWhenNotAllNodesAreChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val child3 = TreeNode(30)
        val unrelatedNode = TreeNode(45)

        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)
                .addChild(child3)

        assert(!parent.containsAll(listOf(child1, child2, child3, unrelatedNode)))
    }

    @Test fun testIsEmptyReturnsTrueWhenNodeHasNoChildren() {
        val childless = TreeNode(10)

        assert(childless.isEmpty())
    }

    @Test fun testIsEmptyReturnsFalseWhenNodeHasChildren() {
        val child = TreeNode(10)
        val parent = TreeNode().addChild(child)

        assert(!parent.isEmpty())
    }

    @Test fun testEqualsReturnsTrueWhenTheyAreTheSame() {
        val foo = TreeNode(10)
        val bar = TreeNode(10)

        assert(foo == bar)
        assert(foo.equals(bar))
    }

    @Test fun testEqualsReturnsFalseWhenTheyAreDifferent() {
        val foo = TreeNode(10)
        val bar = TreeNode(20)

        assert(foo != bar)
        assert(!foo.equals(bar))
    }

    @Test fun testEqualsReturnsFalseWhenTheyAreDifferentTypes(){
        val foo = TreeNode(10)
        val bar = 10

        assert(!foo.equals(bar))
    }

    @Test fun testToStringReturnsOnlyValueWhenNoChildren() {
        val node = TreeNode(10)
        val expectedOutput = "Node:\n" +
                             "  Value: 10\n"

        assert(node.toString() == expectedOutput)
    }

    @Test fun testToStringReturnsOnlyChildrenWhenNoValue() {
        val child = TreeNode(10)
        val parent = TreeNode().addChild(child)
        val expectedOutput = "Node:\n" +
                             "  Children (1):\n" +
                             "    #1 Child Node:\n" +
                             "      Value: 10\n"

        assert(parent.toString() == expectedOutput)
    }

    @Test fun testToStringReturnsMultipleChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)
        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)

        val expectedOutput = "Node:\n" +
                             "  Children (2):\n" +
                             "    #1 Child Node:\n" +
                             "      Value: 10\n" +
                             "    #2 Child Node:\n" +
                             "      Value: 20\n"

        assert(parent.toString() == expectedOutput)
    }

    @Test fun testItReturnsTheFirstChild() {
        val child = TreeNode(10)
        val parent = TreeNode().addChild(child)

        assert(parent[0] == child)
    }

    @Test fun testItReturnsTheNumberOfChildren() {
        val child1 = TreeNode(10)
        val child2 = TreeNode(20)

        val parent = TreeNode()
                .addChild(child1)
                .addChild(child2)

        assert(parent.size == 2)
    }
}