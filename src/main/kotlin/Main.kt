import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.MutableState
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
import ui.SalesTabContent
import ui.SheduleTabContent
import ui.login.Login

class Main{}

fun main() {

    val loggedIn: MutableState<Boolean> = mutableStateOf(false)
    val role: MutableState<Boolean> = mutableStateOf(false)
    val injector = Guice.createInjector(DiModule())
    injector.getInstance(HibernateSessionFactory::class.java).apply { init() }
    val clientTabContent = injector.getInstance(ClientTabContent::class.java).apply { init(role.value) }
    val groupTabContent = injector.getInstance(GroupTabContent::class.java).apply { init() }
    val scheduleTabContent = injector.getInstance(SheduleTabContent::class.java).apply { init() }
    val salesTabContent = injector.getInstance(SalesTabContent::class.java).apply { init() }
    val loginForm = injector.getInstance(Login::class.java).apply { init(loggedIn, role) }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Fitness Administration App",
            state = rememberWindowState(width = 1300.dp, height = 800.dp),
            resizable = false
        ) {
            MaterialTheme {
                val selectedTabIndex = remember { mutableStateOf(0) }
                val setIndex = fun(value: Int){
                    if(role.value){
                        selectedTabIndex.value = value
                    } else{
                        if(value == 1){
                            selectedTabIndex.value =  0
                        }
                        if(value == 3){
                            selectedTabIndex.value =  1
                        }
                    }
                }
                if (loggedIn.value == false) {
                    loginForm.show()
                } else {
                    Column {
                        Row {
                            TabRow(
                                selectedTabIndex = selectedTabIndex.value
                            ) {
                                val selectedTabStyle = TextStyle(fontSize = 19.sp, fontWeight = FontWeight.Bold)
                                if(role.value){
                                Tab(
                                    selected = selectedTabIndex.value == 0,
                                    onClick = { selectedTabIndex.value = 0 },
                                    text = {
                                        Text(
                                            "Группы",
                                            style = if (selectedTabIndex.value == 0) selectedTabStyle else LocalTextStyle.current
                                        )
                                    }
                                )
                                }

                                Tab(
                                    selected = selectedTabIndex.value == 1,
                                    onClick = { setIndex(1) },
                                    text = {
                                        Text(
                                            "Клиенты",
                                            style = if (selectedTabIndex.value == 1) selectedTabStyle else LocalTextStyle.current
                                        )
                                    }
                                )
                                if(role.value) {
                                    Tab(
                                        selected = selectedTabIndex.value == 2,
                                        onClick = { selectedTabIndex.value = 2 },
                                        text = {
                                            Text(
                                                "Расписание",
                                                style = if (selectedTabIndex.value == 2) selectedTabStyle else LocalTextStyle.current
                                            )
                                        }
                                    )
                                }
                                Tab(
                                    selected = selectedTabIndex.value == 3,
                                    onClick = { setIndex(3) },
                                    text = {
                                        Text(
                                            "Выписки",
                                            style = if (selectedTabIndex.value == 3) selectedTabStyle else LocalTextStyle.current
                                        )
                                    }
                                )
                            }
                        }

                        Row {
                            if(role.value){
                                when (selectedTabIndex.value) {
                                    0 -> groupTabContent.show()
                                    1 -> clientTabContent.show()
                                    2 -> scheduleTabContent.show()
                                    3 -> salesTabContent.show()
                                }
                            } else {
                                when (selectedTabIndex.value) {
                                    0 -> clientTabContent.show()
                                    1 -> salesTabContent.show()
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
