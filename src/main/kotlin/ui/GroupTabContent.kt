package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class GroupTabContent {

    @Composable
    fun show() {
        Row {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f)) {
                Text("Список групп")
            }

            Column {
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxHeight().width(1.dp))
            }

            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                Text("Выбранная группа")
            }
        }
    }
}