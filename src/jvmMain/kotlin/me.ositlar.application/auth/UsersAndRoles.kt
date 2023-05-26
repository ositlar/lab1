package me.ositlar.application.auth

import me.ositlar.application.repo.rolesRepo

val roleAdmin = rolesRepo.read().find { it.elem.name.roleName == "Admin" }!!.elem