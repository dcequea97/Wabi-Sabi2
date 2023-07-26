package com.cequea.wabi_sabi.ui.home_admin.restaurant_request


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.ui.home_admin.components.RestaurantRequestItem
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme

@Composable
fun RestaurantRequestScreen(
    viewModel: RestaurantRequestViewModel = hiltViewModel()
) {
    val restaurants = viewModel.restaurants.collectAsState()

    viewModel.getAllRestaurantRequests()


    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
    ) {

        restaurants.value.forEach { restaurant ->
            item {
                RestaurantRequestItem(
                    restaurant = restaurant,
                    onAcceptClick = {viewModel.saveRestaurantRequest(restaurant.id)},
                    onDeclineClick = {}
                )
            }
            item {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun OrderHistoryPreviewDark() {
    WabiSabiTheme(darkTheme = true) {
        RestaurantRequestScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun OrderHistoryPreviewLight() {
    WabiSabiTheme(darkTheme = false) {
        RestaurantRequestScreen()
    }
}