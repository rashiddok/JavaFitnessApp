package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import entity.Group
import ui.group.GroupPanel
import ui.groupList.GroupListPanel
import javax.inject.Inject

class GroupTabContent @Inject constructor(
    private val groupListPanel: GroupListPanel,
    private val groupPanel: GroupPanel
){
    private val selectedGroup: MutableState<Group?> = mutableStateOf(null)

    fun init() {
        groupListPanel.init(selectedGroup)
        groupPanel.init(selectedGroup)
    }

    @Composable
    fun show() {
        Row {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f)) {
                groupListPanel.show()
            }

            Column {
                Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxHeight().width(1.dp))
            }

            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                groupPanel.show()
            }
        }
    }
}