package service

import dao.HibernateSessionFactory
import entity.Client
import entity.Transaction
import entity.TransactionType
import javax.inject.Inject

class TransactionServiceImpl @Inject constructor(
    private val hibernateFactory: HibernateSessionFactory
) : TransactionService {
    override fun withdraw(client: Client, amount: Int, comment: String) {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val transaction = Transaction(TransactionType.WITHDRAWAL, amount, comment, client)
        entityManager.transaction.begin()
        entityManager.persist(transaction)
        entityManager.transaction.commit()
        entityManager.close()
    }

    override fun deposit(client: Client, amount: Int, comment: String) {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val transaction = Transaction(TransactionType.DEPOSIT, amount, comment, client)
        entityManager.transaction.begin()
        entityManager.persist(transaction)
        entityManager.transaction.commit()
        entityManager.close()
    }

    override fun getBalance(client: Client): Int {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();

        val depositQuery = entityManager.createQuery(
            "select t from Transaction t where t.client = :client and t.type =: type",
            Transaction::class.java
        )
        depositQuery.setParameter("client", client)
        depositQuery.setParameter("type", TransactionType.DEPOSIT)
        val totalDeposit = depositQuery.resultList.stream().map { it.amount }.reduce { t, u -> t + u }.orElse(0)

        val withdrawQuery = entityManager.createQuery(
            "select t from Transaction t where t.client = :client and t.type =: type",
            Transaction::class.java
        )
        withdrawQuery.setParameter("client", client)
        withdrawQuery.setParameter("type", TransactionType.WITHDRAWAL)
        val totalWithdraw = withdrawQuery.resultList.stream().map { it.amount }.reduce { t, u -> t + u }.orElse(0)

        entityManager.close()
        return totalDeposit - totalWithdraw
    }
}