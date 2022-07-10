package ui.createClient

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeDialog
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.awt.Dialog
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class NewClientDialog {
    private lateinit var closeCallback: Runnable

    private val firstName = mutableStateOf("")
    private val lastName = mutableStateOf("")
    private val patronymic = mutableStateOf("")

    fun init(closeCallback: Runnable) {
        this.closeCallback = closeCallback
    }

    @Composable
    fun show() {
        Dialog(
            visible = true,
            create = {
                ComposeDialog(null, Dialog.ModalityType.APPLICATION_MODAL)
                    .apply {
                        size = Dimension(400, 300)
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
        ){
            Column (
                modifier = Modifier.padding(10.dp)
            ) {
                fioRow("Имя", firstName)
                fioRow("Фамилия", lastName)
                fioRow("Отчество", patronymic)
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                ) {
                    Button(
                        onClick = {
                            // TODO save client
                            // Композ ругается, если мы самостоятельно вызываем dispose() на диалоге, он хочет это делать сам.
                            // Поэтому меняем значение MutableState и композ сам все пересоберет.
                            closeCallback.run()
                        }
                    ) {
                        Text("СОЗДАТЬ")
                    }
                }
            }
        }
    }

    @Composable
    private fun fioRow(label: String, inputState: MutableState<String>) {
        remember { inputState }

        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.width(150.dp)
            ) {
                Text(label)
            }

            Column (
                modifier = Modifier.width(200.dp).fillMaxWidth()
            ) {
                TextField(
                    value = inputState.value,
                    onValueChange = { input -> inputState.value = input },
                    singleLine = true
                )
            }
        }
    }
}