package me.ositlar.application.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.ositlar.application.access.User
import me.ositlar.application.auth.AuthConfig.Companion.audience
import me.ositlar.application.auth.AuthConfig.Companion.issuer
import me.ositlar.application.auth.AuthConfig.Companion.secret
import me.ositlar.application.config.Config
import java.util.*

fun Route.authRoutes() {
    post(Config.loginPath) {
        val user = call.receive<User>()
        val localUser = userList.find { it.username == user.username }
        if (localUser?.password != user.password)
            return@post call.respondText("Wrong user name password", status = HttpStatusCode.Unauthorized)
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
            .sign(Algorithm.HMAC256(secret))
        call.respond(hashMapOf("token" to token))
    }
    route("hello") {
        authenticate("auth-jwt") {
            // authenticate test
            get("all") {
                val principal = call.principal<UserPrincipal>()
                call.respondText("Hello, ${principal?.user?.username}! ")
            }
            // authorize test
            authorization(setOf(roleAdmin)) {
                get("admin") {
                    call.respond("Hello, Admin")
                }
            }
        }
    }
}