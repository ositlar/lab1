package me.ositlar.application.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.ositlar.application.common.Item
import me.ositlar.application.repo.Repo

inline fun <reified T : Any> Route.repoRoutes(
    repo: Repo<T>
) {
    val serializer: KSerializer<T> = serializer()
    val itemSerializer: KSerializer<Item<T>> = serializer()
    val listItemSerializer = ListSerializer(itemSerializer)

    get {
        val elemList: List<Item<T>> = repo.read()
        if (elemList.isEmpty()) {
            call.respondText("No element found", status = HttpStatusCode.NotFound)
        } else {
            val elemJson = Json.encodeToString(listItemSerializer, elemList)
            call.respond(elemJson)
        }
    }
    get("{id}") {
        val id =
            call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
        val item =
            repo.read(id) ?: return@get call.respondText(
                "No element with id $id",
                status = HttpStatusCode.NotFound
            )
        val itemJson = Json.encodeToString(itemSerializer, item)
        call.respond(itemJson)
    }
    post("byId") {
        val ids = try {
            call.receive<List<String>>()
        } catch (e: Throwable) {
            return@post call.respondText(
                "Request body is not list id", status = HttpStatusCode.BadRequest
            )
        }
        val elements = Json.encodeToString(listItemSerializer, repo.read(ids))
        call.respond(elements)
    }
    post {
        val elemJson = call.receive<String>()
        val elem = Json.decodeFromString(serializer, elemJson)
        repo.create(elem)
        call.respondText(
            "Element stored correctly",
            status = HttpStatusCode.Created
        )
    }
    delete("{id}") {
        val id = call.parameters["id"]
            ?: return@delete call.respond(HttpStatusCode.BadRequest)
        if (repo.delete(id)) {
            call.respondText(
                "Element removed correctly",
                status = HttpStatusCode.Accepted
            )
        } else {
            call.respondText(
                "No element with id $id",
                status = HttpStatusCode.NotFound
            )
        }
    }
    put {
        val newItem = Json.decodeFromString(itemSerializer, call.receive())
        repo.read(newItem.id) ?: return@put call.respondText(
            "No element with id ${newItem.id}",
            status = HttpStatusCode.NotFound
        )
        if(repo.update(newItem)) {
            call.respondText(
                "Element updates correctly",
                status = HttpStatusCode.Created
            )
        } else {
            call.respondText(
                "Element had updated on server",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}