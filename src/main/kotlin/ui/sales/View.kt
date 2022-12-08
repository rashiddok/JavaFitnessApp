package ui.sales

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
import entity.Client
import entity.VisitStatus
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
        if (model.selectedGroup.value == null && model.selectedClient.value == null) {
            return
        }
        val scrollState = rememberScrollState(0)
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .verticalScroll(scrollState)
                .offset(y = 25.dp)
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                if(model.selectedGroup.value !== null){
                    groupTable()
                }
                if(model.selectedClient.value !== null){
                    drawMonthChangePanel()
                    clientTable()
                }
            }

        }
    }

    @Composable
    private fun monthNotFound(){
        Text(
            text = "Данные не найдены",
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp),
            modifier = Modifier.absoluteOffset(y = 100.dp)
        )
    }

    @Composable
    private fun drawMonthChangePanel() {
        Row (
            horizontalArrangement = Arrangement.Center
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

    @Composable
    private fun column(content: String, width: Int, height: Int) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .border(BorderStroke(0.5.dp, Color.Black))
        ) {
            Text(
                text = content,
                style = TextStyle(
                    fontSize = 10.sp
                )
            )
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
                    fontSize = 10.sp,
                    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
                    textAlign = if (isHeader) TextAlign.Center else TextAlign.Start
                )
            )
        }
    }

    @Composable
    private fun clientTable() {
        val groupList = controller.groupService.getClientGroups(model.selectedClient.value!!).filter { group -> group.period == model.selectedMonth.value }
        if(groupList.isEmpty()){
            monthNotFound()
        } else {

        val headers = listOf("Заказано", "Стоимость занятия", "Посещено", "Пропущено", "К списанию за посещение", "К списанию за пропуски", "Итого")
        val rowHeight = 25
        val fioWidth = 190
        val cellWidth = 100
        Column {
            Column (
                modifier = Modifier.absoluteOffset(y = 25.dp)
            ) {
                Row (
                ) {
                    fioColumn("Группа", fioWidth, rowHeight * 2, true)
                    headers.forEach { fioColumn(it, cellWidth, rowHeight * 2, true) }
                }


                groupList.forEach { group ->
                    Row(
                        modifier = Modifier.height(rowHeight.dp)
                    ) {
                        fioColumn(controller.getGroupTitle(group), fioWidth, rowHeight, false)
                        val subscription =
                            controller.subscriptionService.get(model.selectedClient.value!!, group.period)
                                .filter { subscription -> subscription.group == group }[0]
                            val price = group.price
                            val ordered = subscription.orderCount
                            val visitList =
                                controller.groupService.getVisitList(subscription.group, subscription.client)
                            val visited = visitList.filter { it.visitStatus == VisitStatus.VISITED }.size
                            val visitPayment = visited * group.price
                            val canceled = visitList.filter { it.visitStatus == VisitStatus.CANCELED }.size
                            val skipped = ordered - visited - canceled
                            val compensationRate = subscription.compensationRate
                            val skippedPayment = skipped * group.price
                            val totalPayment = visitPayment + skippedPayment
                            for (i in headers.indices) {
                                when (i) {
                                    0 -> column(ordered.toString(), cellWidth, rowHeight)
                                    1 -> column(price.toString(), cellWidth, rowHeight)
                                    2 -> column(visited.toString(), cellWidth, rowHeight)
                                    3 -> column(skipped.toString(), cellWidth, rowHeight)
                                    4 -> column(visitPayment.toString(), cellWidth, rowHeight)
                                    5 -> column(skippedPayment.toString(), cellWidth, rowHeight)
                                    6 -> column(totalPayment.toString(), cellWidth, rowHeight)
                                }
                        }
                    }
                }}
            }
        }
    }

    @Composable
    private fun groupTable() {
        val clientList = controller.groupService.getClientList(model.selectedGroup.value!!)
        val headers = listOf("Заказано", "Посещено", "Пропущено", "Ставка компенсации", "К списанию за посещение", "К списанию за пропуски", "Итого")
        val rowHeight = 25
        val fioWidth = 190
        val cellWidth = 100
        Column {
            Text(
                text = "Стоимость занятия: " + model.selectedGroup.value!!.price,
                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.offset(y = 15.dp)
            )


        Column (
            modifier = Modifier.absoluteOffset(y = 25.dp)
        ) {
            Row (
            ) {
                fioColumn("ФИО", fioWidth, rowHeight * 2, true)
                headers.forEach { fioColumn(it, cellWidth, rowHeight * 2, true) }
            }


                clientList.forEach { client ->
                    Row(
                        modifier = Modifier.height(rowHeight.dp)
                    ) {
                        fioColumn(clientString(client), fioWidth, rowHeight, false)

                        val subscription =
                            controller.subscriptionService.get(client, model.selectedGroup.value!!.period).filter { subscription -> subscription.group == model.selectedGroup.value }[0]
                        val ordered = subscription.orderCount
                        val visitList = controller.groupService.getVisitList(subscription.group, subscription.client)
                        val visited = visitList.filter { it.visitStatus == VisitStatus.VISITED }.size
                        val visitPayment = visited * model.selectedGroup.value!!.price
                        val canceled = visitList.filter { it.visitStatus == VisitStatus.CANCELED }.size
                        val skipped = ordered - visited - canceled
                        val compensationRate = subscription.compensationRate
                        val skippedPayment = skipped * model.selectedGroup.value!!.price
                        val totalPayment = visitPayment + skippedPayment

                        for (i in headers.indices) {
                            when (i) {
                                0 -> column(ordered.toString(), cellWidth, rowHeight)
                                1 -> column(visited.toString(), cellWidth, rowHeight)
                                2 -> column(skipped.toString(), cellWidth, rowHeight)
                                3 -> column(compensationRate.toString(), cellWidth, rowHeight)
                                4 -> column(visitPayment.toString(), cellWidth, rowHeight)
                                5 -> column(skippedPayment.toString(), cellWidth, rowHeight)
                                6 -> column(totalPayment.toString(), cellWidth, rowHeight)
                            }
                    }
                }
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

private fun clientString(client: Client): String {
    return String.format("%s %s %s", client.lastName, client.firstName, client.patronymic)
}