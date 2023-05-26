package component.lesson

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditItemProps
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.Item
import me.ositlar.application.config.Config
import me.ositlar.application.data.Grade
import me.ositlar.application.data.GradeInfo
import me.ositlar.application.data.Lesson
import me.ositlar.application.data.Student
import me.ositlar.application.type.LessonName
import me.ositlar.application.type.TypeError
import query.QueryError
import react.*
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import userInfoContext
import web.html.HTMLLabelElement
import web.html.InputType
import kotlin.js.json

val CLessonEditContainer = FC<EditItemProps<Lesson>>("LessonEditContainer") { props ->
    val sk = props.item.elem.students.joinToString(separator = "") { "s" }
    val myQueryKey = arrayOf("LessonEditContainer", sk).unsafeCast<QueryKey>()
    val userInfo = useContext(userInfoContext)
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = myQueryKey,
        queryFn = {
            fetchText(
                "${Config.studentsPath}byId",
                jso {
                    method = "POST"
                    headers = json(
                        "Content-Type" to "application/json",
                        "Authorization" to userInfo?.second?.authHeader
                    )
                    body = Json.encodeToString(props.item.elem.students.map { it.studentId })
                }
            )
        }
    )
    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val studentItems =
            Json.decodeFromString<Array<Item<Student>>>(query.data ?: "")
                .associateBy { it.id }
        val studentGrades = props.item.elem.students.mapNotNull {pair ->
            studentItems[pair.studentId]?.let {
                GradeInfoFull(it, pair.grade)
            }
        }.toTypedArray()
        CLessonEdit {
            item = props.item
            students = studentGrades
            saveElement = props.saveElement
        }
    }
}

class GradeInfoFull(
    val itemStudent: Item<Student>,
    val grade: Grade?
){
    fun newGrade(grade: Grade?) =
        GradeInfoFull(itemStudent, grade)
}

external interface LessonEditProps : Props {
    var item: Item<Lesson>
    var students: Array<GradeInfoFull>
    var saveElement: (Lesson) -> Unit
}

val CLessonEdit = FC<LessonEditProps>("LessonEdit") { props ->
    var name by useState(props.item.elem.name.lessonName)
    val userInfo = useContext(userInfoContext)
    val labelRef = useRef<HTMLLabelElement>()
    div {
        label {
            +"Change lesson's name"
        }
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        label {
            ref = labelRef
        }
        button {
            +"Change Name"
            onClick = {
                val lesson: Either<NonEmptyList<TypeError>, Lesson> = Lesson(LessonName(name), props.item.elem.students)
                labelRef.current?.textContent = when (lesson) {
                    is Either.Left -> lesson.value.joinToString { it.error }
                    is Either.Right -> {
                        props.saveElement(lesson.value)
                        ""
                    }
                }
            }
        }
    }
    CAddStudentToLesson {
        lesson = props.item
    }
    CDeleteStudent{
        lesson = props.item
    }
    CGradeEdit {
        students = props.students
        changeStudents = {
            props.saveElement(
                Lesson(props.item.elem.name, it.map { it ->
                    GradeInfo(it.itemStudent.id, it.grade)
                }.toTypedArray())
            )
        }
    }
}

