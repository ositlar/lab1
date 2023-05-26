package component.lesson

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditAddProps
import me.ositlar.application.data.Lesson
import me.ositlar.application.type.LessonName
import me.ositlar.application.type.TypeError
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useRef
import react.useState
import web.html.HTMLLabelElement
import web.html.InputType

val CLessonAdd = FC<EditAddProps<Lesson>>("LessonNew") { props ->
    var name by useState("")
    val labelRef = useRef<HTMLLabelElement>()
    div {
        label {
            +"Add lesson"
        }
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        label {
            ref = labelRef
        }
        button {
            +"✔"
            onClick = {
                val lesson: Either<NonEmptyList<TypeError>, Lesson> = Lesson(LessonName(name))
                labelRef.current?.textContent = when (lesson) {
                    is Either.Left -> lesson.value.joinToString { it.error }
                    is Either.Right -> {
                        props.saveElement(lesson.value)
                        ""
                    }
                }
            }
        }
    }
}
