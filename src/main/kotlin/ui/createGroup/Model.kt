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
    val selectedEndTime = mutableStateOf(LocalTime.of(10,0))
    val selectedDates = mutableStateListOf<Int>()
    val selectedWorkoutType = mutableStateOf(WorkoutType.AEROBICS)
    val workoutPrice = mutableStateOf("15")

    fun dayTime(startTime: Int): List<LocalTime>{
        val dt: LocalTime = LocalTime.of(startTime,0)
        val times: ArrayList<LocalTime> = ArrayList()
        var currentTime = startTime
        while (currentTime <= 21){
            times.add(LocalTime.of(currentTime, 0))
            currentTime++
        }
        return times.toList()
    }
    fun init(closeCallback: Runnable) {
        this.closeCallback = closeCallback
    }
}