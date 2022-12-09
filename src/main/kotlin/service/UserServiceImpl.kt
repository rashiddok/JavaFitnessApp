package service

import dao.HibernateSessionFactory
import entity.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject constructor(
    private val hibernateFactory: HibernateSessionFactory
): UserService {
    override fun find(username: String): User? {
        val entityManager = hibernateFactory.sessionFactory.createEntityManager();
        val criteria = entityManager.criteriaBuilder.createQuery(User::class.java)
        criteria.from(User::class.java)
        return entityManager.createQuery(criteria).resultList.filter { v -> v.username == username }?.firstOrNull()
    }
}