package ui.groupList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entity.Group

class View {
    lateinit var model: Model;
    lateinit var controller: Controller;

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        Row(modifier = Modifier.fillMaxWidth()) {
            filter()
        }
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f)){
            list()
        }
        if(model.showButton.value == true){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(shape = CircleShape, onClick = controller::createNewGroup){
                Text("СОЗДАТЬ")
            }
        }
        model.newGroupDialog.value?.show()
        }
    }

    @Composable
    private fun filter() {
        Column() {
            TextField(
                value = model.searchPattern.value,
                onValueChange = controller::find,
                placeholder = { Text("Поиск") },
                singleLine = true,
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            )
            Row(verticalAlignment = Alignment.CenterVertically){
                Checkbox(checked = model.showHiddenGroups.value, onCheckedChange = { controller.toggleHiddenGroups(it) })
                Text(
                    text = "Скрыть закрытые группы",
                    color = Color.Black
                )

            }

        }
    }

    @Composable
    private fun list() {
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ) {
            model.groupList.value.filter { group -> group.isActive || group.isActive == model.showHiddenGroups.value }.forEach{ group ->
                item {
                    listItem(group)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun listItem(group: Group) {
        ListItem(
            modifier = Modifier
                .clickable { controller.selectGroup(group) }
                .fillMaxWidth()
                .background( if (model.selectedGroup.value == group)
                    MaterialTheme.colors.primary else  Color.Transparent
                )
        ) {
            Text(
                text = controller.getGroupTitle(group),
                color =  if (model.selectedGroup.value == group)
                    Color.White else  Color.Black
            )
        }
    }
}