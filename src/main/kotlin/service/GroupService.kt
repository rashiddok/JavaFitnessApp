package service

import entity.*
import java.time.YearMonth
import kotlin.jvm.Throws

interface GroupService {
    @Throws(IllegalArgumentException::class)
    fun create(workoutType: WorkoutType, period: YearMonth, workout: List<Workout>, price: Int): Group
    fun find(pattern: String): List<Group>
    fun getAll(): List<Group>
    @Throws(IllegalArgumentException::class)
    fun markWorkout(workout: Workout, visits: List<WorkoutVisit>)
    @Throws(IllegalArgumentException::class)
    fun close(group: Group): Group
}