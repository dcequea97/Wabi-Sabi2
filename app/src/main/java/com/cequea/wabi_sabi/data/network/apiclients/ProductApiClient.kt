package com.cequea.wabi_sabi.data.network.apiclients

import com.cequea.wabi_sabi.data.model.CartResponse
import com.cequea.wabi_sabi.data.model.ProductModel
import com.cequea.wabi_sabi.data.model.ProductsResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiClient {

    @GET("restaurants/{idRestaurant}/products")
    suspend fun getProductsByRestaurantId(@Path("idRestaurant") idRestaurant: Long): Response<ProductsResponse>

    @GET("products/{idProduct}")
    suspend fun getProductById(@Path("idProduct") idProduct: Long): Response<ProductModel>

    @POST("cart")
    suspend fun addProductToCart(@Body cart: JsonObject): Response<CartResponse>

    @GET("cart/user/{idUser}")
    suspend fun getCartProducts(@Path("idUser") idUser: Long): Response<ProductsResponse>

    @DELETE("cart/{idUser}/{idProduct}")
    suspend fun deleteCartProduct(
        @Path("idUser") idUser: Long,
        @Path("idProduct") idProduct: Long
    ): Response<Any>

    @PUT("cart/{idUser}/{idProduct}")
    suspend fun updateCart(
        @Path("idUser") idUser: Long,
        @Path("idProduct") idProduct: Long,
        @Body cart: JsonObject
    ): Response<ProductModel>

    @PUT("products/{idProduct}")
    suspend fun updateProduct(
        @Path("idProduct") idProduct: Long,
        @Body product: JsonObject
    ): Response<ProductModel>

}