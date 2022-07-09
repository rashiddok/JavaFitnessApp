package ui.groupList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import entity.Group
import java.util.*

class Model {
    lateinit var selectedGroup: MutableState<Group?>

    val searchPattern = mutableStateOf("")
    val groupList: MutableState<List<Group>> = mutableStateOf(Collections.emptyList())

    fun init(selectedClient: MutableState<Group?>) {
        this.selectedGroup = selectedClient
    }
}