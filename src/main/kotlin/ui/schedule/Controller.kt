package ui.schedule

import entity.Group
import service.GroupService
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

                val group = this.fullGroupList.filter { g -> g.period.monthValue == 11 }.find { g -> time == g.time || (time.isAfter(
                    g.time) && time.isBefore(g.endTime))}
                val workout = group?.workout?.find { v -> v.date.dayOfMonth == date.dayOfMonth}
                daySchedule.add(DaySchedule(if(workout == null) "" else group?.workoutType.toString(), time, if(workout?.date == null) date else workout?.date ))
            }
        }
        return daySchedule.toList()
    }
}