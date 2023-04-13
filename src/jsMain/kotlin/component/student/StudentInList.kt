package component.student

import component.template.ElementInListProps
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML.span

val CStudentInList = FC<ElementInListProps<Student>>("StudentInList") { props ->
    span {
        +props.element.fullName()
    }
}