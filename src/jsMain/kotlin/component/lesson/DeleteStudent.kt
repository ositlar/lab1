package component.lesson

import csstype.Color
import emotion.react.css
import invalidateRepoKey
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.Item
import me.ositlar.application.config.Config
import me.ositlar.application.data.Lesson
import me.ositlar.application.data.Student
import me.ositlar.application.data.StudentId
import query.QueryError
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import web.html.HTMLInputElement
import web.html.HTMLSelectElement


external interface SelectDeleteStudentProps : Props {
    var startName: String
    var onPick: (StudentId) -> Unit
}

val CSelectDeleteStudent = FC<SelectDeleteStudentProps>("DeleteStudent") { props ->
    val selectDeleteQueryKey = arrayOf("SelectStudent", props.startName).unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = selectDeleteQueryKey,
        queryFn = {
            fetchText("${Config.studentsPath}ByStartName/${props.startName}")
        }
    )
    val selectRef = useRef<HTMLSelectElement>()
    val students: List<Item<Student>> =
        try {
            Json.decodeFromString(query.data ?: "")
        } catch (e: Throwable) {
            emptyList()
        }
    select {
        ref = selectRef
        students.map {
            option {
                +it.elem.fullName()
                value = it.id
            }
        }
    }
    button {
        css {
            color = Color("Red")
        }
        +"Delete"
        onClick = {
            selectRef.current?.value?.let {
                props.onPick(it)
            }
        }
    }
}

external interface DeleteStudentProps : Props {
    var lesson: Item<Lesson>
}

val CDeleteStudent = FC<DeleteStudentProps>("DeleteStudent") { props ->
    val queryClient = useQueryClient()
    val invalidateRepoKey = useContext(invalidateRepoKey)
    var input by useState("")
    val inputRef = useRef<HTMLInputElement>()

    val deleteMutation = useMutation<HTTPResult, Any, StudentId, Any>(
        mutationFn = { studentId ->
            fetch(
                "${Config.lessonsPath}/${props.lesson.id}/delete/$studentId"
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(invalidateRepoKey)
            }
        }
    )
    div {
        input {
            ref = inputRef
            list = "StudentHint"
            onChange = { input = it.target.value }
        }
        CSelectDeleteStudent {
            startName = input
            onPick = {
                deleteMutation.mutateAsync(it, null)
            }
        }
    }
}
