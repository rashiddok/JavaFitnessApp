package ui.client

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import entity.Client
import ui.balance.RebalanceDialog
import ui.subscription.SubscriptionDialog
import java.time.YearMonth
import java.util.Collections

class Model {
    lateinit var selectedClient: State<Client?>

    var rebalanceDialog = mutableStateOf<RebalanceDialog?>(null)
    var subscriptionDialog = mutableStateOf<SubscriptionDialog?>(null)

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val patronymic = mutableStateOf("")

    val unsavedFirstName = mutableStateOf(false)
    val unsavedLastName = mutableStateOf(false)
    val unsavedPatronymic = mutableStateOf(false)

    val balance = mutableStateOf(0)

    val monthList: MutableState<List<YearMonth>> = mutableStateOf(Collections.emptyList())
    val selectedMonth: MutableState<YearMonth?> = mutableStateOf(null)
    val workoutOrderedCount = mutableStateOf(0)
    val workoutVisitedCount = mutableStateOf(0)
    val workoutCanceledCount = mutableStateOf(0)

    fun init(selectedClient: State<Client?>) {
        this.selectedClient = selectedClient
    }
}