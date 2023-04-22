package me.ositlar.application.command

import kotlinx.serialization.Serializable
import me.ositlar.application.data.LessonId
import me.ositlar.application.data.StudentId

@Serializable
class AddStudentToLesson (
    val lessonId: LessonId,
    val studentId: StudentId,
    val version: Long
        ) {
    companion object{
        const val path = "addStudent"
    }
}