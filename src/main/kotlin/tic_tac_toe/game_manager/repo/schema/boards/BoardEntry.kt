package tic_tac_toe.game_manager.repo.schema.boards

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class BoardEntry(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BoardEntry>(Boards)

    var one by Boards.one
    var two by Boards.two
    var three by Boards.three
    var four by Boards.four
    var five by Boards.five
    var six by Boards.six
    var seven by Boards.seven
    var eight by Boards.eight
    var nine by Boards.nine
}