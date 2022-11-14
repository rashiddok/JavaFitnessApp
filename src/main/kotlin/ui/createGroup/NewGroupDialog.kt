package ui.createGroup

import androidx.compose.runtime.Composable
import javax.inject.Inject

class NewGroupDialog @Inject constructor(
    private val view: View,
    private val model: Model,
    private val controller: Controller
) {

    fun init(closeCallback: Runnable) {
        model.init(closeCallback)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show() {
        view.show()
    }
}