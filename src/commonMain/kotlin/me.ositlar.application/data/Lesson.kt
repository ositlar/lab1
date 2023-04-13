package me.ositlar.application.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.ItemId

@Serializable
class Lesson(
    val name: String,
    val students: Array<GradeInfo> = emptyArray()
) {
    fun addStudent(studentId: StudentId) =
        Lesson(
            name,
            students + GradeInfo (studentId, null)
        )
}

@Serializable
class GradeInfo(
    val studentId: StudentId,
    val grade: Grade?
)

typealias LessonId = ItemId

val Lesson.json
    get() = Json.encodeToString(this)

