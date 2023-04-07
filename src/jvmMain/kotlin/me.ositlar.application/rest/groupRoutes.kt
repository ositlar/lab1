package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.config.Config
import me.ositlar.application.repo.studentsRepo


fun Route.groupRoutes(){
    route(Config.updateGroupPath){
        get {
            val groups = studentsRepo.read().map{it.elem.group}
            if (groups.isEmpty()) {
                call.respondText("No students found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(groups)
            }
        }
        get("{id}") {
            val id =
                call.parameters["id"] ?: return@get call.respondText(
                    "Missing or malformed id",
                    status = HttpStatusCode.BadRequest
                )
            val studentItem =
                studentsRepo.read(id) ?: return@get call.respondText(
                    "No groups with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(studentItem)
        }
        put("{id}") {
            val info = call.receive<String>()
            val id = call.parameters["id"]!!
            val myStud = studentsRepo.read(id)
            myStud!!.elem.group = info
            studentsRepo.update(id, myStud.elem)
            call.respondText(
                "Student updates correctly",
                status = HttpStatusCode.Created
            )
        }
    }
}