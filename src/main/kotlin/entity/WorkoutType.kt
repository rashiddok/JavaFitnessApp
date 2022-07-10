package entity

import java.util.Arrays
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.AttributeConverter

enum class WorkoutType {
    YOGA,
    AEROBICS,
    PILATES;

    private lateinit var entity: EntityView

    companion object {
        fun init(entityManager: EntityManager) {
            val criteria = entityManager.criteriaBuilder.createQuery(EntityView::class.java)
            criteria.from(EntityView::class.java)

            entityManager.createQuery(criteria).resultList.forEach { entity ->
                valueOf(entity.description).entity = entity
            }

            if (values().any { !it::entity.isInitialized }) {
                entityManager.transaction.begin()
                Arrays.stream(values()).forEach {
                    it.entity = EntityView(it.toString())
                    entityManager.persist(it.entity)
                }
                entityManager.transaction.commit()
            }
            // Необходимо синхронизировать Enum с БД. При разработке заносилось вручную
        }
    }

    @Entity
    @Table(name = "workout_types")
    class EntityView(
        @Column(name = "description", nullable = false)
        val description: String
    ) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0
    }

    class Converter : AttributeConverter<WorkoutType, Int> {
        override fun convertToDatabaseColumn(attribute: WorkoutType?): Int {
            return attribute!!.entity.id
        }

        override fun convertToEntityAttribute(dbData: Int?): WorkoutType {
            return values().find { it.entity.id == dbData }!!
        }
    }
}