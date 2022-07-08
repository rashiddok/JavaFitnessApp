package entity

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

            if (values().any { !it::entity.isInitialized })
            // Необходимо синхронизировать Enum с БД. При разработке заносилось вручную
                throw IllegalStateException("Не удалось проинициализировать поле \"id\" значением из БД")
        }
    }

    @Entity
    @Table(name = "visit_statuses")
    class EntityView {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0

        @Column(name = "description", nullable = false)
        val description: String = "";
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