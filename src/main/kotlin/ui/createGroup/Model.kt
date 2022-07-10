package ui.createGroup

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import entity.WorkoutType
import java.time.YearMonth

class Model {
    lateinit var closeCallback: Runnable

    val selectedMonth = mutableStateOf(YearMonth.now())
    val selectedDates = mutableStateListOf<Int>()
    val selectedWorkoutType = mutableStateOf(WorkoutType.AEROBICS)
    val workoutPrice = mutableStateOf("15")

    fun init(closeCallback: Runnable) {
        this.closeCallback = closeCallback
    }
}