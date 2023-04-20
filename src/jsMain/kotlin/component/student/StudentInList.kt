package component.student

import component.template.ElementInListProps
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML.span
import react.router.dom.Link


val CStudentInList = FC<ElementInListProps<Student>>("StudentInList") { props ->
    span {
        Link {
            +props.item.elem.fullName()
            to = "personsLessons/"+props.item.id
        }
    }
}