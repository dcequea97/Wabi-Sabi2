package com.cequea.wabi_sabi.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.data.model.CollectionType
import com.cequea.wabi_sabi.data.model.Restaurant
import com.cequea.wabi_sabi.data.model.RestaurantsCollection
import com.cequea.wabi_sabi.ui.components.RatingComposable
import com.cequea.wabi_sabi.ui.components.WabiSabiCard
import com.cequea.wabi_sabi.ui.components.WabiSabiSurface
import com.cequea.wabi_sabi.ui.components.mirroringIcon
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.getOpeningString
import java.time.DayOfWeek
import java.time.LocalTime

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp

// The Cards show a gradient which spans 3 cards and scrolls with parallax.
private val gradientWidth
    @Composable
    get() = with(LocalDensity.current) {
        (3 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }

@Composable
fun RestaurantCollectionItems(
    restaurantCollection: RestaurantsCollection,
    onRestaurantClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0,
    highlight: Boolean = true
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = restaurantCollection.label,
                style = MaterialTheme.typography.h4,
                color = WabiSabiTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
            IconButton(
                onClick = { /* todo */ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = mirroringIcon(
                        ltrIcon = Icons.Outlined.ArrowForward,
                        rtlIcon = Icons.Outlined.ArrowBack
                    ),
                    tint = WabiSabiTheme.colors.brand,
                    contentDescription = null
                )
            }
        }
        if (highlight && restaurantCollection.type == CollectionType.Highlight) {
            HighlightedRestaurants(index, restaurantCollection.restaurants, onRestaurantClick, onFavoriteClick)
        } else {
            HighlightedRestaurants(index, restaurantCollection.restaurants, onRestaurantClick, onFavoriteClick)
        }
    }
}

@Composable
private fun HighlightedRestaurants(
    index: Int,
    restaurant: List<Restaurant>,
    onRestaurantClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState(0)
    val gradient = when ((index / 2) % 2) {
        0 -> WabiSabiTheme.colors.gradient6_1
        else -> WabiSabiTheme.colors.gradient6_2
    }
    // The Cards show a gradient which spans 3 cards and scrolls with parallax.
    val gradientWidth = with(LocalDensity.current) {
        (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(restaurant) { index, restaurant ->
            HighlightRestaurantItem(
                restaurant,
                onRestaurantClick,
                onFavoriteClick,
                index,
                gradient,
                gradientWidth,
                scroll.value
            )
        }
    }
}

@Composable
fun Restaurants(
    restaurant: List<Restaurant>,
    onRestaurantClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
    ) {
        items(restaurant) { restaurant ->
            RestaurantItem(restaurant, onRestaurantClick)
        }
    }
}

@Composable
fun RestaurantItem(
    restaurant: Restaurant,
    onRestaurantClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
        WabiSabiSurface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp,
            bottom = 8.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable(onClick = { onRestaurantClick(restaurant.id) })
                .padding(8.dp)
        ) {
            RestaurantImage(
                imageUrl = restaurant.backgroundImageUrl,
                elevation = 4.dp,
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.subtitle1,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
private fun HighlightRestaurantItem(
    restaurant: Restaurant,
    onRestaurantClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    index: Int,
    gradient: List<Color>,
    gradientWidth: Float,
    scroll: Int,
    modifier: Modifier = Modifier
) {
    val left = index * with(LocalDensity.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }
    WabiSabiCard(
        elevation = 4.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.surface),
        shape = RoundedCornerShape(10),
        modifier = modifier
            .size(
                width = 250.dp,
                height = 200.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onRestaurantClick(restaurant.id) })
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                RestaurantImage(
                    imageUrl = restaurant.backgroundImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
                //TODO()
                //FavoriteIcon(onFavoriteClick(restaurant.id))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    RestaurantLogo(
                        imageUrl = restaurant.profileImageUrl,
                        contentDescription = "Logo"
                    )

                }
            }
            Text(
                text = restaurant.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentHeight()
            )
            Text(
                text = getOpeningString(restaurant.openingHours,restaurant.closingHours),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
            //RatingComposable(restaurant)
        }
    }
}


@Composable
fun RestaurantImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    WabiSabiSurface(
        color = Color.LightGray,
        elevation = elevation,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun RestaurantLogo(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(R.drawable.placeholder_logo),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(2.dp, Color.Gray, CircleShape)

    )

}

@Preview("default")
//@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
@Composable
fun RestaurantCardPreview() {
    WabiSabiTheme {
        val restaurant = Restaurant(
            id = 1L,
            name = "La Panaderia del Pueblo",
            backgroundImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-background.jpg",
            profileImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-profile.jpg",
            tagline = "El sabor de casa en cada pan",
            workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            rating = 4.7,
            openingHours = LocalTime.of(6, 0),
            closingHours = LocalTime.of(20, 0)
        )
        HighlightRestaurantItem(
            restaurant = restaurant,
            onRestaurantClick = { },
            onFavoriteClick = {},
            index = 0,
            gradient = WabiSabiTheme.colors.gradient6_1,
            gradientWidth = gradientWidth,
            scroll = 0
        )
    }
}


@Preview("default", showSystemUi = true)
//@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
@Composable
fun RestaurantCollectionCardPreview() {
    WabiSabiTheme {
        val restaurant = Restaurant(
            id = 1L,
            name = "La Panaderia del Pueblo",
            backgroundImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-background.jpg",
            profileImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-profile.jpg",
            tagline = "El sabor de casa en cada pan",
            workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            rating = 4.7,
            openingHours = LocalTime.of(6, 0),
            closingHours = LocalTime.of(20, 0)
        )

        val muralla = Restaurant(
            id = 2L,
            name = "Panaderia San Francisco",
            backgroundImageUrl = "https://ejemplo.com/panaderia-san-francisco-background.jpg",
            profileImageUrl = "https://ejemplo.com/panaderia-san-francisco-profile.jpg",
            tagline = "Pan fresco todos los días",
            workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            rating = 4.2,
            openingHours = LocalTime.of(7, 0),
            closingHours = LocalTime.of(19, 0)
        )

        val another = Restaurant(
            id = 3L,
            name = "Panaderia La Union",
            backgroundImageUrl = "https://ejemplo.com/panaderia-la-union-background.jpg",
            profileImageUrl = "https://ejemplo.com/panaderia-la-union-profile.jpg",
            workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            rating = 4.0,
            openingHours = LocalTime.of(5, 0),
            closingHours = LocalTime.of(21, 0)
        )

        RestaurantCollectionItems(
            restaurantCollection =  RestaurantsCollection(
                listOf(
                    restaurant,
                    another,
                    muralla
                ), "Destacados", CollectionType.Highlight
            ),
            onRestaurantClick = { },
            onFavoriteClick = {},
            index = 0
        )
    }
}
