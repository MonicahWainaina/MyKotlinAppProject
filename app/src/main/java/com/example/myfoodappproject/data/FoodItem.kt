package com.example.myfoodappproject.data

 data class FoodItem(
    val customId: String,
    val category: String,
    val description: String,
    val image: String,
    val name: String,
    var price: Int,
    var quantity: Int = 1,
    var totalPrice: Int = 0
) {
    // Secondary constructor with no-argument initialization
    constructor() : this("", "", "", "", "", 0)
}