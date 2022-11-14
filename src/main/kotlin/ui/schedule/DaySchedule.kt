package ui.schedule

import java.time.LocalDateTime
import java.time.LocalTime

class DaySchedule constructor(
    name: String,
    time: LocalTime,
    date: LocalDateTime
) {
    val name: String = name
    val time: LocalTime = time
    val date: LocalDateTime = date


}