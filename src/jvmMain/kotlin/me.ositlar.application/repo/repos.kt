package me.ositlar.application.repo

import me.ositlar.application.access.Role
import me.ositlar.application.access.userAdmin
import me.ositlar.application.access.userTutor
import me.ositlar.application.common.Item
import me.ositlar.application.data.Grade
import me.ositlar.application.data.GradeInfo
import me.ositlar.application.data.Lesson
import me.ositlar.application.data.Student
import me.ositlar.application.type.*

val studentsRepo = ListRepo<Student>()
val lessonsRepo = ListRepo<Lesson>()
val rolesRepo = ListRepo<Role>()

fun createTestData() {

    listOf(
        "Admin" to listOf(userAdmin),
        "User" to listOf(userAdmin, userTutor)
    ).apply {
        map {
            Role.invoke(
                RoleName(it.first), it.second, RoleDescription("")
            ).getOrNull()?.let { role ->
                rolesRepo.create(role)
            }
        }
    }

    listOf(
        "Sheldon" to "Cooper",
        "Leonard" to "Hofstadter",
        "Howard" to "Wolowitz",
        "Penny" to "Hofstadter"
    ).apply {
        map {
            Student.invoke(
                Firstname(it.first),
                Surname(it.second)
            ).getOrNull()?.let {
                studentsRepo.create(it)
            }
        }
    }

    listOf(
        ("Math"),
        ("Phys"),
        ("Story"),
    ).apply {
        map {
            Lesson.invoke(
                LessonName(it)
            ).getOrNull()?.let { lesson ->
                lessonsRepo.create(lesson)
            }
        }
    }

    val students = studentsRepo.read()
    val lessons = lessonsRepo.read()
    val sheldon = students.findLast { it.elem.firstname.name == "Sheldon" }
    check(sheldon != null)
    val leonard = students.findLast { it.elem.firstname.name == "Leonard" }
    check(leonard != null)
    val math = lessons.findLast { it.elem.name.lessonName =="Math" }
    check(math != null)
    val newMath = Lesson(
        math.elem.name,
        arrayOf(
            GradeInfo(sheldon.id, Grade.A),
            GradeInfo(leonard.id, Grade.B)
        ))
    lessonsRepo.update(Item(newMath, math.id, math.version))
}

