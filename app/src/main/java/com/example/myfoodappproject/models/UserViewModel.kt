package com.example.myfoodappproject.models

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myfoodappproject.data.FoodItem
import com.example.myfoodappproject.data.Order
import com.example.myfoodappproject.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserDataViewModel : ViewModel() {
    private val database = Firebase.database("https://foodorderapp-39742-default-rtdb.firebaseio.com/")

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    // Assuming you're using Firebase Auth
    val firebaseAuth = FirebaseAuth.getInstance()

    private val _cartItemCount = mutableStateOf(0)
    val cartItemCount: State<Int> = _cartItemCount

    // Example function to retrieve user ID after authentication
    fun getUserId(): String? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.uid // This retrieves the user ID (UID) if the user is logged in
    }
    fun setUser(userData: User) {
        _user.value = userData
    }


// Update ViewModel operations with the user ID


    fun fetchUserData(uid: String, callback: (User?) -> Unit) {
        val userRef = database.getReference("Users").child(uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(User::class.java)
                callback(userData) // Pass the retrieved user data to the callback
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error scenario if needed
                callback(null) // Pass null to indicate an error
            }
        })
    }

    private val _cartUpdated = MutableStateFlow(Unit)
    val cartUpdated: StateFlow<Unit> = _cartUpdated
    fun addToCart(userId: String, foodItem: FoodItem, quantity: Int, context: Context) {
        val userRef = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(User::class.java)
                userData?.let {
                    val updatedCart = it.cart.toMutableList()
                    val alreadyExists = updatedCart.any { item -> item.customId == foodItem.customId }

                    if (alreadyExists) {
                        // Display a toast message indicating that the item is already in the cart
                        Toast.makeText(context, "Item already in cart", Toast.LENGTH_SHORT).show()
                    } else {
                        // If item is not in the cart, add it
                        val newItem = foodItem.copy(quantity = quantity, totalPrice = foodItem.price * quantity)
                        updatedCart.add(newItem)
                        Toast.makeText(context, "${foodItem.name} added to cart", Toast.LENGTH_SHORT).show()

                        userRef.child("cart").setValue(updatedCart)
                        _cartItemCount.value = updatedCart.size // Update the cart count
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error scenario if needed
            }
        })
    }

    // Inside UserDataViewModel
    private val _cartItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val cartItems: StateFlow<List<FoodItem>> = _cartItems

    // Function to fetch cart items and update StateFlow
    fun fetchCartItems(userId: String) {
        val userCartRef = database.getReference("Users").child(userId).child("cart")
        userCartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<FoodItem>()
                for (foodSnapshot in snapshot.children) {
                    val foodItem = foodSnapshot.getValue(FoodItem::class.java)
                    foodItem?.let {
                        items.add(it)
                        Log.d("ViewModel", "Fetching cart items for user: $it")
                    }
                }
                _cartItems.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed

                Log.e(ContentValues.TAG, "Error fetching food items: ${error.message}")
            }

        })
    }
    // Assuming you've retrieved the logged-in user ID
    fun updateCartItemQuantity(userId: String, foodItemId: String, newQuantity: Int) {
        val userCartRef = database.getReference("Users").child(userId).child("cart")
        userCartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartItems = snapshot.getValue(object : GenericTypeIndicator<List<FoodItem>>() {})
                val updatedCartItems = cartItems?.map {
                    if (it.customId == foodItemId) {
                        it.copy(quantity = newQuantity)
                    } else {
                        it
                    }
                }
                updatedCartItems?.let {
                    userCartRef.setValue(updatedCartItems)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    fun deleteCartItem(userId: String, foodItemId: String) {
        val userCartRef = database.getReference("Users").child(userId).child("cart")
        userCartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartItems = snapshot.getValue(object : GenericTypeIndicator<List<FoodItem>>() {})
                val updatedCartItems = cartItems?.filter { it.customId != foodItemId }
                updatedCartItems?.let {
                    userCartRef.setValue(updatedCartItems)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    // Inside UserDataViewModel
    fun placeOrder(userId: String, context: Context) {
        val userCartRef = database.getReference("Users").child(userId).child("cart")
        val userOrdersRef = database.getReference("Users").child(userId).child("orders")

        userCartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartItems = snapshot.getValue(object : GenericTypeIndicator<List<FoodItem>>() {})
                cartItems?.let {
                    val totalPrice = calculateTotalPrice(it)
                    val orderDetails = hashMapOf(
                        "items" to it,
                        "totalPrice" to totalPrice
                    )

                    userOrdersRef.push().setValue(orderDetails) // Moves cart items to orders
                    userCartRef.removeValue() // Clears the cart after placing the order

                    // Display a toast message indicating successful payment
                    Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })

    }

    private fun calculateTotalPrice(cartItems: List<FoodItem>): Int {
        return cartItems.map { it.price * it.quantity }.sum()
    }

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    // Fetch orders method populating _orders variable
    // Inside the fetchOrders function in your UserDataViewModel
    fun fetchOrders(userId: String) {
        val userOrdersRef = database.getReference("Users").child(userId).child("orders")
        userOrdersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ordersList = mutableListOf<Order>()

                for (orderSnapshot in snapshot.children) {
                    val orderNumber = orderSnapshot.key.toString()
                    val totalPrice = orderSnapshot.child("totalPrice").getValue(Int::class.java) ?: 0
                    val status = orderSnapshot.child("status").getValue(String::class.java) ?: ""

                    val items = mutableListOf<FoodItem>()

                    for (itemSnapshot in orderSnapshot.child("items").children) {
                        val customId = itemSnapshot.child("customId").getValue(String::class.java) ?: ""
                        val category = itemSnapshot.child("category").getValue(String::class.java) ?: ""
                        val description = itemSnapshot.child("description").getValue(String::class.java) ?: ""
                        val image = itemSnapshot.child("image").getValue(String::class.java) ?: ""
                        val name = itemSnapshot.child("name").getValue(String::class.java) ?: ""
                        val price = itemSnapshot.child("price").getValue(Int::class.java) ?: 0
                        val quantity = itemSnapshot.child("quantity").getValue(Int::class.java) ?: 1

                        val foodItem = FoodItem(customId, category, description, image, name, price, quantity)
                        items.add(foodItem)
                    }

                    val order = Order(orderNumber, items, totalPrice, status)
                    ordersList.add(order)
                }

                _orders.value = ordersList
                Log.d("ViewModel", "Orders: $ordersList")
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error scenario
            }
        })
    }


}