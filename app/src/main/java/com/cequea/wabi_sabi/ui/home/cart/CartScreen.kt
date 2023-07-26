package com.cequea.wabi_sabi.ui.home.cart

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.components.CircularIndeterminateProgressBar
import com.cequea.wabi_sabi.ui.components.ConfirmationDialog
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.ui.components.QuantitySelector
import com.cequea.wabi_sabi.ui.components.WabiSabiDivider
import com.cequea.wabi_sabi.ui.components.WabiSabiSurface
import com.cequea.wabi_sabi.ui.components.buttons.WabiSabiButton
import com.cequea.wabi_sabi.ui.home.feed.details.ProductImage
import com.cequea.wabi_sabi.ui.theme.AlphaNearOpaque
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import com.cequea.wabi_sabi.ui.utils.formatPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CartScreen(
    onProductClick: (Long) -> Unit,
    onProceedToCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {
    val isLoading by remember(viewModel::isLoading)

    val isRegisteredOrderSuccessfully by remember(viewModel::isRegisteredOrderSuccessfully)

    var showDialog by remember { mutableStateOf(false) }

    val bankValue = rememberSaveable{ mutableStateOf("") }
    val phoneValue = rememberSaveable{ mutableStateOf("") }
    val referenceValue = rememberSaveable{ mutableStateOf("") }

    // Call getCartProducts() to load the products
    LaunchedEffect(true) {
        viewModel.getCartProducts()
        viewModel.getDollarPrice()
    }

    val products by viewModel.products.collectAsState()
    val dollarPrice by viewModel.dollarPrice.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel.loadError) {
        withContext(Dispatchers.Main) {
            viewModel.loadError.collect { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (!products.isNullOrEmpty()){
        Cart(
            products = products,
            removeProduct = viewModel::removeProduct,
            increaseItemCount = viewModel::increaseItemCount,
            decreaseItemCount = viewModel::decreaseItemCount,
            onProductClick = onProductClick,
            onProceedToCheckoutClick = {
                showDialog = true
            },
            modifier = modifier
        )
    }else{
        EmptyCartMessage()
    }

    if (showDialog) {
        ConfirmationDialog(
            title = context.getString(R.string.confirmation),
            message =
            context.getString(R.string.purchase_order_confirmation),
            confirmText = context.getString(R.string.confirm_transfer),
            cancelText = context.getString(R.string.no),
            onConfirm = { bank, phoneNumber, referenceNumber ->
                viewModel.saveOrder(bank, phoneNumber, referenceNumber)
                showDialog = false
            },
            onCancel = { showDialog = false },
            price = dollarPrice * ((products.sumOf { it.price * it.countInCart }) + 2.0) ,
            bankValue,
            phoneValue,
            referenceValue
        )
    }

    CircularIndeterminateProgressBar(isDisplayed = isLoading)

    if (isRegisteredOrderSuccessfully) {
        Toast.makeText(context, "Orden registrada satisfactoriamente", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun EmptyCartMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "El Carrito esta vacio, agregue productos antes de continuar",
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Cart(
    products: List<Product>,
    removeProduct: (Long) -> Unit,
    increaseItemCount: (Long, Int) -> Unit,
    decreaseItemCount: (Long, Int) -> Unit,
    onProductClick: (Long) -> Unit,
    onProceedToCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    WabiSabiSurface(modifier = modifier.fillMaxSize()) {
        Column {
            CartContent(
                products = products,
                removeProduct = removeProduct,
                increaseItemCount = increaseItemCount,
                decreaseItemCount = decreaseItemCount,
                onProductClick = onProductClick
            )
            CheckoutBar(onProceedToCheckoutClick)
        }
    }
}

@Composable
private fun CartContent(
    products: List<Product>,
    removeProduct: (Long) -> Unit,
    increaseItemCount: (Long, Int) -> Unit,
    decreaseItemCount: (Long, Int) -> Unit,
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val productCountFormattedString = remember(products.size, resources) {
        resources.getQuantityString(
            R.plurals.cart_order_count,
            products.size, products.size
        )
    }
    LazyColumn(modifier) {
        item {
            Text(
                text = stringResource(R.string.cart_order_header, productCountFormattedString),
                style = MaterialTheme.typography.h6,
                color = WabiSabiTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 24.dp, vertical = 4.dp)
                    .wrapContentHeight()
            )
        }
        items(products) { product ->
            SwipeDismissItem(
                background = { offsetX ->
                    /*Background color changes from light gray to red when the
                    swipe to delete with exceeds 160.dp*/
                    val backgroundColor = if (offsetX < (-160).dp) {
                        WabiSabiTheme.colors.error
                    } else {
                        WabiSabiTheme.colors.uiFloated
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(backgroundColor),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Set 4.dp padding only if offset is bigger than 160.dp
                        val padding: Dp by animateDpAsState(
                            if (offsetX > (-160).dp) 4.dp else 0.dp
                        )
                        Box(
                            Modifier
                                .width(offsetX * -1)
                                .padding(padding)
                        ) {
                            // Height equals to width removing padding
                            val height = (offsetX + 8.dp) * -1
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(height)
                                    .align(Alignment.Center),
                                shape = CircleShape,
                                color = WabiSabiTheme.colors.error
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Icon must be visible while in this width range
                                    if (offsetX < (-40).dp && offsetX > (-152).dp) {
                                        // Icon alpha decreases as it is about to disappear
                                        val iconAlpha: Float by animateFloatAsState(
                                            if (offsetX < (-120).dp) 0.5f else 1f
                                        )

                                        Icon(
                                            imageVector = Icons.Filled.DeleteForever,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .graphicsLayer(alpha = iconAlpha),
                                            tint = WabiSabiTheme.colors.uiBackground,
                                            contentDescription = null,
                                        )
                                    }
                                    /*Text opacity increases as the text is supposed to appear in
                                    the screen*/
                                    val textAlpha by animateFloatAsState(
                                        if (offsetX > (-144).dp) 0.5f else 1f
                                    )
                                    if (offsetX < (-120).dp) {
                                        Text(
                                            text = stringResource(id = R.string.remove_item),
                                            style = MaterialTheme.typography.subtitle1,
                                            color = WabiSabiTheme.colors.uiBackground,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .graphicsLayer(
                                                    alpha = textAlpha
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                onItemDismissed = {
                    removeProduct(product.id)
                },
            ) {
                CartItem(
                    product = product,
                    removeProduct = removeProduct,
                    increaseItemCount = increaseItemCount,
                    decreaseItemCount = decreaseItemCount,
                    onProductClick = onProductClick
                )
            }
        }
        item {
            SummaryItem(
                subtotal = products.sumOf { it.price * it.countInCart },
                shippingCosts = 2.0
            )
        }
    }
}

@Composable
fun CartItem(
    product: Product,
    removeProduct: (Long) -> Unit,
    increaseItemCount: (Long, Int) -> Unit,
    decreaseItemCount: (Long, Int) -> Unit,
    onProductClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onProductClick(product.id) }
            .background(WabiSabiTheme.colors.uiBackground)
            .padding(horizontal = 24.dp)

    ) {
        val (divider, image, name, tag, priceSpacer, price, remove, quantity) = createRefs()
        createVerticalChain(name, tag, priceSpacer, price, chainStyle = ChainStyle.Packed)
        ProductImage(
            imageUrl = product.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = product.name,
            style = MaterialTheme.typography.subtitle1,
            color = WabiSabiTheme.colors.textSecondary,
            modifier = Modifier.constrainAs(name) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = remove.start,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        IconButton(
            onClick = { removeProduct(product.id) },
            modifier = Modifier
                .constrainAs(remove) {
                    top.linkTo(name.top)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = WabiSabiTheme.colors.iconSecondary,
                contentDescription = stringResource(R.string.label_remove)
            )
        }
        Text(
            text = product.description,
            style = MaterialTheme.typography.body1,
            color = WabiSabiTheme.colors.textHelp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(tag) {
                linkTo(
                    start = image.end,
                    startMargin = 16.dp,
                    end = parent.end,
                    endMargin = 16.dp,
                    bias = 0f
                )
                width = Dimension.fillToConstraints
            }
        )
        Spacer(
            Modifier
                .height(8.dp)
                .constrainAs(priceSpacer) {
                    linkTo(top = tag.bottom, bottom = price.top)
                }
        )
        Text(
            text = formatPrice(product.price),
            style = MaterialTheme.typography.subtitle1,
            color = WabiSabiTheme.colors.textPrimary,
            modifier = Modifier.constrainAs(price) {
                linkTo(
                    start = image.end,
                    end = quantity.start,
                    startMargin = 16.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        QuantitySelector(
            count = product.countInCart,
            decreaseItemCount = { decreaseItemCount(product.id, product.countInCart - 1) },
            increaseItemCount = { increaseItemCount(product.id, product.countInCart + 1) },
            modifier = Modifier.constrainAs(quantity) {
                baseline.linkTo(price.baseline)
                end.linkTo(parent.end)
            }
        )
        WabiSabiDivider(
            Modifier.constrainAs(divider) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun SummaryItem(
    subtotal: Double,
    shippingCosts: Double,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = stringResource(R.string.cart_summary_header),
            style = MaterialTheme.typography.h6,
            color = WabiSabiTheme.colors.brand,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .heightIn(min = 56.dp)
                .wrapContentHeight()
        )
        Row(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = stringResource(R.string.cart_subtotal_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(subtotal),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_shipping_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(shippingCosts),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        WabiSabiDivider()
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            Text(
                text = stringResource(R.string.cart_total_label),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .wrapContentWidth(Alignment.End)
                    .alignBy(LastBaseline)
            )
            Text(
                text = formatPrice(subtotal + shippingCosts),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.alignBy(LastBaseline)
            )
        }
    }
}

@Composable
private fun CheckoutBar(
    onProceedToCheckoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.background(
            WabiSabiTheme.colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    ) {
        WabiSabiDivider()
        Row {
            Spacer(Modifier.weight(1f))
            WabiSabiButton(
                onClick = { onProceedToCheckoutClick() },
                shape = RectangleShape,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.cart_checkout),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

//@Preview("default")
//@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
//@Preview("large font", fontScale = 2f)
//@Composable
//private fun CartPreview() {
//    WabiSabiTheme {
//        CartScreen(
//            products = SnackRepo.getCart(),
//            removeProduct = {},
//            increaseItemCount = {},
//            decreaseItemCount = {},
//            inspiredByCart = SnackRepo.getInspiredByCart(),
//            onProductClick = {}
//        )
//    }
//}

