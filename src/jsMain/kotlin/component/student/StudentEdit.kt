package component.student

import component.template.EditItemProps
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import web.html.InputType

val CStudentEdit = FC<EditItemProps<Student>>("StudentEdit") { props ->
    var firstname by useState(props.item.elem.firstname)
    var surname by useState(props.item.elem.surname)
    span {
        input {
            type = InputType.text
            value = firstname
            onChange = { firstname = it.target.value }
        }
        input {
            type = InputType.text
            value = surname
            onChange = {surname = it.target.value }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(Student(firstname, surname))
        }
    }
}