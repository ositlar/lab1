import component.student.studentContainer
import component.student.studentSettingContainer
import me.ositlar.application.config.Config
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider
import web.dom.document


fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props> ("App"){
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            div {
                Link {
                    +"Students"
                    to = Config.studentsPath
                }
                Routes {
                    Route {
                        path = Config.studentsPath
                        element = studentContainer.create()
                    }
                    Route {
                        path = Config.studentsPath + ":name"
                        element = studentSettingContainer.create()
                    }
                }
            }
        }
    }
}