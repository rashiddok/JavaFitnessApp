package service

import entity.Client
import entity.Group
import entity.Subscription
import kotlin.jvm.Throws

interface SubscriptionService {
    @Throws(IllegalArgumentException::class)
    fun subscribe(client: Client, group: Group, orderCount: Int, compensation: Int): Subscription
    @Throws(IllegalArgumentException::class)
    fun unsubscribe(subscription: Subscription): Boolean
}