package service

import entity.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

interface GroupService {
    @Throws(IllegalArgumentException::class)
    fun create(workoutType: WorkoutType, period: YearMonth, selectedDates: List<LocalDateTime>, time: LocalTime, endTime: LocalTime, price: Int): Group

    fun find(pattern: String): List<Group>

    fun getAll(): List<Group>

    @Throws(IllegalArgumentException::class)
    fun markWorkout(workout: Workout, visits: List<WorkoutVisit>)

    @Throws(IllegalArgumentException::class)
    fun close(group: Group): Group

    @Throws(IllegalArgumentException::class)
    fun getClientList(group: Group): List<Client>

    @Throws(IllegalArgumentException::class)
    fun getClientGroups(client: Client): List<Group>

    @Throws(IllegalArgumentException::class)
    fun getVisitList(group: Group, client: Client): List<WorkoutVisit>
}