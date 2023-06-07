package com.cequea.wabi_sabi.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import com.cequea.wabi_sabi.data.model.CollectionType
import com.cequea.wabi_sabi.data.model.Filter
import com.cequea.wabi_sabi.data.model.Restaurant
import com.cequea.wabi_sabi.data.model.RestaurantsCollection
import com.cequea.wabi_sabi.data.network.RestaurantApiClient
import com.cequea.wabi_sabi.util.Resource
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
) {
    val rinconcito = Restaurant(
        id = 1L,
        name = "La Panaderia de Sabana Grande",
        backgroundImageUrl = "https://proingra.com/wp-content/uploads/2022/07/04-AGO-BRAHMAN-I.jpg",
        profileImageUrl = "https://thumbs.dreamstime.com/z/panader%C3%ADa-logo-dulce-simple-vector-plantilla-216470510.jpg",
        tagline = "El sabor de casa en cada pan",
        workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
        rating = 4.7,
        openingHours = LocalTime.of(6, 30),
        closingHours = LocalTime.of(20, 30)
    )

    val muralla = Restaurant(
        id = 2L,
        name = "Panaderia San Francisco",
        backgroundImageUrl = "https://img.freepik.com/vector-gratis/fondo-panaderia-casera_52683-7036.jpg",
        profileImageUrl = "https://static.vecteezy.com/system/resources/previews/004/698/199/non_2x/chef-woman-bakery-logo-food-and-restaurant-hand-drawn-cartoon-art-illustration-vector.jpg",
        tagline = "Pan fresco todos los días",
        workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
        rating = 4.2,
        openingHours = LocalTime.of(7, 0),
        closingHours = LocalTime.of(19, 0)
    )

    val another = Restaurant(
        id = 3L,
        name = "Panaderia Cafe con Pan",
        backgroundImageUrl = "https://previews.123rf.com/images/teploleta/teploleta1507/teploleta150700283/42280479-mano-panader%C3%ADa-dibujado-patr%C3%B3n-de-fondo-sin-fisuras.jpg",
        profileImageUrl = "https://www.facebook.com/395206647514549/photos/a.395303227504891/696062567428954/?type=3&eid=ARAhp4sjgF2T6BueIEK4-6i8pdt6zn4PcB5LSeK_clzNKqoa1bhzjAGtVdvuTeHp4u06IFe1JR-SB_qE&__xts__%5B0%5D=68.ARClc3JY3ZUtm0TQnDois-djDp9VPPnyB3PGsMOed1oAu631tRKPi2o6Ww7gwh5D2y0xYgDM-xuu-FWd1gnAmCbW7_3p-fr5v4Hm7oaGcTcqsXJoWp9EZDRUfB7AwyC6ZrrzWl0tfEi-ZR30acY9K7A15OvFW79IhQItZ-yVD0CqSKcETtLBnC1g1F0T3iVYQcqECOH4DwR9blC_SJ9gfTH8tY9JBwzhN2LHEsxhSClsM5ZqeIiCyYZpZ1T3JkaUYN6IB6nNrnxRZFt9sH1YrOsdQb8ULThm-Jw8ex5UDphLVZ-ikdk&__tn__=EHH-R",
        workingDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
        rating = 4.0,
        openingHours = LocalTime.of(5, 0),
        closingHours = LocalTime.of(21, 0)
    )


    suspend fun getRestaurants(): Resource<List<RestaurantsCollection>> {
        val response = try {
            listOf(
                RestaurantsCollection(
                    listOf(
                        rinconcito
                    ), "Destacados", CollectionType.Highlight
                ),
                RestaurantsCollection(
                    listOf(
                        rinconcito,
                        muralla,
                        another
                    ), "Descubre"
                )
            )
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getRestaurantById(idRestaurant: Long): Resource<Restaurant> {
        return Resource.Success(listOf(
            rinconcito,
            muralla,
            another
        ).first { restaurant ->
            restaurant.id == idRestaurant
        })

    }

    suspend fun getFilters(): List<Filter> {
        return listOf(
            Filter(name = "Organic"),
            Filter(name = "Gluten-free"),
            Filter(name = "Dairy-free"),
            Filter(name = "Sweet"),
            Filter(name = "Savory")
        )
    }

    suspend fun getSortFilters(): List<Filter> {
        return listOf(
            Filter(name = "People Favorite", icon = Icons.Filled.People),
            Filter(name = "Rating", icon = Icons.Filled.Star),
            Filter(name = "Alphabetical", icon = Icons.Filled.SortByAlpha)
        )
    }

    suspend fun getPriceFilters(): List<Filter> {
        return listOf(
            Filter(name = "$"),
            Filter(name = "$$"),
            Filter(name = "$$$"),
            Filter(name = "$$$$")
        )
    }

    suspend fun getCategoryFilters(): List<Filter> {
        return listOf(
            Filter(name = "Chips & crackers"),
            Filter(name = "Fruit snacks"),
            Filter(name = "Desserts"),
            Filter(name = "Nuts")
        )
    }

    suspend fun getLifeStyleFilters(): List<Filter> {
        return listOf(
            Filter(name = "Organic"),
            Filter(name = "Gluten-free"),
            Filter(name = "Dairy-free"),
            Filter(name = "Sweet"),
            Filter(name = "Savory")
        )
    }
}