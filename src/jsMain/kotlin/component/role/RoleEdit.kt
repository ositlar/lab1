package component.role

import component.template.EditItemProps
import me.ositlar.application.access.Role
import react.FC
import react.dom.html.ReactHTML
import react.useState
import web.html.InputType

val CRoleEdit = FC<EditItemProps<Role>>("LessonEdit") { props ->
    var name by useState(props.item.elem.name)
    ReactHTML.div {
        ReactHTML.input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        ReactHTML.button {
            +"Change Name"
            onClick = {
                props.saveElement(Role(name, props.item.elem.users))
            }
        }
    }
    CAddUserToLesson {
        role = props.item
        addUser = props.saveElement
    }
    ReactHTML.ol {
        props.item.elem.users.map {
            ReactHTML.li {
                +"${it.username} ${it.password}"
            }
        }
    }
}