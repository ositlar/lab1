package component.role

import component.template.EditAddProps
import me.ositlar.application.access.Role
import react.FC
import react.dom.html.ReactHTML
import react.useState
import web.html.InputType

val CRoleAdd = FC<EditAddProps<Role>>("StudentAdd") { props ->
    var role by useState("")
    ReactHTML.span {
        ReactHTML.input {
            type = InputType.text
            value = role
            onChange = { role = it.target.value }
        }
    }
    ReactHTML.button {
        +"✓"
        onClick = {
            props.saveElement(
                Role(
                    role,
                    emptyList()
                )
            )
        }
    }
}