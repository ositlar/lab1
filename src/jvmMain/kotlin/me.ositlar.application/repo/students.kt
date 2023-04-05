package me.ositlar.application.repo

import me.ositlar.application.data.Student

val studentsRepo = ListRepo<Student>()

fun createTestData() {
    listOf(
        Student("Sheldon", "Cooper", "20z"),
        Student("Leonard", "Hofstadter", "20m"),
        Student("Howard", "Wolowitz", "50m"),
        Student("Penny", "Hofstadter", "21p"),
    ).apply {
        map {
            studentsRepo.create(it)
        }
    }
}