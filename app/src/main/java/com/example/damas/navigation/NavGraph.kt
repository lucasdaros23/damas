package com.example.damas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.damas.feature.home.HomeScreen
import com.example.damas.feature.home.HomeViewModel
import com.example.damas.feature.local.LocalScreen
import com.example.damas.feature.local.LocalViewModel
import com.example.damas.feature.login.LoginScreen
import com.example.damas.feature.login.LoginViewModel

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOCAL){
            val localViewModel: LocalViewModel = hiltViewModel()
            LocalScreen(
                modifier = modifier,
                viewModel = localViewModel,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.HOME){
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                modifier = modifier,
                viewModel = homeViewModel,
                navigateBack = { navController.popBackStack() },
                navigate = { navController.navigate(it) }
            )
        }
        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen (
                modifier = modifier,
                viewModel = loginViewModel,
                navigate = { navController.navigate(it) },
                navigateAndClearBackStack = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }

}