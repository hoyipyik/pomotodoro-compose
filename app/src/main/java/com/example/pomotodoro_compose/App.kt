package  com.example.pomotodoro_compose

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pomotodoro_compose.container.BottomSheetContainer
import com.example.pomotodoro_compose.router.PageNavigation
import com.example.pomotodoro_compose.viewModel.StateViewModel
import com.example.pomotodoro_compose.viewModel.TasksViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val stateViewModel: StateViewModel = viewModel()
    val tasksViewModel : TasksViewModel = viewModel()
    val navController = rememberNavController()
    val bottomSheetNavController = rememberNavController()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(20.dp),
        sheetContent = {
            SheetContent(
                navController = bottomSheetNavController,
                scope = scope,
                bottomSheetState = bottomSheetState,
                stateViewModel = stateViewModel,
                tasksViewModel = tasksViewModel)
        }
    ) {
        PageContent(
            scope = scope,
            bottomSheetState = bottomSheetState,
            navController = navController,
            bottomSheetNavController = bottomSheetNavController,
            stateViewModel = stateViewModel,
            tasksViewModel = tasksViewModel,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent(
    navController: NavHostController,
    stateViewModel: StateViewModel,
    tasksViewModel: TasksViewModel,
    bottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    BottomSheetContainer(navController = navController, stateViewModel = stateViewModel, tasksViewModel = tasksViewModel, scope = scope, bottomSheetState = bottomSheetState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageContent(
    scope: CoroutineScope,
    navController: NavHostController,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetNavController: NavHostController,
    tasksViewModel: TasksViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navBackStackEntryBottomSheet by bottomSheetNavController.currentBackStackEntryAsState()
    val currentRouteBottomSheet = navBackStackEntryBottomSheet?.destination?.route
    Scaffold(
        topBar = { TopBar(stateViewModel = stateViewModel, currentRoute = currentRoute) },
        floatingActionButton = {
            if (currentRoute != "account")
                FloatingActionButton(onClick = {
                    bottomSheetNavController.navigate("addtask") {
                        currentRouteBottomSheet?.let { popUpTo(it) { inclusive = true } }
                    }
                    scope.launch { bottomSheetState.show() }
                    // useful when passing stateViewmodel but only one parametre is contained so useless
//                    currentRoute?.let { stateViewModel.changeCurrentRouterPath(it) }
                }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                stateViewModel = stateViewModel,
                currentRoute = currentRoute
            )
        }
    ) {
        PageNavigation(
            scope = scope,
            bottomSheetState = bottomSheetState,
            navController = navController,
            tasksViewModel = tasksViewModel,
            currentRouteBottomSheet = currentRouteBottomSheet,
            bottomSheetNavController = bottomSheetNavController
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    stateViewModel: StateViewModel,
    currentRoute: String?
) {
    Log.i("/currentRoute", currentRoute.toString())
    var selectedItem by remember { mutableStateOf("board") }
    val items = stateViewModel.bottomNavigationData
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                selected = selectedItem == item.type,
                icon = { Icon(item.icon, contentDescription = null) },
                onClick = {
                    selectedItem = item.type
                    navController.navigate(item.type) {
                        currentRoute?.let { popUpTo(it) { inclusive = true } }
                    }
                    stateViewModel.changeCurrentRouterPath(item.type)
                    stateViewModel.changeTopBarTitle(item.title)
                }
            )
        }
    }
}

@Composable
fun TopBar(stateViewModel: StateViewModel, currentRoute: String?) {
    val title = stateViewModel.topBarTitle
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = null)
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            when(currentRoute){
                "todo" -> {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                    }
                }
                "board" ->{
                    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                        Row() {
                            Icon(Icons.Filled.Add, contentDescription = null)
                            Text(text = "Add Tag")
                        }
                    }
                }
                "account" ->{
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }       
                }
            }
        },
    )
}


