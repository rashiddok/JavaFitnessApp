package di

import com.google.inject.AbstractModule
import service.ClientService
import service.ClientServiceImpl
import service.TransactionService
import service.TransactionServiceImpl
import service.GroupService
import service.GroupServiceImpl

class DiModule : AbstractModule() {
    override fun configure() {
        bind(ClientService::class.java).to(ClientServiceImpl::class.java)
        bind(TransactionService::class.java).to(TransactionServiceImpl::class.java)
        bind(GroupService::class.java).to(GroupServiceImpl::class.java)
    }
}