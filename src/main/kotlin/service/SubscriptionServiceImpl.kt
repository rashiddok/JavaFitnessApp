package service

import entity.Client
import entity.Group
import entity.Subscription
import java.time.YearMonth

class SubscriptionServiceImpl: SubscriptionService {
    override fun get(client: Client, month: YearMonth): List<Subscription> {
        TODO("Not yet implemented")
    }

    override fun subscribe(client: Client, group: Group, orderCount: Int, compensation: Int): Subscription {
        TODO("Not yet implemented")
    }

    override fun unsubscribe(client: Client, group: Group): Boolean {
        TODO("Not yet implemented")
    }
}