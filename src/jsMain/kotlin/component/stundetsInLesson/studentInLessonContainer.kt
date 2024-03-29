package component.stundetsInLesson

import csstype.FontWeight
import emotion.react.css
import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.config.Config
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.router.useParams
import react.useContext
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import userInfoContext
import kotlin.js.json

val studentInLessonContainer = FC<Props>("SimpleStudent") {

    val id = useParams()["name"]
    val userInfo = useContext(userInfoContext)
    val studentListQueryKey = arrayOf("student").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = studentListQueryKey,
        queryFn = {
            fetchText(
                Config.studentsPath + "personsLessons/" + id,
                jso {
                    headers = json("Authorization" to userInfo?.second?.authHeader)
                }
            )
    })


    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val item = Json.decodeFromString<Pair<List<String>, List<String>>>(query.data ?: "")
        label {
            div {
                item.first.forEach { +"$it  :" }
                css {
                    fontWeight = FontWeight.bolder
                }
            }
        }
        item.second.map {
            div { li { +it } }
        }
    }
}