package com.cequea.wabi_sabi.ui.home.feed.details


import android.content.res.Configuration
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.ui.model.Restaurant
import com.cequea.wabi_sabi.ui.components.QuantitySelector
import com.cequea.wabi_sabi.ui.components.RatingComposable
import com.cequea.wabi_sabi.ui.components.WabiSabiDivider
import com.cequea.wabi_sabi.ui.components.WabiSabiSurface
import com.cequea.wabi_sabi.ui.components.buttons.WabiSabiButton
import com.cequea.wabi_sabi.ui.components.mirroringBackIcon
import com.cequea.wabi_sabi.ui.theme.Neutral8
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.formatPrice
import com.cequea.wabi_sabi.ui.utils.getProductsHours
import kotlin.math.max
import kotlin.math.min


private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun RestaurantDetailScreen(
    restaurantId: Long,
    onProductClick: (Long) -> Unit,
    upPress: () -> Unit,
    isProvider: Boolean = false,
    viewModel: RestaurantDetailViewModel = hiltViewModel()
) {
    viewModel.getRestaurantById(restaurantId)
    val restaurant by viewModel.restaurant.collectAsState()
    val products by viewModel.products.collectAsState()

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(
            products = products,
            scroll = scroll,
            onProductClick = onProductClick
        )
        Title(restaurant, isProvider) { scroll.value }
        Image(restaurant.profileImageUrl) { scroll.value }
        Up(upPress)
        //CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(WabiSabiTheme.colors.tornado1))
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = WabiSabiTheme.colors.iconInteractive,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun Body(
    products: List<Product>,
    onProductClick: (Long) -> Unit,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            WabiSabiSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))

                    products.forEach { product ->
                        key(product.id) {
                            ProductItem(
                                product = product,
                                onProductClick = { onProductClick(product.id) })
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductItem(product: Product, onProductClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(130.dp)
                .fillMaxSize()
                .clickable(
                    onClick = onProductClick,
                )
        ) {
            val (name, description, price, image, hours, quantities) = createRefs()

            Text(
                text = product.name,
                style = MaterialTheme.typography.h5,
                color = WabiSabiTheme.colors.textSecondary,
                textAlign = TextAlign.Start,
                maxLines = 1,
                softWrap = true,
                modifier = Modifier
                    .wrapContentWidth()
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        bottom.linkTo(description.top)
                        end.linkTo(image.start, margin = 10.dp)
                    }
            )

            Text(
                text = product.description,
                maxLines = 2,
                softWrap = true,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .constrainAs(description) {
                        top.linkTo(name.bottom)
                        bottom.linkTo(price.top)
                        start.linkTo(name.start)
                        end.linkTo(image.start, margin = 10.dp)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth())

            Text(text = formatPrice(product.price), modifier = Modifier.constrainAs(price) {
                top.linkTo((description.bottom))
                bottom.linkTo(quantities.top)
                start.linkTo(name.start)
            })

            Text(
                text = "Cantidad de Productos: ${product.productsQuantity}",
                color = Color.Blue,
                modifier = Modifier.constrainAs(quantities) {
                    top.linkTo((price.bottom))
                    bottom.linkTo(parent.bottom)
                    start.linkTo(name.start)
                })

            Text(
                text = getProductsHours(product.openingHours, product.closingHours),
                modifier = Modifier.constrainAs(hours) {
                    top.linkTo((quantities.bottom))
                    bottom.linkTo(parent.bottom)
                    start.linkTo(name.start)
                })

            ProductImageItem(
                imageUrl = product.imageUrl,
                contentDescription = null,
                elevation = 5.dp,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

            createHorizontalChain(name, image, chainStyle = ChainStyle.SpreadInside)
            createVerticalChain(
                name,
                description,
                price,
                quantities,
                hours,
                chainStyle = ChainStyle.SpreadInside
            )
        }
    }

}

@Composable
fun ProductImageItem(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    WabiSabiSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CutCornerShape(5.dp),
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            modifier = modifier
                .width(90.dp)
                .height(90.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun Title(restaurant: Restaurant, isProvider: Boolean, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val statusValue = remember { mutableStateOf(restaurant.status) }


    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = WabiSabiTheme.colors.uiBackground)
    ) {
        Text(
            text = restaurant.name,
            style = MaterialTheme.typography.h4,
            fontSize = 32.sp,
            color = WabiSabiTheme.colors.textSecondary,
            modifier = HzPadding
        )
        if (isProvider){
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = statusValue.value,
                    onCheckedChange = { statusValue.value = it }
                )

                Text(
                    text = "Abierto"
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        WabiSabiDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        ProductImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    WabiSabiSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun CartBottomBar(modifier: Modifier = Modifier) {
    val (count, updateCount) = remember { mutableStateOf(1) }
    WabiSabiSurface(modifier) {
        Column {
            WabiSabiDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(HzPadding)
                    .heightIn(min = BottomBarHeight)
            ) {
                QuantitySelector(
                    count = count,
                    decreaseItemCount = { if (count > 0) updateCount(count - 1) },
                    increaseItemCount = { updateCount(count + 1) }
                )
                Spacer(Modifier.width(16.dp))
                WabiSabiButton(
                    onClick = { /* todo */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun SnackDetailPreview() {
    WabiSabiTheme {
        RestaurantDetailScreen(
            restaurantId = 1L,
            onProductClick = { },
            upPress = { }
        )
    }
}
