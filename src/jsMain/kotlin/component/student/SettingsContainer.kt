package component.student

import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.Item
import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json

val containerStudentProfile = FC("groupContainer") { _: Props ->
    val paramsId = useParams()
    val studentID = paramsId["id"]
    val queryClient = useQueryClient()
    val groupListQueryKey = arrayOf(studentID).unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = groupListQueryKey,
        queryFn = {
            fetchText("${Config.updateGroupPath}${studentID}")
        }
    )

    val updateGroupMutation = useMutation<HTTPResult, Any, String, Any>(
        mutationFn = { item: String ->
            fetch(
                "${Config.updateGroupPath}${studentID}",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = item
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(groupListQueryKey)
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
                queryClient.invalidateQueries<Any>(groupListQueryKey)
            }
        }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val item =
            Json.decodeFromString<Item<Student>>(query.data ?: "")
        CStudentItem {
            student = item
            upgradeGroup = {
                updateGroupMutation.mutateAsync(it, null)
            }
            updateStudent = {
                updateStudentMutation.mutateAsync(it, null)
            }
        }
    }
}