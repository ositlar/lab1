package me.ositlar.application.auth

import io.ktor.server.auth.*
import me.ositlar.application.access.User

class UserPrincipal(val user: User): Principal