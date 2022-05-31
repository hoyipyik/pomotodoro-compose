package com.example.pomotodoro_compose.pages.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import java.io.File

@Composable
fun LoginPage(tasksViewModel: TasksViewModel, stateViewModel: StateViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Row(modifier = Modifier.padding(10.dp)) {
            Text(text = "Login", color = MaterialTheme.colors.primary,fontSize = 20.sp)
            Text(text = " / Signup", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.08f))
        OutlinedTextField(
            value = stateViewModel.accountInputText,
            label = { Text(text = "Account")},
            onValueChange = {stateViewModel.accountInputText(it)}
        )
        Spacer(modifier = Modifier.padding(10.dp))
        OutlinedTextField(
            value = stateViewModel.passwdInputText,
            label = { Text(text = "Password")},
            onValueChange = {stateViewModel.passwdInputText(it)}
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.18f))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(0.8f).height(45.dp)) {
            Text(text = "Submit")
        }
    }
}