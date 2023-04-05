package component.student

import me.ositlar.application.data.Student
import me.ositlar.application.common.*
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.span
import react.useState

external interface QueryError

external interface StudentListProps : Props {
    var students: Array<Item<Student>>
    var deleteStudent: (ItemId) -> Unit
    var updateStudent: (Item<Student>) -> Unit
}

val CStudentList = FC<StudentListProps>("StudentList") { props ->
    var editedId by useState<String>("")
    ol {
        props.students.forEach { studentItem ->
            li {
                if (studentItem.id == editedId)
                    CEditStudent {
                        oldStudent = studentItem.elem
                        saveStudent = {
                            props.updateStudent(Item(it, studentItem.id))
                            editedId = ""
                        }
                    }
                else {
                    //Link {
                        CStudentInList {
                            student = studentItem
                        }
                        //to = Config.studentsPath + studentItem.id + "/group"
                    //}
                }

                    span {
                        +" ✂ "
                        onClick = {
                            props.deleteStudent(studentItem.id)
                        }
                    }
                    span {
                        +" ✎ "
                        onClick = {
                            editedId = studentItem.id
                        }
                    }
                }
            }
        }
}