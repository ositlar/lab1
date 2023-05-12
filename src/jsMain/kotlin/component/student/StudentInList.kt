package component.student

import component.template.ElementInListProps
import me.ositlar.application.common.Item
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML.span
import react.router.dom.Link


val CStudentInList = FC<ElementInListProps<Item<Student>>>("StudentInList") { props ->
    span{
        Link {
            +props.element.elem.fullName()
            to = props.element.id
        }
    }
}