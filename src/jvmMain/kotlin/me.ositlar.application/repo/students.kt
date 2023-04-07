package me.ositlar.application.repo

import me.ositlar.application.data.Student

val studentsRepo = ListRepo<Student>()

fun createTestData() {
    listOf(
        Student("Sheldon", "Cooper", "20x"),
        Student("Leonard", "Hofstadter", "20x"),
        Student("Howard", "Wolowitz", "20x"),
        Student("Penny", "Hofstadter", "21z"),
    ).apply {
        map {
            studentsRepo.create(it)
        }
    }
}