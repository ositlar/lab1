package component.student.group

import component.student.QueryError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.config.Config
import react.FC
import react.Props
import react.dom.html.ReactHTML
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.fetchText

val groupContainer = FC("GroupsContainer") { _: Props ->
    val queryClient = useQueryClient()
    val groupQueryKey = arrayOf("groupList").unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = groupQueryKey,
        queryFn = {
            fetchText(Config.studentsByGroupPath)
        }
    )
    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val items =
            Json.decodeFromString<Array<String>>(query.data ?: "")
        CGroupList {
            groups = items
        }
    }
}