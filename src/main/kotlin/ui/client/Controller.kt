package ui.client

import service.ClientService
import service.TransactionService
import ui.balance.RebalanceDialog
import ui.subscription.SubscriptionDialog
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.util.Arrays
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Provider

class Controller @Inject constructor(
    private val clientService: ClientService,
    private val transactionService: TransactionService,
    private val rebalanceDialogProvider: Provider<RebalanceDialog>,
    private val subscriptionDialogProvider: Provider<SubscriptionDialog>
) {
    private lateinit var model: Model

    fun init(model: Model) {
        this.model = model
        model.monthList.value = getMonthList();
    }

    fun updateModel() {
        model.firstName.value = model.selectedClient.value?.firstName ?: ""
        model.lastName.value = model.selectedClient.value?.lastName ?: ""
        model.patronymic.value = model.selectedClient.value?.patronymic ?: ""

        model.selectedMonth.value = null
        model.balance.value = 100 // TODO

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
        // TODO
        model.workoutOrderedCount.value = period.lengthOfMonth();
        model.workoutVisitedCount.value = 0;
        model.workoutCanceledCount.value = 0;
    }

    fun showEditClientBalanceDialog() {
        model.rebalanceDialog.value = rebalanceDialogProvider.get()
            .apply {
                init(
                    model.selectedClient.value!!,
                    {
                        model.balance.value = transactionService.getBalance(model.selectedClient.value!!)
                        model.rebalanceDialog.value = null
                    }
                )
            }
    }

    fun showAddClientToGroupDialog() {
        model.subscriptionDialog.value = subscriptionDialogProvider.get()
            .apply {
                init(
                    { model.subscriptionDialog.value = null },
                    model.selectedClient.value!!
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
}

private fun getMonthList(): List<YearMonth> {
    return Arrays.stream(Month.values())
        .map { YearMonth.of( Year.now().value, it) }
        .collect(Collectors.toList())
}