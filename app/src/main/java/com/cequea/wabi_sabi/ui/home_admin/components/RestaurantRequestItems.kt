package com.cequea.wabi_sabi.ui.home_admin.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cequea.wabi_sabi.ui.components.WabiSabiCard
import com.cequea.wabi_sabi.ui.components.buttons.WabiSabiButton
import com.cequea.wabi_sabi.ui.home.components.RestaurantImage
import com.cequea.wabi_sabi.ui.home.components.RestaurantLogo
import com.cequea.wabi_sabi.ui.model.RestaurantRequest
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.getOpeningString
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun RestaurantRequestItem(
    restaurant: RestaurantRequest,
    onAcceptClick: (Long) -> Unit,
    onDeclineClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    WabiSabiCard(
        elevation = 4.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.surface),
        shape = RoundedCornerShape(10),
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
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
                text = getOpeningString(restaurant.openingHours,restaurant.closingHours),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "Solicitado por: David Cequea",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = WabiSabiTheme.colors.textSecondary,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                WabiSabiButton(onClick = { onDeclineClick(restaurant.id) }) {
                    Text(text = "Declinar")
                }
                Spacer(modifier = Modifier.width(15.dp))
                WabiSabiButton(onClick = { onAcceptClick(restaurant.id) }) {
                    Text(text = "Aceptar")
                }

            }
            //RatingComposable(restaurant)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHighlightRestaurantItem() {
    val restaurant = RestaurantRequest(
        id = 1,
        name = "La Panaderia del Pueblo",
        backgroundImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-background.jpg",
        profileImageUrl = "https://ejemplo.com/la-panaderia-del-pueblo-profile.jpg",
        tagline = "El sabor de casa en cada pan",
        workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
        rating = 4.7,
        openingHours = LocalTime.of(6, 0),
        closingHours = LocalTime.of(20, 0),
        isFavorite = false,
        isHighlight = false,
        status = false,
        idUserRequested = 1
    )
    WabiSabiTheme {
        RestaurantRequestItem(
            restaurant = restaurant,
            onAcceptClick = {},
            onDeclineClick = {}
        )
    }
}