package ui.schedule

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class View {

    private lateinit var model: Model
    private lateinit var controller: Controller

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
        ) {
            table()
        }
    }

    @Composable
    private fun table() {

        val rowHeight = 30
        val fioWidth = 30
        val cellWidth = 40
        val dates = model.dates.value
        val times = model.time.value
        model.monthDates()
        Column (
            modifier = Modifier.border(BorderStroke(1.dp, Color.Black))
        ) {
            Row (
                modifier = Modifier.height(rowHeight.dp)
            ) {
                dateColumn("Время", fioWidth, rowHeight, true)
                dates.forEach { date -> Row (){dateColumn(date.dayOfMonth.toString() + "." + date.monthValue.toString(), fioWidth, rowHeight, false)} }
            }

            times.forEach { time -> Row (){dateColumn(time.toString(), fioWidth, rowHeight, false)} }
//
//            clientList.forEach{client ->
//                Row (
//                    modifier = Modifier.height(rowHeight.dp)
//                ) {
//                    fioColumn(clientString(client), fioWidth, rowHeight, false)
//
//                    workoutList.forEach{ workout ->
//                        visitColumn(workout, client, cellWidth, rowHeight)
//                    }
//                }
//            }
//
//            Row(
//                modifier = Modifier.padding(vertical = 5.dp)
//            ) {
//                Column(
//                    Modifier.width(fioWidth.dp).height(rowHeight.dp)
//                ) {}
//
//                workoutList.forEach { workout ->
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center,
//                        modifier = Modifier.width(cellWidth.dp).height(rowHeight.dp)
//                    ) {
//                        if (workout == model.selectedWorkout.value)
//                            Button(
//                                onClick = controller::commit,
//                                shape = CircleShape,
//                                contentPadding = PaddingValues(5.dp)
//                            ) {
//                                Icon(
//                                    Icons.Filled.Send,
//                                    null,
//                                    tint = Color.White
//                                )
//                            }
//                    }
//                }
            }
    }

    @Composable
    private fun dateColumn(text: String, width: Int, height: Int, isHeader: Boolean) {
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
                    fontSize = 8.sp,
                    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
                    textAlign = if (isHeader) TextAlign.Center else TextAlign.Start
                )
            )
        }
    }
}