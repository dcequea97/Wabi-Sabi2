package com.cequea.wabi_sabi.ui.navigations

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(
    val route: String,
    val arguments: List<NamedNavArgument>
){

    object Login: Destinations("login", emptyList())

    object Register: Destinations("register", emptyList())

    object Home: Destinations(
        "home",
        listOf(
            navArgument("email"){ type = NavType.StringType },
            navArgument("password"){ type = NavType.StringType }
        )
    )

    object Feed: Destinations("feed", emptyList())

    object Search: Destinations("search", emptyList())

    object OrderHistory: Destinations("order_history", emptyList())

    object Cart: Destinations("cart", emptyList())

    object Profile: Destinations("profile", emptyList())

    object Address: Destinations("address", emptyList())

    object RegisterBusiness: Destinations("register_business", emptyList())

    object RestaurantDetail: Destinations("restaurant_detail", listOf(
        navArgument("restaurantId"){ type = NavType.LongType },
    ))

    object ProductItem: Destinations("product_item", listOf(
        navArgument("idProduct"){type = NavType.LongType}
    ))

    object Checkout: Destinations("checkout", emptyList())

    //For Admin
    object HomeAdmin: Destinations(
        "home_admin",
        listOf(
            navArgument("email"){ type = NavType.StringType },
            navArgument("password"){ type = NavType.StringType }
        )
    )

    object HomeProvider: Destinations(
        "home_provider",
        listOf(
            navArgument("email"){ type = NavType.StringType },
            navArgument("password"){ type = NavType.StringType }
        )
    )

    object RestaurantRequest: Destinations("restaurant_request", emptyList())

    object IntermediateFeedProvider: Destinations("intermediate_feed_provider", emptyList())

    object EditProduct: Destinations("edit_product", listOf(
        navArgument("idProduct"){ type = NavType.LongType },
    ))

}