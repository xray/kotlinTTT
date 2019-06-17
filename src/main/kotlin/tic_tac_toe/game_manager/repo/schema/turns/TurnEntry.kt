package tic_tac_toe.game_manager.repo.schema.turns

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import tic_tac_toe.game_manager.repo.schema.boards.BoardEntry
import tic_tac_toe.game_manager.repo.schema.games.GameEntry

class TurnEntry(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TurnEntry>(Turns)

    var player by Turns.player
    var move by Turns.move
    var game by GameEntry referencedOn Turns.game
    var board by BoardEntry referencedOn Turns.board
}