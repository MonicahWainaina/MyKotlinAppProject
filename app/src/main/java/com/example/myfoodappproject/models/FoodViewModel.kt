package com.example.myfoodappproject.models

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myfoodappproject.data.FoodItem
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class FoodsViewModel : ViewModel() {
    private val database = Firebase.database("https://foodorderapp-39742-default-rtdb.firebaseio.com/")

    private var _fooditems = mutableStateOf<List<FoodItem>>(emptyList())
    val foods: State<List<FoodItem>> = _fooditems


    init {
        getFoods()
    }

    private fun getFoods() {
        val foodItemsRef = database.getReference("FoodItems")
        foodItemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<FoodItem>()
                for (foodSnapshot in snapshot.children) {
                    val foodItem = foodSnapshot.getValue(FoodItem::class.java)
                    foodItem?.let {
                        items.add(it)
                        Log.d("FoodItems", "Retrieved FoodItem: $it") // Log each retrieved FoodItem
                    }
                }
                _fooditems.value = items

                if (items.isEmpty()) {
                    Log.d("FoodItems", "No food items retrieved") // Log if the list is empty
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error fetching food items: ${error.message}")
            }
        })
    }
    // Add a function to fetch a FoodItem by its customId
    fun getFoodItemById(customId: String): FoodItem? {
        return foods.value.find { it.customId == customId }
    }

    fun getFoodItemsByCategory(category: String): List<FoodItem> {
        return foods.value.filter { it.category == category }
    }
}