package entity

import java.util.*
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

            if (values().any { !it::entity.isInitialized }) {
                entityManager.transaction.begin()
                Arrays.stream(TransactionType.values()).forEach {
                    it.entity = EntityView(it.toString())
                    entityManager.persist(it.entity)
                }
                entityManager.transaction.commit()
            }
        }
    }

    @Entity
    @Table(name = "transaction_types")
    class EntityView(
        @Column(name = "description", nullable = false)
        val description: String
    ) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0
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
