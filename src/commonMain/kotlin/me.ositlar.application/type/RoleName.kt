package me.ositlar.application.type

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class RoleName private constructor(val roleName: String) {
    companion object {
        private val rightRegister = Regex("\\p{Lu}\\p{Ll}+[A-Za-z]+")
        operator fun invoke(name: String): Either<TypeError, RoleName> = either {
            ensure(rightRegister.matches(name)) { TypeError.WrongName("Wrong Role name") }
            ensure(name.length < 6) {TypeError.WrongLength("Wrong role's length")}
            RoleName(name)
        }
    }

    override fun toString() = roleName
}