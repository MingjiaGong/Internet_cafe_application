package com.example.internetcafeapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.internetcafeapplication.model.CafeDto
import com.example.internetcafeapplication.model.Machine
import com.example.internetcafeapplication.model.User
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject


class WalletViewModel : ViewModel() {
    private val _isLoading: MutableState<Boolean?> = mutableStateOf(null)
    val isLoading: MutableState<Boolean?> = _isLoading

     fun updateUser(user: User, num :Double ){
        _isLoading.value = true
        val url = "http://10.0.2.2:8080/user/${user.uid}"
        Log.d("WalletDebug1", url)

        val newUser = User(credit = num)
        val userJson = Json.encodeToString(User.serializer(), newUser)
        Log.d("WalletDebug1", userJson)
        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.post(url).header(header).body(userJson).responseJson{ request, response, result ->
            Log.d("WalletDebug1", result.toString())
            when(result){
                is Result.Failure-> {
                    val ex = result.getException()
                    _isLoading.value = false
                }

                is Result.Success -> {
                    Log.d("WalletDebug2", result.get().obj().toString())
                    val json = Json { coerceInputValues = true }
                    //userViewModel.currentUser.value?.let { userViewModel.getUser(it.uid) };

                    _isLoading.value = false
                    //Log.d("WalletDebug2",userViewModel.currentUser.value.toString() )
                }

                else -> {
                    var tmp = listOf<CafeDto>()
                    _isLoading.value = false
                }

            }
        }

    }
}