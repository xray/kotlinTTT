package tic_tac_toe.game_manager.repo.schema.boards

import org.jetbrains.exposed.dao.IntIdTable

object Boards : IntIdTable() {
    val one = integer("one")
    val two = integer("two")
    val three = integer("three")
    val four = integer("four")
    val five = integer("five")
    val six = integer("six")
    val seven = integer("seven")
    val eight = integer("eight")
    val nine = integer("nine")
}