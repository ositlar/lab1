package component.template

import invalidateRepoKey
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.common.Item
import me.ositlar.application.common.ItemId
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import kotlin.js.json

external interface RestContainerChildProps<E> : Props {
    var items: Array<Item<E>>
    var addElement: (E) -> Unit
    var updateItem: (Item<E>) -> Unit
    var deleteItem: (ItemId) -> Unit
}

inline fun <reified E : Any> restContainer(
    url: String,
    child: FC<RestContainerChildProps<E>>,
    queryId: String,
    displayName: String = "RestContainer"
) = FC(displayName) { _: Props ->
    val queryClient = useQueryClient()
    val myQueryKey = arrayOf(queryId).unsafeCast<QueryKey>()


    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = myQueryKey,
        queryFn = {
            fetchText(url)
        }
    )
    val addMutation = useMutation<HTTPResult, Any, E, Any>(
        mutationFn = { element: E ->
            fetch(
                url ,
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(element)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        }
    )
    val deleteMutation = useMutation<HTTPResult, Any, ItemId, Any>(
        { id: ItemId ->
            fetch(
                "$url/$id",
                jso {
                    method = "DELETE"
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        }
    )

    val updateMutation = useMutation<HTTPResult, Any, Item<E>, Any>(
        mutationFn = { item: Item<E> ->
            fetch(
                "$url/${item.id}",
                jso {
                    method = "PUT"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(item.elem)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(myQueryKey)
            }
        }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        invalidateRepoKey.Provider(myQueryKey) {
            child {
                items = Json.decodeFromString(query.data ?: "")
                addElement = {
                    addMutation.mutateAsync(it, null)
                }
                updateItem = {
                    updateMutation.mutateAsync(it, null)
                }
                deleteItem = {
                    deleteMutation.mutateAsync(it, null)
                }
            }
        }
    }
}

