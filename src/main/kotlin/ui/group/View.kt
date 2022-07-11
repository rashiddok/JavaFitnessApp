package ui.group

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import entity.*
import java.time.format.DateTimeFormatter

class View {
    private lateinit var model: Model
    private lateinit var controller: Controller

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        if (model.selectedGroup.value == null) {
            return
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            title()
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            table()
        }

        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            price()
        }

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            Column {
                buttons()
            }
        }

        model.subscriptionDialog.value?.show()
    }

    @Composable
    private fun title() {
        Text(
            text = getGroupTitle(model.selectedGroup.value!!),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

    @Composable
    private fun table() {
        val group = model.selectedGroup.value!!
        val workoutList = group.workout
        val clientList = model.clientList.value

        val rowHeight = 30
        val fioWidth = 200
        val cellWidth = 40

        Column (
            modifier = Modifier.border(BorderStroke(1.dp, Color.Black))
        ) {
            Row (
                modifier = Modifier.height(rowHeight.dp)
            ) {
                fioColumn("ФИО", fioWidth, rowHeight, true)

                workoutList.forEach{ workout ->
                    workoutDateColumn(workout, cellWidth, rowHeight)
                }
            }

            clientList.forEach{client ->
                Row (
                    modifier = Modifier.height(rowHeight.dp)
                ) {
                    fioColumn(clientString(client), fioWidth, rowHeight, false)

                    workoutList.forEach{ workout ->
                        visitColumn(workout, client, cellWidth, rowHeight)
                    }
                }
            }

            Row(
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Column(
                    Modifier.width(fioWidth.dp).height(rowHeight.dp)
                ) {}

                workoutList.forEach { workout ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.width(cellWidth.dp).height(rowHeight.dp)
                    ) {
                        if (workout == model.selectedWorkout.value)
                            Button(
                                onClick = controller::commit,
                                shape = CircleShape,
                                contentPadding = PaddingValues(5.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Send,
                                    null,
                                    tint = Color.White
                                )
                            }
                    }
                }
            }
        }
    }

    @Composable
    private fun fioColumn(text: String, width: Int, height: Int, isHeader: Boolean) {
        Column(
            horizontalAlignment = if (isHeader) Alignment.CenterHorizontally else Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .border(BorderStroke(0.5.dp, Color.Black))
        ) {
            Text(
                text = text,
                modifier = Modifier.absolutePadding(left = 3.dp, top = 1.dp, bottom = 1.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
                    textAlign = if (isHeader) TextAlign.Center else TextAlign.Start
                )
            )
        }
    }

    @Composable
    private fun workoutDateColumn(workout: Workout, width: Int, height: Int) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .border(BorderStroke(0.5.dp, Color.Black))
                .background(
                    if (workout == model.selectedWorkout.value)
                        Color.Red
                    else
                        Color.Transparent
                )
                .clickable {
                    controller.selectWorkout(workout)
                },
        ) {
            Text(
                text = workoutDateString(workout),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    @Composable
    private fun visitColumn(workout: Workout, client: Client, width: Int, height: Int) {
        val status = mutableStateOf(controller.getVisitStatus(workout, client))

        Column(
            modifier = Modifier
                .clickable {
                    if(workout == model.selectedWorkout.value) {
                        val newStatus = controller.updateVisitStatus(workout, client)
                        status.value = newStatus
                    }
                }
                .width(width.dp)
                .height(height.dp)
                .border(
                    if (workout == model.selectedWorkout.value)
                        BorderStroke(2.dp, Color.Red)
                    else
                        BorderStroke(0.5.dp, Color.Black)
                )
                .background(
                    when (status.value) {
                        VisitStatus.VISITED -> Color.Green
                        VisitStatus.CANCELED -> Color.Red
                        else -> Color.Transparent
                    }
                )
        ) {}
    }

    @Composable
    private fun price() {
        Column(
            modifier = Modifier.width(200.dp)
        ) {
            Text("Стоимость занятия")
        }

        Column (
            modifier = Modifier.width(50.dp)
        ) {
            TextField(
                value = model.selectedGroup.value!!.price.toString(),
                onValueChange = {},
                enabled = false,
                singleLine = true
            )
        }
    }

    @Composable
    private fun buttons() {
        Row(
            horizontalArrangement = Arrangement.spacedBy( 20.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                shape = CircleShape,
                onClick = controller::showAddClientToGroupDialog
            ){
                Text("ДОБАВИТЬ В ГРУППУ")
            }

            Button(
                shape = CircleShape,
                onClick = controller::showRemoveClientFromGroupDialog
            ){
                Text("УДАЛИТЬ ИЗ ГРУППЫ")
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                shape = CircleShape,
                onClick = controller::closeGroup
            ){
                Text("ЗАКРЫТЬ ГРУППУ")
            }
        }
    }
}

private fun getGroupTitle(group: Group): String {
    val period = group.period.format(DateTimeFormatter.ofPattern("MM.yy"))
    val type = when (group.workoutType){
        WorkoutType.AEROBICS -> "Аэробика"
        WorkoutType.PILATES -> "Пилатес"
        WorkoutType.YOGA -> "Йога"
    }

    return String.format("%s %s", type, period)
}

private fun clientString(client: Client): String {
    return String.format("%s %s %s", client.lastName, client.firstName, client.patronymic)
}

private fun workoutDateString(workout: Workout): String {
    return workout.date.format(DateTimeFormatter.ofPattern("dd.MM"))
}