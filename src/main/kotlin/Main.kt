import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.inject.Guice
import entity.Client
import ui.client.ClientPanel

class Main{

}

fun main() = application {
    val inj = Guice.createInjector()
    val client = Client("name", "secondname", "patronymic")
    val clientPanel = inj.getInstance(ClientPanel::class.java).apply { init(client) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Fitness Administration App",
        state = rememberWindowState(width = 1000.dp, height = 700.dp),
        resizable = false
    ) {
        MaterialTheme {
            clientPanel.show()
        }
    }
}
