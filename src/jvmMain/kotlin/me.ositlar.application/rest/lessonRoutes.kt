package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import me.ositlar.application.auth.authorization
import me.ositlar.application.auth.roleAdmin
import me.ositlar.application.command.AddStudentToLesson
import me.ositlar.application.command.DeleteStudentFromLesson
import me.ositlar.application.common.Item
import me.ositlar.application.config.Config
import me.ositlar.application.data.Lesson
import me.ositlar.application.repo.lessonsRepo
import me.ositlar.application.repo.rolesRepo
import me.ositlar.application.repo.studentsRepo

fun Route.lessonRoutes() {
    route(Config.lessonsPath) {
        repoRoutes(
            lessonsRepo,
            listOf(
                ApiPoint.read to { rolesRepo.read().map { it.elem }.toSet() },
                ApiPoint.write to { setOf(roleAdmin) }
            )
        )
        authenticate("auth-jwt") {
            authorization(setOf(roleAdmin)) {
                post(AddStudentToLesson.path) {
                    val command = Json.decodeFromString(AddStudentToLesson.serializer(), call.receive())
                    val lesson = lessonsRepo.read(listOf(command.lessonId)).getOrNull(0)
                        ?: return@post call.respondText(
                            "No lesson with id ${command.lessonId}",
                            status = HttpStatusCode.NotFound
                        )
                    studentsRepo.read(listOf(command.studentId)).getOrNull(0)
                        ?: return@post call.respondText(
                            "No student with id ${command.lessonId}",
                            status = HttpStatusCode.NotFound
                        )
                    if (lesson.elem.students.find { it.studentId == command.lessonId } != null)
                        return@post call.respondText(
                            "Student already in lesson",
                            status = HttpStatusCode.BadRequest
                        )
                    if (command.version != lesson.version) {
                        call.respondText(
                            "Lesson had updated on server",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                    val newLesson = lesson.elem.addStudent(command.studentId)
                    if (lessonsRepo.update(Item(newLesson, command.lessonId, command.version))) {
                        call.respondText(
                            "Student added correctly",
                            status = HttpStatusCode.OK
                        )
                    } else {
                        call.respondText(
                            "Update error",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                }
                put (DeleteStudentFromLesson.path) {
                    val command = Json.decodeFromString(DeleteStudentFromLesson.serializer(), call.receive())
                    val lesson = lessonsRepo.read(listOf(command.lessonId)).getOrNull(0)
                        ?: return@put call.respondText(
                            "No lesson with id ${command.lessonId}",
                            status = HttpStatusCode.NotFound
                        )
                    if (command.version != lesson.version){
                        call.respondText(
                            "Lesson had updated on server",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                    studentsRepo.read(listOf(command.studentId)).getOrNull(0)
                        ?: return@put call.respondText(
                            "No student with id ${command.lessonId}",
                            status = HttpStatusCode.NotFound
                        )
                    if (lesson.elem.students.find { it.studentId == command.studentId } == null)
                        return@put call.respondText(
                            "Student is already out of lesson",
                            status = HttpStatusCode.BadRequest
                        )
                    val newLesson = Lesson(
                        lesson.elem.name,
                        (lesson.elem.students.toList() - lesson.elem.students.first {
                            it.studentId == command.studentId
                        }).toTypedArray()
                    )
                    if (lessonsRepo.update(Item(newLesson, command.lessonId, command.version))) {
                        call.respondText(
                            "Student deleted correctly",
                            status = HttpStatusCode.OK
                        )
                    } else {
                        call.respondText(
                            "Update error",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                }
            }
        }
    }
}