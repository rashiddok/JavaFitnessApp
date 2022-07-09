package ui.client

import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.util.Arrays
import java.util.stream.Collectors

class Controller {
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
        model.selectedClient.value!!.firstName = firstName
    }

    fun setLastName(lastName: String) {
        model.lastName.value = lastName
        model.selectedClient.value!!.lastName = lastName
    }

    fun setPatronymic(patronymic: String) {
        model.patronymic.value = patronymic
        model.selectedClient.value!!.patronymic = patronymic
    }

    fun setWorkoutPeriod(period: YearMonth) {
        model.selectedMonth.value = period
        // TODO
        model.workoutOrderedCount.value = period.lengthOfMonth();
        model.workoutVisitedCount.value = 0;
        model.workoutCanceledCount.value = 0;
    }

    fun showEditClientBalanceDialog() {
        // TODO
    }

    fun showAddClientToGroupDialog() {
        // TODO
    }
}

private fun getMonthList(): List<YearMonth> {
    return Arrays.stream(Month.values())
        .map { YearMonth.of( Year.now().value, it) }
        .collect(Collectors.toList())
}