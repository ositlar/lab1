package component.role

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditAddProps
import me.ositlar.application.access.Role
import me.ositlar.application.type.RoleDescription
import me.ositlar.application.type.RoleName
import me.ositlar.application.type.TypeError
import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.useRef
import react.useState
import web.html.HTMLLabelElement
import web.html.InputType

val CRoleAdd = FC<EditAddProps<Role>>("StudentAdd") { props ->
    var roleName by useState("")
    val labelRef = useRef<HTMLLabelElement>()
    div {
        label {
            +"Add role"
        }
        ReactHTML.span {
            ReactHTML.input {
                type = InputType.text
                value = roleName
                onChange = { roleName = it.target.value }
            }
        }
        label {
            ref = labelRef
        }
        ReactHTML.button {
            +"✓"
            onClick = {
                val role: Either<NonEmptyList<TypeError>, Role> = Role(RoleName(roleName), emptyList(), RoleDescription(""))
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

}