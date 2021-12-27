import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import react.dom.button
import react.dom.div
import react.dom.input
import react.setState

private val scope = MainScope()

external interface SubmissionState : RState {
    var uri: String
    var gradeResult: GradeResult?
}

@JsExport
class SubmissionForm : RComponent<RProps, SubmissionState>() {
    override fun SubmissionState.init() {
        uri = ""
        gradeResult = null
    }

    override fun RBuilder.render() {
        div {
            +"Enter the URI for the homepage of your assignment and submit:"
            br {}
            input {
                attrs {
                    type = InputType.text
                    value = state.uri
                    onChangeFunction = onInputChange()
                }
            }
            button {
                attrs {
                    type = ButtonType.submit
                    attrs.onClickFunction = onSubmitClicked()
                    +"Submit Assignment"
                }
            }
        }
        gradedAssignment {
            uri = state.uri
            result = state.gradeResult
        }
    }

    private fun onInputChange() = { event: Event ->
        event.target?.also { target ->
            setState {
                uri = (target as HTMLInputElement).value
            }
        } ?: throw IllegalStateException("Detected change in input but no valid target attached to the triggering Event.")
        Unit
    }

    private fun onSubmitClicked() = { _: Event ->
        scope.launch {
            submitAssignment(Submission(state.uri)).also { result ->
                setState {
                    uri = state.uri
                    gradeResult = result
                }
            }
        }
        Unit
    }
}
