package ui.clientList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entity.Client

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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(shape = CircleShape, onClick = controller::createNewClient){
                Text("СОЗДАТЬ")
            }
        }
    }

    @Composable
    private fun filter() {
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
    }

    @Composable
    private fun list() {
        remember{ model.clientList }

        LazyColumn (modifier = Modifier.fillMaxWidth()) {
            model.clientList.value.forEach{ client ->
                item {
                    listItem(client)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun listItem(client: Client) {
        ListItem(
            modifier = Modifier
                .clickable { controller.selectClient(client) }
                .fillMaxWidth()
                .background( if (model.selectedClient.value == client)
                    MaterialTheme.colors.primary else  Color.Transparent
                )
        ) {
            Text(
                text = String.format("%s %s %s", client.lastName, client.firstName, client.patronymic),
                color =  if (model.selectedClient.value == client)
                    Color.White else  Color.Black
            )
        }
    }
}