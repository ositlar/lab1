package component.lesson

import component.template.ElementInListProps
import me.ositlar.application.common.Item
import me.ositlar.application.data.Lesson
import react.FC
import react.dom.html.ReactHTML.span


val CLessonInList = FC<ElementInListProps<Item<Lesson>>>("LessonInList") { props ->
    span {
        +props.element.elem.name.lessonName
    }
}
