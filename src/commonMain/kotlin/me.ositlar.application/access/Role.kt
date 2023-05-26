package me.ositlar.application.access

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.type.RoleDescription
import me.ositlar.application.type.RoleName
import me.ositlar.application.type.TypeError

@Serializable
class Role(val name: RoleName, val users: List<User>, val description:  RoleDescription) {
    fun addUser(user: User) =
        Role(name, users + user, description)
    fun deleteUser(username: String) =
        Role(name, users.filter { it.username != username }, description)
//    fun changeDescription(newDescription: String) =
//        Role(name, users, RoleDescription(newDescription))

    companion object {
        operator fun invoke(
            name: Either<TypeError, RoleName>,
            users: List<User> = emptyList(),
            description: Either<TypeError, RoleDescription>
        ): EitherNel<TypeError, Role> = either {
            zipOrAccumulate(
                { name.bind() },
                { users },
                { description.bind() }
            ) { n, u, d -> Role(n, u, d) }
        }
    }
}

val Role.json
    get() = Json.encodeToString(this)