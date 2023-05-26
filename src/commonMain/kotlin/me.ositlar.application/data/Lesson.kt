package me.ositlar.application.data

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.ItemId
import me.ositlar.application.type.LessonName
import me.ositlar.application.type.TypeError

@Serializable
class Lesson(
    val name: LessonName,
    val students: Array<GradeInfo> = emptyArray()
) {
    fun addStudent(studentId: StudentId) =
        Lesson(
            name,
            students + GradeInfo (studentId, null)
        )
    companion object {
        operator fun invoke(
            name: Either<TypeError, LessonName>,
            students: Array<GradeInfo> = emptyArray(),
        ): EitherNel<TypeError, Lesson> = either {
            zipOrAccumulate(
                { name.bind() },
                { students },
                {}
            ) { n, s, _ -> Lesson(n, s) }
        }
    }
}

@Serializable
class GradeInfo(
    val studentId: StudentId,
    val grade: Grade?
)

typealias LessonId = ItemId

val Lesson.json
    get() = Json.encodeToString(this)

