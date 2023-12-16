package com.example.internetcafeapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.internetcafeapplication.model.OrderDto
import com.example.internetcafeapplication.model.User
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TimeViewModel : ViewModel(){
    val _currentOrder: MutableState<List<OrderDto?>> = mutableStateOf(listOf())
    val currentOrder: MutableState<List<OrderDto?>> = _currentOrder

    private val _isLoading: MutableState<Boolean?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean?> = _isLoading

    fun getCurrentOrders(uid:String){
        val url = "http://10.0.2.2:8080/user/currentOrders/${uid}"

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.get(url).header(header).responseJson{ request, response, result ->
            Log.d("DebugTime1", result.toString())
            when(result){
                is Result.Success -> {
                    val json = Json { coerceInputValues = true }
                    val tmp = json.decodeFromString<List<OrderDto>>(result.get().array().toString())
                    _currentOrder.value = tmp
                    Log.d("DebugTime2", _currentOrder.value.toString())
                }

                else -> {
                }
            }

        }
    }

    fun deactivate(uid:String,orderId:String){
        _isLoading.value = true
        val url = "http://10.0.2.2:8080/machine/deactivate?userId=${uid}&orderId=${orderId}"
        Log.d("DebugTime1", url)

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.post(url).header(header).responseJson{ request, response, result ->
            Log.d("DebugTime1", result.toString())
            when(result){
                is Result.Success -> {
                    //val json = Json { coerceInputValues = true }
                    //val tmp = json.decodeFromString<List<OrderDto>>(result.get().array().toString())
                    //_currentOrder.value = tmp
                    Log.d("DebugTime2", _currentOrder.value.toString())
                    _isLoading.value = false
                }

                else -> {
                    _isLoading.value = null
                }
            }

        }
    }
}