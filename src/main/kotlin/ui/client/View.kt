package ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class View {
    companion object {
        private val COLUMN_WIDTH_LABEL = 150.dp
    }

    private lateinit var model: Model
    private lateinit var controller: Controller

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        remember { model.selectedMonth }
        remember { model.selectedClient }

        if (model.selectedClient.value == null) {
            return
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxHeight(0.9f)) {
                diver("Личные данные")
                fioRow("Фамилия", model.lastName, controller::setLastName, model.unsavedLastName )
                fioRow("Имя", model.firstName, controller::setFirstName, model.unsavedFirstName )
                fioRow("Отчество", model.patronymic, controller::setPatronymic, model.unsavedPatronymic )
                diver("Баланс")
                balanceRow()
                diver("Посещения")
                periodRow()
                if (model.selectedMonth.value != null) {
                    workoutVisitInfoRow("Заказано", model.workoutOrderedCount.value)
                    workoutVisitInfoRow("Посещено", model.workoutVisitedCount.value)
                    workoutVisitInfoRow("Пропущено", model.workoutCanceledCount.value)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(shape = CircleShape,
                onClick = controller::showAddClientToGroupDialog
            ){
                Text("ДОБАВИТЬ В ГРУППУ")
            }
        }

        model.rebalanceDialog.value?.show()
    }

    @Composable
    private fun diver(title: String) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(title, Modifier.scale(0.7f), color = MaterialTheme.colors.primary)
            Divider(color = MaterialTheme.colors.primary)
        }
    }

    @Composable
    private fun fioRow(label: String, inputState: MutableState<String>, setter: (String) -> Unit, unsavedState: MutableState<Boolean>) {
        remember { inputState }

        Row (horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier.width(COLUMN_WIDTH_LABEL)) {
                Text(label)
            }

            Column (modifier = Modifier.width(200.dp)) {
                TextField(
                    value = inputState.value,
                    onValueChange = setter::invoke,
                    singleLine = true
                )
            }

            Column {
                Button(enabled = unsavedState.value, modifier = Modifier.scale(0.8f), shape = CircleShape,
                    onClick = {
                        controller.updateClient()
                    }
                ) {
                    Icon(Icons.Filled.Send, null )
                }
            }
        }
    }

    @Composable
    private fun balanceRow() {
        remember { model.balance }

        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.width(COLUMN_WIDTH_LABEL)
            ) {
                Text("Текущий баланс")
            }

            Column (
                modifier = Modifier.width(200.dp)
            ) {
                TextField(
                    value = model.balance.value.toString(),
                    onValueChange = {},
                    enabled = false,
                    singleLine = true
                )
            }

            Column {
                Button(
                    modifier = Modifier.scale(0.8f),
                    shape = CircleShape,
                    onClick = controller::showEditClientBalanceDialog
                ) {
                    Icon(Icons.Filled.Edit, null )
                }
            }
        }
    }

    @Composable
    private fun periodRow() {
        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (modifier = Modifier.width(COLUMN_WIDTH_LABEL)) {
                Text("Месяц")
            }

            Column(modifier = Modifier.width(200.dp)) {
                remember { model.monthList }
                remember { model.selectedMonth }
                val expandChooser = remember { mutableStateOf(false) }

                TextField(
                    value = model.selectedMonth.value?.toString() ?: "",
                    enabled = false,
                    singleLine = true,
                    onValueChange = {},
                    trailingIcon = {
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            "contentDescription",
                            Modifier.clickable { expandChooser.value = !expandChooser.value },
                            MaterialTheme.colors.primary
                        )
                    }
                )

                DropdownMenu(
                    expanded = expandChooser.value,
                    onDismissRequest = {expandChooser.value = false}
                ) {
                    model.monthList.value.forEach{ month ->
                        DropdownMenuItem(
                            onClick = {controller.setWorkoutPeriod(month); expandChooser.value = false},
                            modifier = Modifier
                                .background( if (model.selectedMonth.value == month)
                                    MaterialTheme.colors.primary else  Color.Transparent
                                )
                        ) {
                            Text(
                                text = month.toString(),
                                color = if (model.selectedMonth.value == month)
                                    Color.White else  Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun workoutVisitInfoRow(label: String, count: Int) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column (modifier = Modifier.width(COLUMN_WIDTH_LABEL)) {
                Text(label)
            }

            Column(modifier = Modifier.width(60.dp)) {
                TextField(
                    value = count.toString(),
                    enabled = false,
                    singleLine = true,
                    onValueChange = {}
                )
            }
        }
    }

}