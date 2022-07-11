package service

import dao.HibernateSessionFactory
import entity.*
import java.time.YearMonth
import javax.inject.Inject

class SubscriptionServiceImpl @Inject constructor(
    private val hibernateFactory: HibernateSessionFactory,
    private val transactionService: TransactionService
) : SubscriptionService {

    override fun get(client: Client, month: YearMonth): List<Subscription> {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager()

        val query = entityManager.createQuery(
            "select s from Subscription s where s.client =: client and s.group.period = :month",
            Subscription::class.java
        )
        query.setParameter("client", client)
        query.setParameter("month", month)
        val result = query.resultList

        entityManager.close()

        return result
    }

    override fun get(client: Client): List<Subscription> {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager()

        val query = entityManager.createQuery(
            "select s from Subscription s where s.client = :client",
            Subscription::class.java
        )
        query.setParameter("client", client)
        val result = query.resultList

        entityManager.close()

        return result
    }

    override fun subscribe(client: Client, group: Group, orderCount: Int, compensationRate: Int): Subscription {
        val subscription = Subscription(client, group, orderCount, compensationRate)
        val entityManager = hibernateFactory.sessionFactory.createEntityManager()
        entityManager.transaction.begin()
        entityManager.persist(subscription)
        entityManager.transaction.commit()
        entityManager.close()

        // TODO должно быть в рамках одной транзакции
        transactionService.deposit(client, orderCount * compensationRate, "Покупка абонемента")

        return subscription
    }

    override fun unsubscribe(client: Client, group: Group): Boolean {
        TODO("Not yet implemented")
    }
}