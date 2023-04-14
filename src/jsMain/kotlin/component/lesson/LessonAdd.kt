package component.lesson

import component.template.EditAddProps
import me.ositlar.application.data.Lesson
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import web.html.InputType

val CLessonAdd = FC<EditAddProps<Lesson>>("LessonNew") { props ->
    var name by useState("")
    div {
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        button {
            +"✔"
            onClick = {
                props.saveElement(Lesson(name))
            }
        }
    }
}
