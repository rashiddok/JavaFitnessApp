package ui.schedule

import androidx.compose.runtime.Composable
import javax.inject.Inject

class Schedule @Inject constructor(
    private val view: View,
    private val model: Model,
    private val controller: Controller
) {
    fun init() {
        view.init(model, controller)
    }

    @Composable
    fun show() {
        view.show()
    }
}