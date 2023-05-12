package component.stundetsInLesson

import component.template.ElementInListProps
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML

val CStudentInLesson = FC<ElementInListProps<Student>>("StudentInList") { props ->
    ReactHTML.span {
        +props.element.fullName()
    }
}