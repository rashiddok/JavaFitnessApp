package ui.client

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import entity.Client
import ui.balance.RebalanceDialog
import ui.subscription.SubscriptionDialog
import java.time.YearMonth

class Model {
    var isAdmin = mutableStateOf(false)

    lateinit var selectedClient: MutableState<Client?>

    var rebalanceDialog = mutableStateOf<RebalanceDialog?>(null)
    var subscriptionDialog = mutableStateOf<SubscriptionDialog?>(null)

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val patronymic = mutableStateOf("")

    val unsavedFirstName = mutableStateOf(false)
    val unsavedLastName = mutableStateOf(false)
    val unsavedPatronymic = mutableStateOf(false)

    val balance = mutableStateOf(0)

    val monthList = mutableStateListOf<YearMonth>()
    val selectedMonth: MutableState<YearMonth?> = mutableStateOf(null)
    val workoutOrderedCount = mutableStateOf(0)
    val workoutVisitedCount = mutableStateOf(0)
    val workoutCanceledCount = mutableStateOf(0)

    fun init(selectedClient: MutableState<Client?>, userIsAdmin: Boolean) {
        this.selectedClient = selectedClient
        this.isAdmin.value = userIsAdmin
    }
}