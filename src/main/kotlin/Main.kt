import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.inject.Guice
import dao.HibernateSessionFactory
import di.DiModule
import ui.ClientTabContent
import ui.GroupTabContent

class Main{}

fun main() {

    val injector = Guice.createInjector(DiModule())
    // инициализация фабрики должна происходить до создания панелей с целью инициализации енамов
    injector.getInstance(HibernateSessionFactory::class.java).apply { init() }
    val clientTabContent = injector.getInstance(ClientTabContent::class.java).apply { init() }
    val groupTabContent = injector.getInstance(GroupTabContent::class.java).apply { init() }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Fitness Administration App",
            state = rememberWindowState(width = 1300.dp, height = 800.dp),
            resizable = false
        ) {
            MaterialTheme {
                val selectedTabIndex = remember { mutableStateOf(0) }

                Column {
                    Row {
                        TabRow(
                            selectedTabIndex = selectedTabIndex.value
                        ) {
                            val selectedTabStyle = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Bold)
                            Tab(
                                selected = selectedTabIndex.value == 0,
                                onClick = { selectedTabIndex.value = 0 },
                                text = {
                                    Text("Группы", style = if (selectedTabIndex.value == 0) selectedTabStyle else LocalTextStyle.current)
                                }
                            )

                            Tab(
                                selected = selectedTabIndex.value == 1,
                                onClick = { selectedTabIndex.value = 1 },
                                text = {
                                    Text("Клиенты", style = if (selectedTabIndex.value == 1) selectedTabStyle else LocalTextStyle.current)
                                }
                            )
                        }
                    }

                    Row {
                        when (selectedTabIndex.value) {
                            0 -> groupTabContent.show()
                            1 -> clientTabContent.show()
                        }
                    }
                }
            }
        }
    }
}
