package com.cequea.wabi_sabi.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.ui.model.Restaurant
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme

@Composable
fun RatingComposable(restaurant: Restaurant, hzPadding: Modifier = Modifier.padding(8.dp)) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = hzPadding
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            tint = Color(0xFF2A7704),
            contentDescription = stringResource(R.string.label_filters),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = restaurant.rating.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = WabiSabiTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .size(24.dp)
                .wrapContentHeight()
        )
    }
}