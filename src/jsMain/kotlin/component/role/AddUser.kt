package component.role

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.ositlar.application.access.Role
import me.ositlar.application.access.userList
import me.ositlar.application.common.Item
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.useRef
import web.html.HTMLSelectElement

external interface AddUserProps : Props {
    var role: Item<Role>
    var addUser: (Role) -> Unit
}

val CAddUserToLesson = FC<AddUserProps>("AddStudent") { props ->
    div {
        val selectRef = useRef<HTMLSelectElement>()
        label {
            +"Add user to Role"
        }
        ReactHTML.select {
            ref = selectRef
            userList.map { user ->
                ReactHTML.option {
                    +user.username
                    value = Json.encodeToString(user)
                }
            }
        }
        ReactHTML.button {
            +"Add"
            onClick = {
                selectRef.current?.value?.let {
                    props.addUser(props.role.elem.addUser(Json.decodeFromString(selectRef.current!!.value)))
                }
            }
        }
    }
}