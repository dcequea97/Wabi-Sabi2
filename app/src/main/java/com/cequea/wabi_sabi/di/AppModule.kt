package com.cequea.wabi_sabi.di

import com.cequea.wabi_sabi.data.network.apiclients.AddressApiClient
import com.cequea.wabi_sabi.data.network.apiclients.OrderApiClient
import com.cequea.wabi_sabi.data.network.apiclients.ProductApiClient
import com.cequea.wabi_sabi.data.network.apiclients.RegisterBusinessApiClient
import com.cequea.wabi_sabi.data.network.apiclients.RestaurantApiClient
import com.cequea.wabi_sabi.data.network.apiclients.UserApiClient
import com.cequea.wabi_sabi.util.Interceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val myIp = "192.168.250.6"
        return Retrofit.Builder()
            .baseUrl("http://$myIp:5000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRestaurantApiClient(retrofit: Retrofit): RestaurantApiClient {
        return retrofit.create(RestaurantApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient{
        return retrofit.create(UserApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProductApiClient(retrofit: Retrofit): ProductApiClient {
        return retrofit.create(ProductApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideAddressApiClient(retrofit: Retrofit): AddressApiClient {
        return retrofit.create(AddressApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderApiClient(retrofit: Retrofit): OrderApiClient {
        return retrofit.create(OrderApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRegisterBusinessApiClient(retrofit: Retrofit): RegisterBusinessApiClient {
        return retrofit.create(RegisterBusinessApiClient::class.java)
    }
}