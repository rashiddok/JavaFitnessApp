package ui.creategroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.awt.Dialog
import java.awt.Dimension
import java.time.LocalDate
import java.time.YearMonth

class View {
    private lateinit var viewController: ViewController
    private lateinit var state: State

    var showCreate = remember { mutableStateOf(false) }

    fun init(state: State, viewController: ViewController) {
        this.state = state
        this.viewController = viewController
    }

    @Composable
    fun show() {
        Dialog(
            visible = showCreate.value,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
            },
            dispose = { composeDialog -> composeDialog.dispose() },
            update = {  }
        ) {
            this.window.size = Dimension(500, 500)
            Column {
                Row {
                    Column {
                        Text("График занятий")
                    }
                    Column {
                        var currentMonth = YearMonth.now().atDay(1)
                        val lengthOfMonth = currentMonth.lengthOfMonth();
                        val startMonthDayOfWeek = currentMonth.dayOfWeek.value;

                        drawMonthChangePanel(currentMonth)

                        var drawingDay = 1

                        drawWeekDayPanel()
                        drawCalendarPanel(drawingDay, lengthOfMonth, startMonthDayOfWeek, currentMonth)
                    }
                }
                Row {
                    Column {
                        Text("Тип")
                    }
                    var expanded = remember { mutableStateOf(false) }
                    var selectedText = remember { mutableStateOf("Скопировать") }
                    Column {
                        TextField(
                            value = selectedText.value,
                            onValueChange = { selectedText.value = it },
                            modifier = Modifier.width(200.dp),
                            enabled = false,
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.ArrowDropDown, "contentDescription",
                                    Modifier.clickable { expanded.value = !expanded.value })
                            },
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },

                            ) {
                            Text("Скопировать", fontSize = 15.sp, modifier = Modifier.padding(10.dp).clickable(onClick = {
                                selectedText.value = "Скопировать";
                                expanded.value = !expanded.value
                            }))
                            Text("Вставить", fontSize = 15.sp, modifier = Modifier.padding(10.dp).clickable(onClick = {
                                selectedText.value = "Вставить";
                                expanded.value = !expanded.value
                            }))
                            Text("Настройки", fontSize = 15.sp, modifier = Modifier.padding(10.dp).clickable(onClick = {
                                selectedText.value = "Настройки";
                                expanded.value = !expanded.value
                            }))
                        }
                    }
                }
                Row {
                    Column {
                        Text("Стоимость занятия")
                    }
                    Column {
                        val textState = remember { mutableStateOf("3467") }
                        TextField(
                            value = textState.value,
                            onValueChange = {
                                val pattern = Regex("[1-9]+\\d*")
                                if (pattern.matches(it))
                                    textState.value = it
                            }
                        )
                    }
                }
                Button(onClick = { showCreate.value = false }) {
                    Text("OK")
                }
            }
        }
    }

    @Composable
    private fun drawCalendarPanel(
        startDay: Int,
        lengthOfMonth: Int,
        startMonthDayOfWeek: Int,
        currentMonth: LocalDate
    ) {
        var drawingDay = startDay
        var drawingMonth = currentMonth
        while (drawingDay <= lengthOfMonth) {
            Row(modifier = Modifier.height(50.dp)) {
                if (drawingDay == 1 && startMonthDayOfWeek != 1) {
                    for (i in 1..startMonthDayOfWeek) {
                        Column(modifier = Modifier.width(50.dp)) {
                            if (i == startMonthDayOfWeek) {
                                OutlinedButton(
                                    onClick = {},
                                    shape = CircleShape,
                                    modifier = Modifier.size(40.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(drawingDay.toString())
                                }
                            } else {
                                Text("")
                            }
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }


                if (drawingMonth.dayOfWeek.value == 1 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 2 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 3 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 4 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 5 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 6 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }

                if (drawingMonth.dayOfWeek.value == 7 && drawingDay <= lengthOfMonth) {
                    Column(modifier = Modifier.width(50.dp)) {
                        OutlinedButton(
                            onClick = {},
                            shape = CircleShape,
                            modifier = Modifier.size(40.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(drawingDay.toString())
                        }
                    }
                    drawingDay++
                    drawingMonth = drawingMonth.plusDays(1)
                }
            }
        }
    }

    @Composable
    private fun drawWeekDayPanel() {
        Row {
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("пн")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("вт")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("ср")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("чт")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("пт")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("сб")
            }
            Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("вс")
            }
        }
    }

    @Composable
    private fun drawMonthChangePanel(currentMonth: LocalDate) {
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column (horizontalAlignment = Alignment.Start){
                OutlinedButton(
                    onClick = {},
                    shape = CircleShape,
                    modifier = Modifier.size(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, "",
                        Modifier.clickable { })
                }
            }

            Column(Modifier.padding(start = 100.dp, end = 100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(currentMonth.month.toString())
            }

            Column (horizontalAlignment = Alignment.End){
                OutlinedButton(
                    onClick = {},
                    shape = CircleShape,
                    modifier = Modifier.size(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Filled.KeyboardArrowRight, "",
                        Modifier.clickable { })
                }
            }
        }
    }
}