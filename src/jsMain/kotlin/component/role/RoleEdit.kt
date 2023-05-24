package component.role

import component.template.EditItemProps
import me.ositlar.application.access.Role
import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.strong
import react.useState
import web.html.InputType

val CRoleEdit = FC<EditItemProps<Role>>("LessonEdit") { props ->
    var name by useState(props.item.elem.name)
    ReactHTML.div {
        label {
            +"Change role's name"
        }
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
        label {
            strong {
                +"Users list of ${props.item.elem.name}"
            }
        }
        ol {
            props.item.elem.users.map { user ->
                li {
                    +user.username
                    +" ✂ "
                    onClick = {
                        props.saveElement(props.item.elem.deleteUser(user.username))
                    }
                }
            }
        }
    p {
        +"Roles list"
    }
}