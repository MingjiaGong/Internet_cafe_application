package com.example.internetcafeapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.internetcafeapplication.model.CafeDto
import com.example.internetcafeapplication.model.Coordinate
import com.example.internetcafeapplication.model.Machine
import com.example.internetcafeapplication.model.MachineDto
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class StartViewModel:ViewModel() {
    private var _machine: MutableState<Machine?> = mutableStateOf(null)
    var machine: MutableState<Machine?> = _machine

    private val _activeMessage: MutableState<String> = mutableStateOf("")
    val activeMessage: MutableState<String> = _activeMessage

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    fun getMachine(machineId: String){
        _isLoading.value = true
        val url = "http://10.0.2.2:8080/machine/${machineId}"

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.get(url).header(header).responseJson{ request, response, result ->
            Log.d("StartDebug1", result.toString())
            when(result){
                is Result.Failure-> {
                    val ex = result.getException()
                    _machine.value = null
                    _isLoading.value = false
                }

                is Result.Success -> {
                    //Log.d("StartDebug", result.get().obj().toString())
                    val json = Json { coerceInputValues = true }
                    var tmp = json.decodeFromString<Machine>(result.get().obj().toString())
                    _machine.value = tmp
                    _isLoading.value = false
                    Log.d("StartDebug2", machine.value.toString())
                }

                else -> {
                    var tmp = listOf<CafeDto>()
                    _machine.value = null
                    _isLoading.value = false
                }

            }
        }

        //return _machine.value
    }

    fun activeMachine(machineId: String,uid: String){
        _isLoading.value = true
        val url = "http://10.0.2.2:8080/machine/activate?userId=${uid}&mid=${machineId}"
        Log.d("debug", url)

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.post(url).header(header).responseJson{ request, response, result ->
            Log.d("StartDebug3", result.toString())
            when(result){
                is Result.Failure-> {
                    val errorBody = String(response.data)
                    val errorMessage = try {
                        // If the response is JSON, you can parse it to get the message
                        JSONObject(errorBody).getString("message")
                    } catch (ex: Exception) {
                        // If not, or if there is any issue parsing, use the error body directly
                        errorBody
                    }
                    //val ex = result.getException().message
                    _activeMessage.value = errorMessage
                    Log.d("StartDebug4", _activeMessage.value)
                }


                is Result.Success -> {
                    val errorBody = String(response.data)
                    val errorMessage = try {
                        // If the response is JSON, you can parse it to get the message
                        JSONObject(errorBody).getString("message")
                    } catch (ex: Exception) {
                        // If not, or if there is any issue parsing, use the error body directly
                        errorBody
                    }
                    //val ex = result.getException().message
                    _activeMessage.value = errorMessage
                    Log.d("StartDebug5", _activeMessage.value)

                }

                else -> {
                }

            }
        }
        _isLoading.value = false

    }

}