package component.student

import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label

external interface StudentGroupProps : Props {
    var student: Student
}

val StudentGroup = FC <StudentGroupProps>("StudentGroup") { props ->
    div {
        label {
            +props.student.fullName()
        }
    }
}