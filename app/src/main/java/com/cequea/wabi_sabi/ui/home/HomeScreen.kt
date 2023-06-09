package com.cequea.wabi_sabi.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.cequea.wabi_sabi.ui.components.DeliveryTopAppBar
import com.cequea.wabi_sabi.ui.home.feed.FeedScreen
import com.cequea.wabi_sabi.ui.home.cart.CartScreen
import com.cequea.wabi_sabi.ui.home.checkout.CheckoutScreen
import com.cequea.wabi_sabi.ui.home.feed.details.ProductItemScreen
import com.cequea.wabi_sabi.ui.home.feed.details.RestaurantDetailScreen
import com.cequea.wabi_sabi.ui.navigations.BottomNavItem
import com.cequea.wabi_sabi.ui.navigations.BottomNavigationBar
import com.cequea.wabi_sabi.ui.navigations.Destinations
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    email: String,
    password: String
) {
    val navControllerHome = rememberAnimatedNavController()
    Scaffold(
        topBar = { DeliveryTopAppBar() },
        content = { paddingValues ->
            BoxWithConstraints(modifier = Modifier.padding(paddingValues)) {
                AnimatedNavHost(
                    navController = navControllerHome,
                    startDestination = Destinations.Feed.route
                ) {
                    addFeed(navControllerHome)

                    addSearch(navControllerHome)

                    addRestaurantDetail(navControllerHome)

                    addProductItem(navControllerHome)

                    addCart(navControllerHome)

                    addProfile()
                }
            }
        },
        bottomBar =
        {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Home",
                        route = "feed",
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name = "Search",
                        route = "search",
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name = "Cart",
                        route = "cart",
                        icon = Icons.Default.ShoppingCart
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
fun NavGraphBuilder.addFeed(
    navController: NavHostController
) {
    composable(
        route = Destinations.Feed.route
    ) {
        FeedScreen(
            onRestaurantClick = { id -> navController.navigate(
                Destinations.RestaurantDetail.route + "/$id"
            ) },
            Modifier
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addSearch(
    navController: NavHostController
) {
    composable(
        route = Destinations.Search.route
    ) {

        SearchScreen(navController)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addCart(
    navController: NavHostController
) {
    composable(
        route = Destinations.Cart.route
    ) {
        CartScreen(
            onProductClick = { id ->
                navController.navigate(
                    route = Destinations.ProductItem.route + "/${id}"
                )
            },
            onProceedToCheckoutClick = {
                navController.navigate(
                    route = Destinations.Checkout.route
                )
            }
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addCheckout(
    navController: NavHostController
) {
    composable(
        route = Destinations.Checkout.route
    ) {

        CheckoutScreen()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addProfile() {
    composable(
        route = Destinations.Profile.route
    ) {
        ProfileScreen()
    }
}


@ExperimentalAnimationApi
fun NavGraphBuilder.addRestaurantDetail(
    navController: NavHostController
) {
    composable(
        route = Destinations.RestaurantDetail.route + "/{restaurantId}",
        arguments = Destinations.RestaurantDetail.arguments
    ) { backStackEntry ->

        val restaurantId = backStackEntry.arguments?.getLong("restaurantId") ?: 0

        RestaurantDetailScreen(
            restaurantId = restaurantId,
            onProductClick = { id ->
                navController.navigate(
                    route = Destinations.ProductItem.route + "/${id}"
                )
            },
            upPress = {}
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addProductItem(
    navController: NavHostController
) {
    composable(
        route = Destinations.ProductItem.route + "/{idProduct}",
        arguments = Destinations.ProductItem.arguments
    ) { backStackEntry ->

        val idProduct = backStackEntry.arguments?.getLong("idProduct") ?: 0

        ProductItemScreen(
            idProduct = idProduct,
            onAddToCartClick = { navController.navigate(route = Destinations.Cart.route) }
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    WabiSabiTheme {
        HomeScreen(email = "Hplo", password = "JHoli")
    }
}