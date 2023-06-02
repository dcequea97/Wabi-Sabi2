package com.cequea.wabi_sabi.ui.home.feed.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.cequea.wabi_sabi.ui.components.WabiSabiDivider
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.formatPrice

@Composable
fun ProductItemScreen(
    idProduct: Long,
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
                AddToCartButton(
                    count = product!!.countInCart,
                    onAddClick = { /* Add product to cart */ },
                    onRemoveClick = { /* Remove product from cart */ },
                    modifier = Modifier.padding(top = 16.dp)
                )
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
fun AddToCartButton(
    count: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Remove from cart"
            )
        }
        Text(
            text = count.toString(),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(36.dp),
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = onAddClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add to cart"
            )
        }
    }
}

@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun PreviewProductItem(){
    val product = Product(
        id = 1L,
        restaurantId = 1L,
        description = "My Product Description",
        categoryId = listOf(1,2,4,9),
        name = "Cupcake",
        imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
        price = 10.59,
    )
    WabiSabiTheme {
        ProductItemScreen(1)
    }
}