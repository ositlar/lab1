package component.student

import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.span

external interface StudentInListProps : Props {
    var student: Student
}

val CStudentInList = FC<StudentInListProps>("StudentInList") { props ->
    span {
        +props.student.fullName()
    }
}