package ui.subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import entity.Client
import entity.Group
import entity.WorkoutType
import java.awt.Dialog
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.time.format.DateTimeFormatter

class View {
    private  lateinit var model: Model
    private lateinit var controller: Controller

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        Dialog(
            visible = true,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
                    .apply {
                        size = Dimension(600, 480)
                        isResizable = false
                        addWindowListener(object : WindowAdapter() {
                            override fun windowClosing(e: WindowEvent?) {
                                model.closeCallback.run()
                            }
                        })
                    }
            },
            dispose = ComposeDialog::dispose,
            update = {}
        ) {
            Column (
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Группа")
                    groupCombobox()
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Действие")
                    actionCombobox()
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Клиент")
                    clientCombobox()
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Количество занятий")
                    numberInput(model.orderCount.value, controller::setOrderCount)
                    labelColumn("Занятий в группе: " + model.selectedGroup.value?.workout?.size)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Ставка компенсации")
                    numberInput(model.compensationRate.value, controller::setCompensationRate)
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn(
                        when (model.selectedAction.value) {
                            Model.Action.ADD -> "Пополнение счета"
                            Model.Action.REMOVE -> "Списание со счета"
                        }
                    )
                    Column {
                        TextField(
                            value = model.sum.value?.toString() ?: "",
                            enabled = false,
                            onValueChange = {},
                            singleLine = true,
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                ) {
                    Button (
                        onClick = controller::commit,
                        shape = CircleShape
                    ) {
                        Text(
                            when (model.selectedAction.value) {
                                Model.Action.ADD -> "Добавить"
                                Model.Action.REMOVE -> "Удалить"
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun labelColumn(label: String) {
        Column (
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(200.dp)
        ) {
            Text(label)
        }
    }

    @Composable
    fun groupCombobox() {
        Column {
            val expanded = remember { mutableStateOf(false) }

            TextField(
                value = model.selectedGroup.value?.let { groupString(it) } ?: "",
                enabled = false,
                onValueChange = {},
                singleLine = true,
                modifier = Modifier.width(350.dp),
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown, null,
                        Modifier.clickable { expanded.value = !expanded.value }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                model.groupList.forEach { group ->
                    DropdownMenuItem(
                        onClick = {
                            controller.setGroup(group)
                            expanded.value = false
                        },
                        modifier = Modifier
                            .background( if (model.selectedGroup.value == group)
                                MaterialTheme.colors.primary else  Color.Transparent
                            )
                    ) {
                        Text(
                            text = groupString(group),
                            color = if (model.selectedGroup.value == group)
                                Color.White else  Color.Black
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun actionCombobox() {
        Column {
            val expanded = remember { mutableStateOf(false) }

            TextField(
                value = actionString(model.selectedAction.value),
                onValueChange = {},
                modifier = Modifier.width(350.dp),
                enabled = false,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown, null,
                        Modifier.clickable { expanded.value = !expanded.value }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },

                ) {
                Model.Action.values().forEach { action ->
                    DropdownMenuItem(
                        onClick = {
                            controller.setAction(action)
                            expanded.value = false
                        },
                        modifier = Modifier
                            .background( if (model.selectedAction.value == action)
                                MaterialTheme.colors.primary else  Color.Transparent
                            )
                    ) {
                        Text(
                            text = actionString(action),
                            color = if (model.selectedAction.value == action)
                                Color.White else  Color.Black
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun clientCombobox() {
        Column {
            val expanded = remember { mutableStateOf(false) }

            TextField(
                value = model.selectedClient.value?.let { clientString(it) } ?: "",
                enabled = false,
                onValueChange = {},
                singleLine = true,
                modifier = Modifier.width(350.dp),
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown, null,
                        Modifier.clickable { expanded.value = !expanded.value }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },

                ) {
                model.clientList.forEach { client ->
                    DropdownMenuItem(
                        onClick = {
                            controller.setClient(client)
                            expanded.value = false
                        },
                        modifier = Modifier
                            .background( if (model.selectedClient.value == client)
                                MaterialTheme.colors.primary else  Color.Transparent
                            )
                    ) {
                        Text(
                            text = clientString(client),
                            color = if (model.selectedClient.value == client)
                                Color.White else  Color.Black
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun numberInput(currentValue: Int?, setter: (String) -> Unit) {
        Column {
            TextField(
                value = currentValue?.toString() ?: "",

                onValueChange = setter::invoke,
                enabled = when (model.selectedAction.value) {
                    Model.Action.ADD -> true
                    Model.Action.REMOVE -> false
                },
                singleLine = true,
                modifier = Modifier.width(60.dp)
            )
        }
    }
}

fun groupString(group: Group): String {
    val type = when (group.workoutType){
        WorkoutType.AEROBICS -> "Аэробика"
        WorkoutType.PILATES -> "Пилатес"
        WorkoutType.YOGA -> "Йога"
    }
    val period = group.period.format(DateTimeFormatter.ofPattern("MM.yy"))

    return String.format("%s %s", type, period)
}

fun clientString(client: Client): String {
    return String.format("%s %s %s", client.lastName, client.firstName, client.patronymic)
}

fun actionString(action: Model.Action): String {
    return when (action) {
        Model.Action.ADD -> "Добавить"
        Model.Action.REMOVE -> "Удалить"
    }
}