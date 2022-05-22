package com.example.pomotodoro_compose.viewModel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.BottomNavigationData

class StateViewModel : ViewModel() {
    private var _topBarTitle by mutableStateOf("Board")
    var topBarTitle: String
        get() = _topBarTitle
        set(value) {
            _topBarTitle = value
        }
    fun changeTopBarTitle(newTitle: String) {
        topBarTitle = newTitle
    }

    private val _botomNavigationDataList = listOf<BottomNavigationData>(
        BottomNavigationData(type = "todo", title = "Today's Task", icon = Icons.Filled.Task),
        BottomNavigationData(type = "board", title = "Board", icon = Icons.Filled.Dashboard),
        BottomNavigationData(type = "account", title = "Account", icon = Icons.Filled.Person),
    )
    val bottomNavigationData
        get() = _botomNavigationDataList


    private var _currentRouterPath by mutableStateOf("board")
    val currentRouterPath
        get() = _currentRouterPath

    fun changeCurrentRouterPath(path: String){
        _currentRouterPath = path
    }
}