package com.example.pomotodoro_compose.legacy

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomotodoro_compose.data.entity.TasksData
import com.example.pomotodoro_compose.data.api.TasksApi
import com.example.pomotodoro_compose.data.api.TasksApiService
import kotlinx.coroutines.launch
import java.lang.Exception

class TestViewModel: ViewModel() {
    private val api: TasksApiService = TasksApi.retrofitService
    lateinit var data: List<TasksData>
    init {
        fetchingData()
    }

    private fun fetchingData(){
        viewModelScope.launch {
            try {
//                data = api.getFullTasksData(accountId)

                Log.i("/fetchingdata", data.toString())

            }catch (e: Exception){
                Log.e("/fetchingerror", e.toString())
            }
        }
    }
}