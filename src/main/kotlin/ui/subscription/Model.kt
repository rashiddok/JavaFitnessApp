package ui.subscription

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import entity.Client
import entity.Group

class Model {
    lateinit var closeCallback: Runnable

    fun init(closeCallback: Runnable) {
        this.closeCallback = closeCallback
    }

    val groupList = mutableStateListOf<Group>()
    val selectedGroup = mutableStateOf<Group?>(null)
    val selectedAction = mutableStateOf(Action.ADD)
    val clientList = mutableStateListOf<Client>()
    val selectedClient = mutableStateOf<Client?>(null)
    val orderCount = mutableStateOf<Int?>(null)
    val compensationRate = mutableStateOf<Int?>(null)
    val sum = mutableStateOf<Int?>(null)

    enum class Action {
        ADD, REMOVE
    }
}