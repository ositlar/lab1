package me.ositlar.application.type

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Firstname private constructor(val name: String) {
    companion object {
        private val rightRegister = Regex("\\p{Lu}\\p{Ll}+")
        operator fun invoke(name: String): Either<TypeError, Firstname> = either {
            ensure(rightRegister.matches(name)) { TypeError.WrongName("Wrong Firstname") }
            Firstname(name)
        }
    }
    override fun toString() = name
}