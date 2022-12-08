package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entity.Client
import entity.Group
import ui.client.ClientPanel
import ui.clientList.ClientListPanel
import ui.group.GroupPanel
import ui.groupList.GroupListPanel
import ui.sales.Sales
import javax.inject.Inject

class SalesTabContent @Inject constructor(
    private val sales: Sales,
    private val groupListPanel: GroupListPanel,
    private val groupPanel: GroupPanel,
    private val clientListPanel: ClientListPanel,
    private val clientPanel: ClientPanel

) {

    private val selectedGroup: MutableState<Group?> = mutableStateOf(null)
    private val selectedClient: MutableState<Client?> = mutableStateOf(null)
    private val selectedDropdownValue: MutableState<String> = mutableStateOf("Группа")

    fun init(){
        sales.init(selectedClient, selectedGroup)
        groupListPanel.init(selectedGroup)
        groupPanel.init(selectedGroup)
        clientListPanel.init(selectedClient)
        clientPanel.init(selectedClient)
    }

    @Composable
    fun show() {
        Row {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f).offset(0.dp, 15.dp)) {
                Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.offset(15.dp)) {
                    Text(
                        text = "Выписка по ",
                        color = Color.Black,
                    )
                    clientCombobox()
                }

                if(selectedDropdownValue.value == "Группа") {
                    groupListPanel.show()
                } else if(selectedDropdownValue.value == "Клиент"){
                    clientListPanel.show()
                }

            }

            Column {
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxHeight().width(1.dp))
            }
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                sales.show()
            }
        }
    }

    @Composable
    fun clientCombobox() {
        Column (modifier = Modifier.fillMaxWidth(0.9f))  {
            val expanded = remember { mutableStateOf(false) }

            TextField(
                value = selectedDropdownValue.value,
                enabled = false,
                onValueChange = {},
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
                DropdownMenuItem(
                    onClick = {
                        selectedDropdownValue.value = "Группа"
                        selectedClient.value = null
                        expanded.value = false
                    },
                    modifier = Modifier
                        .background( if (selectedDropdownValue.value == "Группа")
                            MaterialTheme.colors.primary else  Color.Transparent
                        )
                ) {
                    Text(
                        text = "Группа",
                        color = if (selectedDropdownValue.value == "Группа")
                            Color.White else  Color.Black
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        selectedDropdownValue.value = "Клиент"
                        selectedGroup.value = null
                        expanded.value = false
                    },
                    modifier = Modifier
                        .background( if (selectedDropdownValue.value == "Клиент")
                            MaterialTheme.colors.primary else  Color.Transparent
                        )
                ) {
                    Text(
                        text = "Клиент",
                        color = if (selectedDropdownValue.value == "Клиент")
                            Color.White else  Color.Black
                    )
                }
            }
        }
    }
}