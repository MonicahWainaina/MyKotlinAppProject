@file:OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)

package com.example.myfoodappproject.ui.theme.screens.home

import android.annotation.SuppressLint
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.R
import com.example.myfoodappproject.data.FoodItem
import com.example.myfoodappproject.models.FoodsViewModel
import com.example.myfoodappproject.models.UserDataViewModel
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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
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

    var quantity by remember { mutableStateOf(1) }

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

    var searchText by remember { mutableStateOf("") }

    // Function to filter food items based on search text

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
                            value = searchText,
                            onValueChange = { searchText = it },
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
                            addToCart = { foodItem, quantity -> // Update addToCart to accept quantity parameter
                                userDataViewModel.addToCart(userDataViewModel.getUserId()!!, foodItem, quantity, context)
                            },
                            getUserId = { userDataViewModel.getUserId()!! },
                            searchText = searchText // Pass filtered items to FoodList
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












@Composable
fun FoodList(
    viewModel: FoodsViewModel = viewModel(),
    navController: NavHostController,
    addToCart: (FoodItem, Int) -> Unit, // Modify addToCart parameter
    getUserId: () -> String, // Add getUserId function parameter
    searchText: String // Receive the search text here
) {
    val foodItems = viewModel.foods.value.filter {
        it.name.contains(searchText, ignoreCase = true) // Filter food items based on search text
    }

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
            items(foodItems) { foodItem ->

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
                                val quantity = 1 // For example, set a default quantity or get it from somewhere
                                userId?.let {
                                    addToCart(foodItem, quantity) // Add selected item to the user's cart with quantity
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




