package ui.sales
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import entity.Client
import entity.Group
import javax.inject.Inject

class Sales @Inject constructor(
    private val view: View,
    private val model: Model,
    private val controller: Controller
) {

    fun init(selectedClient: State<Client?>, selectedGroup: State<Group?>){
        model.init(selectedClient, selectedGroup)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show(){
        view.show()
    }
}