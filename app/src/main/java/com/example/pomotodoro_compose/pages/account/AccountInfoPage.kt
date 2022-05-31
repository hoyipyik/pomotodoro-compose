package com.example.pomotodoro_compose.pages.account


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel

@Composable
fun AccountInfoPage(
    tasksViewModel: TasksViewModel,
    stateViewModel: StateViewModel,
    groupTagViewModel: GroupTagViewModel
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("http://123.56.107.143:4000/")) }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(0.34f)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Icon(Icons.Filled.AccountCircle, contentDescription = null, Modifier.size(64.dp))
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Text(
                text = tasksViewModel.accountId,
//                text = "Hoyipyik",
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.width(140.dp)) {
                    Icon(
                        Icons.Filled.LocationCity,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Text(text = "Online", modifier = Modifier.padding(2.dp))
                }
                Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.width(140.dp)) {
                    Icon(
                        Icons.Filled.Book,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = tasksViewModel.doneBoardWorkNum.toString() + " Tasks done",
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
        Divider(thickness = 2.dp)
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .padding(vertical = 11.dp)
                    .height(30.dp)
                    .clickable {
                        context.startActivity(intent)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Web, contentDescription = null)
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Text(text = "Use Pomotodoro Online")
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 11.dp)
                    .height(30.dp)
                    .clickable {
                        tasksViewModel.clearAllTasks(tasksViewModel.accountId)
                        groupTagViewModel.clearAllData()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.CleaningServices, contentDescription = null)
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Text(text = "Clean All Tasks")
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 11.dp)
                    .height(30.dp)
                    .clickable {
                        tasksViewModel.deleteAccount(tasksViewModel.accountId)
                        tasksViewModel.logout()
                        groupTagViewModel.logout()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Delete, contentDescription = null)
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Text(text = "Delete Account")
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 11.dp)
                    .height(30.dp)
                    .clickable {
                        tasksViewModel.logout()
                        groupTagViewModel.logout()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Logout,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 2.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.fillMaxWidth(0.05f))
                Text(text = "Logout", color = MaterialTheme.colors.primary)
                Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            }
        }
    }
}