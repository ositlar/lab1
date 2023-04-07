package component.student.group

import me.ositlar.application.config.Config
import me.ositlar.application.data.Student
import react.FC
import react.Props
import react.router.dom.Link

external interface GroupItemProps : Props {
    var student: Student
}

val CGroupItem = FC <GroupItemProps>("GroupItem") { props ->
    Link {
        +props.student.fullName()
        to = "${Config.studentsByGroupPath}"
    }

}