package ui.groupList

import entity.Group
import entity.Workout
import entity.WorkoutType
import ui.createGroup.NewGroupDialog
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Arrays
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random
import kotlin.streams.toList

class Controller @Inject constructor(
    private val newGroupDialogProvider: Provider<NewGroupDialog>
) {
    private lateinit var model: Model;
    private lateinit var fullGroupList: List<Group>

    fun init(model: Model) {
        this.model = model
        this.fullGroupList = stubGroup()

        model.groupList.value = fullGroupList;
    }

    fun find(searchPattern: String) {
        model.searchPattern.value = searchPattern
        model.groupList.value = fullGroupList.stream()
            .filter { group ->
                getGroupTitle(group).contains(searchPattern, true)
            }
            .toList()
    }

    fun selectGroup(group: Group) {
        model.selectedGroup.value = group
    }

    fun createNewGroup() {
        model.newGroupDialog.value = newGroupDialogProvider.get()
            .apply {
                init{ model.newGroupDialog.value = null }
            }
    }

    fun getGroupTitle(group: Group): String {
        val type = when (group.workoutType){
            WorkoutType.AEROBICS -> "Аэробика"
            WorkoutType.PILATES -> "Пилатес"
            WorkoutType.YOGA -> "Йога"
        }
        val period = group.period.format(DateTimeFormatter.ofPattern("MM.yy"))

        return String.format("%s %s", type, period)
    }
}



private fun stubGroup(): List<Group> {
    return Arrays.stream(Month.values())
        .map { month ->
            Group(WorkoutType.values()[Random.nextInt(0, 3)], 20, YearMonth.of(2022, month), ArrayList(1))
                .also { stabWorkoutList(it) }
        }
        .toList()
}

private fun stabWorkoutList(group: Group) {
    val workoutList = group.workout as ArrayList

    for (day in 1..group.period.lengthOfMonth() step 2) {
        workoutList.add(
            Workout(
                LocalDateTime.of(group.period.year, group.period.monthValue, day, 0, 0),
                group
            )
        )
    }
}