package component.group

import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol

external interface GroupListProps : Props {
    var groups: Array<String>
}

val CGroupList = FC<GroupListProps>("GroupList") { props ->
    ol {
        props.groups.forEach { group ->
            li {
                CGroupInList {
                    this.group = group
                }
            }
        }
    }
}