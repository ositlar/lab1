package component.role

import component.template.EditAddProps
import me.ositlar.application.access.Role
import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.useState
import web.html.InputType

val CRoleAdd = FC<EditAddProps<Role>>("StudentAdd") { props ->
    var role by useState("")
    div {
        label {
            +"Add role"
        }
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

}