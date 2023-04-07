import component.student.containerStudentProfile
import component.student.group.groupContainer
import component.student.studentContainer
import me.ositlar.application.config.Config
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.tr
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

val app = FC<Props> ("App") {
    QueryClientProvider {
        client = QueryClient()
        HashRouter {
            div {
                tr {
                    Link {
                        +"Students"
                        to = Config.studentsPath
                    }
                }
                tr {
                    Link {
                        +"Get students in group"
                        to = Config.studentsByGroupPath
                    }
                }

            }
            Routes {
                Route {
                    path = Config.studentsPath
                    element = studentContainer.create {}
                }
                Route {
                    path = "${Config.updateGroupPath}:id"
                    element = containerStudentProfile.create {}
                }
                Route {
                    path = Config.studentsByGroupPath
                    element = groupContainer.create {}
                }
            }
        }
    }
}