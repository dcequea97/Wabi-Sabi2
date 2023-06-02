package com.cequea.wabi_sabi.di

import com.cequea.wabi_sabi.data.network.RestaurantApiClient
import com.cequea.wabi_sabi.data.repository.RestaurantRepository
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
        val myIp = "192.168.0.103"
        return Retrofit.Builder()
            .baseUrl("http://$myIp:8000/api/")
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
}