package ui.group

import entity.*
import service.GroupService
import ui.subscription.SubscriptionDialog
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Provider

class Controller @Inject constructor(
    private val groupService: GroupService,
    private val subscriptionDialogProvider: Provider<SubscriptionDialog>
){
    lateinit var model: Model

    private var visitsCache: MutableList<WorkoutVisit> = ArrayList()
    private var isCommited: Boolean = false

    fun init(model: Model) {
        this.model = model
    }

    fun updateModel() {
        model.clientList.value = groupService.getClientList(model.selectedGroup.value!!)
    }

    fun closeGroup() {
        // TODO
    }

    fun showAddClientToGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    closeCallback = {
                        model.subscriptionDialog.value = null
                        updateModel()
                    },
                    group = model.selectedGroup.value!!
                )
            }
    }

    fun showRemoveClientFromGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    closeCallback = {
                        model.subscriptionDialog.value = null
                        updateModel()
                    },
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

    fun selectWorkout(newWorkout: Workout) {
        val oldWorkout = model.selectedWorkout.value

        if (oldWorkout != null && !isCommited) {
            val toDelete = ArrayList<WorkoutVisit>()
            oldWorkout.visits.forEach { original ->
                val cached = visitsCache.find { it.client == original.client }
                if (cached != null)
                    original.visitStatus = cached.visitStatus
                else
                    toDelete.add(original)
            }
            oldWorkout.visits.removeAll(toDelete)
        }

        isCommited = false
        model.selectedWorkout.value = newWorkout
        visitsCache = newWorkout.visits.stream()
            .map {
                WorkoutVisit(it.client, it.workout, it.visitStatus)
            }
            .collect(Collectors.toList())
    }

    fun updateVisitStatus(workout: Workout, client: Client): VisitStatus {
        val currentStatus = workout.visits.stream()
            .filter { visit -> visit.client == client }
            .findFirst()

        val newStatus = currentStatus
            .map { visit -> visit.visitStatus }
            .map { visit ->
                if (visit.ordinal + 1 < VisitStatus.values().size) VisitStatus.values()[visit.ordinal + 1] else VisitStatus.values()[0]
            }
            .orElse(VisitStatus.VISITED)

        currentStatus.ifPresentOrElse(
            {
                it.visitStatus = newStatus
            },
            {
                workout.visits.add(WorkoutVisit(client, workout, newStatus))
            }
        )

        // TODO

        return newStatus
    }

    fun commit() {
        val visits = model.selectedWorkout.value!!.visits

        groupService.markWorkout(model.selectedWorkout.value!!, visits)

        model.selectedWorkout.value = null
        isCommited = true
    }
}