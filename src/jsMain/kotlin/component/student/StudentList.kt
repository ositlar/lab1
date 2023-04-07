package component.student

import me.ositlar.application.common.Item
import me.ositlar.application.common.ItemId
import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.span
import react.router.dom.Link

external interface QueryError

external interface StudentListProps : Props {
    var students: Array<Item<Student>>
    var deleteStudent: (ItemId) -> Unit
}

val CStudentList = FC<StudentListProps>("StudentList") { props ->
    ol {
        props.students.forEach { studentItem ->
            li {
                    CStudentInList {
                        student = studentItem.elem
                    }
                span {
                    +" ✂ "
                    onClick = {
                        props.deleteStudent(studentItem.id)
                    }
                }
                span{
                    Link{
                        +" ✎️"
                        to = "${Config.updateGroupPath}${studentItem.id}"
                    }
                }
                }
            }
        }
}
//🏘