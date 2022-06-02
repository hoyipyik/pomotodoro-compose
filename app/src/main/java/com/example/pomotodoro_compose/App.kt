package  com.example.pomotodoro_compose

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pomotodoro_compose.container.BottomSheetContainer
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import com.example.pomotodoro_compose.data.viewModel.StateViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModel
import com.example.pomotodoro_compose.data.viewModel.TasksViewModelFactory
import com.example.pomotodoro_compose.router.PageNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val context: Context = LocalContext.current
    val stateViewModel: StateViewModel = viewModel()
    val tasksViewModel: TasksViewModel = viewModel(
        factory = TasksViewModelFactory(context.applicationContext as Application)
    )
    val groupTagViewModel: GroupTagViewModel = viewModel()
    val navController = rememberNavController()
    val bottomSheetNavController = rememberNavController()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            SheetContent(
                groupTagViewModel = groupTagViewModel,
                scope = scope,
                bottomSheetNavController = bottomSheetNavController,
                bottomSheetState = bottomSheetState,
                stateViewModel = stateViewModel,
                tasksViewModel = tasksViewModel
            )
        }
    ) {
        PageContent(
            scope = scope,
            bottomSheetNavController = bottomSheetNavController,
            groupTagViewModel = groupTagViewModel,
            bottomSheetState = bottomSheetState,
            navController = navController,
            stateViewModel = stateViewModel,
            tasksViewModel = tasksViewModel,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent(
    stateViewModel: StateViewModel,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupTagViewModel: GroupTagViewModel,
    bottomSheetNavController: NavHostController
) {
    BottomSheetContainer(
        bottomSheetNavController = bottomSheetNavController,
        groupTagViewModel = groupTagViewModel,
        stateViewModel = stateViewModel,
        tasksViewModel = tasksViewModel,
        scope = scope,
        bottomSheetState = bottomSheetState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageContent(
    scope: CoroutineScope,
    navController: NavHostController,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState,
    tasksViewModel: TasksViewModel,
    groupTagViewModel: GroupTagViewModel,
    bottomSheetNavController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navBackStackEntry2 by bottomSheetNavController.currentBackStackEntryAsState()
    val currentRouteBottomSheet = navBackStackEntry2?.destination?.route
    Scaffold(
        topBar = {
            TopBar(
                stateViewModel = stateViewModel,
                currentRoute = currentRoute,
                scope = scope,
                bottomSheetState = bottomSheetState,
                tasksViewModel = tasksViewModel,
                groupTagViewModel = groupTagViewModel
            )
        },
        floatingActionButton = {
            if (currentRoute != "account"
                && stateViewModel.subNavRoute != "timeline"
            )
                FloatingActionButton(
                    onClick = {
                        if (currentRoute == "board") {
                            bottomSheetNavController.navigate("addtask") {
                                currentRouteBottomSheet?.let { popUpTo(it) { inclusive = true } }
                            }
                        }
                        stateViewModel.changeCurrentRouteBottomSheetPath("addtask")
                        scope.launch { bottomSheetState.show() }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                stateViewModel = stateViewModel,
                currentRoute = currentRoute,
                tasksViewModel = tasksViewModel,
            )
        }
    ) {
        PageNavigation(
            scope = scope,
            bottomSheetNavController = bottomSheetNavController,
            groupTagViewModel = groupTagViewModel,
            bottomSheetState = bottomSheetState,
            navController = navController,
            tasksViewModel = tasksViewModel,
            stateViewModel = stateViewModel
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    stateViewModel: StateViewModel,
    currentRoute: String?,
    tasksViewModel: TasksViewModel
) {
    Log.i("/currentRoute", currentRoute.toString())
    var selectedItem by remember { mutableStateOf("account") }
    val items = stateViewModel.bottomNavigationData
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.onPrimary,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = selectedItem == item.type,
                icon = { Icon(item.icon, contentDescription = null) },
                onClick = {
                    stateViewModel.subNavRoute = "today"
                    selectedItem = item.type
                    navController.navigate(item.type) {
                        currentRoute?.let { popUpTo(it) { inclusive = true } }
                        if (item.type == "account") {
                            tasksViewModel.calculateTotalDoneNum()
                        }
                    }
                    stateViewModel.changeCurrentRouterPath(item.type)
                    stateViewModel.changeTopBarTitle(item.title)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopBar(
    stateViewModel: StateViewModel,
    currentRoute: String?,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    groupTagViewModel: GroupTagViewModel,
    tasksViewModel: TasksViewModel
) {
    val title = stateViewModel.topBarTitle
    var expanded by remember { mutableStateOf(false) }

    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:") // only email apps should handle this
    intent.putExtra(Intent.EXTRA_EMAIL, "hoyipyik@proton.me")
    intent.putExtra(Intent.EXTRA_SUBJECT, "I write to give you some feedback")

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            "I am using pomotodoro to manage my work, you can try it too. :) \n" +
                    "Here is the download link: \n" + "https://123.56.107.143/index.php/s/zfLpcCZ8pg4PBWr"
        )
        // (Optional) Here we're setting the title of the content
        putExtra(Intent.EXTRA_TITLE, "Try Pomotodoro here")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    val shareResultIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT, "Here are my results: \n"
                    + "Accomplished tasks number: ${tasksViewModel.doneTodoWorkNum} \n" +
                    "Unchecked tasks number ${tasksViewModel.unfinishedTodoWorkNum} \n" +
                    "Overdue tasks number ${tasksViewModel.overdueTaskNum}"
        )
        // (Optional) Here we're setting the title of the content
        putExtra(Intent.EXTRA_TITLE, "Pomotodoro Result")
        type = "text/plain"
    }
    val shareResultsIntent = Intent.createChooser(shareResultIntent, null)

    val context = LocalContext.current
    TopAppBar(
        backgroundColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            when (currentRoute) {
                "todo" -> {
                    IconButton(onClick = {
                        startActivity(context, shareResultsIntent, null)
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                    }
                }
                "board" -> {
                    Button(
                        onClick = {
//                        bottomSheetNavController.navigate("addgrouptag") {
//                            popUpTo(stateViewModel.currentRouteBottomSheetPath) { inclusive = true }
//                        }
                            stateViewModel.changeCurrentRouteBottomSheetPath("addgrouptag")
                            scope.launch { bottomSheetState.show() }
                        },
                        modifier = Modifier
                            .width(139.dp)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Text(text = "Add Tag")
                        }
                    }
                }
                "account" -> {
                    IconButton(onClick = {
                        startActivity(context, shareIntent, null)
                    }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            }
        },
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(onClick = {
//            tasksViewModel.refreshData()
//            groupTagViewModel.refreshGroupTagData()
        }) {
            Text("Refresh")
        }
//        DropdownMenuItem(onClick = { /* Handle settings! */ }) {
//            Text("Settings")
//        }
        DropdownMenuItem(onClick = {
            startActivity(context, intent, null)
        }) {
            Text("Feedback")
        }
    }
}


