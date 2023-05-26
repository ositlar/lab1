package me.ositlar.application.type

sealed interface TypeError  {
    val error: String
    class WrongName(override val error: String) : TypeError
    class WrongLength (override val error: String) : TypeError
}