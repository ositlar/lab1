package component.role

import component.template.ElementInListProps
import me.ositlar.application.access.Role
import me.ositlar.application.common.Item
import react.FC
import react.dom.html.ReactHTML

val CRoleInList = FC<ElementInListProps<Item<Role>>>("StudentInList") { props ->
    ReactHTML.span {
        +props.element.elem.name.roleName
    }
}