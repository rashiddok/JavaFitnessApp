package ui.subscription

import androidx.compose.runtime.Composable
import entity.Client
import entity.Group
import service.GroupService
import javax.inject.Inject

class SubscriptionDialog @Inject constructor (
    private val model: Model,
    private val controller: Controller,
    private val view: View,
    private val groupService: GroupService
) {
    fun init(closeCallback: Runnable, client: Client? = null, group: Group? = null) {
        model.init(closeCallback)
        controller.init(model)
        view.init(model, controller)

        model.groupList.addAll(groupService.getAll())

        if (client != null) {
            controller.setClient(client)
        } else if (group != null) {
            controller.setGroup(group)
        }
    }

    @Composable
    fun show() {
        view.show()
    }
}