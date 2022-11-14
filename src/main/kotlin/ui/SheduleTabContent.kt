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
import ui.schedule.Schedule
import javax.inject.Inject

class SheduleTabContent @Inject constructor(
    private val schedule: Schedule
) {

    fun init(){
        schedule.init()
    }

    @Composable
    fun show() {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                schedule.show()
            }
        }
    }