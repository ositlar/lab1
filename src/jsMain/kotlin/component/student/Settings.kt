package component.student

import me.ositlar.application.common.Item
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.span
import react.useRef
import react.useState
import web.html.HTMLInputElement

external interface StudentItemProps : Props {
    var student: Item<Student>
    var upgradeGroup: (String) -> Unit
    var updateStudent: (Item<Student>) -> Unit
}

val CStudentItem = FC<StudentItemProps>("GroupItem") { props ->
    var editedId by useState("")

    if (props.student.id == editedId)
        CUpdateGroup {
            student = props.student.elem
            saveGroup = {
                props.upgradeGroup(it)
            }
            saveStudent = {
                props.updateStudent(Item(it, props.student.id))
            }
        }
    else
        ReactHTML.div {
            span{
                +props.student.elem.firstname
                +" "
            }
            span{
                +props.student.elem.surname
                +" "
            }
            span{
                +props.student.elem.group
            }
            span {
                +" ✎ "
                onClick = {
                    editedId = props.student.id
                }
            }
            button {
                +"X"
                onClick = {
                    editedId = ""
                }
            }

        }

}

external interface UpdateGroupProps : Props {
    var student: Student
    var saveGroup: (String) -> Unit
    var saveStudent: (Student) -> Unit
}

val CUpdateGroup = FC<UpdateGroupProps>("Edit student") { props ->
    val firstnameRef = useRef<HTMLInputElement>()
    val surnameRef = useRef<HTMLInputElement>()
    val groupRef = useRef<HTMLInputElement>()
    ReactHTML.div {
        ReactHTML.input {
            defaultValue = props.student.firstname
            ref = firstnameRef
        }
        ReactHTML.input {
            defaultValue = props.student.surname
            ref = surnameRef
        }

        ReactHTML.input {
            defaultValue = props.student.group
            ref = groupRef
        }
    }
        ReactHTML.button {
            +"Student"
            onClick = {
                    surnameRef.current?.value?.let { surname ->
                        firstnameRef.current?.value?.let{ firstname ->
                            props.saveStudent(Student(firstname, surname, props.student.group))
                        }
                }
            }
        }
        ReactHTML.button {
            +"Group"
            onClick = {
               groupRef.current?.value?.let { group ->
                   props.saveGroup(group)
                    }
                }
            }
        }


