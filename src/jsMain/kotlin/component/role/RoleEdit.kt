package component.role

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditItemProps
import me.ositlar.application.access.Role
import me.ositlar.application.type.RoleDescription
import me.ositlar.application.type.RoleName
import me.ositlar.application.type.TypeError
import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ol
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.strong
import react.useRef
import react.useState
import web.html.HTMLLabelElement
import web.html.InputType

val CRoleEdit = FC<EditItemProps<Role>>("LessonEdit") { props ->
    var name by useState(props.item.elem.name.roleName)
    var description by useState(props.item.elem.description.roleDescription)
    val labelRef = useRef<HTMLLabelElement>()
    val labelDesRef = useRef<HTMLLabelElement>()
    div {
        label {
            +"Change role's name"
        }
        ReactHTML.input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        label {
            ref = labelRef
        }
        ReactHTML.button {
            +"Change Name"
            onClick = {
                val role: Either<NonEmptyList<TypeError>, Role> = Role(RoleName(name), props.item.elem.users, RoleDescription(props.item.elem.description.toString()))
                labelRef.current?.textContent = when (role) {
                    is Either.Left -> role.value.joinToString { it.error }
                    is Either.Right -> {
                        props.saveElement(role.value)
                        ""
                    }
                }
            }
        }
    }
    div {
        label {
            +"Change role's description"
        }
        input {
            type = InputType.text
            value = description
            onChange = { description = it.target.value }
        }
        label {
            ref = labelDesRef
        }
        button {
            +"Change description"
            onClick = {
                val role: Either<NonEmptyList<TypeError>, Role> = Role(RoleName(props.item.elem.name.toString()), props.item.elem.users, RoleDescription(description))
                labelDesRef.current?.textContent = when (role) {
                    is Either.Left -> role.value.joinToString { it.error }
                    is Either.Right -> {
                        props.saveElement(role.value)
                        ""
                    }
                }
            }
        }
    }
    CAddUserToLesson {
        role = props.item
        addUser = props.saveElement
    }
    label {
        +"Role ${props.item.elem.name.roleName}'s description : "
        +props.item.elem.description.roleDescription
    }
    div {
        label {
            strong {
                +"Users list of ${props.item.elem.name.roleName}"
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
    }
    p {
        +"Roles list"
    }
}