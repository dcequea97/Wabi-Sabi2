package com.cequea.wabi_sabi.ui.home_admin

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.cequea.wabi_sabi.ui.components.DeliveryTopAppBar
import com.cequea.wabi_sabi.ui.home.order.OrderHistoryScreen
import com.cequea.wabi_sabi.ui.home.profile.ProfileScreen
import com.cequea.wabi_sabi.ui.home_admin.restaurant_request.RestaurantRequestScreen
import com.cequea.wabi_sabi.ui.navigations.BottomNavItem
import com.cequea.wabi_sabi.ui.navigations.BottomNavigationBar
import com.cequea.wabi_sabi.ui.navigations.Destinations
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeAdminScreen(
    email: String,
    password: String,
    viewModel: HomeAdminViewModel = hiltViewModel()
) {
    val navControllerHome = rememberAnimatedNavController()

    Scaffold(
        topBar = { DeliveryTopAppBar( address = "Perfil Administrador") },
        content = { paddingValues ->
            BoxWithConstraints(modifier = Modifier.padding(paddingValues)) {
                AnimatedNavHost(
                    navController = navControllerHome,
                    startDestination = Destinations.RestaurantRequest.route
                ) {
                    addRestaurantRequest(navControllerHome)

                    addHistory(navControllerHome)

                    addProfile(navControllerHome)
                }
            }
        },
        bottomBar =
        {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "restaurant_request",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Orders",
                        route = "order_history",
                        icon = Icons.Default.WorkHistory
                    ),
                    BottomNavItem(
                        name = "Profile",
                        route = "profile",
                        icon = Icons.Default.Person
                    )
                ),
                navController = navControllerHome,
                onItemClick = { navControllerHome.navigate(it.route) }
            )
        }
    )
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addRestaurantRequest(
    navController: NavHostController
) {
    composable(
        route = Destinations.RestaurantRequest.route
    ) {
        RestaurantRequestScreen()
    }
}




@ExperimentalAnimationApi
fun NavGraphBuilder.addHistory(
    navController: NavHostController
) {
    composable(
        route = Destinations.OrderHistory.route
    ) {
        OrderHistoryScreen(isAdmin = true)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addProfile(
    navController: NavHostController
) {
    composable(
        route = Destinations.Profile.route
    ) {
        ProfileScreen(
            onAddressClicked = { navController.navigate(Destinations.Address.route) },
            onRegisterBusinessClicked = { navController.navigate(Destinations.RegisterBusiness.route) },
            onLogoutClicked = { TODO() }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    WabiSabiTheme {
        HomeAdminScreen(email = "Hplo", password = "JHoli")
    }
}