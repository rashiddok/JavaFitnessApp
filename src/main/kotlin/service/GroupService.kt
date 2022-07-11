package service

import entity.*
import java.time.LocalDateTime
import java.time.YearMonth

interface GroupService {
    @Throws(IllegalArgumentException::class)
    fun create(workoutType: WorkoutType, period: YearMonth, selectedDates: List<LocalDateTime>, price: Int): Group

    fun find(pattern: String): List<Group>

    fun getAll(): List<Group>

    @Throws(IllegalArgumentException::class)
    fun markWorkout(workout: Workout, visits: List<WorkoutVisit>)

    @Throws(IllegalArgumentException::class)
    fun close(group: Group): Group

    @Throws(IllegalArgumentException::class)
    fun getClientList(group: Group): List<Client>
}