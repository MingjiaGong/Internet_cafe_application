package com.example.internetcafeapplication.view.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.internetcafeapplication.R
import com.example.internetcafeapplication.view.navigation.BottomBarScreen.Profile.iconRes

sealed class BottomBarScreen(
    @DrawableRes val iconRes: Int? = null, // Add this to hold drawable resource IDs
    val icon: ImageVector? = null,
    val title: String
) {
    object Store : BottomBarScreen(icon =Icons.Default.Home , title ="Store")
    object Start : BottomBarScreen(icon =Icons.Default.PlayArrow , title ="Start")
    object Wallet : BottomBarScreen(iconRes = R.drawable.baseline_wallet_24 , title ="Wallet")
    object Time : BottomBarScreen(iconRes = R.drawable.baseline_access_time_24, title ="Time")
    object Profile : BottomBarScreen(icon =Icons.Default.Person , title ="Profile")

    @Composable
    fun getIcon(): Painter? {
        return iconRes?.let { painterResource(id = it) }
    }
}

