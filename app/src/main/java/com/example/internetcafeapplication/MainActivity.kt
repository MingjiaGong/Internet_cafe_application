package com.example.internetcafeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.model.Coordinate
import com.example.internetcafeapplication.view.navigation.BottomNavGragh
import com.example.internetcafeapplication.view.navigation.BottomNevBar
import com.example.internetcafeapplication.ui.theme.InternetCafeApplicationTheme
import com.example.internetcafeapplication.view.navigation.MainNavGragh
import com.example.internetcafeapplication.viewmodel.ApplicationViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.internetcafeapplication.viewmodel.StoreViewModel

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.startKoin

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private val userViewModel by viewModels<UserViewModel>()

    //val applicationViewModel: ApplicationViewModel by viewModels()
    //private val applicationViewModel : ApplicationViewModel by viewModel<ApplicationViewModel>()


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var currentLocation :Coordinate = Coordinate()


    //private val applicationViewModel:ApplicationViewModel by viewModels<ApplicationViewModel> ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Log.d("debug location", fusedLocationClient.lastLocation.toString())

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location: Location? ->
                location?.let {
                    Log.d("debug location1", location.toString())
                    if (location != null) {
                        currentLocation.latitude = location.latitude
                        currentLocation.longitude = location.longitude
                    }
                }
            }


//        val locationRequest = LocationRequest.create().apply {
//            interval = 10000 // 位置更新的间隔时间，以毫秒为单位
//            fastestInterval = 5000 // 最快的更新间隔时间，以毫秒为单位
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 设置请求的优先级
//        }
//
//        val locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                locationResult ?: return
//                for (location in locationResult.locations) {
//                    // 在这里处理得到的每一个位置更新
//
//                    Log.d("debug location2", location.toString())
//                }
//            }
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        setContent {
            //val location by applicationViewModel.getLocationLiveData().observeAsState()

            InternetCafeApplicationTheme {
                val context = LocalContext.current
                //val location by applicationViewModel.getLocationLiveData().observeAsState()

                val navControler = rememberNavController()

                //Log.d("locationMainActivity", location.toString())
                MainNavGragh(userViewModel, navControler,currentLocation)
                if (userViewModel.currentUser.value != null) {
                    //userViewModel.getInitialCafe(location)
                    navControler.navigate("CafeNav")
                }
            }
            //prepLocationUpdates()

        }
    }
//
//    private fun prepLocationUpdates() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PERMISSION_GRANTED
//        ) {
//            requestLocationUpdates()
//            Log.d("locationMainActivity","have permition")
//        } else {
//            Log.d("locationMainActivity","have not permition")
//            requestSinglePermissionLancher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    private val requestSinglePermissionLancher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                requestLocationUpdates()
//            } else {
//                Toast.makeText(this, "GPS unavaliable", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    private fun requestLocationUpdates() {
//        applicationViewModel.startLocationUpdates()
//        Log.d("locationMainActivity","requestLocationUpdates")
//    }
}


    @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InternetCafeApp(viewModel: UserViewModel,navcontroller: NavHostController,mainNav:NavHostController,location:Coordinate) {
    Scaffold(
        bottomBar ={
            BottomNevBar(navcontroller)
        } ) {
            BottomNavGragh(viewModel,navcontroller,mainNav, location)

    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InternetCafeApplicationTheme {
//        InternetCafeApp(null,rememberNavController(),rememberNavController(),Coordinate())
//    }
//}