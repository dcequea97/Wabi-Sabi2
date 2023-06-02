package com.cequea.wabi_sabi.data.repository

import com.cequea.wabi_sabi.data.model.Product
import com.cequea.wabi_sabi.util.Resource
import javax.inject.Inject


class ProductRepository @Inject constructor() {
    suspend fun getProductsByRestaurant(restaurantId: Long): Resource<List<Product>>{
        return Resource.Success(
            products.filter {
                it.restaurantId == restaurantId
            }
        )
    }

    suspend fun getProductById(productId: Long): Resource<Product>{
        return Resource.Success(
            products.first {
                it.id == productId
            }
        )
    }

    private val products = listOf(
        Product(
            id = 1L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Cupcake",
            imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
            price = 10.59,
        ),
        Product(
            id = 2L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Donut",
            imageUrl = "https://source.unsplash.com/Yc5sL-ejk6U",
            price = 10.59,
        ),
        Product(
            id = 3L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Eclair",
            imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
            price = 10.59,
        ),
        Product
            (
            id = 4L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Froyo",
            imageUrl = "https://source.unsplash.com/3U2V5WqK1PQ",
            price = 10.59,
        ),
        Product(
            id = 5L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Gingerbread",
            imageUrl = "https://source.unsplash.com/Y4YR9OjdIMk",
            price = 18.09
        ),
        Product(
            id = 6L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Honeycomb",
            imageUrl = "https://source.unsplash.com/bELvIg_KZGU",
            price = 10.59,
        ),
        Product(
            id = 7L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Ice Cream Sandwich",
            imageUrl = "https://source.unsplash.com/YgYJsFDd4AU",
            price = 110.59,
        ),
        Product(
            id = 8L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Jellybean",
            imageUrl = "https://source.unsplash.com/0u_vbeOkMpk",
            price = 10.59,
        ),
        Product(
            id = 9L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "KitKat",
            imageUrl = "https://source.unsplash.com/yb16pT5F_jE",
            price = 13.07
        ),
        Product(
            id = 10L,
            restaurantId = 1L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Lollipop",
            imageUrl = "https://source.unsplash.com/AHF_ZktTL6Q",
            price = 10.59,
        ),
        Product(
            id = 11L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Marshmallow",
            imageUrl = "https://source.unsplash.com/rqFm0IgMVYY",
            price = 10.59,
        ),
        Product(
            id = 12L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Nougat",
            imageUrl = "https://source.unsplash.com/qRE_OpbVPR8",
            price = 10.59,
        ),
        Product(
            id = 13L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Oreo",
            imageUrl = "https://source.unsplash.com/33fWPnyN6tU",
            price = 10.59,
        ),
        Product(
            id = 14L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Pie",
            imageUrl = "https://source.unsplash.com/aX_ljOOyWJY",
            price = 10.59,
        ),
        Product(
            id = 15L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Chips",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/UsSdMZ78Q3E",
        ),
        Product(
            id = 16L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Pretzels",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/7meCnGCJ5Ms",
        ),
        Product(
            id = 17L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Smoothies",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/m741tj4Cz7M",
        ),
        Product(
            id = 18L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Popcorn",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/iuwMdNq0-s4",
        ),
        Product(
            id = 19L,
            restaurantId = 2L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Almonds",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/qgWWQU1SzqM",
        ),
        Product(
            id = 20L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Cheese",
            price = 10.59,
            imageUrl = "https://source.unsplash.com/9MzCd76xLGk",
        ),
        Product(
            id = 21L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Apples",
            imageUrl = "https://source.unsplash.com/1d9xXWMtQzQ",
            price = 10.59,
        ),
        Product(
            id = 22L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Apple sauce",
            imageUrl = "https://source.unsplash.com/wZxpOw84QTU",
            price = 10.59,
        ),
        Product(
            id = 23L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Apple chips",
            imageUrl = "https://source.unsplash.com/okzeRxm_GPo",
            price = 10.59,
        ),
        Product(
            id = 24L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Apple juice",
            imageUrl = "https://source.unsplash.com/l7imGdupuhU",
            price = 10.59,
        ),
        Product(
            id = 25L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Apple pie",
            imageUrl = "https://source.unsplash.com/bkXzABDt08Q",
            price = 10.59,
        ),
        Product(
            id = 26L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Grapes",
            imageUrl = "https://source.unsplash.com/y2MeW00BdBo",
            price = 10.59,
        ),
        Product(
            id = 27L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Kiwi",
            imageUrl = "https://source.unsplash.com/1oMGgHn-M8k",
            price = 10.59,
        ),
        Product(
            id = 28L,
            restaurantId = 3L,
            description = "My Product Description",
            categoryId = listOf(1,2,4,9),
            name = "Mango",
            imageUrl = "https://source.unsplash.com/TIGDsyy0TK4",
            price = 10.59,
        )
    )
}


