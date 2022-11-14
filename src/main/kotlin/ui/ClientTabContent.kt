package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import entity.Client
import ui.client.ClientPanel
import ui.clientList.ClientListPanel
import javax.inject.Inject

class ClientTabContent @Inject constructor(
    private val clientListPanel: ClientListPanel,
    private val clientPanel: ClientPanel
) {
    val selectedClient: MutableState<Client?> = mutableStateOf(null)

    fun init() {
        clientListPanel.init(selectedClient)
        clientPanel.init(selectedClient)
    }

    @Composable
    fun show() {
        Row {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f)) {
                clientListPanel.show()
            }

            Column {
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxHeight().width(1.dp))
            }

            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                clientPanel.show()
            }
        }
    }
}