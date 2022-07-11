package ui.createGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import entity.WorkoutType
import java.awt.Dialog
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.time.Month
import java.time.YearMonth

class View {
    private lateinit var controller: Controller
    private lateinit var model: Model

    fun init(model: Model, controller: Controller) {
        this.model = model
        this.controller = controller
    }

    @Composable
    fun show() {
        Dialog(
            visible = true,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
                    .apply {
                        size = Dimension(600, 600)
                        isResizable = false
                        addWindowListener(object : WindowAdapter() {
                            override fun windowClosing(e: WindowEvent?) {
                                model.closeCallback.run()
                            }
                        })
                    }
            },
            dispose = ComposeDialog::dispose,
            update = {}
        ) {
            Column (
                modifier = Modifier.padding(20.dp)
            ) {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.height(350.dp)
                ) {
                    labelColumn("График занятий")
                    Column {
                        drawMonthChangePanel()
                        drawWeekDayPanel()
                        drawCalendarPanel()
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    labelColumn("Тип занятий")
                    Column {
                        workoutTypeCombobox()
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    labelColumn("Стоимость занятия")
                    Column {
                        TextField(
                            value = model.workoutPrice.value,
                            onValueChange = controller::setPrice
                        )
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                ) {
                    Button(
                        onClick = controller::createNewGroup,
                        shape = CircleShape
                    ) {
                        Text("СОЗДАТЬ")
                    }
                }
            }
        }
    }

    @Composable
    fun labelColumn(label: String) {
        Column (
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(200.dp)
        ) {
            Text(label)
        }
    }

    @Composable
    private fun drawCalendarPanel() {
        val currentMonth = model.selectedMonth.value.atDay(1)
        val lengthOfMonth = currentMonth.lengthOfMonth();
        val startMonthDayOfWeek = currentMonth.dayOfWeek.value;

        var counter = 1
        var drawingDay = currentMonth
        while (counter <= lengthOfMonth) {
            Row(
                modifier = Modifier.height(50.dp)
            ) {
                if (counter == 1 && startMonthDayOfWeek != 1) {
                    for (i in 1 until startMonthDayOfWeek) {
                        Column(modifier = Modifier.width(50.dp)) {
                            Text("")
                        }
                    }
                }

                if (drawingDay.dayOfWeek.value == 1 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 2 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 3 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 4 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 5 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 6 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }

                if (drawingDay.dayOfWeek.value == 7 && counter <= lengthOfMonth) {
                    day(counter)
                    counter++
                    drawingDay = drawingDay.plusDays(1)
                }
            }
        }
    }

    @Composable
    private fun day(day: Int) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(50.dp)
        ) {
            OutlinedButton(
                onClick = { controller.updateDay(day) },
                shape = CircleShape,
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp),
                border = if (model.selectedDates.contains(day))
                    BorderStroke(1.dp, MaterialTheme.colors.primary) else BorderStroke(1.dp, Color.Transparent),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black
                )
            ) {
                Text(day.toString())
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
    private fun drawMonthChangePanel() {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Icon(Icons.Filled.KeyboardArrowLeft, null, tint = MaterialTheme.colors.primary, modifier = Modifier.size(30.dp)
                    .clickable { controller.setPrevMonth() })
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(250.dp)
            ) {
                Text(yearMonthString(model.selectedMonth.value))
            }

            Column {
                Icon(Icons.Filled.KeyboardArrowRight, null, tint = MaterialTheme.colors.primary, modifier = Modifier.size(30.dp)
                    .clickable { controller.setNextMonth() })
            }
        }
    }

    @Composable
    fun workoutTypeCombobox() {
        val expanded = remember { mutableStateOf(false) }

        TextField(
            value = workoutTypeString(model.selectedWorkoutType.value),
            onValueChange = {},
            modifier = Modifier.width(200.dp),
            enabled = false,
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown, null,
                    Modifier.clickable { expanded.value = !expanded.value }
                )
            }
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },

            ) {
            WorkoutType.values().forEach { workoutType ->
                DropdownMenuItem(
                    onClick = {
                        controller.setWorkoutType(workoutType)
                        expanded.value = false
                    },
                    modifier = Modifier
                        .background( if (model.selectedWorkoutType.value == workoutType)
                            MaterialTheme.colors.primary else  Color.Transparent
                        )
                ) {
                    Text(
                        text = workoutTypeString(workoutType),
                        color = if (model.selectedWorkoutType.value == workoutType)
                            Color.White else  Color.Black
                    )
                }
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

private fun workoutTypeString(workoutType: WorkoutType): String {
    return when (workoutType){
        WorkoutType.AEROBICS -> "Аэробика"
        WorkoutType.PILATES -> "Пилатес"
        WorkoutType.YOGA -> "Йога"
    }
}