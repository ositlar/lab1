package component.stundetsInLesson

import csstype.FontWeight
import emotion.react.css
import js.core.get
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.config.Config
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.router.useParams
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

val studentInLessonContainer = FC<Props>("SimpleStudent") {

    val id = useParams()["name"]
    val studentListQueryKey = arrayOf("student").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(queryKey = studentListQueryKey, queryFn = {
        fetchText(Config.studentsPath + "inLesson/" + id)
    })

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val item = Json.decodeFromString<Pair<List<String>, List<String>>>(query.data ?: "")
        label {
            div {
                item.second.forEach { +"$it  :" }
                css {
                    fontWeight = FontWeight.bolder
                }
            }
        }
        item.first.map {
            div { label { +it } }
        }
    }
}