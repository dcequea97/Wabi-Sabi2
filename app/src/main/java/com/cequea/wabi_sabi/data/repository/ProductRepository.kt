package com.cequea.wabi_sabi.data.repository

import com.cequea.wabi_sabi.data.model.Product
import com.cequea.wabi_sabi.util.Resource
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject


class ProductRepository @Inject constructor() {
    suspend fun getProductsByRestaurant(restaurantId: Long): Resource<List<Product>> {
        return Resource.Success(
            products.filter {
                it.restaurantId == restaurantId
            }
        )
    }

    suspend fun getProductById(productId: Long): Resource<Product> {
        return Resource.Success(
            products.first {
                it.id == productId
            }
        )
    }

    suspend fun addProductToCart(product: Product): Resource<Product>{
        products.first{
            product.id == it.id
        }.countInCart = product.countInCart
        return Resource.Success(
            products.first{
                product.id == it.id
            }
        )
    }

    suspend fun getCartProducts():Resource<List<Product>>{
        return Resource.Success(
            products.filter {
                it.countInCart > 0
            }
        )
    }

    val breakfastFoods = listOf("huevos revueltos", "panqueques", "tostadas", "café", "jugo de naranja")
    val lunchFoods = listOf("ensalada césar", "sopa de pollo", "pollo a la parrilla", "arroz con frijoles", "refresco de cola")
    val dinnerFoods = listOf("salmón a la parrilla", "ensalada mixta", "puré de papas", "vino tinto", "pastel de manzana")

    val breakfastDescription = "El pack puede contener empanadas, cachitos, pan, o arepas, junto con: ${breakfastFoods.joinToString(separator = ", ")}"
    val lunchDescription = "El pack puede contener empanadas, cachitos, pan, o arepas, junto con: ${lunchFoods.joinToString(separator = ", ")}"
    val dinnerDescription = "El pack puede contener empanadas, cachitos, pan, o arepas, junto con: ${dinnerFoods.joinToString(separator = ", ")}"

    var pack1 = Product(
        id = 1L,
        restaurantId = 1L,
        name = "Pack de Desayuno",
        imageUrl = "https://www.ardene.com/on/demandware.static/-/Sites-master-catalog/default/dwc5589430/images/large/7B-AC00836-a5a6014e-6289-4b9a-b506-c74523bfbe66.jpg",
        price = 10.0,
        description = breakfastDescription,
        categoryId = listOf(2L),
        countInCart = 0,
        openingHours = LocalTime.of(6, 0),
        closingHours = LocalTime.of(11, 0),
        openingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    )

    var pack2 = Product(
        id = 2L,
        restaurantId = 1L,
        name = "Pack de Almuerzo",
        imageUrl = "https://cdn.shopify.com/s/files/1/0548/9429/7273/products/1973_500x.jpg?v=1645151746",
        price = 12.0,
        description = lunchDescription,
        categoryId = listOf(2L),
        countInCart = 3,
        openingHours = LocalTime.of(12, 0),
        closingHours = LocalTime.of(16, 0),
        openingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)
    )

    var pack3 = Product(
        id = 3L,
        restaurantId = 1L,
        name = "Pack de Cena",
        imageUrl = "https://cottagecountrycandies.com/wp-content/uploads/2018/02/2-Surprise-Bag.jpg",
        price = 15.0,
        description = dinnerDescription,
        categoryId = listOf(2L),
        countInCart = 5,
        openingHours = LocalTime.of(18, 0),
        closingHours = LocalTime.of(23, 0),
        openingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
    )

    val products = listOf(pack1, pack2, pack3)


}


