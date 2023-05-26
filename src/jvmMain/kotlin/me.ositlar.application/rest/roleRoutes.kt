package me.ositlar.application.rest

import io.ktor.server.routing.*
import me.ositlar.application.auth.roleAdmin
import me.ositlar.application.config.Config
import me.ositlar.application.repo.rolesRepo

fun Route.roleRoutes() {
    route(Config.rolesPath) {
        repoRoutes(
            rolesRepo,
            listOf(
                ApiPoint.read to { rolesRepo.read().map { it.elem }.toSet() },
                ApiPoint.write to { setOf(roleAdmin) }
            )
        )
    }
}
