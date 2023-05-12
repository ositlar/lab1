package me.ositlar.application.auth

import me.ositlar.application.access.Role
import me.ositlar.application.access.userAdmin
import me.ositlar.application.access.userTutor

val roleAdmin = Role("admin", listOf(userAdmin))
val roleUser = Role("user", listOf(userAdmin, userTutor))
val roleList = listOf(roleAdmin, roleUser)