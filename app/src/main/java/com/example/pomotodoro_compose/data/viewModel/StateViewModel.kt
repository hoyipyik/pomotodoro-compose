package com.example.pomotodoro_compose.data.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pomotodoro_compose.data.entity.BottomNavigationData

class StateViewModel : ViewModel() {
    private var _topBarTitle by mutableStateOf("Account")
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


    private var _currentRouterPath by mutableStateOf("account")
    val currentRouterPath
        get() = _currentRouterPath

    private var _currentRouteBottomSheetPath by mutableStateOf("blank")
    val currentRouteBottomSheetPath
        get() = _currentRouteBottomSheetPath

    fun changeCurrentRouteBottomSheetPath(path: String){
        _currentRouteBottomSheetPath = path
    }

    fun changeCurrentRouterPath(path: String){
        _currentRouterPath = path
    }

    private var _accountInputText by mutableStateOf("")
    val accountInputText
        get() = _accountInputText
    private var _passwdInputText by mutableStateOf("")
    val passwdInputText
        get() = _passwdInputText
    fun accountInputText(text: String){
        _accountInputText = text.replace("\\s".toRegex(), "")
    }
    fun passwdInputText(text: String){
        _passwdInputText = text
    }

    fun restoreInputText(){
        _accountInputText = ""
        _passwdInputText = ""
    }

    val channelId = "pomotodoro"
    val notificationId = 0
//    private var _accountCity by mutableStateOf("Waterloo")
//    val accountCity: String
//        get() = _accountCity
}