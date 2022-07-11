package ui.client

import entity.VisitStatus
import service.ClientService
import service.GroupService
import service.SubscriptionService
import service.TransactionService
import ui.balance.RebalanceDialog
import ui.subscription.SubscriptionDialog
import java.time.YearMonth
import javax.inject.Inject
import javax.inject.Provider
import kotlin.streams.toList

class Controller @Inject constructor(
    private val clientService: ClientService,
    private val transactionService: TransactionService,
    private val subscriptionService: SubscriptionService,
    private val groupService: GroupService,
    private val rebalanceDialogProvider: Provider<RebalanceDialog>,
    private val subscriptionDialogProvider: Provider<SubscriptionDialog>
) {
    private lateinit var model: Model

    fun init(model: Model) {
        this.model = model
    }

    fun updateModel() {
        model.firstName.value = model.selectedClient.value!!.firstName
        model.lastName.value = model.selectedClient.value!!.lastName
        model.patronymic.value = model.selectedClient.value!!.patronymic

        updateBalance()

        updateMonthList()
        model.selectedMonth.value = null
        model.workoutOrderedCount.value = 0;
        model.workoutVisitedCount.value = 0;
        model.workoutCanceledCount.value = 0;
    }

    fun setFirstName(firstName: String) {
        model.firstName.value = firstName
        model.unsavedFirstName.value = model.selectedClient.value!!.firstName != model.firstName.value
    }

    fun setLastName(lastName: String) {
        model.lastName.value = lastName
        model.unsavedLastName.value = model.selectedClient.value!!.lastName != model.lastName.value
    }

    fun setPatronymic(patronymic: String) {
        model.patronymic.value = patronymic
        model.unsavedPatronymic.value = model.selectedClient.value!!.patronymic != model.patronymic.value
    }

    fun setWorkoutPeriod(period: YearMonth) {
        model.selectedMonth.value = period

        val subscription = subscriptionService.get(model.selectedClient.value!!, period)[0]
        val visitList = groupService.getVisitList(subscription.group, subscription.client)

        model.workoutOrderedCount.value = subscription.orderCount
        model.workoutVisitedCount.value = visitList.filter { it.visitStatus == VisitStatus.VISITED }.size
        model.workoutCanceledCount.value = visitList.filter { it.visitStatus == VisitStatus.CANCELED }.size;
    }

    fun showEditClientBalanceDialog() {
        model.rebalanceDialog.value = rebalanceDialogProvider.get()
            .apply {
                init(
                    client = model.selectedClient.value!!,
                    closeCallback = {
                        model.rebalanceDialog.value = null
                        updateBalance()
                    },
                )
            }
    }

    fun showAddClientToGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    closeCallback = {
                        model.subscriptionDialog.value = null
                        updateBalance()
                        updateMonthList()
                    },
                    client = model.selectedClient.value!!
                )
            }
    }

    fun updateClient() {
        model.selectedClient.value!!.patronymic = model.patronymic.value
        model.selectedClient.value!!.lastName = model.lastName.value
        model.selectedClient.value!!.firstName = model.firstName.value

        clientService.update(model.selectedClient.value!!)

        model.unsavedLastName.value = false
        model.unsavedFirstName.value = false
        model.unsavedPatronymic.value = false
    }

    private fun updateBalance() {
        model.balance.value = transactionService.getBalance(model.selectedClient.value!!)
    }

    private fun updateMonthList() {
        model.monthList.clear()
        val clientSubscriptions = subscriptionService.get(model.selectedClient.value!!)
        model.monthList.addAll(
            clientSubscriptions.stream().map { it.group.period }.toList()
        )
    }
}