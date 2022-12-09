package ui.clientList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import entity.Client
import javax.inject.Inject

class ClientListPanel
@Inject constructor(
    private val model: Model,
    private val controller: Controller,
    private val view: View
){
    fun init(selectedClient: MutableState<Client?>) {
        model.init(selectedClient)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show(showCreateButton: Boolean = true) {
        view.show(showCreateButton)
    }
}