package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.auth.authorization
import me.ositlar.application.auth.roleAdmin
import me.ositlar.application.auth.roleList
import me.ositlar.application.config.Config
import me.ositlar.application.repo.studentsRepo

fun Route.studentRoutes() {
    route(Config.studentsPath) {
        repoRoutes(
            studentsRepo,
            listOf(
                ApiPoint.read to { roleList.toSet() },
                ApiPoint.write to { setOf(roleAdmin) }
            )
        )
        authenticate("auth-jwt") {
            authorization(setOf(roleAdmin)) {
                get("ByStartName/{startName}") {
                    val startName =
                        call.parameters["startName"] ?: return@get call.respondText(
                            "Missing or malformed startName",
                            status = HttpStatusCode.BadRequest
                        )
                    val students = studentsRepo.read().filter {
                        it.elem.firstname.startsWith(startName)
                    }
                    if (students.isEmpty()) {
                        call.respondText("No students found", status = HttpStatusCode.NotFound)
                    } else {
                        call.respond(students)
                    }
                }
            }
        }
    }
}