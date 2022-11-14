package ui.groupList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import entity.Group
import javax.inject.Inject

class GroupListPanel @Inject constructor(
    private val model: Model,
    private val controller: Controller,
    private val view: View
) {
    fun init(selectedGroup: MutableState<Group?>) {
        model.init(selectedGroup)
        controller.init(model)
        view.init(model, controller)
    }

    @Composable
    fun show() {
        view.show()
    }
}