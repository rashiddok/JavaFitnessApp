package ui.group

import entity.*
import ui.subscription.SubscriptionDialog
import javax.inject.Inject
import javax.inject.Provider

class Controller @Inject constructor(
    private val subscriptionDialogProvider: Provider<SubscriptionDialog>
){
    lateinit var model: Model

    fun init(model: Model) {
        this.model = model
    }

    fun updateModel() {
        model.clientList.value = getClientList(model.selectedGroup.value!!)
    }

    fun closeGroup() {
        // TODO
    }

    fun showAddClientToGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    closeCallback = { model.subscriptionDialog.value = null },
                    group = model.selectedGroup.value!!
                )
            }
    }

    fun showRemoveClientFromGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    closeCallback = { model.subscriptionDialog.value = null },
                    group = model.selectedGroup.value!!
                )
            }
    }

    fun getVisitStatus(workout: Workout, client: Client): VisitStatus? {
        return workout.visits.stream()
            .filter { visit -> visit.client == client }
            .map { visit -> visit.visitStatus }
            .findFirst()
            .orElse(null)
    }

    fun updateVisitStatus(workout: Workout, client: Client): VisitStatus {
        val currentStatus = workout.visits.stream()
            .filter { visit -> visit.client == client }
            .findFirst()

        currentStatus.ifPresent{
            workout.visits.remove(it)
        }

        val newStatus = currentStatus
            .map { visit -> visit.visitStatus }
            .map { visit ->
                if (visit.ordinal + 1 < VisitStatus.values().size) VisitStatus.values()[visit.ordinal + 1] else VisitStatus.values()[0]
            }
            .orElse(VisitStatus.VISITED)

        workout.visits.add(WorkoutVisit(client, workout, newStatus))

        // TODO

        return newStatus
    }
}

private fun getClientList(group: Group): List<Client> {
    return listOf(
        Client("Dima", "Dima", "Dima"),
        Client("Vlad", "Vlad", "Vlad"),
        Client("Mia", "Mia", "Mia"),
        Client("Oly", "Oly", "Oly"),
        Client("Paul", "Paul", "Paul"),
        Client("Angelina", "Angelina", "Angelina")
    )
}