package com.cequea.wabi_sabi.ui.home_provider


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.ui.components.CircularIndeterminateProgressBar

@Composable
fun IntermediateFeedProviderScreen(
    onGetRestaurantSuccess: (Long) -> Unit,
    viewModel: IntermediateFeedProviderViewModel = hiltViewModel()
) {

    val idRestaurant by remember(viewModel::idRestaurant)

    val getRestaurantSuccess by remember(viewModel::getRestaurantSuccess)
    
    viewModel.getIdRestaurant()

    if (getRestaurantSuccess){
        onGetRestaurantSuccess(idRestaurant)
    }

    CircularIndeterminateProgressBar(!getRestaurantSuccess)
}