package com.cequea.wabi_sabi.data.network.services

import com.cequea.wabi_sabi.data.model.CartResponse
import com.cequea.wabi_sabi.data.model.toDomain
import com.cequea.wabi_sabi.data.network.SafeApiCall
import com.cequea.wabi_sabi.data.network.apiclients.ProductApiClient
import com.cequea.wabi_sabi.ui.model.Product
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import javax.inject.Inject

class ProductService @Inject constructor(
    private val api: ProductApiClient,
    private val safeApiCall: SafeApiCall
) {

    suspend fun getAllProductsByRestaurant(idRestaurant: Long): List<Product> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getProductsByRestaurantId(idRestaurant) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                emptyList()
            } else {
                apiCall.body.map {
                    it.toDomain()
                }
            }
        }
    }

    suspend fun getProductById(productId: Long): Product? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getProductById(productId) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }

    suspend fun getCartProducts(userId: Long): List<Product> {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.getCartProducts(userId) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                emptyList()
            } else {
                apiCall.body.map {
                    it.toDomain()
                }
            }
        }
    }

    suspend fun addProductToCart(idProduct: Long, idUser: Long, quantity: Int): CartResponse? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("product_id", idProduct)
                jsonObject.addProperty("user_id", idUser)
                jsonObject.addProperty("count_in_cart", quantity)
                api.addProductToCart(jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body
            }
        }
    }

    suspend fun deleteCartProduct(idUser: Long, idProduct: Long): Int? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall { api.deleteCartProduct(idUser, idProduct) }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.statusCode
            }
        }
    }

    suspend fun updateCart(idProduct: Long, idUser: Long, newCountInCart: Int): Product? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()
                jsonObject.addProperty("count_in_cart", newCountInCart)
                api.updateCart(idUser, idProduct, jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }

    suspend fun updateProduct(product: Product): Product? {
        return withContext(Dispatchers.IO) {
            val apiCall = safeApiCall.safeCall {
                val jsonObject = JsonObject()

                jsonObject.addProperty("restaurantId", product.restaurantId)
                jsonObject.addProperty("name", product.name)
                jsonObject.addProperty("imageUrl", product.imageUrl)
                jsonObject.addProperty("price", product.price)
                jsonObject.addProperty("description", product.description)

                val categoryArray = JsonArray()
                product.categoryId.forEach { categoryId ->
                    categoryArray.add(categoryId)
                }
                jsonObject.add("categoryId", categoryArray)

                jsonObject.addProperty("countInCart", product.countInCart)
                jsonObject.addProperty("openingHours", product.openingHours.toString())
                jsonObject.addProperty("closingHours", product.closingHours.toString())

                val openingDaysArray = JsonArray()
                product.openingDays.forEach { openingDay ->
                    openingDaysArray.add(openingDay.name)
                }
                jsonObject.add("openingDays", openingDaysArray)

                jsonObject.addProperty("productsQuantity", product.productsQuantity)
                jsonObject.addProperty("quantity", product.quantity)
                jsonObject.addProperty("quantityFreeze", product.quantityFreeze)
                jsonObject.addProperty("status", product.status)

                api.updateProduct(product.id, jsonObject)
            }
            return@withContext if (apiCall.failed || !apiCall.isSuccessful) {
                null
            } else {
                apiCall.body.toDomain()
            }
        }
    }

}
