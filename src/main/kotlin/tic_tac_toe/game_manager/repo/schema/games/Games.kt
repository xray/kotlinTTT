package tic_tac_toe.game_manager.repo.schema.games

import org.jetbrains.exposed.dao.IntIdTable

object Games : IntIdTable() {
    val complete = bool("complete")
}