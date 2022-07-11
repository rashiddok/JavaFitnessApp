package ui.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import entity.Client
import entity.TransactionType
import service.TransactionService
import java.awt.Dialog
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.inject.Inject

class RebalanceDialog @Inject constructor(
    private val transactionService: TransactionService
) {
    private lateinit var closeCallback: Runnable
    private lateinit var client: Client

    val type = mutableStateOf(TransactionType.DEPOSIT)
    val amount = mutableStateOf(0)
    val comment = mutableStateOf("")

    fun init(client: Client, closeCallback: Runnable) {
        this.client = client
        this.closeCallback = closeCallback
    }

    @Composable
    fun show() {
        Dialog(
            visible = true,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
                    .apply {
                        size = Dimension(380, 380)
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
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Тип")
                    typeCombobox()
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Сумма")
                    amountInput()
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    labelColumn("Комментарий")
                    commentInput()
                }

                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                ) {
                    Button(
                        shape = CircleShape,
                        onClick = {
                            when (type.value) {
                                TransactionType.DEPOSIT -> transactionService.deposit(client, amount.value, comment.value)
                                TransactionType.WITHDRAWAL -> transactionService.withdraw(client, amount.value, comment.value)
                            }

                            // Композ ругается, если мы самостоятельно вызываем dispose() на диалоге, он хочет это делать сам.
                            // Поэтому меняем значение MutableState и композ сам все пересоберет.
                            closeCallback.run()
                        }
                    ) {
                        Text(
                            when (type.value) {
                                TransactionType.DEPOSIT -> "Пополнить"
                                TransactionType.WITHDRAWAL -> "Списать"
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun labelColumn(label: String) {
        Column (
            modifier = Modifier.width(150.dp)
        ) {
            Text(label)
        }
    }

    @Composable
    fun typeCombobox() {
        Column (
            modifier = Modifier.width(200.dp)
        ) {
            val expanded = remember { mutableStateOf(false) }

            TextField(
                value = typeString(type.value),
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
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
                TransactionType.values().forEach { transactionType ->
                    DropdownMenuItem(
                        onClick = {
                            type.value = transactionType
                            expanded.value = false
                        },
                        modifier = Modifier
                            .background( if (type.value == transactionType)
                                MaterialTheme.colors.primary else  Color.Transparent
                            )
                    ) {
                        Text(
                            text = typeString(transactionType),
                            color = if (type.value == transactionType)
                                Color.White else  Color.Black
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun amountInput() {
        Column (
            modifier = Modifier.width(200.dp)
        ) {
            TextField(
                value = amount.value.toString(),
                onValueChange = { input ->
                    if (Regex("[1-9]+\\d*").matches(input)) amount.value = input.toInt()
                },
                singleLine = true
            )
        }
    }

    @Composable
    private fun commentInput() {
        Column (
            modifier = Modifier.width(200.dp).fillMaxWidth()
        ) {
            TextField(
                value = comment.value,
                onValueChange = { input -> comment.value = input },
                maxLines = 5,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

private fun typeString(transactionType: TransactionType): String {
    return when (transactionType) {
        TransactionType.DEPOSIT -> "Пополнение"
        TransactionType.WITHDRAWAL -> "Списание"
    }
}