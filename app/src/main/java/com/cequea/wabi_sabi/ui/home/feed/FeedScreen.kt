package com.cequea.wabi_sabi.ui.home.feed

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.ui.model.Filter
import com.cequea.wabi_sabi.ui.model.RestaurantsCollection
import com.cequea.wabi_sabi.ui.components.WabiSabiDivider
import com.cequea.wabi_sabi.ui.components.WabiSabiSurface
import com.cequea.wabi_sabi.ui.home.components.FilterScreen
import com.cequea.wabi_sabi.ui.home.components.RestaurantCollectionItems
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme

@Composable
fun FeedScreen(
    onRestaurantClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val restaurantCollections by viewModel.restaurantsList.collectAsState()
    val filters by viewModel.filtersList.collectAsState()
    val priceFiltersList by viewModel.priceFiltersList.collectAsState()
    val categoryFiltersList by viewModel.categoryFiltersList.collectAsState()
    val lifeStyleFiltersList by viewModel.lifeStyleFiltersList.collectAsState()
    val sortDefaultFiltersList by viewModel.sortDefaultFiltersList.collectAsState()
    val sortFiltersList by viewModel.sortFiltersList.collectAsState()
    Feed(
        restaurantCollections,
        filters,
        priceFiltersList,
        categoryFiltersList,
        lifeStyleFiltersList,
        sortDefaultFiltersList,
        sortFiltersList,
        onRestaurantClick,
        onFavoriteClick =  {viewModel.modifyFavorite(it) },
        modifier
    )
}

@Composable
private fun Feed(
    restaurantCollection: List<RestaurantsCollection>,
    filters: List<Filter>,
    priceFiltersList: List<Filter>,
    categoryFiltersList: List<Filter>,
    lifeStyleFiltersList: List<Filter>,
    sortDefaultFiltersList: String,
    sortFiltersList: List<Filter>,
    onRestaurantClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    WabiSabiSurface(modifier = modifier.fillMaxSize()) {
        Box {
            RestaurantCollectionList(
                restaurantCollection,
                filters,
                priceFiltersList,
                categoryFiltersList,
                lifeStyleFiltersList,
                sortDefaultFiltersList,
                sortFiltersList,
                onRestaurantClick,
                onFavoriteClick,
            )
        }
    }
}

@Composable
private fun RestaurantCollectionList(
    restaurantCollection: List<RestaurantsCollection>,
    filters: List<Filter>,
    priceFiltersList: List<Filter>,
    categoryFiltersList: List<Filter>,
    lifeStyleFiltersList: List<Filter>,
    sortDefaultFiltersList: String,
    sortFiltersList: List<Filter>,
    onRestaurantClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Box(modifier) {
        LazyColumn {
            itemsIndexed(restaurantCollection) { index, restaurantCollections ->
                if (index > 0) {
                    WabiSabiDivider(thickness = 2.dp)
                }

                RestaurantCollectionItems(
                    restaurantCollection = restaurantCollections,
                    onRestaurantClick = onRestaurantClick,
                    onFavoriteClick = onFavoriteClick,
                    index = index
                )
            }
        }
    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false },
            priceFiltersList,
            categoryFiltersList,
            lifeStyleFiltersList,
            sortFiltersList,
            sortDefaultFiltersList,
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    WabiSabiTheme {
        FeedScreen(onRestaurantClick = { })
    }
}
