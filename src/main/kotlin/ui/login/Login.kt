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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import entity.*
import service.GroupService
import service.SubscriptionService
import service.UserService
import javax.inject.Inject

class Login @Inject constructor(
    private val userService: UserService
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
                  modifier = Modifier.padding(15.dp)
              ) {
                  Text(text = "Авторизация", fontSize = 24.sp)
              }
              Row (
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(5.dp)
              ) {
//                  labelColumn("Логин")
                  Column {
                      TextField(
                          value = username.value,
                          label = { Text("Логин") },
                          onValueChange = {input -> username.value = input},
                      )
                  }
              }
              Row (
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(5.dp)
              ) {
//                  labelColumn("Пароль")
                  Column {
                      TextField(
                          value = password.value,
                          label = { Text("Пароль") },
                          onValueChange = {input -> password.value = input},
                          visualTransformation = PasswordVisualTransformation()
                      )
                  }
              }
              Row (
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.Center){
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
        val user = userService.find(username.value) ?: return
        if(password.value == user.password){
            loggedIn.value = true
            role.value = user.isAdmin == "true"
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