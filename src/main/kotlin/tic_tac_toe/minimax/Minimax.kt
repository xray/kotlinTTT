package tic_tac_toe.minimax

fun score(node: TreeNode) : Int {
    return maximize(node)
}

private fun maximize(node: TreeNode) : Int{
    return if (node.hasValue){
        node.value
    } else {
        var childValues = arrayOf<Int>()

        for (child in node) {
            childValues += minimize(child)
        }

        childValues.max()!!
    }
}

private fun minimize(node: TreeNode) : Int{
    return if (node.hasValue){
        node.value
    } else {
        var childValues = arrayOf<Int>()

        for (child in node) {
            childValues += maximize(child)
        }

        childValues.min()!!
    }
}