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
import react.dom.html.ReactHTML.div
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json

val studentSettingContainer = FC("StudentSettingContainer") { _: Props ->
    val id = useParams()["name"]
    val queryClient = useQueryClient()
    val studentQueryKey = arrayOf("student").unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = studentQueryKey,
        queryFn = {
            fetchText(Config.studentsPath + id)
        }
    )
    val updateStudentGroupMutation = useMutation<HTTPResult, Any, Pair<String, String>, Any>(
        mutationFn = { pair: Pair<String, String> ->
            fetch(
                Config.updateGroup + "group",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(pair)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentQueryKey)
            }
        }
    )
    val updateStudentMutation = useMutation<HTTPResult, Any, Triple<String, String, String>, Any>(
        mutationFn = { triple: Triple<String, String, String> ->
            fetch(
                Config.updateGroup + "name",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(triple)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentQueryKey)
            }
        }
    )
    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        val item =
            Json.decodeFromString<Item<Student>>(query.data ?: "")
        CStudentSetting {
            student = item
            updateStudent = {
                updateStudentMutation.mutateAsync(it, null)
            }
            updateStudentGroup = {
                updateStudentGroupMutation.mutateAsync(it, null)
            }
        }
    }
}