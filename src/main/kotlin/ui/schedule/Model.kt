package ui.schedule

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.YearMonth
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


class Model {
    var dates: MutableState<List<LocalDate>> = mutableStateOf(this.monthDates())
    var time: MutableState<List<LocalTime>> = mutableStateOf(this.dayTime())

    fun monthDates(): List<LocalDate>{
        val ym: YearMonth = YearMonth.of(2022, Month.NOVEMBER)
        val firstOfMonth: LocalDate = ym.atDay(1)
        val firstOfFollowingMonth: LocalDate = ym.plusMonths(1).atDay(1)
        val dates: List<LocalDate> = firstOfMonth.datesUntil(firstOfFollowingMonth).collect(Collectors.toList())
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
}