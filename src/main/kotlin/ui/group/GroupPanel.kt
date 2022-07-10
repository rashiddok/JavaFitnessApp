package ui.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import entity.Group
import javax.inject.Inject

class GroupPanel @Inject constructor(
    private val model: Model,
    private val controller: Controller,
    private val view: View
) {
    fun init(selectedGroup: State<Group?>) {
        model.init(selectedGroup)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show() {
        if (model.selectedGroup.value == null) {
            return
        }

        controller.updateModel()
        view.show()
    }
}