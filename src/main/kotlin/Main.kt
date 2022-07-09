import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.inject.Guice
import entity.Client
import ui.ClientTabPanel

class Main{

}

val selectedClient: MutableState<Client?> = mutableStateOf(null)
val clientTabPanel = Guice.createInjector().getInstance(ClientTabPanel::class.java).apply { init() }

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Fitness Administration App",
        state = rememberWindowState(width = 1000.dp, height = 700.dp),
        resizable = false
    ) {
        MaterialTheme {
            clientTabPanel.show()
        }
    }
}
