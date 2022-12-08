package ui.sales

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import entity.Client
import entity.Group
import java.time.YearMonth

class Model {

    val selectedMonth = mutableStateOf(YearMonth.now())
    lateinit var selectedClient: State<Client?>
    lateinit var selectedGroup: State<Group?>

    fun init(selectedClient: State<Client?>, selectedGroup: State<Group?>) {
        this.selectedClient = selectedClient
        this.selectedGroup = selectedGroup
    }
}