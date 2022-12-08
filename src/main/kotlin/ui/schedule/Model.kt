package ui.schedule

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class Model {
    var time: MutableState<List<LocalTime>> = mutableStateOf(this.dayTime())
    val dayTimeList: MutableState<List<DaySchedule>> = mutableStateOf(Collections.emptyList())
    val selectedMonth = mutableStateOf(YearMonth.now())
    var dates: MutableState<List<LocalDateTime>> = mutableStateOf(Collections.emptyList())

    fun monthDates(): List<LocalDateTime>{
        val ym = selectedMonth.value
        val firstOfMonth: LocalDate = ym.atDay(1)
        val firstOfFollowingMonth: LocalDate = ym.plusMonths(1).atDay(1)
        val dates: List<LocalDateTime> = firstOfMonth.datesUntil(firstOfFollowingMonth).map{ d -> d.atTime(0,0)}.collect(Collectors.toList())
        return dates
    }

    private fun dayTime(): List<LocalTime>{
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

    fun init(){
    dates.value = monthDates()
    }
}