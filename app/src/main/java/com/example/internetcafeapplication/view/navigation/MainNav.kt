package com.example.internetcafeapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.InternetCafeApp
import com.example.internetcafeapplication.model.Coordinate
import com.example.internetcafeapplication.viewmodel.UserViewModel

@Composable
fun MainNavGragh(
    viewModel: UserViewModel,
    navcontroller: NavHostController = rememberNavController(),
    location: Coordinate

) {
    NavHost(navController = navcontroller, startDestination = "LogInNav") {
        composable(route = "CafeNav") {
            InternetCafeApp(viewModel, rememberNavController(),navcontroller,location)
        }
        composable(route = "LogInNav") {
            LogInNavGragh(viewModel, rememberNavController(),navcontroller)
        }


    }
}