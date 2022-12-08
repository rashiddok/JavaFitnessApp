package ui.schedule

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Month
import java.time.YearMonth

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
                .fillMaxHeight(0.9f)
                .verticalScroll(scrollState)
                .padding(vertical = 20.dp)
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                drawMonthChangePanel()
                table()
            }
        }
    }

    @Composable
    private fun table() {

        val rowHeight = 30
        val fioWidth = 70
        val cellWidth = 40
        val dates = model.dates.value
        val times = model.time.value
        Row(            modifier = Modifier.fillMaxHeight()){
        Column (

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
                    timeColumn(group.name, showName, fioWidth) }
            } }
            }
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

    @Composable
    private fun drawMonthChangePanel() {
        Row (

            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Column {
                Icon(
                    Icons.Filled.KeyboardArrowLeft, null, tint = MaterialTheme.colors.primary, modifier = Modifier.size(30.dp)
                        .clickable { controller.setPrevMonth() })
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(250.dp)
            ) {
                Text(yearMonthString(model.selectedMonth.value))
            }

            Column {
                Icon(
                    Icons.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colors.primary, modifier = Modifier.size(30.dp)
                        .clickable { controller.setNextMonth() })
            }
        }
    }
}

private fun yearMonthString(yearMonth: YearMonth): String {
    return when (yearMonth.month!!) {
        Month.JANUARY -> "Январь " + yearMonth.year
        Month.FEBRUARY -> "Февраль " + yearMonth.year
        Month.MARCH -> "Март " + yearMonth.year
        Month.APRIL -> "Апрель " + yearMonth.year
        Month.MAY -> "Май " + yearMonth.year
        Month.JUNE -> "Июнь " + yearMonth.year
        Month.JULY -> "Июль " + yearMonth.year
        Month.AUGUST -> "Август " + yearMonth.year
        Month.SEPTEMBER -> "Сентябрь " + yearMonth.year
        Month.OCTOBER -> "Октябрь " + yearMonth.year
        Month.NOVEMBER -> "Ноябрь " + yearMonth.year
        Month.DECEMBER -> "Декабрь " + yearMonth.year
    }
}