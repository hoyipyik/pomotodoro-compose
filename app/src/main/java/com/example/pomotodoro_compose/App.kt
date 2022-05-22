package  com.example.pomotodoro_compose

import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pomotodoro_compose.container.BottomSheetContainer
import com.example.pomotodoro_compose.router.PageNavigation
import com.example.pomotodoro_compose.viewModel.StateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val stateViewModel: StateViewModel = viewModel()
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
        sheetContent = { SheetContent(navController = bottomSheetNavController, stateViewModel = stateViewModel) }
    ) {
        PageContent(
            scope = scope,
            bottomSheetState = bottomSheetState,
            navController = navController,
            bottomSheetNavController = bottomSheetNavController,
            stateViewModel = stateViewModel
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent(navController: NavHostController, stateViewModel: StateViewModel) {
    BottomSheetContainer(navController = navController, stateViewModel = stateViewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageContent(
    scope: CoroutineScope,
    navController: NavHostController,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState,
    bottomSheetNavController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navBackStackEntry2 by bottomSheetNavController.currentBackStackEntryAsState()
    val currentRoute2 = navBackStackEntry2?.destination?.route
    Scaffold(
        topBar = { TopBar(stateViewModel = stateViewModel) },
        floatingActionButton = {
            if (currentRoute != "account")
                FloatingActionButton(onClick = {
                    scope.launch { bottomSheetState.show() }
                    bottomSheetNavController.navigate("addtask") {
                        currentRoute2?.let { popUpTo(it) { inclusive = true } }
                    }
                    currentRoute?.let { stateViewModel.changeCurrentRouterPath(it) }
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
            navController = navController
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
                    stateViewModel.changeTopBarTitle(item.title)
                }
            )
        }
    }
}

@Composable
fun TopBar(stateViewModel: StateViewModel) {
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
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        },
    )
}


