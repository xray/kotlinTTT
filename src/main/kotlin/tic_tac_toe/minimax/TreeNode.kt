package tic_tac_toe.minimax

class TreeNode : Collection<TreeNode> {
    val hasValue: Boolean
    val value: Int

    private val children: List<TreeNode>

    constructor(){
        children = listOf()
        hasValue = false
        value = 0
    }

    constructor(nodeValue: Int){
        children = listOf()
        hasValue = true
        value = nodeValue
    }

    private constructor(newChildren: List<TreeNode>, newHasValue: Boolean, newValue: Int){
        children = newChildren
        hasValue = newHasValue
        value = newValue
    }

    fun addChild(newChild: TreeNode) : TreeNode{
        return if (children.isNotEmpty()) {
            val newChildren = children.toMutableList()
            newChildren.add(newChild)

            TreeNode(newChildren, hasValue, value)
        } else {
            TreeNode(listOf(newChild), hasValue, value)
        }

    }

    override fun contains(element: TreeNode): Boolean {
        for (child in children){
            if (child == element){
                return true
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<TreeNode>): Boolean {
        var doesContainAll = true

        for(newTreeNode in elements){
            if(!this.contains(newTreeNode)){
                doesContainAll = false
            }
        }

        return doesContainAll
    }

    override fun isEmpty(): Boolean {
        return children.isEmpty()
    }

    override fun iterator(): Iterator<TreeNode> {
        return children.iterator()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is TreeNode) false
        else (children == other.children && hasValue == other.hasValue && value == other.value)
    }

    override fun toString(): String {
        var finalString = "Node:\n"
        var childCount = 1

        if (hasValue){
            finalString += "  Value: $value\n"
        }

        if (size > 0){
            finalString += "  Children ($size):\n"

            for (child in children){
                val childLines = child.toString().split('\n')
                finalString += "    #$childCount Child ${childLines[0]}\n"

                for ((lineNumber, line) in childLines.withIndex()){
                    if (lineNumber != 0 && lineNumber != childLines.size - 1) {
                        finalString += "    $line\n"
                    }
                }
                childCount++
            }
        }

        return finalString
    }

    override fun hashCode(): Int {
        var result = hasValue.hashCode()
        result = 31 * result + value
        result = 31 * result + children.hashCode()
        return result
    }

    operator fun get(i: Int): TreeNode {
        return children[i]
    }

    override val size: Int
        get() = children.size
}