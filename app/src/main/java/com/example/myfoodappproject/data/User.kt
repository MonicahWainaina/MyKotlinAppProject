package com.example.myfoodappproject.data



data class User(
    val name: String = "",
    val email: String = "",
    val cart: List<FoodItem> = emptyList(),
    val orders: Map<String, Order> = emptyMap()
)