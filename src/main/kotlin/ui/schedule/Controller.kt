package ui.schedule

import entity.Group
import entity.Workout
import service.GroupService
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlin.collections.ArrayList

class Controller @Inject constructor(
    private val groupService: GroupService
) {
    private lateinit var model: Model
    private lateinit var fullGroupList: List<Group>

    fun init(model: Model) {
        this.model = model
        this.fullGroupList = groupService.getAll()
        model.dayTimeList.value =  setList()
    }

    private fun setList(): List<DaySchedule> {
        val daySchedule: ArrayList<DaySchedule> = ArrayList()
        this.model.dates.value.forEach { date->
            this.model.time.value.forEach { time ->
                val group = cb(date, time)
                val workout = group?.workout?.find { v -> v.date.dayOfMonth == date.dayOfMonth}
                daySchedule.add(DaySchedule(if(workout == null) "" else group?.workoutType.toString(), time, if(workout?.date == null) date else workout?.date ))
            }
        }
        return daySchedule.toList()
    }

    fun setNextMonth() {
        val currentMonth = model.selectedMonth.value
        this.fullGroupList = groupService.getAll()
        model.selectedMonth.value = currentMonth.plusMonths(1)
        model.dates.value = model.monthDates()
        model.dayTimeList.value =  setList()
    }

    fun setPrevMonth() {
        val currentMonth = model.selectedMonth.value
        this.fullGroupList = groupService.getAll()
        model.selectedMonth.value = currentMonth.minusMonths(1)
        model.dates.value = model.monthDates()
        model.dayTimeList.value =  setList()
    }

    private fun cb(date: LocalDateTime, time: LocalTime): Group?{
        val monthGroups = this.fullGroupList.filter { g -> g.period == model.selectedMonth.value }
        val dayGroups = monthGroups.filter { g -> cb2(g.workout, date)}
        return dayGroups.find { g -> time == g.time || (time.isAfter( g.time) && time.isBefore(g.endTime))}
    }

    private fun cb2(workouts: List<Workout>, date: LocalDateTime): Boolean{
        return workouts.find{workout ->  workout.date.dayOfMonth == date.dayOfMonth} !== null
    }

}