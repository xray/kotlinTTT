package tic_tac_toe.game_manager.repo.schema.games

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class GameEntry(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GameEntry>(Games)

    var complete by Games.complete
}