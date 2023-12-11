@file:OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)

package com.example.myfoodappproject.ui.theme.screens.home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.R
import com.example.myfoodappproject.navigation.ROUTE_CART
import com.example.myfoodappproject.navigation.ROUTE_CATEGORIES
import com.example.myfoodappproject.navigation.ROUTE_HOME
import com.example.myfoodappproject.navigation.ROUTE_ORDERS
import com.example.myfoodappproject.navigation.ROUTE_WELCOME
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen(navController: NavHostController,userDataViewModel: UserDataViewModel = viewModel()){


    val cartItems = userDataViewModel.cartItemCount.value


    val context = LocalContext.current
    // Function to navigate to the cart screen


    // Function to increment the number of items in the cart and navigate to the cart screen

    val viewModel: FoodsViewModel = viewModel()


    var userName by remember { mutableStateOf("User") }

    // Fetch the user data
    val userUid = FirebaseAuth.getInstance().currentUser?.uid
    userUid?.let {
        userDataViewModel.fetchUserData(it) { user ->
            user?.let {
                userName = user.name
            }
        }
    }


    val items = listOf(
        NavigationItem(
            title = "Home",
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
            route = ROUTE_HOME
        ),
        NavigationItem(
            title = "Categories",
            unselectedIcon = Icons.Outlined.List,
            selectedIcon = Icons.Filled.List,
            route = ROUTE_CATEGORIES
        ),
        NavigationItem(
            title = "My Cart",
            unselectedIcon = Icons.Outlined.ShoppingCart,
            selectedIcon = Icons.Filled.ShoppingCart,
            route = ROUTE_CART
        ),
        NavigationItem(
            title = "My Orders",
            unselectedIcon = Icons.Outlined.AccountCircle,
            selectedIcon = Icons.Filled.AccountCircle,
            route = ROUTE_ORDERS
        ),
    )
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet{
                    Column(
                        modifier = Modifier
                            .background(Color.Magenta) // Set background color to magenta
                            .padding(vertical = 40.dp, horizontal = 30.dp)
                            .fillMaxWidth()// Larger padding
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Icon representing a person
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person",
                                tint = Color.White // Set icon color to white
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Text saying "Welcome user"
                            Text(
                                text = "Welcome $userName",
                                color = Color.White, // Set text color to white
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    items.forEachIndexed { index,item  ->
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                navController.navigate(item.route)
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if(index == selectedItemIndex){
                                        item.selectedIcon
                                    }else item.unselectedIcon,
                                    contentDescription =item.title
                                )
                            },
                            badge = {
                                item.badgeCount?.let {
                                    Text(text = item.badgeCount.toString())
                                }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }

                    Spacer( modifier = Modifier.weight(1f))

                    // Logout button at the bottom
                    TextButton(
                        onClick = { navController.navigate(ROUTE_WELCOME) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.textButtonColors( Color.Magenta) // Set button background color to magenta
                    ) {
                        Text(text = "Logout", color = Color.White) // Set text color to white
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Kikwetu Dishes", color = Color.White)
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Magenta ),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(7.dp)
                            .padding(it)
                    ) {
                        Text(
                            text = "Hello $userName",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 4.dp),
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.ExtraBold
                        )

                        var text by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = {
                                Text("Search and Place Your Order")
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color.Magenta
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 1.dp, vertical = 12.dp)
                                .shadow(12.dp, shape = RoundedCornerShape(12.dp)),
                            textStyle = TextStyle(color = Color.Black),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Magenta,
                                unfocusedBorderColor = Color.Magenta,
                                cursorColor = Color.Black,
                                textColor = Color.Black,
                                containerColor = Color.White
                            )
                        )
                        FoodList(
                            viewModel = viewModel,
                            navController = navController,
                            addToCart = { foodItem -> userDataViewModel.addToCart(userDataViewModel.getUserId()!!, foodItem, context)},
                            getUserId = { userDataViewModel.getUserId()!! }
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(ROUTE_CART)},
                        containerColor = Color.Magenta
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                            Text(
                                text = cartItems.toString(),
                                color = Color.White,
                                style = TextStyle(fontSize = 14.sp)
                            )
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.End

            )
        }
    }
}
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}


data class NavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val badgeCount: Int? = null,
    var route: String
)

data class FoodItem(
    val customId: String,
    val category: String,
    val description: String,
    val image: String,
    val name: String,
    val price: Int,
    val quantity: Int = 1 // Default quantity to 1
) {
    // Secondary constructor with no-argument initialization
    constructor() : this("", "", "", "", "", 0)
}


data class User(
    val name: String = "",
    val email: String = "",
    val cart: List<FoodItem> = emptyList()
)


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
                Log.e(TAG, "Error fetching food items: ${error.message}")
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
class UserDataViewModel : ViewModel() {
    private val database = Firebase.database // Replace with your Firebase database reference

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
    fun addToCart(userId: String, foodItem: FoodItem,context:Context) {
        val userRef = database.getReference("Users").child(userId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(User::class.java)
                userData?.let {
                    val updatedCart = it.cart.toMutableList()
                    val alreadyExists = updatedCart.any { item -> item.customId == foodItem.customId }

                    if (alreadyExists) {
                        // Display a toast message indicating that the item is already in the cart
                        // Replace 'context' with your actual context reference
                        Toast.makeText(context, "Item already in cart", Toast.LENGTH_SHORT).show()
                    } else {
                        updatedCart.add(foodItem)
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

                Log.e(TAG, "Error fetching food items: ${error.message}")
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


}


@Composable
fun FoodList(
    viewModel: FoodsViewModel = viewModel(),
    navController: NavHostController,
    addToCart: (FoodItem) -> Unit, // Modify addToCart parameter
    getUserId: () -> String // Add getUserId function parameter
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        item {
            Column (
                modifier = Modifier.padding(horizontal = 5.dp)
            ){

                val pagerState = rememberPagerState(
                    initialPage = 0,
                )
                val imageSlider = listOf(
                    painterResource(id = R.drawable.kenyanfood),
                    painterResource(id = R.drawable.nyamachoma),
                    painterResource(id = R.drawable.popularkenyan),
                    painterResource(id = R.drawable.nyamachomaplatter),
                    painterResource(id = R.drawable.pancakes),
                    painterResource(id = R.drawable.samosas),
                    painterResource(id = R.drawable.breakfast)
                )

                LaunchedEffect(Unit) {
                    while (true) {
                        yield()
                        delay(2600)
                        pagerState.animateScrollToPage(
                            page = (pagerState.currentPage + 1) % pagerState.pageCount
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp)
                        .padding(5.dp)
                ) {
                    HorizontalPager(
                        count = imageSlider.size,
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 1.dp),
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    ) { page ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .graphicsLayer {
                                    val pageOffset =
                                        calculateCurrentOffsetForPage(page).absoluteValue

                                    scaleX = lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                    scaleY = lerp(
                                        start = 0.85f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                    alpha = lerp(
                                        start = 0.5f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                }
                        ) {
                            Box (

                            ){

                                Image(
                                    painter = imageSlider[page],
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                // Label at top-left
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(
                                            color = Color.Magenta,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Featured",
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }

                        }
                    }

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(5.dp)
                    )
                    Text(
                        text = "What would you like to have today?",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        if (viewModel.foods.value.isEmpty()) {
            item {
                // Display a message or placeholder content for empty data
                Text(
                    text = "No food items available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        } else {
            items(viewModel.foods.value) { foodItem ->

                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                        .shadow(
                            12.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = Color.Magenta,
                            spotColor = Color.Magenta
                        )
                        .clickable {
                            navController.navigate("food_details/${foodItem.customId}")
                        },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    // Load the image using Coil from the provided URL
                    Box {
                        Image(
                            painter = rememberImagePainter(data = foodItem.image),
                            contentDescription = "Food Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = Color.Magenta,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = foodItem.category,
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }


                    // Rest of your card content using the foodItem details
                    // ...

                    // White section containing name, price, and icon
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = foodItem.name,
                                fontSize = 18.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Kshs: ${foodItem.price}", // Display price here
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(
                            onClick = {
                                val userId = getUserId() // Retrieve the user ID
                                userId?.let {
                                    addToCart(foodItem) // Add selected item to the user's cart
                                }},
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Add to Cart",
                                tint = Color.Magenta
                            )
                        }
                    }
                }
            }
        }
    }

}




