package com.cequea.wabi_sabi.ui.home.profile


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.ui.components.buttons.DrawableButton
import com.cequea.wabi_sabi.ui.components.buttons.IconButton
import com.cequea.wabi_sabi.ui.theme.Dimension
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme

@Composable
fun ProfileScreen(
    onAddressClicked: () -> Unit,
    onRegisterBusinessClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    isAdminOrProvider: Boolean = false,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    viewModel.getUser()
    val user by viewModel.user.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimension.pagePadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.your_profile),
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.onPrimary,
            )
        }
        /** Header section */
        item {
            ProfileHeaderSection(
                imageUrl = user?.imageUrl,
                name = user?.name,
                email = user?.email,
                phone = user?.phone,
            )
        }

        /** General options */
        item {
            Text(
                text = stringResource(id = R.string.general),
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        if (!isAdminOrProvider){
            item {
                ProfileOptionItem(
                    icon = R.drawable.baseline_edit_location_24,
                    title = R.string.directions,
                    onOptionClicked = {
                        onAddressClicked()
                    },
                )
            }
            item {
                ProfileOptionItem(
                    icon = R.drawable.baseline_add_business_24,
                    title = R.string.register_business,
                    onOptionClicked = {
                        onRegisterBusinessClicked()
                    },
                )
            }
        }

        item {
            ProfileOptionItem(
                icon = R.drawable.baseline_logout_24,
                title = R.string.logout,
                onOptionClicked = {
                    onLogoutClicked()
                },
            )
        }
    }
}

@Composable
fun ProfileOptionItem(icon: Int?, title: Int?, onOptionClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .clickable { onOptionClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        DrawableButton(
            painter = rememberAsyncImagePainter(model = icon),
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
            iconTint = MaterialTheme.colors.onPrimary,
            onButtonClicked = {},
            iconSize = Dimension.smIcon,
            paddingValue = PaddingValues(Dimension.md),
            shape = CircleShape,
        )
        title?.let {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.8f),
            )
        }
        IconButton(
            icon = Icons.Rounded.KeyboardArrowRight,
            backgroundColor = MaterialTheme.colors.background,
            iconTint = MaterialTheme.colors.onPrimary,
            onButtonClicked = {},
            iconSize = Dimension.smIcon,
            paddingValue = PaddingValues(Dimension.md),
            shape = CircleShape,
        )
    }
}

@Composable
fun ProfileHeaderSection(imageUrl: String?, name: String?, email: String?, phone: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "User image",
            placeholder = painterResource(R.drawable.placeholder_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(82.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)

        )

        Column {
            Text(
                text = name ?: "",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = email ?: "",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = phone ?: "",
                style = MaterialTheme.typography.caption
                    .copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    WabiSabiTheme {
        ProfileScreen(
            onAddressClicked = { },
            onRegisterBusinessClicked = { },
            onLogoutClicked = { }
        )
    }
}