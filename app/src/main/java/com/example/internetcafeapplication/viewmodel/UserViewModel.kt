package com.example.internetcafeapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internetcafeapplication.model.authorization.AuthRepository
import com.example.internetcafeapplication.model.authorization.Resource
import com.example.internetcafeapplication.model.User
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (private val repository: AuthRepository) : ViewModel() {
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val _currentUser: MutableState<User?> = mutableStateOf(null)
    val currentUser: MutableState<User?> = _currentUser

    init {
        if (repository.currentUser != null) {
            Log.d("DebugUser", repository.currentUser!!.uid)
            _loginFlow.value = Resource.Success(repository.currentUser!!)
            getUser(repository.currentUser!!.uid)
            //_currentUser.value = Json.decodeFromString<User>(repository.currentUser!!.uid)

        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
        Log.d("DebugUser",result.toString())
        if(result == Resource.Success(repository.currentUser)) {getUser(repository.currentUser!!.uid)}

    }

    fun signupUser(email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup( email, password)
        _signupFlow.value = result

    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null

    }



    fun addUser(){
        val url = "http://10.0.2.2:8080/user/addUser"

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val email = it.email!!
            val newUser = User(email = email,uid = uid)
            val userJson = encodeToString(User.serializer(),newUser)

            val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
            Fuel.post(url).header(header).body(userJson).responseJson{request, response, result ->
                when(result){
                    is Result.Failure-> {
                        val ex = result.getException()
                        if(ex.response.statusCode == 404){
                            // do something if got 404
                            var tmp = User()
                            _currentUser.value = tmp
                        }
                    }

                    is Result.Success -> {
                        val tmp = Json.decodeFromString<User>(result.get().obj().toString())
//                    var tmp = UserRepositories(users = listOf())
                        _currentUser.value = tmp
                    }

                    else -> {
                        var tmp = User()
                        _currentUser.value = tmp
                    }
                }

            }

        }
    }

    fun cancelSignUp(){
        _signupFlow.value = null
    }

    fun cancelSignIn(){
        _loginFlow.value = null
    }

    fun getUser(uid:String){
        val url = "http://10.0.2.2:8080/user/${uid}"

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.get(url).header(header).responseJson{request, response, result ->
            when(result){
                is Result.Success -> {
                    val json = Json { coerceInputValues = true }
                    val tmp = json.decodeFromString<User>(result.get().obj().toString())
                    _currentUser.value = tmp
                    Log.d("DebugUser4", _currentUser.value.toString())
                }

                else -> {
                }
            }

        }
    }


//    val _repositories: MutableState<UserRepositories> = mutableStateOf(
//        UserRepositories(
//            users = listOf()
//        )
//    )

//    val repositories: MutableState<UserRepositories> = _repositories
//
//    fun getUsers(){
//        val url = "https://1387-2600-4040-5c52-900-70c9-762b-55fa-ba6d.ngrok-free.app/user/allUsers/"
//
//        val header:HashMap<String,String> = hashMapOf()
//        Fuel.get(url).header(header).responseJson{request, response, result ->
//            Log.d("Debug", result.toString())
//            when(result){
//                is Result.Failure-> {
//                    val ex = result.getException()
//                    if(ex.response.statusCode == 404){
//                        // do something if got 404
//                        var tmp = UserRepositories(users = listOf())
//                        _repositories.value = tmp
//                    }
//                }
//
//                is Result.Success -> {
//                    val tmp = Json.decodeFromString<UserRepositories>(result.get().obj().toString())
////                    var tmp = UserRepositories(users = listOf())
//                    _repositories.value = tmp
//                }
//
//                else -> {
//                    var tmp = UserRepositories(users = listOf())
//                    _repositories.value = tmp
//                }
//            }
//
//        }
//
//
//    }

}