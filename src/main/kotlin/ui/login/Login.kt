package ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import entity.*
import service.GroupService
import service.SubscriptionService
import javax.inject.Inject

class Login @Inject constructor(
) {
    lateinit var loggedIn: MutableState<Boolean>
    lateinit var role: MutableState<Boolean>

    var username: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")

    fun init(isLoggedIn: MutableState<Boolean>, roleState: MutableState<Boolean>) {
        loggedIn = isLoggedIn
        role = roleState
    }

    @Composable
    fun show() {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().fillMaxHeight()){
          Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
              Row (
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(5.dp)
              ) {
                  labelColumn("Логин")
                  Column {
                      TextField(
                          value = username.value,
                          onValueChange = {input -> username.value = input},
                      )
                  }
              }
              Row (
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(5.dp)
              ) {
                  labelColumn("Пароль")
                  Column {
                      TextField(
                          value = password.value,
                          onValueChange = {input -> password.value = input},
                      )
                  }
              }
              Row{
                  Button(
                      shape = CircleShape,
                      modifier = Modifier.width(200.dp),
                      onClick = {
                          checkCreds()
                      }
                  ){
                      Text("Войти")
                  }
              }

          }
        }

    }

    fun checkCreds(){
        if(username.value == "admin" && password.value == "12345678"){
            loggedIn.value = true
            role.value = true
        } else if(username.value == "buh" && password.value == "12345678") {
            loggedIn.value = true
            role.value = false
        }
    }

    @Composable
    fun labelColumn(label: String) {
        Column (
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(90.dp)
        ) {
            Text(label)
        }
    }

}