package me.ositlar.application.auth

import me.ositlar.application.access.Role
import me.ositlar.application.access.User

val userAdmin = User("admin","admin")
val userTutor = User("tutor", "tutor")
val userList = listOf(userAdmin, userTutor)

val roleAdmin = Role("admin")
val roleUser = Role("user")
val roleList = listOf(roleAdmin, roleUser)

val userRoles = mapOf(
    userAdmin to setOf(roleAdmin, roleUser),
    userTutor to setOf(roleUser)
)