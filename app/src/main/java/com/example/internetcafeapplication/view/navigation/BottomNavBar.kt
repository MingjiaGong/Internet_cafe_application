package com.example.internetcafeapplication.view.navigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.internetcafeapplication.model.Coordinate
import com.example.internetcafeapplication.view.profile.ProfileHomePage
import com.example.internetcafeapplication.view.start.CameraPage
import com.example.internetcafeapplication.view.start.StartHomePage
import com.example.internetcafeapplication.view.store.StoreDetailPage
import com.example.internetcafeapplication.view.store.StoreHomePage
import com.example.internetcafeapplication.view.time.TimeHomePage
import com.example.internetcafeapplication.view.wallet.WalletHomePage
import com.example.internetcafeapplication.viewmodel.StoreViewModel
import com.example.internetcafeapplication.viewmodel.UserViewModel

@Composable
fun BottomNevBar(navcontroller: NavHostController){
    val items = listOf(
        BottomBarScreen.Store,
        BottomBarScreen.Start,
        BottomBarScreen.Wallet,
        BottomBarScreen.Time,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navcontroller.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar(Modifier.background(Color.White), contentColor = Color.Black, containerColor = Color.White){
        items.forEach{screen->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navcontroller)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
){
    NavigationBarItem(
        label = {Text(text = screen.title)},
        alwaysShowLabel = true,
        selected = currentDestination?.hierarchy?.any{it.route == screen.title}==true,
        onClick = { navController.navigate(screen.title) },
        icon = {
            screen.icon?.let {
                // If we have an ImageVector, use it directly
                Icon(imageVector = it, contentDescription = screen.title)
            } ?: screen.getIcon()?.let {
                // Otherwise, use the Painter from the drawable resource
                Icon(painter = it, contentDescription = screen.title)
            }
        }

    )

}


@Composable
fun BottomNavGragh(
    viewModel: UserViewModel,
    navcontroller: NavHostController = rememberNavController(),
    mainNav:NavHostController,
    location: Coordinate
){

    val storeviewModel: StoreViewModel = viewModel()

    NavHost(navController = navcontroller, startDestination = BottomBarScreen.Store.title){
        composable(route = BottomBarScreen.Store.title){
            StoreHomePage(location,navcontroller,storeviewModel)
        }
        composable(route = "storeDetail"){
            StoreDetailPage(storeviewModel)
        }
        composable(route = BottomBarScreen.Start.title){
            StartHomePage(navcontroller = navcontroller, machineString = "",viewModel)
        }
        composable(route = BottomBarScreen.Start.title+ "/{machineString}"){
            val machineString = it.arguments?.getString("machineString") ?: ""
            StartHomePage(
                navcontroller = navcontroller,
                machineString = machineString,
                userViewModel = viewModel,
            )
        }
        composable(route = "camera"){
            CameraPage(navcontroller = navcontroller)
        }
        composable(route = BottomBarScreen.Wallet.title){
            WalletHomePage(viewModel)
        }
        composable(route = BottomBarScreen.Time.title){
            TimeHomePage(viewModel)
        }
        composable(route = BottomBarScreen.Profile.title){
            ProfileHomePage(viewModel,navcontroller = navcontroller,mainNav)
        }



    }
}


