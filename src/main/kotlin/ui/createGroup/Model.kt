package ui.createGroup

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import entity.WorkoutType
import java.time.LocalTime
import java.time.YearMonth

class Model {
    lateinit var closeCallback: Runnable

    val selectedMonth = mutableStateOf(YearMonth.now())
    val selectedTime = mutableStateOf(LocalTime.of(9,0))
    val selectedDates = mutableStateListOf<Int>()
    val selectedWorkoutType = mutableStateOf(WorkoutType.AEROBICS)
    val workoutPrice = mutableStateOf("15")

    fun dayTime(): List<LocalTime>{
        val dt: LocalTime = LocalTime.of(9,0)
        val WORK_DAY_HOURS = 12
        val times: ArrayList<LocalTime> = ArrayList()
        var currentTime = 0
        while (currentTime <= WORK_DAY_HOURS){
            times.add(dt.plusHours(currentTime.toLong()))
            currentTime++
        }
        return times.toList()
    }
    fun init(closeCallback: Runnable) {
        this.closeCallback = closeCallback
    }
}