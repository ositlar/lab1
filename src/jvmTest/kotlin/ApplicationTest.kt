import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.ositlar.application.main
import me.ositlar.application.repo.studentsRepo

class ApplicationTest : StringSpec({
    "Students routes" {
        testApplication {
            application {
                main()
            }
            withClue("get1") {
                val group = "20m"
                Json.decodeFromString<Array<String>>(
                    client.put("groups/") {
                        contentType(ContentType.Application.Json)
                        setBody(group)
                    }.bodyAsText()
                ).map { it shouldBe "Leonard Hofstadter" }
            }

            val students = studentsRepo.read().filter { it.elem.group == "20x" }
            withClue("get2") {
                val group = "20x"
                val respondStudents = Json.decodeFromString<Array<String>>(
                    client.put("groups/") {
                        contentType(ContentType.Application.Json)
                        setBody(group)
                    }.bodyAsText()
                )
                respondStudents[0] shouldBe students[0].elem.fullName()
                respondStudents[1] shouldBe students[1].elem.fullName()
            }
        }
    }
})

            /**
            val students = withClue("read") {
                val response = client.get("/students/")
                response.status shouldBe HttpStatusCode.OK
                Json.decodeFromString<List<Item<Student>>>(response.bodyAsText()).apply {
                    size shouldBe 4
                }
            }
            withClue("read id") {
                val sheldon = students.first { it.elem.firstname == "Sheldon" }
                val response = client.get("/students/${sheldon.id}")
                response.status shouldBe HttpStatusCode.OK
                Json.decodeFromString<Item<Student>>(response.bodyAsText()).apply {
                    elem.firstname shouldBe "Sheldon"
                }
            }
            val newStudents = withClue("create") {
                val response = client.post("/students/") {
                    contentType(ContentType.Application.Json)
                    setBody(Student("Raj", "Koothrappali", "28i").json)
                }
                response.status shouldBe HttpStatusCode.Created
                Json.decodeFromString<List<Item<Student>>>(
                    client.get("/students/").bodyAsText()
                ).apply {
                    size shouldBe 5
                }
            }
            val emi = withClue("update") {
                val raj = newStudents.first { it.elem.firstname == "Raj" }
                client.put("/students/${raj.id}") {
                    contentType(ContentType.Application.Json)
                    setBody(Student("Amy", "Fowler", "62l").json)
                }
                Json.decodeFromString<Item<Student>>(
                    client.get("/students/${raj.id}").bodyAsText()
                ).apply {
                    elem.firstname shouldBe "Amy"
                }
            }
            withClue("delete"){
                client.delete("/students/${emi.id}")
                Json.decodeFromString<List<Item<Student>>>(
                    client.get("/students/").bodyAsText()
                ).apply {
                    size shouldBe 4
                }
            }

        }
    }
})
             **/