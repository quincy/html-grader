import kotlinx.html.classes
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.dom.table
import react.dom.td
import react.dom.tr

external interface AssignmentProps : RProps {
    var uri: String?
    var result: GradeResult?
}

external interface AssignmentState : RState {
    var uri: String?
    var result: GradeResult?
}

@JsExport
class GradedAssignment : RComponent<AssignmentProps, AssignmentState>() {
    override fun RBuilder.render() {
        when (val grade = props.result) {
            null -> {
                // nop
            }
            is Grades -> {
                div {
                    h2 { +"Grade for ${props.uri}" }
                    table {
                        tr {
                            td { +"Total Score" }
                            td { +"${grade.totalScore}" }
                        }
                    }
                }
            }
            is GradeError -> {
                div {
                    attrs {
                        classes = setOf("gradingError")
                    }
                    h3 { +"Error" }
                    +(grade.error)
                }
            }
            is BadGradeRequest -> {
                div {
                    attrs {
                        classes = setOf("gradingError")
                    }
                    h3 { +"Error" }
                    +(grade.error)
                }
            }
        }
    }
}

fun RBuilder.gradedAssignment(handler: AssignmentProps.() -> Unit): ReactElement {
    return child(GradedAssignment::class) {
        this.attrs(handler)
    }
}