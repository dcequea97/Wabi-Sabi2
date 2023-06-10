package com.cequea.wabi_sabi.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.cequea.wabi_sabi.ui.theme.Dimension

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    icon: ImageVector,
    iconSize: Dp = Dimension.mdIcon,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = Dimension.zero,
    paddingValue: PaddingValues = PaddingValues(Dimension.xs),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable {
                onButtonClicked()
            }
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            imageVector = icon,
            contentDescription = "icon",
            tint = iconTint
        )
    }
}