package component.group

import react.FC
import react.Props

external interface GroupInListProps : Props {
    var group : String
}

val CGroupInList = FC<GroupInListProps>("GroupInList") { props ->
    +props.group
}