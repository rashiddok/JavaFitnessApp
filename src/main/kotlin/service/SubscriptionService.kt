package service

import entity.Client
import entity.Group
import entity.Subscription
import java.time.YearMonth
import kotlin.jvm.Throws

interface SubscriptionService {
    @Throws(IllegalArgumentException::class)
    fun get(client: Client, month: YearMonth): List<Subscription>

    @Throws(IllegalArgumentException::class)
    fun subscribe(client: Client, group: Group, orderCount: Int, compensation: Int): Subscription

    @Throws(IllegalArgumentException::class)
    fun unsubscribe(client: Client, group: Group): Boolean
}