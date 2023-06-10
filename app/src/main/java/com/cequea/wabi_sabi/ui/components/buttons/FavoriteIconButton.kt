package com.cequea.wabi_sabi.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FavoriteIconButton(
    isFavorite: Boolean, onClick: (Long) -> Unit, idRestaurant: Long
) {
    IconButton(
        onClick = { onClick(idRestaurant) },
        modifier = Modifier.padding(8.dp)
    ) {
        val icon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite
        Icon(
            imageVector = icon,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
        )
    }
}