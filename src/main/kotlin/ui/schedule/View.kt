package ui.schedule

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
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
        val scrollState = rememberScrollState(0)
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .verticalScroll(scrollState)
        ) {
            table()
        }
    }

    @Composable
    private fun table() {

        val rowHeight = 30
        val fioWidth = 70
        val cellWidth = 40
        val dates = model.dates.value
        val times = model.time.value
        Column (
            modifier = Modifier.border(BorderStroke(1.dp, Color.Black))
        ) {
            Row (
                modifier = Modifier.height(rowHeight.dp)
            ) {
                dateColumn("Время", cellWidth, rowHeight, true)
                times.forEach { time -> Row (){
                    dateColumn(time.toString(), fioWidth, rowHeight, true)
                } }
            }
            dates.forEach { date -> Row (){
                dateColumn(date.dayOfMonth.toString() + "." + date.monthValue.toString(), cellWidth, rowHeight, false)
                val dateGroups = model.dayTimeList.value.filter{g -> g.date.dayOfMonth == date.dayOfMonth}
                dateGroups.forEach { group ->
                    val showName = group.date.dayOfMonth == date.dayOfMonth && group.name != "null"
                    timeColumn(group.name.toString(), showName, fioWidth) }
            } }
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
                    fontSize = 10.sp,
                    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
                    textAlign = if (isHeader) TextAlign.Center else TextAlign.Start
                )
            )
        }
    }

    @Composable
    private fun timeColumn(text: String, showName: Boolean, width: Int) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(width.dp)
                .height(30.dp)
                .border(BorderStroke(0.5.dp, Color.Black))
        ) {
            Text(
                text = if(showName) text else "",
                modifier = Modifier.absolutePadding(left = 3.dp, top = 1.dp, bottom = 1.dp),
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}