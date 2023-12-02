package com.example.myfoodappproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodapp.ui.theme.screens.login.LoginScreen
import com.example.foodapp.ui.theme.screens.signup.SignUpScreen
import com.example.foodapp.ui.theme.screens.welcome.WelcomeScreen
import com.example.myfoodappproject.ui.theme.screens.cart.CartScreen
import com.example.myfoodappproject.ui.theme.screens.categories.CategoriesScreen
import com.example.myfoodappproject.ui.theme.screens.home.HomeScreen
import com.example.myfoodappproject.ui.theme.screens.orders.OrdersScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination:String = ROUTE_WELCOME
){
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ){
        composable(ROUTE_WELCOME){
            WelcomeScreen(navController = navController)
        }

        composable(ROUTE_SIGNUP){
            SignUpScreen(navController = navController)
        }

        composable(ROUTE_LOGIN){
            LoginScreen(navController = navController)
        }
        composable(ROUTE_HOME){
            HomeScreen(navController = navController)
        }
        composable(ROUTE_CART){
            CartScreen(navController = navController)
        }
        composable(ROUTE_CATEGORIES){
            CategoriesScreen(navController = navController)
        }
        composable(ROUTE_ORDERS){
            OrdersScreen(navController = navController)
        }
    }
}