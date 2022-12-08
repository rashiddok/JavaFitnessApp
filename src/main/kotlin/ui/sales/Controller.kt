package ui.sales

import entity.Group
import entity.WorkoutType
import service.ClientService
import service.GroupService
import service.SubscriptionService
import javax.inject.Inject

class Controller @Inject constructor(
    val groupService: GroupService,
    val subscriptionService: SubscriptionService,
    val clientService: ClientService
) {

    lateinit var model: Model

    fun setNextMonth() {
        val currentMonth = model.selectedMonth.value
        model.selectedMonth.value = currentMonth.plusMonths(1)
    }

    fun setPrevMonth() {
        val currentMonth = model.selectedMonth.value
        model.selectedMonth.value = currentMonth.minusMonths(1)
    }

    fun getGroupTitle(group: Group): String {
        val type = when (group.workoutType){
            WorkoutType.AEROBICS -> "Аэробика"
            WorkoutType.PILATES -> "Пилатес"
            WorkoutType.YOGA -> "Йога"
        }
//        val period = group.period.format(DateTimeFormatter.ofPattern("MM.yy"))

        return String.format("%s", type)
    }

    fun init(model: Model){
        this.model = model
    }
}