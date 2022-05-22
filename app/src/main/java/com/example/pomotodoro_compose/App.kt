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
import com.example.pomotodoro_compose.router.BottomSheetNavigation
import com.example.pomotodoro_compose.router.PageNavigation
import com.example.pomotodoro_compose.viewModel.StateViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun App() {
    val stateViewModel: StateViewModel = viewModel()
    val navController = rememberNavController()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetElevation = 16.dp,
        sheetShape = RoundedCornerShape(20.dp),
        sheetContent = { SheetContent() }
    ) {
        PageContent(
            scope = scope,
            bottomSheetState = bottomSheetState,
            navController = navController,
            stateViewModel = stateViewModel
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetContent() {
    BottomSheetNavigation()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageContent(
    scope: CoroutineScope,
    navController: NavHostController,
    stateViewModel: StateViewModel,
    bottomSheetState: ModalBottomSheetState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopBar(stateViewModel = stateViewModel) },
        floatingActionButton = {
            if(currentRoute != "account")
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        bottomBar = { BottomBar(navController = navController, stateViewModel = stateViewModel, currentRoute = currentRoute) }
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
                    navController.navigate(item.type){
                        currentRoute?.let { popUpTo(it){ inclusive = true } }
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


