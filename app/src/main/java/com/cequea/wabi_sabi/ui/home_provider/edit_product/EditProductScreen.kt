package com.cequea.wabi_sabi.ui.home_provider.edit_product

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.ui.components.RoundedButton
import com.cequea.wabi_sabi.ui.components.TransparentTextField
import com.cequea.wabi_sabi.ui.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalTime


@Composable
fun EditProductScreen(
    idProduct: Long,
    onSave: (Product) -> Unit,
    onBack: () -> Unit,
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val product by viewModel.product.observeAsState()

    viewModel.getProductById(idProduct)

    val context = LocalContext.current

    LaunchedEffect(viewModel.loadError) {
        withContext(Dispatchers.Main) {
            viewModel.loadError.collect { error ->
                if(!error.isNullOrEmpty()){
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (!product.isNullOrEmpty()){
        val nameValue = remember { mutableStateOf(product!!.name) }
        val imageUrlValue = remember { mutableStateOf(product!!.imageUrl) }
        val priceValue = remember { mutableStateOf(product!!.price.toString()) }
        val descriptionValue = remember { mutableStateOf(product!!.description) }
        val openingHoursValue = remember { mutableStateOf(product!!.openingHours.toString()) }
        val closingHoursValue = remember { mutableStateOf(product!!.closingHours.toString()) }
        val productsQuantityValue = remember { mutableStateOf(product!!.productsQuantity.toString()) }
        val quantityValue = remember { mutableStateOf(product!!.quantity.toString()) }
        val quantityValueMutable = remember { mutableStateOf("0") }
        val statusValue = remember { mutableStateOf(product!!.status) }

        val focusManager = LocalFocusManager.current

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            onBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = MaterialTheme.colors.primary
                        )
                    }

                    Text(
                        text = "Editar Producto",
                        style = MaterialTheme.typography.h5.copy(
                            color = MaterialTheme.colors.primary
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TransparentTextField(
                        textFieldValue = nameValue,
                        textLabel = "Nombre",
                        keyboardType = KeyboardType.Text,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = imageUrlValue,
                        textLabel = "Url de Imagen",
                        keyboardType = KeyboardType.Text,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = priceValue,
                        textLabel = "Precio",
                        keyboardType = KeyboardType.Number,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = descriptionValue,
                        textLabel = "Descripcion",
                        keyboardType = KeyboardType.Text,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = openingHoursValue,
                        textLabel = "Hora de Apertura (HH:mm)",
                        keyboardType = KeyboardType.Text,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = closingHoursValue,
                        textLabel = "Hora de Cierre (HH:mm)",
                        keyboardType = KeyboardType.Text,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = productsQuantityValue,
                        textLabel = "Cantidad de Productos en el Pack",
                        keyboardType = KeyboardType.Number,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        imeAction = ImeAction.Next
                    )

                    TransparentTextField(
                        textFieldValue = quantityValue,
                        textLabel = "Cantidad Disponible",
                        keyboardType = KeyboardType.Number,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.clearFocus()
                            }
                        ),
                        imeAction = ImeAction.Done
                    )
                    /*Row(){
                        TransparentTextField(
                            textFieldValue = quantityValue,
                            textLabel = "Cantidad Disponible",
                            keyboardType = KeyboardType.Number,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.clearFocus()
                                }
                            ),
                            imeAction = ImeAction.Done,
                            modifier = Modifier.weight(2F),
                            enabled = false
                        )

                        TransparentTextField(
                            textFieldValue = quantityValueMutable,
                            textLabel = "Agregar o Eliminar",
                            keyboardType = KeyboardType.Number,
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.clearFocus()
                                }
                            ),
                            imeAction = ImeAction.Done,
                            modifier = Modifier.weight(2F)
                        )

                        IconButton(
                            onClick = { /* Handle delete click */ },
                            modifier = Modifier.weight(1F)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = MaterialTheme.colors.primary
                            )
                        }

                        IconButton(
                            onClick = { /* Handle add click */ },
                            modifier = Modifier.weight(1F)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Icon",
                                tint = MaterialTheme.colors.primary
                            )
                        }

                    }*/

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
                            text = "Mostrar Producto"
                        )
                    }

                    Text(
                        text = "Productos Actualmente en Carritos: ${product?.quantityFreeze}"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    RoundedButton(
                        text = "Guardar Cambios",
                        onClick = {
                            val updatedProduct = Product(
                                id = product!!.id,
                                name = nameValue.value!!,
                                imageUrl = imageUrlValue.value!!,
                                price = priceValue.value.toDouble(),
                                description = descriptionValue.value!!,
                                openingHours = LocalTime.parse(openingHoursValue.value),
                                closingHours = LocalTime.parse(closingHoursValue.value),
                                productsQuantity = productsQuantityValue.value.toInt(),
                                quantity = quantityValue.value.toInt(),
                                quantityFreeze = product!!.quantityFreeze,
                                restaurantId = product!!.restaurantId,
                                categoryId = product!!.categoryId,
                                countInCart = product!!.countInCart,
                                openingDays = product!!.openingDays,
                                status = statusValue.value
                            )
                            viewModel.updateProduct(updatedProduct)
                        }
                    )
                }
            }
        }
    }
}