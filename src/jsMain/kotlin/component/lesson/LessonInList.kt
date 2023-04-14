package component.lesson

import component.template.ElementInListProps
import me.ositlar.application.data.Lesson
import react.FC
import react.dom.html.ReactHTML.span


val CLessonInList = FC<ElementInListProps<Lesson>>("LessonInList") { props ->
    span {
        +props.item.elem.name

    }
}
