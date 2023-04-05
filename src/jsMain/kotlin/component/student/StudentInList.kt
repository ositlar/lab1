package component.student

import me.ositlar.application.common.Item
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.router.dom.Link

external interface StudentInListProps : Props {
    var student: Item<Student>
}

val CStudentInList = FC<StudentInListProps>("StudentInList") { props ->
    Link {
        +props.student.elem.fullName()
        to = props.student.id
    }
}