package com.example.internetcafeapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.internetcafeapplication.model.Cafe
import com.example.internetcafeapplication.model.CafeDto
import com.example.internetcafeapplication.model.CafeRepositories
import com.example.internetcafeapplication.model.Coordinate
import com.example.internetcafeapplication.model.Machine
import com.example.internetcafeapplication.model.MachineDto
import com.example.internetcafeapplication.model.MachineType
import com.example.internetcafeapplication.model.authorization.Resource
import com.example.internetcafeapplication.view.store.MainComputerData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import javax.crypto.Mac


class StoreViewModel : ViewModel(){

    private val _cafes: MutableState<List<CafeDto>> = mutableStateOf(mutableListOf())
    val cafes: MutableState<List<CafeDto>> = _cafes

    private val _mainlist: MutableState<CafeDto?> = mutableStateOf(null)
    val mainlist: MutableState<CafeDto?> = _mainlist

    private val _detailList: MutableState<MachineDto?> = mutableStateOf(null)
    val detailList: MutableState<MachineDto?> = _detailList

    private val _currentLocation: MutableState<Coordinate?> = mutableStateOf(null)

//    private val _currentCafe: MutableState<String> = mutableStateOf("")
//    val currentCafe: MutableState<String> = _currentCafe
//    val _currentCafeIndex: MutableState<Int> = mutableIntStateOf(0)
    val currentCafeIndex: MutableState<Int> = mutableIntStateOf(0)





    fun getInitialCafe(newCoordinate:Coordinate = Coordinate()){
        getNearCafe(newCoordinate)
    }

    fun getNearCafe(newCoordinate:Coordinate = Coordinate()) : Boolean{
        val url = "http://10.0.2.2:8080/cafe/getCafeList"
        Log.d("CafeDebug", newCoordinate.toString())
        val coordinateJson = Json.encodeToString(Coordinate.serializer(), newCoordinate)

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.post(url).header(header).body(coordinateJson).responseJson{ request, response, result ->
            Log.d("CafeDebug", result.toString())
            when(result){
                is Result.Failure-> {
                    val ex = result.getException()
                    if(ex.response.statusCode == 404){
                        // do something if got 404
                        var tmp = listOf<CafeDto>()
                        _cafes.value = tmp

                    }
                }

                is Result.Success -> {
                    var tmp = Json.decodeFromString<List<CafeDto>>(result.get().array().toString())
                    _cafes.value = tmp
                    _mainlist.value = tmp[currentCafeIndex.value]

                    Log.d("debug _cafes", _cafes.value.toString())

                }

                else -> {
                    var tmp = listOf<CafeDto>()
                    _cafes.value = tmp
                }

            }
        }
        return true
    }

    fun changeCafe(index: Int){
        //Log.d("debug", index.toString())
        _mainlist.value = _cafes.value[index]
        currentCafeIndex.value = index

    }

    fun showDetail(key : MachineType){
        val url = "http://10.0.2.2:8080/cafe/getMachineDetail?type=${key}"

        val json = Json.encodeToString(CafeDto.serializer(), mainlist.value!!)

        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
        Fuel.post(url).header(header).body(json).responseJson{ request, response, result ->

            when(result){
                is Result.Failure-> {
                    val ex = result.getException()
                    if(ex.response.statusCode == 404){
                        // do something if got 404
                        var tmp = MachineDto()
                        _detailList.value = tmp

                    }
                }

                is Result.Success -> {
                   // Log.d("CafeDebug showDetail", result.get().obj().toString())
                    val json = Json { coerceInputValues = true }
                    var tmp = json.decodeFromString<MachineDto>(result.get().obj().toString())
                    _detailList.value = tmp
                    //Log.d("CafeDebug", _cafes.value.toString())

                }

                else -> {
                    var tmp = MachineDto()
                    _detailList.value = tmp
                }
            }

        }
    }




//    fun getNearCafe(newCoordinate:Coordinate = Coordinate()) : Boolean{
//        val url = "http://10.0.2.2:8080/cafe/getCafe"
//
//        //val newCoordinate = Coordinate(latitude,longitude)
//        val coordinateJson = Json.encodeToString(Coordinate.serializer(), newCoordinate)
//        Log.d("CafeJson", coordinateJson)
//
//        val header:HashMap<String,String> = hashMapOf("Content-Type" to "application/json")
//        Fuel.post(url).header(header).body(coordinateJson).responseJson{ request, response, result ->
//            Log.d("CafeDebug", result.toString())
//            when(result){
//                is Result.Failure-> {
//                    val ex = result.getException()
//                    if(ex.response.statusCode == 404){
//                        // do something if got 404
//                        var tmp = CafeRepositories(cafes = listOf())
//                        _repositories.value = tmp
//
//                    }
//                }
//
//                is Result.Success -> {
//                    var tmp = Json.decodeFromString<List<Cafe>>(result.get().array().toString())
//                    _repositories.value = CafeRepositories(tmp)
//                    Log.d("CafeDebug", _repositories.value.toString())
//                    getList()
//                }
//
//                else -> {
//                    var tmp = CafeRepositories(cafes = listOf())
//                    _repositories.value = tmp
//                }
//            }
//        }
//        return true
//
//
//
//
//    }
//
//    fun getList(){
//        var  totalStoreList: MutableList<MutableList<MainComputerData>> = mutableListOf()
//        Log.d("debug0","start")
//
//        for(cafe in repositories.value.cafes){
//            Log.d("debug1",repositories.value.cafes.toString())
//            var storeList: MutableList<MainComputerData> = mutableListOf()
//            Log.d("debug2",storeList.toString())
//
//            val machines =  cafe.machines
//
//            var hashMap: HashMap<String, Array<MutableList<Machine>>> = HashMap()
//            for(machine in machines){
//                if(machine.type !in hashMap) hashMap[machine.type] = Array(2){ mutableListOf<Machine>()}
//                if(machine.activated) hashMap[machine.type]?.let{
//                    it[1].add(machine)}
//                else hashMap[machine.type]?.let {it[0].add(machine) }
//            }
//
//            for(machines in hashMap.entries){
//                storeList.add(MainComputerData(machines.key,machines.value[0],machines.value[1]))
//
//                Log.d("debug3",storeList.toString())
//
//            }
//            totalStoreList.add(storeList)
//            _totalHashMap.value.add(hashMap)
//            Log.d("debug3.5",totalStoreList.toString())
//        }
//
//        _totalList.value = totalStoreList
//        Log.d("debug",_totalList.value.toString())
//        list.value = _totalList.value[0]
//        _currentCafe.value = _repositories.value.cafes[0].name
//    }



}