package com.cequea.wabi_sabi.ui.home.order

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.ui.components.buttons.TwoButtonsRow
import com.cequea.wabi_sabi.ui.components.buttons.WabiSabiButton
import com.cequea.wabi_sabi.ui.model.Order
import com.cequea.wabi_sabi.ui.model.OrderProduct
import com.cequea.wabi_sabi.ui.theme.WabiSabiTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel = hiltViewModel(),
    isAdmin: Boolean = false,
    isProvider: Boolean = false
) {
    val orders = viewModel.orders.collectAsState()
    if (isAdmin) {
        viewModel.getAllOrders()
    } else if(isProvider) {
        viewModel.getOrdersByUserRestaurant()
    }else {
        viewModel.getOrdersByUser()
    }

    val context = LocalContext.current

    LaunchedEffect(viewModel.loadError) {
        withContext(Dispatchers.Main) {
            viewModel.loadError.collect { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
    ) {

        orders.value.forEach { order ->
            item {
                OrderItem(
                    order = order,
                    isAdmin = isAdmin,
                    isProvider = isProvider,
                    onAcceptClick = viewModel::acceptPayment,
                    onDeclineClick = viewModel::refusePayment,
                    onCancelClick = viewModel::cancelOrder,
                    onPrepareOrderClick = viewModel::orderPrepared,
                    onOrderSendClick = viewModel::orderSend,
                    onOrderReceiveClick = viewModel::orderReceive,
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.LightGray,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    isAdmin: Boolean,
    isProvider: Boolean,
    onAcceptClick: (Int) -> Unit = {},
    onDeclineClick: (Int) -> Unit = {},
    onCancelClick: (Int) -> Unit = {},
    onPrepareOrderClick: (Int) -> Unit = {},
    onOrderSendClick: (Int) -> Unit = {},
    onOrderReceiveClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = "Order #${String.format("%04d", order.id)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = order.dateTime,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    order.products.forEach { item ->
                        OrderProductItem(
                            item = item,
                            order = order,
                            isAdmin = isAdmin,
                            isProvider = isProvider
                        )
                    }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = "Total: ${order.totalPrice}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.padding(8.dp)
                                )

                                Text(
                                    text = order.status.description,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        )
                        if (isAdmin && order.status.id == 1) {
                            TwoButtonsRow(
                                onDeclineClick,
                                onAcceptClick,
                                order,
                                "Rechazar",
                                "Aceptar"
                            )
                        }
                        if (isProvider && order.status.id == 2) {
                            TwoButtonsRow(
                                onCancelClick,
                                onPrepareOrderClick,
                                order,
                                "Rechazar Pedido",
                                "Preparar Pedido"
                            )
                        }
                        if (isProvider && order.status.id == 7) {
                            TwoButtonsRow(
                                {  },
                                onOrderSendClick,
                                order,
                                null,
                                "Enviar Pedido"
                            )
                        }
                        if (isProvider && order.status.id == 3) {
                            TwoButtonsRow(
                                {  },
                                onOrderReceiveClick,
                                order,
                                null,
                                "Pedido Completado"
                            )
                        }
                    }

                }
            )
        }
    )
}


@Composable
fun OrderProductItem(item: OrderProduct, order: Order, isAdmin: Boolean, isProvider: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.productImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                placeholder = painterResource(R.drawable.placeholder_logo),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)

            )
            Column {
                Text(
                    text = item.productName,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "$${item.price} x${item.quantity}",
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(4.dp)
                )
            }

        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun OrderHistoryPreviewDark() {
    WabiSabiTheme(darkTheme = true) {
        OrderHistoryScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun OrderHistoryPreviewLight() {
    WabiSabiTheme(darkTheme = false) {
        OrderHistoryScreen()
    }
}