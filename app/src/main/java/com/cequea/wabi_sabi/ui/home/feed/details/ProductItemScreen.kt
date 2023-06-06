package com.cequea.wabi_sabi.ui.home.feed.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.data.model.Product
import com.cequea.wabi_sabi.ui.components.QuantitySelector
import com.cequea.wabi_sabi.ui.components.WabiSabiDivider
import com.cequea.wabi_sabi.ui.components.WabiSabiSurface
import com.cequea.wabi_sabi.ui.components.buttons.WabiSabiButton
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.formatPrice
import java.time.DayOfWeek
import java.time.LocalTime

@Composable
fun ProductItemScreen(
    idProduct: Long,
    onAddToCartClick: () -> Unit,
    viewModel: ProductItemViewModel = hiltViewModel()
) {
    val product by viewModel.product.observeAsState()
    viewModel.getProductById(idProduct)
    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product!!.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                placeholder = painterResource(R.drawable.placeholder),
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = product!!.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = formatPrice(product!!.price),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }

            Text(
                text = product!!.description,
                style = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            WabiSabiDivider(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                CartBottomBar(viewModel, product!!, onAddToCartClick)
                Text(
                    text = formatPrice(product!!.countInCart * product!!.price),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun CartBottomBar(
    viewModel: ProductItemViewModel,
    product: Product,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (count, updateCount) = remember { mutableStateOf(product.countInCart) }
    WabiSabiSurface(modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(Modifier.padding(24.dp))
                    .heightIn(min = 56.dp)
            ) {
                QuantitySelector(
                    count = count,
                    decreaseItemCount = { if (count > 0) updateCount(count - 1) },
                    increaseItemCount = { updateCount(count + 1) }
                )
                Spacer(Modifier.width(16.dp))
                WabiSabiButton(
                    onClick = {
                        product.countInCart = count
                        viewModel.addProductToCart(product = product)
                        onAddToCartClick()},
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        modifier = Modifier.fillMaxWidth(),
                        color = WabiSabiTheme.colors.textSecondary,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewProductItem(){
    val breakfastFoods = listOf("huevos revueltos", "panqueques", "tostadas", "café", "jugo de naranja")
    val breakfastDescription = "El pack puede contener empanadas, cachitos, pan, o arepas, junto con: ${breakfastFoods.joinToString(separator = ", ")}"

    val product =  Product(
        id = 1L,
        restaurantId = 1L,
        name = "Pack de Desayuno",
        imageUrl = "https://ejemplo.com/pack-de-desayuno.jpg",
        price = 10.0,
        description = breakfastDescription,
        categoryId = listOf(2L),
        countInCart = 0,
        openingHours = LocalTime.of(6, 0),
        closingHours = LocalTime.of(11, 0),
        openingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    )
    WabiSabiTheme {
        ProductItemScreen(1, {})
    }
}