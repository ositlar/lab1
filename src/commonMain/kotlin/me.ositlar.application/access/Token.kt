package me.ositlar.application.access

import kotlinx.serialization.Serializable

@Serializable
class Token (
    val token : String
){
    val authHeader
        get() = "Bearer $token"
}