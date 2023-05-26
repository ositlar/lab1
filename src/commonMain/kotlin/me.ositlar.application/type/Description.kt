package me.ositlar.application.type

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class RoleDescription private constructor(val roleDescription: String) {
    companion object {
        operator fun invoke(description: String): Either<TypeError, RoleDescription> = either {
            ensure(description.length < 21) {TypeError.WrongLength("Wrong role's description length")}
            RoleDescription(description)
        }
    }

    override fun toString() = roleDescription
}