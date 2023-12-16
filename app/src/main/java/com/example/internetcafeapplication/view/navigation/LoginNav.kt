package com.example.internetcafeapplication.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.view.logIn.LogInPage
import com.example.internetcafeapplication.view.logIn.RegisterPage
import com.example.internetcafeapplication.viewmodel.UserViewModel

@Composable
fun LogInNavGragh(
    viewModel: UserViewModel?,
    navcontroller: NavHostController = rememberNavController(),
    mainNav:NavHostController
) {
    NavHost(navController = navcontroller, startDestination = "LogIn") {
        composable(route = "Register") {
            RegisterPage(viewModel, navcontroller = navcontroller,mainNav)
        }
        composable(route = "LogIn") {
            LogInPage(viewModel, navcontroller = navcontroller,mainNav)
        }


    }
}