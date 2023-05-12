package auth

import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.access.Token
import me.ositlar.application.access.User
import me.ositlar.application.access.json
import me.ositlar.application.config.Config
import react.FC
import react.Props
import tools.fetch
import kotlin.js.json

external interface AuthContainerProps : Props {
    var user: User?
    var signIn: (Pair<User, Token>) -> Unit
    var signOff: () -> Unit
}

val CAuthContainer = FC<AuthContainerProps>("AuthContainer") { props ->
    val _user = props.user
    if (_user != null) {
        CAuthOut {
            user = _user
            singOff = props.signOff
        }
    } else {
        CAuthIn {
            signIn = { name: Username, pass: Password ->
                val user = User(name, pass)
                fetch(
                    Config.loginPath,
                    jso {
                        method = "POST"
                        headers = json (
                            "Content-Type" to "application/json"
                                )
                        body = user.json
                    }
                )
                    .then { it.text() }
                    .then { props.signIn(user to Json.decodeFromString<Token>(it)) }
            }
        }
    }
}