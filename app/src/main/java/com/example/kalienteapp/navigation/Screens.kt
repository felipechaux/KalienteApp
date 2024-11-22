package com.example.kalienteapp.navigation

sealed class Screens(val route: String) {

    data object Home : Screens("home_route")
}
