package component.settings

import csstype.FontWeight
import emotion.react.css
import me.ositlar.application.common.Item
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useRef
import web.html.HTMLInputElement

external interface UpdateGroupProps : Props {
    var student: Item<Student>
    var saveGroup: (Pair<String, String>) -> Unit
    var saveStudent: (Triple<String, String, String>) -> Unit
}

val CUpdateGroup = FC <UpdateGroupProps>("UpdateGroup") { props ->
    val newGroupRef = useRef<HTMLInputElement>()
    val newFirstnameRef = useRef<HTMLInputElement>()
    val newSurnameRef = useRef<HTMLInputElement>()
    div {
        css {
            fontWeight = FontWeight.bold
        }
        label {
            +props.student.elem.firstname
            +" "
        }
        label {
            +props.student.elem.surname
            +" "
        }
        label {
            +props.student.elem.group
        }

    }
    div {
        input {
            defaultValue = props.student.elem.firstname
            ref = newFirstnameRef
        }
        input {
            defaultValue = props.student.elem.surname
            ref = newSurnameRef
        }
        button {
            +"✓"
            onClick = {
                newFirstnameRef.current?.value?.let { firstname ->
                    newSurnameRef.current?.value?.let { surname ->
                        props.saveStudent(Triple(props.student.id, firstname, surname))
                    }
                }
            }
        }
    }
    div {
        input {
            defaultValue = props.student.elem.group
            ref = newGroupRef
        }
        button {
            +"✓"
            onClick = {
                newGroupRef.current?.value?.let { group ->
                    props.saveGroup(Pair(props.student.id, group))
                }
            }
        }
    }
}