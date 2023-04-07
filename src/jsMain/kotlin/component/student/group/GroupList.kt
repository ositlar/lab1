package component.student.group

import me.ositlar.application.config.Config
import react.FC
import react.Props
import react.dom.html.FormMethod
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import web.html.InputType

external interface GroupListProps : Props {
    var groups: Array<String>
}

val CGroupList = FC <GroupListProps>("GroupList") {
    form {
        action = Config.studentsByGroupPath
        method = FormMethod.post
        input {
            name = "group"
        }
        input {
            type = InputType.submit
        }
    }
}