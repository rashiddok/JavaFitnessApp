package service

import dao.HibernateSessionFactory
import entity.Group
import entity.Workout
import entity.WorkoutType
import entity.WorkoutVisit
import java.time.LocalDateTime
import java.time.YearMonth
import javax.inject.Inject
import javax.persistence.EntityManager

class GroupServiceImpl @Inject constructor(
    private val hibernateFactory: HibernateSessionFactory
) : GroupService  {

    override fun create(workoutType: WorkoutType, period: YearMonth, selectedDates: List<LocalDateTime>, price: Int): Group {
        val entityManager = beginTransaction()

        val group = Group(workoutType, price, period, ArrayList())

        // Костыль для наполнения группы запланированными датами тренировок
        selectedDates.stream()
            .map { date -> Workout(date, group) }
            .sorted { o1, o2 -> o1.date.compareTo(o2.date) }
            .forEach { group.workout.add(it) }


        entityManager.persist(group)
        commitAndClose(entityManager)
        return group
    }

    override fun find(pattern: String): List<Group> {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Group> {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val criteria = entityManager.criteriaBuilder.createQuery(Group::class.java)
        criteria.from(Group::class.java)
        return entityManager.createQuery(criteria).resultList
    }

    override fun markWorkout(workout: Workout, visits: List<WorkoutVisit>) {
        TODO("Not yet implemented")
    }

    override fun close(group: Group): Group {
        TODO("Not yet implemented")
    }

    private fun beginTransaction() : EntityManager {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        entityManager.transaction.begin()
        return entityManager
    }

    private fun commitAndClose(entityManager: EntityManager) {
        entityManager.transaction.commit()
        entityManager.close()
    }
}