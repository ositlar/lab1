package me.ositlar.application.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.config.Config
import me.ositlar.application.repo.lessonsRepo
import me.ositlar.application.repo.studentsRepo

fun Route.studentLessonRoutes() {
    route(Config.studentsPath + "personsLessons/") {
        get("{id}") {
            val id = call.parameters["id"]
            val lesson = lessonsRepo.read()
                .filter{ item -> item.elem.students.any {it.studentId == id}}
                .map { it.elem.name }
            val student = studentsRepo.read()
                .filter { it.id == id }
                .map { it.elem.fullName() }
            val personalLessons = Pair(student, lesson)
            call.respond(personalLessons)
        }
    }
}