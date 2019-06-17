package tic_tac_toe.game_manager.repo.schema.turns

import org.jetbrains.exposed.dao.IntIdTable
import tic_tac_toe.game_manager.repo.schema.boards.Boards
import tic_tac_toe.game_manager.repo.schema.games.Games

object Turns : IntIdTable() {
    val player = integer("player")
    val move = integer("move")
    val game = reference("game", Games)
    val board = reference("board", Boards)
}
