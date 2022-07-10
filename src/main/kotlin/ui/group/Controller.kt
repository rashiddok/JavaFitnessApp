package ui.group

import entity.*

class Controller {
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
        // TODO
    }

    fun showRemoveClientFromGroupDialog() {
        // TODO
    }

    fun getVisitStatus(workout: Workout, client: Client): VisitStatus? {
        return workout.visits.stream()
            .filter { visit -> visit.client == client }
            .map { visit -> visit.visitStatus }
            .findFirst()
            .orElse(null)
    }

    fun updateVisitStatus(workout: Workout, client: Client): VisitStatus {
        val visits = workout.visits as ArrayList

        val currentStatus = visits.stream()
            .filter { visit -> visit.client == client }
            .findFirst()

        currentStatus.ifPresent{ visits.remove(it)}

        val newStatus = currentStatus
            .map { visit -> visit.visitStatus }
            .map { visit ->
                if (visit.ordinal + 1 < VisitStatus.values().size) VisitStatus.values()[visit.ordinal + 1] else VisitStatus.values()[0]
            }
            .orElse(VisitStatus.VISITED)

        visits.add(WorkoutVisit(client, workout, newStatus))

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