package component.settings

import me.ositlar.application.common.Item
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.useState

external interface StudentSettingsProps : Props {
    var student: Item<Student>
    var updateStudentGroup: (Pair<String, String>) -> Unit
    var updateStudent: (Triple<String, String, String>) -> Unit
}

val CStudentSetting = FC <StudentSettingsProps>("StudentSetting") { props ->
    var editedId by useState("")
    if (props.student.id == editedId) {
        CUpdateGroup {
            this.student = props.student
            saveGroup = {
                props.updateStudentGroup(it)
            }
            saveStudent = {
                props.updateStudent(it)
            }
        }
        button {
            +"X"
            onClick = {
                editedId = ""
            }
        }
    }
    else {
        div{
            +"${props.student.elem.fullName()} ${props.student.elem.group}"
        }
        span {
            +" ✎ "
            onClick = {
                editedId = props.student.id
            }
        }
    }
}