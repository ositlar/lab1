package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import me.ositlar.application.repo.studentsRepo

fun Route.studentRoutes() {
    route(Config.studentsPath) {
        get {
            val students = studentsRepo.read()
            if (students.isEmpty()) {
                call.respondText("No students found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students)
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
                    "No student with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(studentItem)
        }
        post {
            val student = call.receive<Student>()
            studentsRepo.create(student)
            call.respondText(
                "Student stored correctly",
                status = HttpStatusCode.Created
            )
        }
        delete("{id}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (studentsRepo.delete(id)) {
                call.respondText(
                    "Student removed correctly",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText(
                    "Not Found",
                    status = HttpStatusCode.NotFound
                )
            }
        }
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            studentsRepo.read(id) ?: return@put call.respondText(
                "No student with id $id",
                status = HttpStatusCode.NotFound
            )
            val newStudent = call.receive<Student>()
            studentsRepo.update(id, newStudent)
            call.respondText(
                "Student updates correctly",
                status = HttpStatusCode.Created
            )
        }
    }
}

fun Route.studentsByGroup(){
    route(Config.studentsByGroupPath){
        get("{group}") {
            val id =
                call.parameters["group"] ?: return@get call.respondText(
                    "Missing or malformed group",
                    status = HttpStatusCode.BadRequest
                )
            val students = studentsRepo.read().map{
                if(it.elem.group==id)
                    it.elem.fullName()
                else null
            }.filterNotNull()
            if (students.isEmpty()) {
                call.respondText("No students with this group found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students)
            }
        }
        get{
            val receiveGroup =
                call.parameters["group"] ?: return@get call.respondText(
                    "Missing or malformed param group",
                    status = HttpStatusCode.BadRequest
                )
            val students = studentsRepo.read().filter{ it.elem.group == receiveGroup }
            if (students.isEmpty()) {
                call.respondText("No students and groups found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students)
            }
        }
        put {
            val receiveGroup = call.receive<String>()
            val students = studentsRepo.read()
            val studentsInGroup = students.filter{ it.elem.group == receiveGroup }
            if (studentsInGroup.isEmpty()) {
                call.respondText("No groups found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(studentsInGroup.map{it.elem.fullName()})
            }
        }
    }
}