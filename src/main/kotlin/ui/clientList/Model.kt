package ui.clientList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entity.Client
import java.util.Collections

class Model {
    lateinit var selectedClient: MutableState<Client?>

    val searchPattern = mutableStateOf("")
    val clientList: MutableState<List<Client>> = mutableStateOf(Collections.emptyList())

    fun init(selectedClient: MutableState<Client?>) {
        this.selectedClient = selectedClient
    }
}