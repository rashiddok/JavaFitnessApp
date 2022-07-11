package ui.subscription

import entity.Client
import entity.Group
import service.ClientService
import service.GroupService
import service.SubscriptionService
import javax.inject.Inject

class Controller @Inject constructor(
    private val subscriptionService: SubscriptionService,
    private val clientService: ClientService,
    private val groupService: GroupService
) {
    private lateinit var model: Model

    fun init(model: Model) {
        this.model = model
    }

    fun setGroup(group: Group) {
        model.selectedGroup.value = group

        updateClientList()
        val currentClient = model.selectedClient.value
        if (model.clientList.contains(currentClient)) {
            setClient(currentClient)
        } else {
            setClient(null)
        }
    }

    fun setAction(action: Model.Action) {
        model.selectedAction.value = action

        updateClientList()
        val currentClient = model.selectedClient.value
        if (model.clientList.contains(currentClient)) {
            setClient(currentClient)
        } else {
            setClient(null)
        }
    }

    fun setClient(client: Client?) {
        model.selectedClient.value = client

        if (client == null) {
            model.orderCount.value = null
            model.compensationRate.value = null
            model.sum.value = null
        } else {
            model.orderCount.value =
                if (model.selectedAction.value == Model.Action.ADD)
                    0
                else
                    subscriptionService.get(client, model.selectedGroup.value!!.period).find { it.group == model.selectedGroup.value!! }!!.orderCount
            model.compensationRate.value
                if (model.selectedAction.value == Model.Action.ADD)
                    0
                else
                    subscriptionService.get(client, model.selectedGroup.value!!.period).find { it.group == model.selectedGroup.value!! }!!.compensationRate
            model.sum.value =
                if (model.selectedAction.value == Model.Action.ADD)
                    0
                else
                    model.orderCount.value!! * model.compensationRate.value!!
        }
    }

    fun setOrderCount(input: String) {
        if (!Regex("[1-9]+\\d*").matches(input)) {
            return
        }

        val orderCount = input.toInt()
        model.orderCount.value = orderCount

        val compensationRate = model.compensationRate.value
        if (compensationRate == null) {
            model.sum.value = null
        } else {
            model.sum.value = orderCount * compensationRate
        }
    }

    fun setCompensationRate(input: String) {
        if (!Regex("[1-9]+\\d*").matches(input)) {
            return
        }
        val compensationRate = input.toInt()
        model.compensationRate.value = compensationRate

        val orderCount = model.orderCount.value
        if (orderCount == null) {
            model.sum.value = null
        } else {
            model.sum.value = compensationRate * orderCount
        }
    }

    fun commit() {
        when (model.selectedAction.value) {
            Model.Action.ADD -> subscriptionService.subscribe(model.selectedClient.value!!, model.selectedGroup.value!!, model.orderCount.value!!, model.compensationRate.value!!)
            Model.Action.REMOVE -> subscriptionService.unsubscribe(model.selectedClient.value!!, model.selectedGroup.value!!)
        }
        subscriptionService
    }

    private fun updateClientList() {
        model.clientList.clear()

        if (model.selectedGroup.value == null) {
            return
        }

        when (model.selectedAction.value) {
            Model.Action.ADD -> model.clientList.addAll(clientService.getAll())
            Model.Action.REMOVE -> model.clientList.addAll(groupService.getClientList(model.selectedGroup.value!!))
        }
    }
}