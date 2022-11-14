package entity

import java.util.*
import javax.persistence.*

enum class VisitStatus {
    VISITED,
    CANCELED,
    SKIPPED;

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
                Arrays.stream(VisitStatus.values()).forEach {
                    it.entity = EntityView(it.toString())
                    entityManager.persist(it.entity)
                }
                entityManager.transaction.commit()
            }
        }
    }

    @Entity
    @Table(name = "visit_statuses")
    class EntityView(
        @Column(name = "description", nullable = false)
        val description: String
    ) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0
    }

    class Converter : AttributeConverter<VisitStatus, Int> {
        override fun convertToDatabaseColumn(attribute: VisitStatus?): Int {
            return attribute!!.entity.id;
        }

        override fun convertToEntityAttribute(dbData: Int?): VisitStatus {
            return values().find { it.entity.id == dbData }!!
        }
    }
}