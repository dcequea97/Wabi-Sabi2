package com.cequea.wabi_sabi.data.repository

import android.content.Context
import com.cequea.wabi_sabi.R
import com.cequea.wabi_sabi.core.isNull
import com.cequea.wabi_sabi.core.isNullOrEmpty
import com.cequea.wabi_sabi.data.model.CartResponse
import com.cequea.wabi_sabi.data.network.services.ProductService
import com.cequea.wabi_sabi.data.repository.datastore.DataStoreRepository
import com.cequea.wabi_sabi.ui.model.Product
import com.cequea.wabi_sabi.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val api: ProductService,
    @ApplicationContext private val context: Context
) {
    suspend fun getProductsByRestaurant(restaurantId: Long): Resource<List<Product>> {
        val response = api.getAllProductsByRestaurant(restaurantId)
        if (response.isNullOrEmpty()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response
        )
    }

    suspend fun getProductById(productId: Long): Resource<Product> {
        val response = api.getProductById(productId)
        if (response.isNull()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )
    }

    suspend fun addProductToCart(idProduct: Long, idUser: Long, quantity: Int): Resource<CartResponse>{
            val response = api.addProductToCart(idProduct, idUser, quantity)
            if (response.isNull()){
                return Resource.Error(
                    message = context.getString(R.string.universal_error)
                )
            }
            return Resource.Success(
                response!!
            )
    }

    suspend fun getCartProducts(idUser: Long):Resource<List<Product>>{
        val response = api.getCartProducts(idUser)
        if (response.isNullOrEmpty()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response
        )
    }

    suspend fun deleteCartProduct(idUser: Long, idProduct: Long):Resource<Int>{
        val response = api.deleteCartProduct(idUser,idProduct)
        if (response.isNullOrEmpty()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }else if(response != 204){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response
        )
    }

    suspend fun updateCart(idProduct: Long, idUser: Long, newCountInCart: Int): Resource<Product>{
        val response = api.updateCart(idProduct, idUser, newCountInCart)
        if (response.isNull()){
            return Resource.Error(
                message = context.getString(R.string.universal_error)
            )
        }
        return Resource.Success(
            response!!
        )
    }

}


