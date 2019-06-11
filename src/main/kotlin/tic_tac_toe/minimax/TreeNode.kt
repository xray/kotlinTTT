package tic_tac_toe.minimax

class TreeNode(val points: Int, val children: Array<TreeNode>, val hasPoints: Boolean){
    constructor(points: Int, children: Array<TreeNode>) : this(points, children, true)
    constructor(points: Int) : this(points, arrayOf<TreeNode>(), true)
    constructor(children: Array<TreeNode>) : this(0, children, false)
    constructor() : this(0, arrayOf<TreeNode>(), false)
}
