package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.common.Item
import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import me.ositlar.application.repo.studentsRepo


fun Route.groupRoutes() {
    route(Config.groupPath) {
        get {
            val students = studentsRepo.read()
            if (students.isEmpty()) {
                call.respondText("No students found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students.map { it.elem.group }.toSet())
            }
        }
        get("{id") {
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
            val group = call.receive<String>()
            val id = call.parameters["id"]!!
            val student = studentsRepo.read(id)
            val newStudent = Student(student!!.elem.firstname, student.elem.surname, group)
            studentsRepo.update(id, newStudent)
            call.respondText(
                "Student updates correctly",
                status = HttpStatusCode.Created
            )
        }
    }
}