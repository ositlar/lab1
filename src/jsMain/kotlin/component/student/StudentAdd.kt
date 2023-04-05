package component.student

import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useRef
import web.html.HTMLInputElement

external interface AddStudentProps : Props {
    var addStudent: (Student) -> Unit
}

val CAddStudent = FC<AddStudentProps>("AddStudent") { props ->
    val firstnameRef = useRef<HTMLInputElement>()
    val surnameRef = useRef<HTMLInputElement>()
    val groupRef = useRef<HTMLInputElement>()
    div {
        div {
            label { +"firstname " }
            input { ref = firstnameRef }
        }
        div {
            label { +"surname " }
            input { ref = surnameRef }
        }
        div {
            label { +"group " }
            input { ref = groupRef }
        }
        button {
            +"Add"
            onClick = {
                firstnameRef.current?.value?.let { firstname ->
                    surnameRef.current?.value?.let { surname ->
                        groupRef.current?.value?.let { group ->
                            props.addStudent(Student(firstname, surname, group))
                        }
                    }
                }
            }
        }
    }
}