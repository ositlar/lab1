package me.ositlar.application.rest

//fun Route.editGroupsRoutes() {
//    route(Config.updateGroup) {
//        put ("group") {
//            val pair = call.receive<Pair<String, String>>()
//            val id = pair.first
//            val group = pair.second
//            val oldStudent = studentsRepo.read(id) ?: return@put call.respondText(
//                "No student with id $id",
//                status = HttpStatusCode.NotFound
//            )
//            val newStudent = Student(oldStudent.elem.firstname, oldStudent.elem.surname, group)
//            studentsRepo.update(id, newStudent)
//            call.respondText(
//                "Student updates correctly",
//                status = HttpStatusCode.Created
//            )
//        }
//        put("name") {
//            val triple = call.receive<Triple<String, String, String>>()
//            val id = triple.first
//            val firstname = triple.second
//            val surname = triple.third
//            val oldStudent = studentsRepo.read(id) ?: return@put call.respondText(
//                "No student with id $id",
//                status = HttpStatusCode.NotFound
//            )
//            val newStudent = Student(firstname, surname, oldStudent.elem.group)
//            studentsRepo.update(id, newStudent)
//            call.respondText(
//                "Student updates correctly",
//                status = HttpStatusCode.Created
//            )
//        }
//    }
//}