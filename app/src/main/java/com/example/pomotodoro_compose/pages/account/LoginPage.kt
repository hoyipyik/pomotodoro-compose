package com.example.pomotodoro_compose.pages.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pomotodoro_compose.R
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginPage(
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel
) {
    LaunchedEffect(tasksViewModel.logFailFlag) {
        if (tasksViewModel.logFailFlag) {
            delay(2000L)
            tasksViewModel.changeLogFailFlag(false)
        }
    }

    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.05f))
        Text(
            text = "Pomotodoro",
            fontFamily = MaterialTheme.typography.h1.fontFamily,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth(0.8f)
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Row {
            Text(text = "Login", color = MaterialTheme.colors.primary)
            Text(text = " / Signup")
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.02f))
        OutlinedTextField(
            value = stateViewModel.accountInputText,
            label = { Text(text = "Account") },
            onValueChange = { stateViewModel.accountInputText(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.03f))
        OutlinedTextField(
            value = stateViewModel.passwdInputText,
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { stateViewModel.passwdInputText(it) }
        )
        if (tasksViewModel.logFailFlag) {
            Column() {
                Spacer(modifier = Modifier.fillMaxHeight(0.08f))
                Text(text = "Login Fail", color = MaterialTheme.colors.error)
                Spacer(modifier = Modifier.fillMaxHeight(0.08f))
            }
        }else{
            Spacer(modifier = Modifier.fillMaxHeight(0.14f))
        }
        Button(
            onClick = {
                if(stateViewModel.passwdInputText != "" && stateViewModel.accountInputText != "") {
                    tasksViewModel.sendLogInfo(
                        id = stateViewModel.accountInputText,
                        password = stateViewModel.passwdInputText
                    )
                    groupTagViewModel.sendLogInfo(
                        id = stateViewModel.accountInputText,
                        password = stateViewModel.passwdInputText
                    )
                    stateViewModel.restoreInputText()
                    focusManager.clearFocus()
                }
            }, modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(45.dp)
        ) {
            Text(text = "Submit")
        }
    }
}