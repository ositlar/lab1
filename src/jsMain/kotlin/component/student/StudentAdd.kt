package component.student

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditAddProps
import me.ositlar.application.data.Student
import me.ositlar.application.type.Firstname
import me.ositlar.application.type.Surname
import me.ositlar.application.type.TypeError
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import react.useRef
import react.useState
import web.html.HTMLLabelElement
import web.html.InputType

val CStudentAdd = FC<EditAddProps<Student>>("StudentAdd") { props ->
    div {
        var firstname by useState("")
        var surname by useState("")
        val labelRef = useRef<HTMLLabelElement>()
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
            label {
                ref = labelRef
            }
        }
        button {
            +"✓"
            onClick = {
                val student: Either<NonEmptyList<TypeError>, Student> = Student(Firstname(firstname), Surname(surname))
                labelRef.current?.textContent = when (student) {
                    is Either.Left -> student.value.joinToString { it.error }
                    is Either.Right -> {
                        props.saveElement(student.value)
                        ""
                    }
                }
            }
        }
    }
}
