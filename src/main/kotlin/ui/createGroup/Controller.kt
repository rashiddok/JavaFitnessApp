package ui.createGroup

import entity.WorkoutType
import service.GroupService
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.streams.toList

class Controller @Inject constructor(
    private val groupService: GroupService
) {
    private lateinit var model: Model

    fun init(model: Model) {
        this.model = model
    }

    fun setNextMonth() {
        val currentMonth = model.selectedMonth.value
        model.selectedMonth.value = currentMonth.plusMonths(1)
        model.selectedDates.clear()
    }

    fun setPrevMonth() {
        val currentMonth = model.selectedMonth.value
        model.selectedMonth.value = currentMonth.minusMonths(1)
        model.selectedDates.clear()
    }

    fun updateDay(day: Int) {
        val dates = model.selectedDates

        when(dates.contains(day)) {
            true -> dates.remove(day)
            false -> dates.add(day)
        }
    }

    fun setWorkoutType(workoutType: WorkoutType) {
        model.selectedWorkoutType.value = workoutType
    }

    fun setPrice(price: String) {
        if (Regex("[1-9]+\\d*").matches(price)) {
            model.workoutPrice.value = price
        }
    }

    fun createNewGroup() {
        val workoutType = model.selectedWorkoutType.value
        val yearMonth = model.selectedMonth.value
        val price = model.workoutPrice.value.toInt()
        val dates = model.selectedDates.stream()
            .map { day -> LocalDateTime.of(yearMonth.year, yearMonth.monthValue, day, 0, 0) }
            .toList()

        groupService.create(workoutType, yearMonth, dates, price)

        model.closeCallback.run()
    }
}