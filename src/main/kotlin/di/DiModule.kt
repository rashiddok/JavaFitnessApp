package di

import com.google.inject.AbstractModule
import service.*

class DiModule : AbstractModule() {
    override fun configure() {
        bind(ClientService::class.java).to(ClientServiceImpl::class.java)
        bind(TransactionService::class.java).to(TransactionServiceImpl::class.java)
        bind(GroupService::class.java).to(GroupServiceImpl::class.java)
        bind(SubscriptionService::class.java).to(SubscriptionServiceImpl::class.java)
    }
}