import component.student.containerStudentProfile
import component.student.studentContainer
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
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
            Routes {
                Route {
                    path = "*"
                    element = studentContainer.create {}
                }
                Route {
                    path = "editgroup/:id"
                    element = containerStudentProfile.create {
                    }
                }
            }
        }
    }
}