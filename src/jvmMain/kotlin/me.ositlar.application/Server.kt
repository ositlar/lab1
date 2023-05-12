package me.ositlar.application

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import me.ositlar.application.auth.AuthException
import me.ositlar.application.auth.authRoutes
import me.ositlar.application.repo.createTestData
import me.ositlar.application.rest.lessonRoutes
import me.ositlar.application.rest.studentLessonRoutes
import me.ositlar.application.rest.studentRoutes

fun Application.main(isTest: Boolean = true) {
    config(isTest)
    static()
    rest()
    if (isTest) logRoute()
}

fun Application.config(isTest: Boolean) {
    install(StatusPages) {
        exception<AuthException> { call, cause ->
            cause.handler(call)
        }
    }
    install(ContentNegotiation) {
        json()
    }
    if (isTest) {
        createTestData()
        install(createApplicationPlugin("DelayEmulator") {
            onCall {
                delay(1000L)
            }
        })
    }
}

fun Application.rest() {
    routing {
        authRoutes()
        studentRoutes()
        lessonRoutes()
        studentLessonRoutes()
    }
}

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "127.0.0.1",
        watchPaths = listOf("classes")
    ) {
        main()
    }.start(wait = true)
}