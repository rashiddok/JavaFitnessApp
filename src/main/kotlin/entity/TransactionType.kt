package entity

import javax.persistence.*

enum class TransactionType {
    WITHDRAWAL,
    DEPOSIT;

    private lateinit var entity: EntityView;

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
    @Table(name = "transaction_types")
    class EntityView {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0

        @Column(name = "description", nullable = false)
        val description: String = "";
    }

    class Converter : AttributeConverter<TransactionType, Int> {
        override fun convertToDatabaseColumn(attribute: TransactionType?): Int {
            return attribute!!.entity.id
        }

        override fun convertToEntityAttribute(dbData: Int?): TransactionType {
            return values().find { it.entity.id == dbData }!!
        }
    }
}
