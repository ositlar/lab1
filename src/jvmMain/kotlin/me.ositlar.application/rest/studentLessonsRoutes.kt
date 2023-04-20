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
//        delete ("{idS}/delete/{idL}") {
//            val idS = call.parameters["idS"]!!
//            val student = studentsRepo.read(idS)
//            val idL = call.parameters["idL"]
//            val oldLesson = lessonsRepo.read()
//                .firstOrNull { item: Item<Lesson> -> item.id == idL }
//                //.firstOrNull { item -> item.elem.students.any { it.studentId == idS } }
////            val newLesson = oldLesson.map { it.elem.students.filter { gradeInfo ->
////                gradeInfo.studentId == student!!.id
////            } }
//            val new = Lesson(oldLesson!!.elem.name,
//            oldLesson.elem.students.filter { gradeInfo: GradeInfo ->
//                gradeInfo.studentId == student!!.id
//            }.toTypedArray())
//            if (lessonsRepo.update(oldLesson.id, new)) {
//                call.respondText("Lesson removed correctly",
//                status = HttpStatusCode.Accepted)
//            } else {
//                call.respondText("No lessons found", status = HttpStatusCode.NotFound)
//            }
//        }
//        post ("{idS}") {
//            val idS = call.parameters["idS"] ?: return@post call.respondText(
//                "Missing or malformed id",
//                status = HttpStatusCode.BadRequest
//            )
//            val lesson = call.receiveParameters()["lesson"] ?: return@post call.respondText(
//                "Missing or malformed lesson",
//                status = HttpStatusCode.BadRequest
//            )
//            val student = studentsRepo.read(idS)
//            val oldLesson = lessonsRepo.read()
//                .firstOrNull {item: Item<Lesson> -> item.elem.name == lesson }
//            val newLesson = Lesson(oldLesson!!.elem.name,
//            oldLesson.elem.students.filter { gradeInfo: GradeInfo ->
//                gradeInfo.studentId == student!!.id
//            }.toTypedArray())
//            lessonsRepo.update(oldLesson.id, newLesson)
//        }
    }
}