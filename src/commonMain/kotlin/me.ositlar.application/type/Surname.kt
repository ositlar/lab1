package me.ositlar.application.type

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Surname private constructor(val name: String) {
    companion object {
        private val rightRegister = Regex("\\p{Lu}\\p{Ll}+")
        operator fun invoke(name: String): Either<TypeError, Surname> = either {
            ensure(rightRegister.matches(name)) { TypeError.WrongName("Wrong Surname") }
            Surname(name)
        }
    }
    override fun toString() = name
}