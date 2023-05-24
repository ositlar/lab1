package component.student

import component.template.EditAddProps
import me.ositlar.application.data.Student
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import react.useState
import web.html.InputType

val CStudentAdd = FC<EditAddProps<Student>>("StudentAdd") { props ->
    div {
        var firstname by useState("")
        var surname by useState("")
        label {
            +"Add student"
        }
        span {
            input {
                type = InputType.text
                value = firstname
                onChange = { firstname = it.target.value }
            }
            input {
                type = InputType.text
                value = surname
                onChange = { surname = it.target.value }
            }
        }
        button {
            +"✓"
            onClick = {
                props.saveElement(Student(firstname, surname))
            }
        }
    }
}
