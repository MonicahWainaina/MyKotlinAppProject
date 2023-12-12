package com.example.myfoodappproject.data

data class Order(
    val orderNumber: String = "",
    val items: List<FoodItem> = emptyList(),
    val totalPrice: Int = 0,
    val status: String = ""
)