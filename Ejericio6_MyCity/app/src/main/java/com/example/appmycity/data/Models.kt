package com.example.cityguide.data

data class Place(
    val id: String,
    val name: String,
    val description: String,
    val address: String
)

data class Category(
    val id: String,
    val title: String,
    val places: List<Place>
)

object DemoRepository {
    // Contenido de ejemplo (cámbialo por tu ciudad/país)
    val categories: List<Category> = listOf(
        Category(
            id = "cafeterias",
            title = "Cafeterías",
            places = listOf(
                Place("c1","Café Roma","Espresso y pastelería artesanal","Calle 12 #123"),
                Place("c2","Latte Lab","Café de especialidad y brunch","Av. Central 45"),
                Place("c3","Moka Moka","Ambiente pet-friendly y terraza","Parque Norte 233")
            )
        ),
        Category(
            id = "restaurantes",
            title = "Restaurantes",
            places = listOf(
                Place("r1","La Parrilla","Carnes y asados","Boulevard 300"),
                Place("r2","Nikkei 12","Fusión peruano-japonesa","Pasaje 7 #22"),
                Place("r3","La Huerta","Vegetariano y vegano","Calle Verde 88")
            )
        ),
        Category(
            id = "parques",
            title = "Parques",
            places = listOf(
                Place("p1","Parque Central","Lagos, bicis y picnic","Centro"),
                Place("p2","Parque Botánico","Senderos y mariposario","Zona Sur"),
                Place("p3","Eco Parque","Canopy y miradores","Km 8 vía Oeste")
            )
        ),
        Category(
            id = "shopping",
            title = "Centros comerciales",
            places = listOf(
                Place("s1","Mall Río","Cines y patio de comidas","Río 100"),
                Place("s2","Galería Norte","Marcas locales","Norte 55")
            )
        )
    )

    fun getCategory(id: String) = categories.first { it.id == id }
    fun getPlace(categoryId: String, placeId: String) =
        getCategory(categoryId).places.first { it.id == placeId }
}
