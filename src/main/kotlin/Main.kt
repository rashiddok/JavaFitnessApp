import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.inject.Guice
import entity.Client
import ui.client.ClientPanel
import ui.clientList.ClientListPanel

class Main{

}

val selectedClient: MutableState<Client?> = mutableStateOf(null)
val inj = Guice.createInjector()
val clientPanel = inj.getInstance(ClientPanel::class.java).apply { init(selectedClient) }
val clientListPanel = inj.getInstance(ClientListPanel::class.java).apply { init(selectedClient) }

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Fitness Administration App",
        state = rememberWindowState(width = 1000.dp, height = 700.dp),
        resizable = false
    ) {
        MaterialTheme {
            Row {
                Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f)) {
                    clientListPanel.show()
                }

                Column {
                    Divider(color = MaterialTheme.colors.primary, modifier = Modifier.fillMaxHeight().width(1.dp))
                }

                Column(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
                    clientPanel.show()
                }
            }
        }
    }
}
