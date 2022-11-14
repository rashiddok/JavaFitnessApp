package service

import dao.HibernateSessionFactory
import entity.Client
import javax.inject.Inject
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
class ClientServiceImpl @Inject constructor(
    private val hibernateFactory: HibernateSessionFactory
) : ClientService {

    override fun create(firstName: String, secondName: String, patronymic: String): Client {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val client = Client(firstName, secondName, patronymic)
        beginTransaction(entityManager)
        entityManager.persist(client)
        commitAndClose(entityManager)
        return client
    }

    override fun find(pattern: String): List<Client> {
        return ArrayList()
    }

    override fun getAll(): List<Client> {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val criteria = entityManager.criteriaBuilder.createQuery(Client::class.java)
        criteria.from(Client::class.java)

        return entityManager.createQuery(criteria).resultList
    }

    override fun update(client: Client): Client {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        beginTransaction(entityManager)
        entityManager.merge(client)
        commitAndClose(entityManager)
        return client
    }

    override fun remove(client: Client) {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager()
        beginTransaction(entityManager)
        entityManager.remove(client)
        commitAndClose(entityManager)
    }

    private fun beginTransaction(entityManager: EntityManager) {
        entityManager.transaction.begin()
    }

    private fun commitAndClose(entityManager: EntityManager) {
        entityManager.transaction.commit()
        entityManager.close()
    }
}