package ui.closeGroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import entity.*
import service.GroupService
import service.SubscriptionService
import java.awt.Dialog
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CloseGroupDialog @Inject constructor(
    private val groupService: GroupService,
    private val subscriptionService: SubscriptionService
) {
    private lateinit var group: Group
    private lateinit var closeCallback: Runnable

    private val headers = listOf("Заказано", "Посещено", "К списанию за посещение", "Пропущено", "Ставка компенсации", "К списанию за пропуски", "Итого к списанию")

    fun init(group: Group, closeCallback: Runnable) {
        this.group = group
        this.closeCallback = closeCallback
    }

    @Composable
    fun show() {
        Dialog(
            visible = true,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
                    .apply {
                        size = Dimension(1100, 800)
                        isResizable = false
                        addWindowListener(object : WindowAdapter() {
                            override fun windowClosing(e: WindowEvent?) {
                                closeCallback.run()
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

                // Название
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    title()
                }

                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Column(
                        modifier = Modifier.width(200.dp)
                    ) {
                        Text(
                            text = "Стоимость занятия:",
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }

                    Column {
                        Text(
                            text = group.price.toString(),
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                ) {
                    table()
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        shape = CircleShape,
                        onClick = {
                            groupService.close(group)
                            closeCallback.run()
                        }
                    ){
                        Text("ЗАКРЫТЬ ГРУППУ")
                    }
                }
            }
        }
    }

    @Composable
    fun title() {
        Text(
            text = groupString(group),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

    @Composable
    private fun table() {
        val clientList = groupService.getClientList(group)

        val rowHeight = 30
        val fioWidth = 300
        val cellWidth = 105

        Column (
            modifier = Modifier.border(BorderStroke(1.dp, Color.Black))
        ) {
            Row (
                //modifier = Modifier.height(rowHeight.dp)
            ) {
                fioColumn("ФИО", fioWidth, rowHeight * 2, true)

                headers.forEach { fioColumn(it, cellWidth, rowHeight * 2, true) }
            }

            clientList.forEach{client ->
                Row (
                    modifier = Modifier.height(rowHeight.dp)
                ) {
                    fioColumn(clientString(client), fioWidth, rowHeight, false)

                    val subscription = subscriptionService.get(client, group.period)[0]
                    val ordered = subscription.orderCount
                    val visitList = groupService.getVisitList(subscription.group, subscription.client)
                    val visited = visitList.filter { it.visitStatus == VisitStatus.VISITED }.size
                    val visitPayment = visited * group.price
                    val canceled = visitList.filter { it.visitStatus == VisitStatus.CANCELED }.size
                    val skipped = ordered - visited - canceled
                    val compensationRate = subscription.compensationRate
                    val skippedPayment = skipped * compensationRate
                    val totalPayment = visitPayment + skippedPayment

                    for (i in headers.indices) {
                        when (i) {
                            0 -> column(ordered.toString(), cellWidth, rowHeight)
                            1 -> column(visited.toString(), cellWidth, rowHeight)
                            2 -> column(visitPayment.toString(), cellWidth, rowHeight)
                            3 -> column(skipped.toString(), cellWidth, rowHeight)
                            4 -> column(compensationRate.toString(), cellWidth, rowHeight)
                            5 -> column(skippedPayment.toString(), cellWidth, rowHeight)
                            6 -> column(totalPayment.toString(), cellWidth, rowHeight)
                        }
                    }
                }
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
                    fontSize = 12.sp
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
                    fontSize = 14.sp,
                    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Medium,
                    textAlign = if (isHeader) TextAlign.Center else TextAlign.Start
                )
            )
        }
    }
}

private fun groupString(group: Group): String {
    val period = group.period.format(DateTimeFormatter.ofPattern("MM.yy"))
    val type = when (group.workoutType){
        WorkoutType.AEROBICS -> "Аэробика"
        WorkoutType.PILATES -> "Пилатес"
        WorkoutType.YOGA -> "Йога"
    }

    return String.format("%s %s", type, period)
}

private fun workoutDateString(workout: Workout): String {
    return workout.date.format(DateTimeFormatter.ofPattern("dd.MM"))
}

private fun clientString(client: Client): String {
    return String.format("%s %s %s", client.lastName, client.firstName, client.patronymic)
}