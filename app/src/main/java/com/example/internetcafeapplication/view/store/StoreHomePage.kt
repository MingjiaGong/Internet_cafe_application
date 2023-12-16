package com.example.internetcafeapplication.view.store

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.internetcafeapplication.viewmodel.ApplicationViewModel
import com.example.internetcafeapplication.viewmodel.StoreViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.navigation.NavHostController
import com.example.internetcafeapplication.model.Coordinate
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.async
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

//val applicationViewModel: ApplicationViewModel by viewModel<ApplicationViewModel>()
//val location by applicationViewModel.getLocationLiveData().observeAsState()

//private fun preplocationUpdates(location:Coordinate){
//    if(ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)==PERMISSION_GRANTED){
//        requestLocationUpdates()
//    }else{
//        requestSinglePermissionLancher.lunch(Manifest.permission.ACCESS_FINE_LOCATION)
////    }
//}

fun requestLocationUpdates() {
    TODO("Not yet implemented")
}

@Composable
fun StoreHomePage(location:Coordinate,navcontroller: NavHostController?,viewModel: StoreViewModel){


    // Using LocalContext.current to obtain the Context
    //val boxRect = remember { mutableStateOf(Rect.Zero) }
    //val viewModel: StoreViewModel = viewModel()
    Log.d("StoreHPDebug",viewModel.mainlist.value?.name ?:"")
    var showDialog by remember { mutableStateOf(false) }
    //var selectedOption by remember { mutableStateOf(options.firstOrNull() ?: "") }


    val context = LocalContext.current
    LaunchedEffect(true) {
        //Log.d("debug","debugSHP")
        //viewModel.getInitialCafe(location)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return@LaunchedEffect
        }
        getCurrentLocation(context, viewModel)
    }


    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF89B8F0),
            Color(0xFFDCF0F7)
        ),
    )
    // Top Constraint Layout
    Box(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(brush = gradientBrush)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            ){
            Text(
                text = "Location Summary",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF98DEFF)),

            ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${viewModel.mainlist.value?.name ?:""} " +
                                "\n${viewModel.mainlist.value?.address ?:""}",
                        style = TextStyle(
                            color = Color.Blue, // Replace with dark blue color
                            fontSize = 15.sp),
                        textAlign = TextAlign.Start
                    )

            }
        }

    }

    Box(
        modifier = Modifier
            .padding(top = 120.dp)
    ){
        TotalComputerList(viewModel,navcontroller)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("All cafes") },
            text = {
                LazyColumn {
                    itemsIndexed(viewModel.cafes.value) {index, item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),

                        ){
                            Text(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .clickable{
                                        showDialog = false
                                        viewModel.changeCafe(index)},
                                text = "${item.name} \n${item.address}",
                                textAlign = TextAlign.Start,

                                )
                        }


                    }
                }
            },
            confirmButton = {

                Button(onClick = { showDialog = false }) {
                    Text("Dismiss")
                }

            }
        )
    }




}

fun getCurrentLocation(context: Context, viewModel: StoreViewModel) {

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.create().apply {
        interval = 10000 // 位置更新的间隔时间，以毫秒为单位
        fastestInterval = 5000 // 最快的更新间隔时间，以毫秒为单位
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 设置请求的优先级
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            for (location in locationResult.locations) {
                // 在这里处理得到的每一个位置更新
                var newLocation = Coordinate()
                newLocation.latitude = location.latitude
                newLocation.longitude = location.longitude
                viewModel.getInitialCafe(newLocation)
                Log.d("debug location2", newLocation.toString())
            }
        }
    }

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}


@Preview(showBackground = true)
@Composable
fun StoreHomePagePreview(){
    StoreHomePage(Coordinate(),null, viewModel())
}