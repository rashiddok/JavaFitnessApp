import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.inject.Guice
import entity.Client
import ui.ClientTabContent
import ui.GroupTabContent

class Main{

}

val selectedClient: MutableState<Client?> = mutableStateOf(null)
val injector = Guice.createInjector()
val clientTabContent = injector.getInstance(ClientTabContent::class.java).apply { init() }
val groupTabContent = injector.getInstance(GroupTabContent::class.java).apply { init() }

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Fitness Administration App",
        state = rememberWindowState(width = 1000.dp, height = 700.dp),
        resizable = false
    ) {
        MaterialTheme {
            val selectedTabIndex = remember { mutableStateOf(0) }

            Column {
                Row {
                    tabBar(selectedTabIndex)
                }

                Row {
                    tadContent(selectedTabIndex)
                }
            }
        }
    }
}

@Composable
fun tabBar(selectedTabIndex: MutableState<Int>) {
    TabRow(
        selectedTabIndex = selectedTabIndex.value
    ) {
        Tab(
            selected = selectedTabIndex.value == 0,
            onClick = { selectedTabIndex.value = 0 },
            text = { Text("Группы") }
        )

        Tab(
            selected = selectedTabIndex.value == 1,
            onClick = { selectedTabIndex.value = 1 },
            text = { Text("Клиенты")}
        )
    }
}

@Composable
fun tadContent(selectedTabIndex: MutableState<Int>) {
    when (selectedTabIndex.value) {
        0 -> groupTabContent.show()
        1 -> clientTabContent.show()
    }
}
