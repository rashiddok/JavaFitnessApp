package dao

import entity.*
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import javax.inject.Singleton

@Singleton
class HibernateSessionFactory {
    val sessionFactory : SessionFactory = Configuration()
        .addAnnotatedClass(WorkoutType.EntityView::class.java)
        .addAnnotatedClass(VisitStatus.EntityView::class.java)
        .addAnnotatedClass(TransactionType.EntityView::class.java)
        .addAnnotatedClass(Client::class.java)
        .addAnnotatedClass(Group::class.java)
        .addAnnotatedClass(Subscription::class.java)
        .addAnnotatedClass(Workout::class.java)
        .addAnnotatedClass(WorkoutVisit::class.java)
        .addAnnotatedClass(Transaction::class.java)
        .addAnnotatedClass(User::class.java)
        .buildSessionFactory()

    fun init() {
        // Сохраняем енамы в таблицу
        WorkoutType.init(sessionFactory.createEntityManager())
        VisitStatus.init(sessionFactory.createEntityManager())
        TransactionType.init(sessionFactory.createEntityManager())
    }
}