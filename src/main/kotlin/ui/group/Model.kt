package ui.group

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import entity.Client
import entity.Group

class Model {
    lateinit var selectedGroup: State<Group?>

    val clientList: MutableState<List<Client>> = mutableStateOf(emptyList())

    fun init(selectedGroup: State<Group?>) {
        this.selectedGroup = selectedGroup
    }
}