package component.student

import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.Item
import me.ositlar.application.common.ItemId
import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json

val studentContainer = FC("StudentsContainer") { _: Props ->
    val queryClient = useQueryClient()
    val studentListQueryKey = arrayOf("studentList").unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = studentListQueryKey,
        queryFn = {
            fetchText(Config.studentsPath)
        }
    )
    val addStudentMutation = useMutation<HTTPResult, Any, Student, Any>(
        mutationFn = { student: Student ->
            fetch(
                Config.studentsPath,
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(student)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentListQueryKey)
            }
        }
    )

    val deleteStudentMutation = useMutation<HTTPResult, Any, ItemId, Any>(
        { studentId: ItemId ->
            fetch(
                "${Config.studentsPath}$studentId",
                jso {
                    method = "DELETE"
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentListQueryKey)
            }
        }
    )

    val updateStudentMutation = useMutation<HTTPResult, Any, Item<Student>, Any>(
        mutationFn = { studentItem: Item<Student> ->
            fetch(
                "${Config.studentsPath}${studentItem.id}",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(studentItem.elem)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentListQueryKey)
            }
        }
    )

    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        val items =
            Json.decodeFromString<Array<Item<Student>>>(query.data ?: "")
        CAddStudent {
            addStudent = {
                addStudentMutation.mutateAsync(it, null)
            }
        }
        CStudentList {
            students = items
            deleteStudent = {
                deleteStudentMutation.mutateAsync(it, null)
            }
            updateStudent = {
                updateStudentMutation.mutateAsync(it, null)
            }
        }
    }
}