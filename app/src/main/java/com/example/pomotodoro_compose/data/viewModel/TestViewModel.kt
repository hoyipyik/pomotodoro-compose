package com.example.pomotodoro_compose.data.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pomotodoro_compose.data.online.TasksApi
import com.example.pomotodoro_compose.data.online.TasksApiService
import com.example.pomotodoro_compose.data.online.TestItem
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class TestViewModel: ViewModel() {
    private val api: TasksApiService = TasksApi.retrofitService
    lateinit var data: List<TestItem>
    init {
        fetchingData()
    }

    private fun fetchingData(){
        viewModelScope.launch {
            try {
                data = api.getTestMessage()
                Log.i("/fetchingdata", data.toString())

            }catch (e: Exception){
                Log.e("/fetchingerror", e.toString())
            }
        }
    }
}