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
            HighlightedRestaurants(index, restaurantCollection.restaurants, onRestaurantClick)
        } else {
            HighlightedRestaurants(index, restaurantCollection.restaurants, onRestaurantClick)
        }
    }
}

@Composable
private fun HighlightedRestaurants(
    index: Int,
    restaurant: List<Restaurant>,
    onRestaurantClick: (Long) -> Unit,
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
                text = "PickUp beetwen 5:00 and 6:45",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
            RatingComposable(restaurant)
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
            1,
            "El Rinconcito AnDaLuz",
            "https://scontent.fccs3-1.fna.fbcdn.net/v/t1.6435-9/126720125_2704571226461305_2472062523380462448_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=a26aad&_nc_ohc=CXsHyk4_tjEAX9OOqzh&_nc_ht=scontent.fccs3-1.fna&oh=00_AfAI3FGj7iU7Z8rg0_ra51SryjuvRSBHLqzlT3KA1xeYZQ&oe=64989601",
            "data:image/jpeg;base64,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hhntnOM8TUf4paFdVVYleoaunTPWchSzEtWVJLtk8IcknJzLj/3Y9Gzn7GO+f6/UY/Tfj8prW+Gen19R01K6OgI2kubBqRssj07SdwOW+ZuTAo3j34jaLX6NqEpvR96srMK8AqfXDE4wT2mfwP8AFLT6XSVabU12s1ZKhq1QqEzkZywORk+k7LTo6kGErRQPRUUD+Eo3XfhR0/UWm1TZSWOWWsrsY9yQrD5Sfpx9IG3oOu0dS1tH2Vy9OjRrnbGB5jq1NaFWGQQrWt9CBMnxH8Sto9OEo+bVao+XSoGSM8M+PoDgd/mI4xmTHQOg6fQ0eVpqzgZY5OWsbHdm9TwB7CRfSvCrNqz1DXMLL8YrRf6vTLzgL/bbB+8fUk+2Ai/hT06vSpfprF26xLA124gl1bmtkPcpgkf4t3uJ0OUfx/0HUMa9f09iur0gPA7XV92QjsfoPXJHtjB4W+Jmj1ICXsNNcDgpYcKTwPlY/Ung4IwYF/iYaL0cbkdWU+qsCP1E0+odb0tAJu1FVe3vudQR+XeBJRK70PxMmsc/Zq3alcg3sNqMwJG2sHl+3JHH1ligIiICIiAiIgIiICIiAiIgIiICIiAiIgJiapdwYqCwBAOBkA9wD6DgTLEBERAREQPhlB8XfDLSa12uQmi5u7IAVc+7r79skd8S/wAQOHr8Gtap2rrUC+4Fg/gDLH0H4RaSplfVu+pcYO05VMj3A5YduCf1nTYgYKKURVStQqqoCqoAVQBgBQOAAPQTPEQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQERED/2Q==",
            "El Rinconcit andaluz",
            openingHour = 8,
            closeHour = 16,
            workingDays = arrayListOf(1, 2, 3, 5, 6, 7)

        )
        HighlightRestaurantItem(
            restaurant = restaurant,
            onRestaurantClick = { },
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
            1,
            "El Rinconcito AnDaLuz",
            "https://scontent.fccs3-1.fna.fbcdn.net/v/t1.6435-9/126720125_2704571226461305_2472062523380462448_n.jpg?_nc_cat=107&ccb=1-7&_nc_sid=a26aad&_nc_ohc=CXsHyk4_tjEAX9OOqzh&_nc_ht=scontent.fccs3-1.fna&oh=00_AfAI3FGj7iU7Z8rg0_ra51SryjuvRSBHLqzlT3KA1xeYZQ&oe=64989601",
            "data:image/jpeg;base64,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hhntnOM8TUf4paFdVVYleoaunTPWchSzEtWVJLtk8IcknJzLj/3Y9Gzn7GO+f6/UY/Tfj8prW+Gen19R01K6OgI2kubBqRssj07SdwOW+ZuTAo3j34jaLX6NqEpvR96srMK8AqfXDE4wT2mfwP8AFLT6XSVabU12s1ZKhq1QqEzkZywORk+k7LTo6kGErRQPRUUD+Eo3XfhR0/UWm1TZSWOWWsrsY9yQrD5Sfpx9IG3oOu0dS1tH2Vy9OjRrnbGB5jq1NaFWGQQrWt9CBMnxH8Sto9OEo+bVao+XSoGSM8M+PoDgd/mI4xmTHQOg6fQ0eVpqzgZY5OWsbHdm9TwB7CRfSvCrNqz1DXMLL8YrRf6vTLzgL/bbB+8fUk+2Ai/hT06vSpfprF26xLA124gl1bmtkPcpgkf4t3uJ0OUfx/0HUMa9f09iur0gPA7XV92QjsfoPXJHtjB4W+Jmj1ICXsNNcDgpYcKTwPlY/Ung4IwYF/iYaL0cbkdWU+qsCP1E0+odb0tAJu1FVe3vudQR+XeBJRK70PxMmsc/Zq3alcg3sNqMwJG2sHl+3JHH1ligIiICIiAiIgIiICIiAiIgIiICIiAiIgJiapdwYqCwBAOBkA9wD6DgTLEBERAREQPhlB8XfDLSa12uQmi5u7IAVc+7r79skd8S/wAQOHr8Gtap2rrUC+4Fg/gDLH0H4RaSplfVu+pcYO05VMj3A5YduCf1nTYgYKKURVStQqqoCqoAVQBgBQOAAPQTPEQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQERED/2Q==",
            "El Rinconcit andaluz",
            openingHour = 8,
            closeHour = 16,
            workingDays = arrayListOf(1, 2, 3, 5, 6, 7)

        )
        val muralla = Restaurant(
            2,
            "La Muralla Restaurant",
            "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFBcVFBQYGBcZGSAdGBoaGhsdIBogHBkZGBohGRogISwjGhwoHRkgJTUkKC0vMjIyGiI4PTgxPCwxMjEBCwsLDw4PHRERHTEpIygxMTE0MTExMTExMTExMTExMTExMTEzMTExMTExMTExMTExMTExMTExMTExMTExMTExMf/AABEIAKgBKwMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAFAAMEBgcCAQj/xABHEAACAQIEAwYDBAcGBAUFAAABAhEAAwQSITEFQVEGEyJhcYEykaFCUrHRBxQjYnLB8BUzgqKy4WOS0vEWQ0RT4iRUg8LD/8QAGgEAAwEBAQEAAAAAAAAAAAAAAAECAwQFBv/EAC0RAAICAgIBBAEDAgcAAAAAAAABAhEDIRIxQQQiUWETFHGBkaEFFUKxwfDx/9oADAMBAAIRAxEAPwDZaVKlQAqVKlQAqVKm2uKCFJEnYdYoAcpUqVAHhNM4a+HXMswZ38tKfpjCiFgdT/qNAD9KvJpUAe0qi4rHWrQm5cRB+8wH40NtdqsGxIF9dOZDBfZiIPzoAJ4Q+E/xv/rNP0LwnFbbK7IxdQ/xKJHigj135VM/XE5tl/jBX/UBSQEilXgadjPpXtUgEKz/APSZway6W27tRcLGXUQxGm5G/vWgCqZ+kJ9Lfkrn/KfypMa7M87L9kcXdsnFYS4FZbjKq5ijHLGofbnEGNqs2D7aY3BkW8fYZhtnICt7MBkf+tas/wCjWzl4dZ/eLN82P5VZr9lXUq6hlO4YAg+xptbG5fJTDa4ZxOWRlW8Rrplf/Eh0f118jWedq+zr4O6AXFwZdCphspJUBkJJ9ta0fi36O8Ld8VrNYfcFNVn+AnT2IrP+K8OxVvGLbvZsaLY0UFszKqs0SPEImedRJWXB70wClxB8W/Uk/h/Lb2pzD4uyMwYrqTrrI2iCZPP+t1veB/sjFDu3tthrvS4WBB/iYlT6GDVZ4rwi1h7r2/2d3K2hAOaIB1U6Ea8j8qwlBpbNlKLdbItvjdoeFnkbhgGkHQyYHX5ESNdG4/t20CIdtNjkOnkRz3IjaMw2yw0cVhlJzWUG2hUkDTWNKaGNwn/trruMjGPMHT+jWfFM0S+mSV7R2QPtnaRG4jLE8/ASk/uoetNt2otgQEcmGAJj7QEmJ++iv/iavU4jggP7se1mfqW/qTTlviGEDT3d0gjlZtiCNVI0OhJ1HSKFBfAmvojWu1QVmZbQANwuJaYnOYiP3jzqM/H5CiDA6KPud3oS3Qt/WxROJYQB89m8ZYlPCF8MJlzQRrIb5iub3GcOS3cYFjI0zDPHjDSQJnTTenwj8C/gAYji4dgSHI1MSBqdeQ2kD5Vy/EAZi20nmSTzn5/zPpVjGPulCFwV1C2pK2GPSIYkaaDly8zUOzj7iAzhb2Y7kqR5ADTYD+dU414BV8EBeNQoVbTAR19J5f0ABtMsNxJmIHdmSeu536VPv8Quaxh7gI30/wBtKkYjhrtbDKCXmd9hlP8AOKSSvaBypaIGAtm6+RwUA1y7zVlt4FAB4BQzgXDriuDcABymQOWun0rziPD/ANq5N26JMwM8AHURB6UNK9GTk5bZ9G0qVKus5xUqVKgBUxcQZ1PMBo+lOOwAJOw1NZTx/tzi3N4YW2FW3GR4BJBbKTLabchSY0rNYJqFi+K2LQLXLqKBvLCsDx3E8fcJW9dukkLKiTMnSAsLM+dC7OGu3GhLdx2Bg5vDAgEGOmszPOmPibPxH9JWCt6Wy91p+ypA2+8RQXH/AKRL4AWxhgJGbPcbQZwHEbAxMb1SrPDr7lmttbtW7Y8WnimSDBAJIB6kUW/8LXbtvvV7xmCoAAggjKsmS2aTrsKhtlcV5GuIdqsZe/vMSE6LaGg9xE+5qLje0mJuEB8Q7kaatkHTZOfvRa3+jjFkBu8tA5vhbNMaGZEj28qNYf8ARtbibl985MnIFCjbaRJ1nU/KlsdxM3uYy8Sx8MnmDJOussxmjPC+CXMSY720uRM5zuSSCTAUDdtNuUitLw3YzA2wZsK0iC1wljtqRJ0J8qdfE4DDbth7UCN0nT61QnJeCir2Sdbd1w9xntBQqokB5si6I3JhvDpzFXYu1rB4sB7hFq5KG4xZsuW1c+I7jUj51Ev9vcCDAulz+6rR8zAPtRDBcfwt7RLyEn7LHKfk1FoTbCXGGS2iXAoE3bYJAgw9xVOoj71O4m4bdy0gdwLjMupDQQhcfECfs9aZv21uLlcZlkGDtKkMp9iAfaliE7xrbNM23zL6wV16iGNVZJKt4wi93TMpOQMNMpMlhpqZPh+tVX9IeKUB1I1FlyD0JgD8aMX7CnEJimOtq2y5QN9zM9fzrPP0i8T7x7pG3dqB7laTlQ4q2aZ2OtZMDhl/4Sn5jN/OjdAeDcStrYtJr4bSDTqFANTrHFrTAw0QJ1EaUckyW9k+qXwpc/FbrfcS5H/NaQfgas1vidtjGaPUdPOqr2KxAuYnFXCRoFAn965db+Qo8lR6bLRxLg2HxAi9aR/MjUejDUfOqNwXszetPefBXUQC5HdXVzowiQM3xLAPKtAu4tQG8WoUn5AmhfZPWyz/AH7jH5Qn/wCtJ90OL02UXH4m7dxT28RaTCsqrNxLZuqTB1Lr8KEfeFDnt3UUXExD92JCumFkdIBLCa0AWrZOLuOuaXyc+Q6bbmh7cAsZLDZMrLaa4xH2iqCCynwt8R3FYyVm0Z1ooQxlzZcZdAmSP1a2AJ1MftBFMjidw6Ni7pO5H6vbMchGa55D5VerHYvApbQ4nKbjCS3eNbmf3c3Lyrn/AMF8McEpbDxoYvOY9YahRY3kX/UUheI3F2xV0emHwo//AKU2vHrpbXGYqATELhkMRrM3I39auLdi+Hj/ANP/AJ7n/VUXE9nMBbBJtKoG5NxwPclqronmis3e0L7freNP/wCXCj8Cai4e5cumRdxIB2drtsjpHhG9EcbwXC3Ltn9XayApY3FF0kuMvhgSZg+lO4bhL3Ei4QAe7YAR4WWC31FTJhy+CLhVAcWSXZgubM3MTG/WunwjjF2mGbI1tgdTlBUcxtOu9Hjw5e8FyPEFKz5Egx8xT6JUJEtkMWPFNPZKkm3rXvdU+IrLHiO3mGR8pmJIkETod8u8c67t/pA4ef8Az49UcfyrG/1VCguKHbWCJI+yCDoIjUjflRXhPCFuwwGQ5jJ00OVmGp15fUV0Jk8UaxY7ZYJy0XgAomWVlB/hJGtCMf8ApKwiEi2r3I5gZR9dfpVQ4l2SylHxGN8LqW0XXYsOYkEg8qmYbsPgUe0XuXbiFGZ/GFAYKlwarGkE6TT2HtX2N4jtrjMQxFiyzKwzAKrOI5LIAHL61zhOB8RuqzNYRC6BYdggADzsJI0JNEuJfpEFoRatJlzlAWfSRoDCjYxQjEdr+KXY7pEAIkd3bLH5tOtS2Df0HcB2FuDKbl9RDAkW1JkiPtEjXQagcqMYPgGEwvjd1kACbpQDRSJjQCZJPn6Csv4ne4w65rhxWX0ZR8lAqrYi/cJOckkb5hJ+utFhbNybtPwzDIUW9aC6ytsF5kydADOtCcX+lTBppbt3bnTQIPqZ+lY02OXoSflTJxY6GjYtGo4n9LTnS3ZROhcl/wAIoNi+3uOufDfVB/w1VfqQTVKmVLLyMVHe63WlVhaQdx3E8Rc/vL915+87EfKYoaR0NQO9b7x+dTuH5nLAmYHOnVDuzjOObV6mLVdi1RXXSabFOieRYMD2txNo/s7jqOmYx8jpVn4b+lXELpdtJcHX4T8xp9KzipnDLeZj5CnQG18F7W/rtxUVGtIUYOGymfCSCp3j5VRe2d9BduW82mZRPUKTJp7sdb/+tsCAQRcBDGB/dsdTyFCu2HBsR3hv92e6uOQhBBGhgRzynkY1ioq5JMpXG2jWeC8UwWKUNYdibenwkMkjWROxiaJizYOuYyfibqJ1BA05H51nHYHu8FbuG+baO7652EhVXQRPOdPU1bML2rwzvbt2wHZicwtZCEUAksxMaAb7+9CpNpEqDbpHHafjGGwiKua41xjKi3uq8sxPKf60qN+jsJctX7rXMoN1BOgmLatBnbV6q/bPjlu7iHyW/CttchO4ZTcBPuGHyqLwriDphFs2UUzcN24XOzEKFULIkeHeeYpKUW7s3fp5Ri7X7mtPxWz3d62gi4tts3M8l1bmfEKkdnrnd4O25IVYd2J6FmasowHFMbcS/layoETKDMxYgwozDy61M4o2KbDW8O2JkFVyIDbUEd3LI8NMhup5UJPlbM9caXyXS1xVbfDL2IILB3uEbA6tkG/pQQ9sHu6LhSv7I22zXBoCAQVAEsYAEGDrWe4+1irdlLN9T3XxoveJpr4lU5onnAnfzoz2a7SopW13BCAPkZrikoAMw1iWII33E1UoaSQKSt2G+1vFUxLYYPh7qvcBS2ubRWDmRcgTqADHLWoPCOJXMDcRLltUtXWUXCz/AN2SYUz5r4oMRO9LinG7l22wWFbwuxUczJyzuSTJ8qoPFeKPcul3cvnaWnSYcEabaRHpU8djtUb9erOe3nHFQiy1suhINxScpIDAgA7iYqXwfta74gNe0t907lANihgfM+dUrtRxEXb7XQFAEQsaGOo8/wCdFW6CK02yHd4otu6l2xbCZdQpYtyI1+dEsL2wxuQ5VQgmA2TY9BrBnzpHgLYnK4Tuc+UgFYBSIJUeRB9aK4pbdlURWGRCMq5ZmJJJ6nzqXKK0uy1jb2+h3GdtriKQbANwR4gTk1XUkRI15VNwfarSyt22M1zLJtmQuaIJB9RInSowvYa4r98Db7xR4xqHJJWW9NIO2tB+JcOVSlu1dkKAMxggbyQRA5VUYObSQPiru+i5cS42iMqoVknWT0OoPSi9sqwBnesl4zdKEZmJBXwk7tBME/nQ61x7EqAq32CjYT/tTWKUW0yZyhSo0zBcHBRBLQubx5tWzdVggQNBV1wl1LNq2i2WI6Kq6kknmRTmIa0sLCAxoIGw6CqL2n4nczgFmtoGgZW3EEg6bdKFrszbT0i1YrirtMYKcv8A7lyyI05QWihN/HYgwFw2FVW0hrjNuI1VUHLSq/huMT3hY3GEaEGPs+Y1186m4a0r27MuzFn1mRACsY9oGtUtiB2P7T4+27Wra2LYUmO6w45QJBaaG3+L8UuSGxOI9FIt7/wgUZx1gfr2GtgwsMz6mCPOeWlEe7ti5faFyqwAgToEB0A/io4/YWiiNgL9wnPcuMQdc90nkD94zvUd+Flc0xowGknUgH8KvGFxVoM0sB3t05JBEwqrHkdKFcVdQjtM5rzFY1nLbVJHloflRS+R39APBdn1uWw7MwLHQAcpjn86j4/s3cTW34x8iPbn7VaOFsBbtWyYfulYiRz1Pv5U/iMZbVkQsJPh05c/F05fOtajRjbsp9rAOMP8DZjcOkGdPL2oXirDIQHBU7wdDWhHFWxdt25Gqsc06TsB76/Kqp2sTPiCUggKoJkbiep86zVF27A+HsM7BVEk7Aek1YeD8IuJ3hdYlYGo8+m1DuDIy3AyqGYAwMymdCDsfOrxw095bLFTJXWASOfPpSbTQU0yp3ezN3Lum3U/lUdOzVzncQfM1erzKcwE6aHTbQH8CKhgII1Mnl6f960SiRbKuOzLRJuDzhT+dOW+FdzqGLFtDpG3SraiArGsTtNR8Rg1YKdY8hO/vQ6oqN2ROGYbvHFtjCulxSegNth/Op/aTtH3jd3bBVFGRU5Kq+FY6HSnLOBAzZJnurg2G/dHnNCb+DJbORo0z76/jUeDVMmdnex1jEW++Y7N+0zEhbeu5iPCZHpNWq5wSxhXuKjWLbXLaDLLFVHWB4jnOpEwYFUFHe3OVmUxG5g+oBBinLt25dS2l25fbDZT3g+G2txQWVlJBAtjTQkHQ7DWo43plxm4y5RC+OwouYq3bcJcs5oa5bBtkIPhygwH5bA1XOI9pcly5bS2O6ViqhhJCqwIEz1WZOutF8V2ewbW7Bt4q45cCUDzkHdO4hTqIZQIjSaj9juD4a6cfauIrZLPeW7jEhrYQNmYabyVojjj8FTzTl5K7ieOhnZltlc2/wC05aCDI/dFN3eKXHdi+WXiTAY+EQup5QeVFRwDG3AbiYEnZpW14dVLgqDyIjQDptTPaCzOKXu1BVkQrlQrmHhUsUmQS8yPwrSqMXJsftI5F23Yts+a0ucqQcviV2MkiJYHbaKDE5PCyENlIBYEEF510J+ZqZxC2BcuIVZCloArLL4gZMjfnsag2EUEnWBkI157mk3Q1Fs5uX7lssquwVx4hyIjmOutRsDZz3EUCZYD6iiOKKsSR+FcYe0C40jpFRzNHj+wha46Eu3biBYNt7cR8QbKCROxBg+i1dv0U9m0uI+NxCBwDlsBhI8J8bwdzPhE9GrNGtXHvNbUZna7lA6s5yj6xW6YNzh7NrDKAq2wqGfLc+pbX3rRaRl2wV26nI1xcxy6kDeBvl9vwrOUui8ga4Qwk5NYYbrrlHnWo9obWZGkkCNSuhA5xWZ58HaYi0CXkZZZn33gDSawlDyuzohPw+hiy1w+APlW0haHUzAIJgRroNtvxqLjcaLivBcgiVYrGZpEiANNzz5UfxuGuEDvChGvxZ5UMIPh0I05TUDEcLewhIu22QN4EzkNOviCHfedKmE157Hki110CLGCuQtx7buphQGnWRsOYFFf7Kt//bP8moVYvvdOTNDhhkgnU5to5npVjuYfEgx+tMI82/OqySrtk4lrSsseP4rdN/ObNx7SqoByfA2YSdfFH+2lAeI3L1xtLDhZJGZCT7mfOjd/F5AIUasARL8zH3q8v48IPgMSJ8Z3JA5zG9NtWQo66K6yX1EGywBE6p5VMwmJvLbZQjIbYLJ4RqT0HXU0c/tGNcjDSTDKZA9Up6zjs4DA3Bm1GimJ96LQ/wCABaxF5hdd7dw3cgFsxqAZDRptz96jHFXRZWC6u2adT1QDoNp9utWq3xGGZWcmAJm2efmH8qlW8XZyszOQP4XOX6HlRyXYlEqOCvg4eGzG5JOWSWkPOg6kUE4lw/ElzctrcMtKgBgVAJjy18q0Rrtpgnd3F11HxAQeeqzNN4jKNP2Z/iuJP+ZvLpTU0JozS9gMQHDm3ckDQhWGsGDqDTScMxJcOLVwsTI8BOYwJ0Ig71pdshfsWiObK6DX/nE/SiGDvoRMgRtD549cpMU+aFxMrPZ/Fo8nD3AQdxHPzB86h4zBXQQWtuuu7CJjf1rY7l+ztmB/wP8AzFRb3E8PkU55VZj4RGsHQtO4o5Bx3sy7ggNu8XdTlgxEcyPOrPwrjdu3aZShJO2qjmevlRx72GE5rZY9TbX5c4NRUxVhxmSzuBoVURtzyR9aOTHxh3sFJxZS9whGhiCPh+4FPPyp+zczMhCnSfu8xHWiYZIVjh7XjMSff90eVGMJaBEi3aHopP8AOmpy6Bwh3shYW1P2T81/Op+FwOgBG3mKJ4awI+ED0FTUQaaCrtsiorSBtnBqCOkMPmpH86Hvw0ERG2lWV0gTyrgYeT0qVJN15DrZSOJ8GlTptVUx+AZQRJyncSYPqOdazdwqkwxEc41qvcX4epCwI8OvmZOtUKwLdtYK1grNy3ZW1fi2XuFmLk6LdKSwBBlhlGoAMgCqnwjG3u/uJZR7hxFprTJb3ZWylhqCY0iRB86uHFLqtw9bFxgGtYkMsiSLbo4cgcwGMx5iqPaa7axFo2BF3NFvbxF/CsSY1mjyHg0Tgp4/4xhrHdW3cmLrWyE5EKG8Q9h7VRu0eCxmHxd5L7ot5Uzu1rKAVZlbSAuskHadKlYjjPFUFy2+KuWmV1D2wwRpubEZY099KZ4NlGIxC4pndnw1xMzeJw5yxuWJI60CIltC73We4106guz5ixIXUsTJ6UymGJMT0qdw/CYpmJSzcbOigkI0HTLvtyFG+GdmMSCTcs5dNCzov0msZcm9HVDio7A13AZBvPLanuH4ETPn1jTnvVhvdn7mme7aT1cn6AV0nB7S74lP8KOain5K76AHY7DMeLKzD+7Zrjew8P8AmZTWncbcST1qscLsWbF67e79nNxVUjuiIyxznWYHKitzilm64TOykkxK6Ek9fpWykqqzH8clumdXeJI1oi4R4RlZSYzTzPlH86qPEOP4LDgrh7VvP+4o+TOeU1aL/AbVwmbgJAIEqpyz71Ub/wCj22v/AK0D+K1/86UpQXbFb8ICYztBdulXzKAABETsdmJ3r27x5mZcyWDCxJtJI6AHkKkXuxwGi4u03qlwfUA1AvdlMQPhNt/4bij6PlNTGWN9NDk5VtFp7CYXvg91rVvMrZbbgQ0kS0LOWACNd9SKg4/txfs3bllLOHZbdx1UvbJYhWIGY5tTpVr7PWzYwKJlhwJcCDqzSZI57VkvFTN+8etxz/mNbcUzPkzTeMWWQW5ETdUDlMTTXFkZUXN99By+9P8AKrs+JtnTuQfMtB+YAqFirFq4ALls6EH4uY231NZuN2axlTX0VvHKwt3JGuUxtoI12rnAPFtBGmUdf6NWTGYK3ctugfJmWJynT5E03huFZUVVYNlEAyPTYkfhS47Dl7a+yu4FJv3R0C7+n1p/HI6pcOo8LGOUZTFSsNwi6l667qQrAZCecAb8hXXGUYWLp3GRp57iNwaSi0n/ACU5JyVfQN4WDktnT4Br9Ov9RUfirnJd0EZSOu9EeHGLFvb4dfrFCeLf3b6b9NtSNxS/0/wPudfY4C3dz/w5Oo+7PSvOGuxtpE7CYJ5aa8qWGjuREz3fnzGvzrngqK1oBlmGadf3iR8gaI9r9hyXtf7k+5dZiJaDpMErMdYY5veoHDLrZXAb7bdTPPXTbWiCWj9kAjll1+dRez9tmN1AGkNOg2nTX5Gm37kStxf8HuIwgjMVMxuJ/wCqlwGwHtroJkgk+ROlGHwTtpl15kso0jkAZ3jlUfgnBLqIVuSniMc5mNdxzp0+SEnHg19oce2IWIIDAgzNWaws66VDtcJBEF9fQfgCaN2bAA6mKpKnZnJ2kji2lPom1QuDcQ73ICqhmtLdMEkDMTprroI186ZvceKriP2YLWboRRJ8YP2j6Q+33aOUasXCVhC8rLvr08+oPtXeGtkfFr08qiX+KsJHdpobm5MTbupbX555PpXeI4ibdlrjKMyXO7IBMEh8pI5xllvauXHgjHI58maScnFRonFKE4vC5oHkfxNP2uIu1428i5RcdJkyQiI89JOePavFx2a5dtgR3eWD1zAk/IiK61NGfBgTivZtWtkWwDcJBzsSoAmSAu23WaquI7L2LRF27i8uRg4FpMxkN4YOvPyq2cXfNmDkxy16TVaxKB0a2gGYKCGLGG1mCPepnkUdsqEORxc4vgnuO3cveuuVLNdOXN3YlcoAgRvGkxzqTc45dJL20tIT9oICx9WNVrD2D3gJXQBjmVsy6KefI+RqyW8CTbDAbDWuTL6iS6Z14MMfKB+L4piG+K8/scv+mKjYO3m8TeJp3Op+ZopcwMih9tHDZFgbkk+1ZwzW9s6vxxXSJiJTgSvEwjne4fYAV2eHrzk+pNXyKoauR1ofjF0NT7+ASNFFCmuG0+0ofiU66eU86G2lZDAd29cDf3j5TyzGPlR3gkEFfeo3FsAoi5b1tvseh6Gj/ZjC29GJUQNZPWk6yROSacZHaYc9KTYY9KOtibK73FH9eVRL/GbA+0T6A1ioR+SHJ/AEfBPuAQeu1RbmGuSZY+5BojiO0NvkjH5Cmv7Ys9Y9jXRFJeTN2/BbfWvDHSq9+sYu29sX7+TNbVyDalgWzysaCVyidtGrjhvaV7kB7abE+AsCNuRkTWqzqrlr+5o8Lb9uyyTXWbyoV/b9nMQ+dCI3WRr6GaK4fEWHUMtwsDsYyg9YJ3rVZYNWmiHjku0zpLpXYkehro4on4grfxKD9a9FxPsqh9bk/QEV0Hfkqj+EL+NNNPohoVtp07s/4S0fIyKi47gtq6pVv2cxqVtg6GdxB+lPOlw/ePqf96b/AFR+lPsOhqxwCzGQOkARJZvTY7/Ou8BwBLKkIrOJmYDbwNDLV2uCc6Rr9adtYW5myq0N90Ez8t6luh9noAAgAj3I+igVyAOSqPYfianWxezi0Xlj9hlB5edccUbukLXLOkxmURqP8UVLyRXbX9RqN6SI3eHqa9VxVfv9p0XRbLf42j6AGmV7S3W+FLaegn6mol6iEfJtH0+R9IuFgzyogg0PWDE+lVDC8TuMPE58zt9BtRjCXzAkya0hljLoznhlHsf4HhHti0HVZW0VZhczSYtgZVyjwwg1O0edR8TwO47u6sviW7pJ1LtNsnTkGb50RFxtII9/5V2r3CNWTbTwn86HHVEKTuyBjOFXHN2GQKyv3RDGT3jW3bNppGTQid68fhTPbNp2VhN1lYmS7vORmEaEZjMdARREm5pqm33T9NdtqbJucig6aH86FAObBqYK8LhuSgJuFgVfk621YEFDr+zPzp7DYTu7jXPD40/aRzfOze4hyP8ACKls1zkyD2O/PnXGIcARQo0Dk2C+JwQdj5HX/cfOqjiUKMSuZf4SGB9VaP8AVR3i7DUSZ5RVUxPeA+G4CZ+E1llnqjfFCL7Jdm4ndsohd4Go3GujefQmtD7J4NWWGAIIgis1w2IvAjPazA81/LWrvw/HNZtq4ZfFAyzqCeRFefOk7S+zpcfbSZN412e7iyzLcls/h8OwJMA9dI+VVRhKm6VGZZBjY6Az5Va8ZxNr1oI+jBtar2ItgWLgiN9OmlZynG7iq2LDzUqk7B2C4mrhmKlVUgEmOe1WLh1uw8Ztff8AKqXgw5svbUDLddc7MNFCwd403iKj4e9ctRkOUaGCZGu3PTernGT3Fm/JNtGv4bgOHYZsgIG+pj3E60L7QdibVxC1lRbcbb5W8jJ09RQfgvaVwkMIAiYMirhwftDauwhb5iPrShK3xb3/AMnLkjOD5GPNaawz2rqHITDLzU8iKkcO4SCddVHPkelaj2x4Pbe2bhtyyCQ6kA+h6iqPwXjSKe6dnCE6jeOfwzrWjtOmhc/yLRwvDbfJZ9mNSl7PMUNxbZKjnH50/d42BOW1IE/a5ekU5hO0askW7ev2pJjyjStIJEPHMr+PwPdqSy5RtOn8qpOQVoXaTHM9kyqr4lg6+fWqBdXU1srRcINI07i1xcaiWwUXuYUuXkuWtZgY33AHvUJ+zqrgVyKRiAwZnB5d3nywD109aqxwt/vMmU2ybYYxKiBpqdJIg6+vWnbvGcXlZO8lVYqQMnJSnLcZf5Vg8ck9MulWmXLhvZpBaud6TcugZkmYyiMxPI6nrRDs8lt7RFwKrABraAGFVxMjXcsDNZ/w7j2JuMizmBGT4RoHaZJA5ET9KuvYq6iLdfEOFUW7YUkzAzXEgSJiVPzpxwScqloicqi32cdpcDhyo7sFrxM5QWAC6TA5nlVe4gz5rFxEgsmUoBlEgGY1JPhG+lF+3WKFu8WtOJyLkGbKAlwDMYjU6DWfaq9jsRibbABg62i5Vo0kJ49wCYDVLxcZVY4ybVh7AsL1pLbZxcZlcAZiqoM1sEnqWY/Sp1lL+HY5WKiBmK6D3k6knegvZnFMLtmy1sybdtA0+EAsbqM3OeXlFWTjGGZbbuly0VfuyPE0gMQshY8UmdzUSwzauPSLjNKVS8jWI4xczi4CFYCARr16+tDxxN0fvA3jmZ8zzjahN7FXEYoVFyJh10kcpHJvKorY5zH7JtdtRXH+Ob/9O/jjS6DeK49dZ85c5vvDQjSOVM8U7U3HtC08MoM+bdZPvQS/cuDe04nnFDmx/kw9q6MeKV7MJKHiiT3ildDrm28o/Ou7F0Cddt6HXseCZO/8OtNHEZt/hH9Sf65Vv+FsFkoNJjTmO4BB0noBv1o/wviRgS01RDidZ1iDr6wKnYDGEQJrpxe3s5s3u6NYwd0MAetSw+lUnhnEJElx6E60ew2LBGpHsRXUpI4JRaDGfy+tLvKHNilCz4o6nb501dxwFU5IlJhJ71DsXiNCelD8TxKNZqVgMUGt5v3j/KssmRRjZpCDbK9xPEg6ltQf696CHFDP3hInWV/H5xV7ulSNVBqqdoLYS6sQJUEaeoP4VwLJ+SXE9P03p1klxuiNhsQUJ/eAy67a8/l9BRnDcbyhQwzbZ5g86r8s3MUgja60/wAKfk9L/LF5kXGz2itMNbYU59IkRoBmI2PPehPGMULi/srjQQdJBBjfagTDWDTvA8D3uGZUdVurfgFtsrL5An4qP067s5fUen/TpOL7LD2SulDetMoMZW01B3A331U/OrCUtOrLdtgWwjHwqoYQsgpA0OlZ1xbh12xca1cuAvAJKM2siRroedDbS3EYMt5ww5gn8Zrq9nFqjz/0meVTW0yxpiraBkS6pZyoQvInTWQBpG20TT7Yp7TQ2hmdCOYmq7aRbjZbjMWOz6SIGskiaJ/2epYM1xmI2LEk/jFcEo44Pb/sazjNakibi+1l5kNvNKTtPp+dVUYpxcJB1nUj+vOp5wQYOTliWA3nwmNI9OdRV4rcssj2GKFl0hVOo+L4gfKt8cVJ0tmE5OCuqClvGI8ZS5DTlkmDvpEztFQrj3Lf2Mq5dypSWzLAMuZ0miN97ifqzupuXLoz3GZR3Y8bKVIWJ1HlQniODuTcKuqIWzZQsCYJAEknYGumMIwfF9mDlJ07dHOJxgKCYZiYiZghmJkbgQwg84qDJqPe0b61Ly0mj18GGKvyEuM4h7WjoYuDQ95mgKTIEgdagjiduIFgnUliW+KTOojT2p65xhJz5ixAgKQNugMQKnYbHYN7eqG3c0AVXYqerEkc+m1Q013E89U1pgbC4oI/eopHdkELm31+lEsRjGvqbbo1vIFUazoGe4JGxPjqQuNwqI4ZyHkFQEZp166ADyimT2hsC2EAltpy8qIzlJ7iwcVXa/qRcZj7uaVtFx3XdTBbwwgMGJDeEa8ppzC4u7kdzbID51iSf70Q0DltpXeD7TWkBGQtqSDGomNBrtpXlztYuoW3A5DaOlKTm9cRxjFSvkhzG8bYkSmUlbagiRpa1WB113oTxrir3rgZiYCgAchEnl5k0Wx/bUXLap+qppEsWnNHURpVfxXEVdi3cos8hMCqxxku4/7BNxarkGeEYktbZWbQMSCdTMDT3mpd7FQxlwpb/LB5f1+FV0cXcKFCqFXYRtrP400/FLh1JX/lH5VD9M5SbNFniopXZZr+PuXBkzlhG3SPzofirNxFLOpCzvodTpGhoIcdc+8faBVo4fgw9hQ53gsCSCTOk/8AanLH+PbEskZaigCXHWkrzudBt/X9c6ncT4QqJntsxE/DlLf5oH4UMVGYDSFPM6VpHjJWmJtp0OPiJ0G1eWnOaZ2G39elcPA3bauVSZyg7cyBVJURK2tk9OIsOY/70ZwPEeWbXSq3Z4TebUKIiZLLt5wa9VWtkhxqCPPlI1FW68HNVl2fjpNu4CfhZY6CmMVxjwiDqRVOu41srAAgHfU66867TiEDQ1Er00i4JdMNNxJi2sx6GrhwHETh0J+83vrWcJxAfeNXns/emxbPIlvq1cnqXJQ2jfHFXoPm5pVd7UDW2R90j5EfnRu20+nSgnaS0fA+YkHQJERoSTPn/KuXC/cj0PRusyAiXIr03DTQNINvXee+SFaYodZ4tcw7v3eWGYE5lDbTETtvUoPsKFY0gOxB8U9Nh1HWqj2ed/iKTxpfYWxfaW5c8VxbTNzm0CfnyqFdxueCAoH7q5fnQoqOra02CRG8VXBM82GeWN9aCiYiGXWCZijmEvMcutVdbqlkgfZAMxvrPoKKYHiQBgAkjzX6a1hnwtrSMpZuTtsmX8ZCXF5+P8TVYu3G03028qJKjXDccGB4jETynkKmYTBJ4C2oMTvt5TH41cOOIwm+aD/DcU6W0CucpUAiZnb6+dCsdd7wm2F+E5gSevp6129q4IyOAsCAUnb0NB8QrBzneNNwI5CNJoXu2nsl0hjG2ypAO4H9Gn7N0ZRUS4408ZJjWeX+1N5q2SdbOzFktWiGzU5bukc6QpTWqkeZ+M8u3Sd6bp4UqVlLGMZTXWU09XINFlfjSELfnXJSngfKuGFSmy3jicFa8inktliFUEkmABuSdgKsuE7B4xoLqtoHlcJn/lUGabkl2S4pEHD4C1kBKXGMSxV1j2Eaa+tTk4joBkkRuQSV3HKKcvcINsG3kFxgIDLH1EyD5GrYewxNq0wItBUBYkuNxLTKmOfPSuWVS72b84x6KAVuXjlQEmDlGokLqSNdYArlOFuyiDPNonQeekT71pGJ4Ng7NhQtzvJ+EZgSM2pJWKHcUa0A3dlwSBoMoAOxkKYiNh50TlKGkjJzctplcXs4ir43HjEhgDl01gEc6hXktrIyHlExtEbbipt5yqhQdNfX/b2oYULNABJpR5t+5gj2yqtcACxoZ6U/xYqto6D7P/enbWE7uSTJP0objr+dmU5snhGg+6Ij5z862hVkyXwRGvK1tgNwANfXlQyKJ3LIWWVZHX8xTH60eQA9praL+DGUW+yIBRxuKXrSoiOyrkBgAbn2ocmZj4QSfIT9AKMjh+IciLYUQPij8BrRKn2iopxToj2eNYrMJuOQOUgT5bU5w7iF13i5cdhE+IzrRLD9nn+0Rm9DH400eCXLZLEqygahRB/r3qGoVpI29LNxzRbfk9Dae9LNSyrB1Omh8vWve7HWsaZ9Wpp9MbzazQ/ig8R9vwokbZodxE+L2FOPZzes3jIZmvLg0X3/ABrnPrvXeOQAJ5rPzrZLZ4WSap12Mg/Su8OVLasR51IwVxF+IN7AfnRaxiLJ3n3WaUp1qjn4t7OMBiLSW2Aa7mZjDAjLHmImfeieEw5YKBqoURn128uVFH4YjYZXt2wSV08MczrrUDAYPEZgsPqQK5ZNOykjy6jD/wApD7/70PxFlzPh7tRudwfWiPFLWIt3GQ5vCxHwzt5xStJda0ztOWcplQOU9NKSdAyr4m14lGcGTvA0151wcN++nyFFMdw8FkZPhMzrqKj/ANnJ1NdUW6ItAfWkAaVKrNKHBbNLJSpVJaij1VFKfKlSoGjlnrwAnYTSpU/BEmy2di+FzirIbVmaf4Qvi5bnSK2JVI+vvSpVHfZlLsjHBWrhUm2uYnUg5SNd5FO8VV0tR3rZSQoGhOpj4omlSqI6Ynvsp3EAQIPXbQfMjf1oRioho8valSpsqIPtYF7nkukk++w50+1hbcBRE7nqaVKhJUK3YG4jdLmEaBz5/LpQ22oV8rtIJ5UqVJbtGjCXG7FuxcBt6woMMd5H+9Cb2KZ98o9FH4xNKlTwtvGmwklyJ2E41dt6eAjoVA/0xRex2kBIzWT6oZ+hA/GvKVVIfFB1LxNvOtq6f3coBPsTQPH8YvAx3JtrOuYEkjnGgAMetKlUkxSsreMuF3ZiYDNMdNAB9BUUsR8JPzpUq1Q5a6HsPjLmZRmMSB9amcRwbPdZUMgQOp2B296VKn5LcpODi2yVhOzBb45FELvZRWj9o+ggSAfypUqTkzmQ0eyTja4p9VI/maSdmboO6EDzI/EUqVZTejRSZqgw1q3w6yZ1CAzrGs0Aw2MsZXPeqGEZQdJ113pUq50xVojXcWrGQ6mfMUc4LYa5auKGAC+KD6UqVaEszftdbKuriQCYPry+n4VXO/b7x+Zr2lXRDoD/2Q==",
            "data:image/jpeg;base64,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",
            "Ya no se que poner",
            openingHour = 8,
            closeHour = 20,
            workingDays = arrayListOf(1, 2, 3, 5, 6, 7)
        )
        val another = Restaurant(
            3,
            "Delicateses Codazzi",
            "https://fastly.4sqi.net/img/general/600x600/19966974_9PcDfifBaNuGE0a5iIcwRBkCBiz653cWTiRiN0w5UFI.jpg",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAABHVBMVEX///8SPDuIdxyKeBsANz0AODwALy4AMzKMeRqOexkAKimDcQAANjV+awAAMjEAIyKdqKcAJiRneXh6iYnEysoAHhzt7+/7+vd8aQCzqX2/t5aPm5t+jY308+319/fIwaWutrZJYWB8cCHY3d1YbGzd2ci5sIqyurrg5OPo5dnV0LtsZyfZ1cLt6+EAGxlGUjJgYCvJzs6uo3VjdnXOyK+nm2gAAAAAFhRSWC9pZSgoRDg4SzWgqqp0ayRhYCuill4hQTktTUw/TzOSgzowSDaUhT1AW1qdkFIlR0ZWWi5dbGF1cDqIjXpqajwqPyTKwZ9XZVBeYz6SgCp0dU4bNA2HfkRiWwBGTRkaOiegn4ZVVRV2ahOWj123tZugoYwaOjfdAAAgAElEQVR4nO1di3vaSJIXEgiQxMs8BAIBNiAQBjvGEDsOjuMkfmR27/bOe7dze7N7//+fcV39ltTCntnMJPbn+mZiW2pJ9euurleXWpr2Sq/0Sq/0Sq/0Sq/0Sq/0w5O7nKwHXtDpBKvwe/OSoMDzBuv1Ily6v+VqN1wjXB1vPQn933SDP4Bc1/eX4WQ+8BCnwWDiP/nKcICgzX9bz3w/8ieDziqYPwoz9Fadx1v9uBQOVp3BMu2sO+88a3SMlgjlInnYXa+CH0+Z/GaadFbz6IFV5wXBI4RATuivvrdSDOpLoPnGQ/oyXHnpcw/ZQ6SMA2Q4vD+QsSfSBOzhfLc58wNts1afWs49bA8Xz8UersOn8+nPg1Xn11zwQxAaklXHmzzO9SJYPaXZD0o+Zj/9PFiMHaefCSGzvlqrxgjB81KdgedGYRAzh2AQX5K9Bwo7sokfbOKIXwStNwP80w9W6dLph/M1toc72nwvCgJvMAB7mN4EGXvN7wTqFv4CmZnAm//GAPMPIBcZRGQP0QigsCnNuvlaoDoBMcgzsxlLbMSfFBm5a6Rsn2kMhUYmeMTFfgExVCJuipx89vAIhSJuksn1Ni8ohiJxk0ypKvXZ0rLTkQycv4ojfgnkesyII3yprZDFHwQ/agQMJn+90+J7KzgZKE2i5k/A4g9+YIuPTT5EwAEOgNVtPOS7KboAAhDvV+SQvz9Bjj4l9EseDL2V9zyNhjsJVunpYEpL79k6NIT8wa7MGliRZw2PkO+lBINIuT5P4VTQRGH+XprJ94NOBI/bUZuM50wuMYaEgs6LwwfkMlM/3/x4GYpvRP5qjf65ekEhRZIm2qDzvXn4vSnu04TPXmLdnTZhshk8e53jDjapSxNh2lLbc6O1GqO/GvzRnPx+tFbYBy/4Doz8jhTE8IRXz17DxMm/kkU1+AGTFP86rVfsN//lDSAh/4pES/PVIw2fMXmgPYMXYiLUhObi5sWEvXEa9vEPVartZVD37Qj/fPZOWhrt1ayT783D06g1ajQaJ7O006Nhd3ztWLVa+bonNxpbGavxO7DTb7X63+xmrUb39L5WqVmIKlYveX5av27WzLKTweSUzwSkfTOTsaa7b9/LWOOUnusrYfS3Y6tZqTRrt71/vfNmw7rTtEzKPJC1H3lYo56pWPlMlPLs9H4Z/WUOdz1h2ERtnMooeabVu69UKk49frzXNHlnWrVkj0fuMazv7ehg4N6Mc585kvr75G0CHVCF9vxPJvxlbqV7xvVNr0kvSYxi96iMz5i3kcOzjBl5lmnumOZ7R2beaXbTTp+cqbjP1KTuvnZULShC90/ZTBxhfEmjV2PXODF53Lc4hlMZYCXxyKNqGoJb0hlnrZTzKexLqnFaU7YgCP3imxzhsKpt6AXxVe9thV+TH0eZK0u3G/LDfTP+LDifMkgn9O4RIZKokcK+hFDdgCBc2oWvHKF/hdvHvZqTpnRRTZ4vexEkFh/fngphpqKG0KWNyylz9T6FfS6lkz8rHwdN0AjahoRQc2EU44FFEO1DS5wZViJnOIurlCceKRQV6g46y8qqMZ4E/5ZLY5/ohE7bVksx5tUtGLqMEDRMLNx3b/49+giTMeKu4uLTxIO4NErZlCc6SQjLv1zQk3nFGG5K6exX8LwN2wyAirQrW9cL5xJC1CUxEf38Ls6uSVkrHsfvjMH7JeMgDWE5YVM2bZvdxRnHTwL7b1LZr+Emd4aR2sLZ10p6AmFMydzYieuI87MsKXDAGBlGOsKIDcMAbblxAuEu9knzSWnHEObrmp1EGKWOfZ64Pg8D4drJIUTdeqINbH0Hwtg4+W3deMdv04x7RjvZd7BP89nQ0/sAaWc8hsfpCOclZR9C79q6AgbSNXeGblymP7QZUTaLEu/gTNSEEwEyjNQbEb222NUH0OGPIPRLymmArMy6ZCgnyL1W0HXjMP2h+T35AWtbL3zh58yY47abfdz6YacYI0YfQXhXKCovnCHxLn5U3xMJviR4yqcK6iCE4lTcIH42CrtuNAMZi/RBDpHUwrnWXIzwUxpCJKPK0ajNPDtlrlkNQPhhB2MRB//G0CVhd/bjjxdygnjPZiP8Q9ygG0V+Ppv5cnz+9VhqgQxsFGHC4zCM4peMgmozW0+Rn3IXEKZreARD9tDtqESbkcc/MPYR9xfHX99dHlxKyg303dom4oLOnx/qxQKQLm6HFDdB+D4F4TrNsJn/REJ6oTqTcX4q6gK9EmhTONjLkm7IPVWRfW80hJdZ4P7j+SXi3gCS9DeoLN1AUwzB+wDndULF97wP9kCTYIQZNcIHo3AcEXL2231UuKJU5PYnlzl+r8Aoienc5hKEKZJmQErkA5LL8wPBvaR4gX2kp4zLL58ui/w8NBH91WcIvySejGlSisJ4w3v7fTFdmeQMqrtyHw+LhWLSmmYcEWUFSBbkU7JnOi+ByvqgF2T2hXoD9h/QKaOAGxjoJwHIZ4hVJVKSjhD5E9JEv9ALxUMCOPfBkK1YlLIHBGH2HJ6sFHNLekS0E2V/4DNhHBi0gaLsg1pclDAmw7ZLpYe7zZWB2wi+NIaQzaiYNVq2pR7LXcCzqN7JShclKHdpwMzOXRZJrysQilSIHXcOyhEJAu5LJfsqGMznQZJ96B/Dbn8O2Iv0KyQSfF5VGgmEsUxUx8YTndBHLCpkTJ3/KMY4l5nMHRrQE7+QTpfaZXkrLixoksTUrlA1G3S9XXoIFixBHZZi7Ltt1OBOLitcSqqRWp6wJMl2FCFSs0IJlH8xBL9WAF0vEGY/fuLqCyF8B2NYuhkMSKdwAPrhBf09zwKMRSmqaGRVA4IZfXUuwf6dHQv05rawbs2ZAmEk5YccaN7/1t8fDKrKLMscgefJuz73Hum6ov6F//3G0O3PIbs5k6vcp6JRPCc35BbRS3hGQtUsOrEl9kkpwX6cbgqHjGUWpu1A+CD0DChmCrHYQAPdFp5eJndOVHWBcZr7WmjT1dWSJKWgnfTiV/JXjT5jU9ANev4jmSqKEHEH+zFal0R/sYQKns7MgEQQgtR/lFov8LSyO/QiNnfR0BDx5RKU+2qzCFOeh9kD6Ag6kVhKUufSnjum15e1FFKxHyNbshRDemxiywjlhKYUmVrYE8AI2yB9IL/smpywVHRC5f6TT33Q93zqFCV7Rp+EpjrzYHPv6PXJfCxnnzu7ljqhFdhi0t+zg2QMc0mESE8Ji4ePrEC3XRHwnPHc14cS1ZlsNCT/HXk+rJtyn4g9Joio94SezqQ9y1qmLZ4g9jMJ9iPkt4V70eRQFjLCmoRwIJwNOuDLtl26wWbnQYS4oNyDEh3ETBzhZ4Pjzv+5KGljqk8Gwru9KFJjq8y34UCVs19RJ883IiSXZvPclhFKEfZng9sDNjPCDln8dks8AETxFyKPQKR6X0IIXUEjh9poScYaQ3JIFIzEgsoVUk+oKTwwFkBJ7O/IV2H22pK1FA48RsjUmYQwFPKT6FTQvzR4oOfuiDYlxyT/vSBi4QozHngoKIwHnrLCeojMbUtTkMx+U537vynwKW9KLK9tSd1JCKXQO3FDuIbCp5OG8E7BSN6tFAvfk5uiAyS/C6clRYP1ELmpanUrjX2J5iUpaopxyxFKakw4Lc5t/F7gINKAi11xg51kMjclzwjEmQwsFkscyBBZNGnH0J7KHRe4T2hO+9t9xzqSNc4Csc9dbiVATS9wLyuy7DBQI0TGjydvEpr5yuDmkKVc8G1on0jKsM3jOTI5b3iwUaPdS++T+1OB69nMLV0GfQL7MhAeChDl8AhC5GswTZqUerBy9CybM9iBpwiFrLvC9yED64HY4qfhrhGKxiLDG3HCpQcPhLsZZZ+TawuHJ5qu9CII2T1hgjC5T9oeuKRIeWHHSkJKRT8B21ScCWyi13LsUZ+ZokHasWNLniCmI4FQCnKayqUdNHO4wxPNVhKEB3GEiBPWn8nFEpLaIZfwDsXBNjEMIlkYCu+O3HrJrS/uBh46oVgDEjaR3LVkNbxU9kVfGnxxpRIVOiw3CYRISFnuJlm9sBT+tNBCOBYl7ApNAC4h5c0SnYMFHNbFhKKBaKpYPIjE1GJ9w5XYT6T9Ca1snguJp0SRLyQhZJeX+ERTuIkTFULQPlSpCLEGoSxExNnmCEdE0dCxudb6f72IJAOktdSOGMK4pegT72bZFj5rdHGnv73OHRgJhICBHUvq5rk0dbmUYoTYp5E8DmyKcnLDB0NC2JEC0FolmvWxRHjktwtieS/CSWPcPMOQr2yeX4vIXL/eNJG7JNyzCu807mwlrWHUhMoISU9LE9fjHjqbVDcyQnAMU7JZpuSYbaQhHEp8jK4rDskWhG2RY69JYtyoQY1B9jCJEM15lqbNJ0NN2cCwK8g8jDttItnB8ofQDMstQghZkpTUeF7yTZclkbeUlUj1CIxmDYzvXYEPoTwi0yN86GNBZLuodcNKPn2xRjYw/JGSwReO/4bLAkN4xRHOkKKJ6RZOuS/S464MkVCVvLEqrp3A1jGU+sARunZEAGYvhfvPggisIGg4qygECySE3H8pcVUqGWqRzmEINwa9FCmwQft9Stb8v6RVaJl9SYk1SHEIto43Uh+IMRzRGor3RWmxzxGDxBOMkVCs32cIizTlQHWbZNslZfDAiyCY+hEIW9pP2bRJKNfQ3xlSqopPsikBiG87kfuAe1RdCjD734a82MfkQiSn5JCxm2keXfcpwmivguUzIqMFZBfYugfr2qsC80tnGWUdF0YoyQ0awkMx0sTet05OKfsVOoRyus6qD7f1+wqtdnFONzJCyu6DrjSHozN4ep0iZD1AxhiW7t/FFY1rXxzwiIaNCJ2uzp4CIF35kTsJDaE8V/O1/X2nabF6wFsyhIcRac+bUomfdQLjxVUtNVq6lJyStFcD169ZBCFb6qCDA9l0ckQadL+dzehM2skhSNzgnnC2VlxGsx+Pz+lD5SG8jE5WRyquwYN9k7LEyZ57JyF0FAglL4kULtVGWJdyJ5mkvXRucgR3Wvi3xPRAYQm90v/T8XspUZ7JfrksFmiYK9QbGkK5UYyAucmO2h0sDpI/ySdLCkIi2VYDW3yxANwcYsfX4LfktPhZIKR+AE9VXNiFQlEsjeYyhzhRRYSfO93hbvZvcR8UFDUvjNCcuZER7qdJqRvcFI+JeJhDEgIJ9VUbjwb2AV2S4F6Hi5yf/5GeRSY0EmbsvOLcophh2WODLhqQRXiWyLjayT4o8jC2xBkjJDoQzTL/hal0KTNIpevKLr5hfup0IkW75LqameG+P5u3V2vNi1bvwRl2n9x5QXh+mdwhzZuzCUM5Wbb1XZUkIMybws6SASSC4AoXVAgjy1F3Re77Wick7aSuQxIRuNFe/70cOVXrNRr/e8Ey3AYXg9xHvWDYdkHOKpOM6MreWdOBet9vp9S8sCZUIr9GtTRGyHQlHHPtN3zEmi2ccEi5MXfyUKNEtVjZ4voz+wtXOUhgC6Ubb35l83xxhhQio3vsUpMwgzppNS+McEaTZ++ZTydld8mUmP/M7wKDRNKeygnChXSdWquBKRew87nzYukuJPpYmh3wHNRmJ/uVvltKX2zHdK1FCvdYHKFLx5BYdrvSEi9k+PE6m7JQQWQeIPZLf7QznrDYEI0m9UKJnWXGIX+r2bvLcpBhGqQVZj0BoVTkUo5MJ414dbpShQlDtrt4r1xF4kW8YZO//L+2Iz1r/t3eLaQI4cOOmhdM91qkrI3FrnraENElHRI+qYSQJwT83eJjjR6SOQ+SkeSqBZaSdxW1obsgrR53eWJUjiIsywiVM8DEo0zSGIqiLxFMznf3v7VkSkT2EKSMZIauZOxkP/sPY2cRZAZ7WBGEXYGQpQsj5NyzAVL3gViZ7exE6JzOWS5YXvbB9TMMlPXXgr5b0eCylkfGsNmKFO5FEKqEkAUahrqFFVlW2yGl5pQXssgR9kYKWiqjz4b+iJSCUY24BLh6EeoXeXlabaQsvtSjylR0CAvoV7Z6qnJekSFO8wnwjfo8+JfjT+HSO87Ml6ULc5/Nffzy5WMGIyCHvuLVO1Y+kM0cv7k8QAN/+e7r8UXOhNegzCGsLHB5OossJCUE3OICRSaiXPkKJL1KgdWicZAC0bkVpTpSKKJxhze336cZPf0jJAJgUD59OCgUC8Vi0Tg4fHN8YdXKdOG8cPgF6k4vzi+hehF3vWEUivY/p/Wy1ewRhETcjoZsiAoqayC91ISv0mNzoCbNKJLKSYNoDhd8CV1eFSHJdCOb/Tlgt9AL7z5dXLw/PzSk8j3DsDvaSa+WJaUBBkQlRjFS36fr9l/QHadTVnyZyzhWhmdkSLowFpjl5cQptYhSpjZjyW91EXuCICoRNvsBs/eyKiU5f3QRLvgIWP0DGrdCjHvdwK9n/aOop5NN7SxZ1HqXzd5KM56WzEVyA05kCYrWBAJEOkzNSF51Qs8jiMlhRA7+jcHsfSRZSZQp8tlFJ6nJuNFiTXB5ItSJsb4wrmTBKBz8Ij/Ip4UHkj/kZKIrIuw+hYMvMPGzmViJyBUbAVEMJsS5obWZNY2WlmANZpSwFxe2OfcFwnwJ/qUPtvGYs9pAu2RvvHmId4S80lEzLL3knjQSKtxF+CMFXmLBJ+PkY8ukgxLrquLBm/Ovh1qc7lj/slI2qbfwohOZA0eR+4J2sW1aixe0scJDwDYDvG2164drb6O3oX6nhIuS/Dv0R6mtR19jcsPBBmFuk3pMVsMZfQOfzgFW5JQpZ5LrwAUuH0ahpNhDQ0A8jNTXg2Byhzm2oIuMjH3H311dXBm2cdOJ7wboLzoIVcjQTNSbds/Z3sgwhkbpJgpwFrISoGOsqyuKMjnfFpO/rdySkAkqktRP0jA6DllDxwhrw1i3tJ+044+7+BXbkiDxvotvXbYloRpAPDh+//5n5T48vs6mmhog8tyYJOvFywumkfLmjOSjwAab8YUt93fY7MAfJPfA2Kf2QsdV4vZGcRnQqm2js6XPqZtoDLiyMIqXn3KgkpqntHre+JA1Fe/T/0F0hEJ/LoP2VWo739tcdXZ93iO0uT5HFvvyw5u/4flMKs0u/+9b8/10Gje0Cev+UjrAJ5B7xSWVuiJARJGVvvOnT+ZtWOC3S//qVkpzMYzodrygnpm070l+58H+/C22n/WYmeaWDpe32S9pH7E1MsyI+Hah4G3ZD9+VpW9Py4mkcf12yXhJI6gg/8Xu0vRKr/TN6EVuJxih9otXBIb+vTn4vUm3X7qc6i8cog8vlIUveMPr5QYhLGlhWtz77AmQofAU8lgvc/vENXjENOH6Ivf4JC+nsXyx9+L0Dds7mOf8w6uXtZMp3yZZMhY/4lerfiv54iNydklkUMOr5/+tXEKe9EmSsC2fUW3W/vxofiXrTc+L7nXt/YCfVP11tIh9ksS27c9RFbPj4wnPgNaxrziu8bsTdmwx49l+2GoZxD9ZtSqRWoxS3DP1vc2jHzD70WjpJT4/snywdY0uWOgJyfQHq876uaD0153VICF3XsnQGULdtlcK1zQcdFYdbz1Z+u63Wwv7dptFI3KXk0Gw6igW1bTJDV5HgbpCWDW/W7tpBt8NF+uBR3bfb1Qd07Save21dd1jrNbOyGsXo6ppmeg0HB/2mpkuKfion9GVw0Y1b1lWs169HXdJNc+0CxdUetXefcW0rrXWdlwzrdr19raSGeNV/2mvglrUxlvysO2ZeIEwXME3pwcpC6j+Zj25g1oAeLFlMyCfb14+5TMzQxO/PdSvOGaePHVU4ZWmLXhLI0+KSrqspuhelNrSi7WxdUSXmrtlWip1apK3B5qkKuXecehGxKeO9DbovnJ76iTRr3W5E+8qurvn5HGM8KoJMHnrMChVU3BwC69pkC2bhhTWrClq+vHFDdhYOWOSXtmarBhsv4wR1ghCdFNa2VDPi2Kqfi1tJ40YPvmrcvPo/AsT39FNQQgPbuIj+44oydvuw2sMWJKmFDUaN/6mKUOoXaOfPXqWIuxXMEKTIMQtp3GEcFS91YlEy070q3mau4qO2zLYudTLEIJ44fcz+hWp2qnagvcA8TtZDOHYYV0hIeyV6RAxhGgmV69jCMnQywjhTZ7ULW0IxT91jP+YXMU00XzH16sZQngYfsemAXs3MxaqWhWmIhQ2MoT3Uq0QRwjAcIkUQwgDKiOcmuxVExkhmhqpm2RjJInPVbOPrg1WMVvirledudKvYQhhaHAFZhWmEoNQpfuN5znCxklNFJVzhPALll2KsAENphLCPTTNSf2NhHDUMFM3fNH8efKT4wtp7Lw4RvxN2c4g8clchhCJWRP3ZhWpDQ6hijUL3ryKIqziQTRjCEewrfQ1RWg1GuKNacAwbjVq+cptK45wi/v1KGlQQ2Twk9+/nUQ/R+oqg4rlwusg8gbrOR1SPEGmo/pZhWyMPzrBO6TnOUKyI3nzhCPEBuFEgTBDEZrDajOCcP/kNl9nVkFCWCUdQtWavwwnizXwF6h8r4Xic6uDlE+wQpXZMqQFVxhh9WQ0ohN+K9k0ghDZMsT9PfnKy+wEo6F182qESHcM4fxspjEpPbEcBlog7A+1VlOotfV6vkDelprhQcqH4ier4DFPlEkpo26rNbV42ThGOGrSgi/ogFkLuwGZKMITi75vRxFqIOXYRyAIQfIr1RjCxkmrBSJf0x6hMPJZ9Ri5g5W3E2QMYb83nTbgLZR7gZBss0+46k6nU5g8RC9yhKAq8dxlCOHCmkAIrg2dvAJhFd0L/tr9dZUwiCmcZLiLgopgkWr2YwgboA72HGamaI2sMCBbjQl2BCHYQ/zKGkOIbjN8K6RUoxsMDCMINSLeio0RKLmLzmqdYF35ndVJsFJO3wRCLJxTZt0YQnj/H3PVgGEBn4C8diz7NGQrha3JOqdvViSE4BshJFuCEMvljFkT9dZK/jzt0+NKSwEUroPOKhgsQl/ulakVERPyRnmFiSlTiUhOK/w0NtR4d0d2MfilxNpVOcJxHv+sEEM5xD58l7qH2O/tYusB8lKL7Bvgh3NQqANl7emELMm4se8fS+T6kzkKndAd6IF6DcIddrqLfcnRGYpwzqbwC4siMtYZnHiL/75GMVLtlF581NBat012izE6dTbS+q3TJvzUTuBWZguucRyoJ+/fQwvolTM8lVF4ZVokHJuvwJIFg3mYxr0USbjB01JsjSolPIqNbrfanWrVLj7Uhf+6XRpSVbvaDP6uolbkku6QXVydUmM3Zae68AtSy+xWQ9R5+6foIUP53tX+CbvgKTHUIhYppVvDZ0lKi/gEa/hcaJJmERHw3dbwWVDCIkYJrOFzzghPVBYxTmA3vcnzWxV2JygaevJHxnFrL10P/2C0nAyQ7V4ouQ12jJWLbGmAbY7nJcOv706LxRzZamITE3GsIHsVPGGcXBc7NrNuHajarXf5y2HjMY9IR3Uwj8iZnKJGvR77p1HFV3W79T30Y6o1evjvHnHxWl357Ejb0j/3elvirdPWxI86GYtc22QSLv3HJ1PHdRPLGTtoWsnnz/par2aVifsyalaYDzeFgGm0h1y3bq22p43NvNXTbs0KCuvO8nnYdW5klfGbi/tmvixeQm0083l4Y3N6lsdvrprlPGo1RU4OcXyuzbzJ3ijqWY/GThGa00Ka9ZM/OQ6+faUFP+hrTF2TufqztyTof1tHBx2cycn3kM8KMblJM0izJt5Ir5eX0y0Q1uLIaVrBHi9yYyHchIwp7jzkivJdve5jTulOcj0pckJB45MGEoemLa3fZBtToiCA7qlVp+/91Pe0LuxjQxBqveYUZ3VIjmx7BAi7ZSVCbQ+P4T5BeOrQraL28jxgQk2fkhLGNEnMv/WOHGIcIYQAOF8CUGkG8z5jkkjjVOtCYEER9t9ShAg04j+TjrCB2koIUVBBUskSQojTHk0JY3idlcpuQA4xPfZVIhTRrYZ+y+MXpNE87AqE2njIEXbB2U5D2EcNerExrMcQwpZaKXsnSzCQPZ+nw0Cx7y5rL0kpTkxs6w7bkAl/1c+sAdz6TEJ4smUIW/BnKkLYwx9HuRShw16ZlxBWzUc+Lwp2/HGfzF3AipwaJkUICgdPvy0EtWRhpk62FbOusTQKhBrJrppTjWt6BcJZP8MMEEHYqJWbRL8IhKMZumna5wZcFLKr8i9pPqqPM6UQ4kdsDkbYh88JY0XTagB7JIOJt3iDbDDlgCPUMEJn7/StAmGLICz3bvm3bkF5nXaPrqs0CBQIt2StKoYMBfmwSpqSdkFhcHzxO3r1ZL72AhHjA0ITAlaS8EX/XtOVmP5w5JC9oujuvzGEaAy7OCsaRVhnY6iNp/QsGcO9stlLIoTnM3sx73RIuvox53L5REPBEUofGhuSfDDeZRLB7FVAUqk+jyMcav0eaSYjPOPz8GRKzxKEaLSscQzhFH+CNO3zniqakGohd71zIBMIhTI7Rc4Wy3gfof9b9SbTgAqEkIrrWxGE27OILj0Zc4QwwYk0cIQnvW53n60IPYF8TzIZy+AJYVUC4Qw4YBlvbMu01q2TjhA17+YjCB1L2EP031iMIV4M6IKa4ghBTYN5emQJkcIbJKqCUGicLNjYjXBLkeBs6Blx45Cbtk1DiNepZYRd05J9mtGZQIjXWq9hHnCE0I+QfN21hEhomZa6Ryc6O2ylxv1SSvi5oN7ACmf2iOU36cajY0dkpznCU+xx9yjCUTMTQXhfBoTUL8WmZ7oVfmkLA7t2VPsdS+TOVbU1EkHoq86uAjUqjsM/CTLDT5qiQ8iX6Ve1IbAwekuV/H3ZYXsg948cB2+R323mAeG+6UClwqiGQg7A6Th4Q+9TC/t9Th5fCDsDQxyjXZcdXMXQxX00Nh3nLE1Ml7CQ+JQkzBLZTlWEP6ue7gFV4Qmz7vi01+iTQ+MuuCOzXrV7SkZ424Pjpz3AVa2fwq/d3ukYHdOm+NRetw5/nfar8AdSWdMb4oEAAABoSURBVL3xKfqBvCRy4Ym2zZjjFooP8d/dLXpctzUi157G5RQqh5DlWP+qOF2K8Afr9a+79g+hMJxMFjQjvzPKf5RQgL9c/oAZm/liMQnDJ0T5r/RKr/RKr/RKr/RKr/RKr/RKr/Qo/T+m0VA3kAbv1QAAAABJRU5ErkJggg==",
            "Ven y deleitate con nuestra comida",
            openingHour = 8,
            closeHour = 20,
            workingDays = arrayListOf(1, 2, 3, 5, 6, 7)
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
            index = 0
        )
    }
}
