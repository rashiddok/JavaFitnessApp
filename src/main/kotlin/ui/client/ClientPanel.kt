package ui.client

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import entity.Client
import javax.inject.Inject

class ClientPanel
@Inject constructor(
    private val model: Model,
    private val view: View,
    private val controller: Controller
) {

    fun init(selectedClient: State<Client?>) {
        model.init(selectedClient)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show() {
        if (model.selectedClient.value == null) {
            return
        }

        controller.updateModel()
        view.show()
    }
}
