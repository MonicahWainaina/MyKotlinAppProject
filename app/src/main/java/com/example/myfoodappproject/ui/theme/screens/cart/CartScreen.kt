package com.example.myfoodappproject.ui.theme.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.navigation.ROUTE_CART
import com.example.myfoodappproject.navigation.ROUTE_CATEGORIES
import com.example.myfoodappproject.navigation.ROUTE_HOME
import com.example.myfoodappproject.navigation.ROUTE_ORDERS
import com.example.myfoodappproject.navigation.ROUTE_WELCOME
import com.example.myfoodappproject.ui.theme.MyFoodAppProjectTheme
import com.example.myfoodappproject.ui.theme.screens.home.FoodItem
import com.example.myfoodappproject.ui.theme.screens.home.NavigationItem
import com.example.myfoodappproject.ui.theme.screens.home.UserDataViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController){
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
        var quantity by remember { mutableStateOf(1) }
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
                                text = "Welcome user",
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
                        onClick = { navController.navigate(ROUTE_WELCOME)},
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
                            Text(text = "My Cart", color = Color.White)
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
            Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        CartScreen()
                    }
                }
            )
        }
    }
}
data class NavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val badgeCount: Int? = null,
    var route: String
)
@Composable
fun CartScreen(
    userDataViewModel: UserDataViewModel = viewModel()
) {
    val userId = userDataViewModel.getUserId() ?: ""
    // Fetch cart items initially and whenever the user ID changes
    LaunchedEffect(key1 = userDataViewModel) {
        val userId = userDataViewModel.getUserId() ?: return@LaunchedEffect
        userDataViewModel.fetchCartItems(userId)
    }

    // Observe changes in cart items
    val cartItemsState by userDataViewModel.cartItems.collectAsState()

    // Calculate total price
    val totalPrice = remember(cartItemsState) {
        cartItemsState.map { it.price * it.quantity }.sum()
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(cartItemsState) { item ->
            CartItemCard(
                cartItem = item,
                onQuantityChange = { newQuantity ->
                    // Update the quantity of the item in the cart
                    userDataViewModel.updateCartItemQuantity(userId, item.customId, newQuantity)
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            // Total price section
            // Your existing Total Price and Checkout button UI here
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Price:",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    // Replace totalPrice with your actual calculated total price
                    Text(
                        text = "Kshs $totalPrice",// Assuming totalPrice is a String or a formatted value
                        fontWeight = FontWeight.Bold,
                        color = Color.Magenta
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Checkout button
                Button(
                    onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.Magenta)
                        ) {
                            Text(
                                text = "Checkout",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
            }
        }
    }
}

@Composable
fun CartItemCard(cartItem: FoodItem, onQuantityChange: (Int) -> Unit) {
    var quantity by remember { mutableStateOf(cartItem.quantity) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Item image on the far left
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                // Replace with your actual item image or icon
                Image(
                    painter = rememberImagePainter(data = cartItem.image),
                    contentDescription = "Item Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = cartItem.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1, // Restrict to a single line
                overflow = TextOverflow.Ellipsis, // Use ellipsis for overflow
            )


            // Item name, price, quantity, and remove button
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End

            ) {

                Text(
                    text = "Kshs: ${cartItem.price * cartItem.quantity}", // Display price here
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Quantity indicators and buttons
                // ... (Your existing quantity UI here)
                Row(
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                        .background(
                            Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    // Remove icon and functionality
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                Color.Magenta,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                if (quantity > 1) {
                                    quantity -= 1
                                    onQuantityChange(quantity) // Update the quantity using onQuantityChange
                                }
                            }
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Remove",
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.White
                        )
                    }

                    // Quantity indicator
                    Text(
                        text = "$quantity",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .align(alignment = Alignment.CenterVertically),
                    )

                    // Add icon and functionality
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                Color.Magenta,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                quantity += 1
                                onQuantityChange(quantity) // Update the quantity using onQuantityChange
                            }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier.align(Alignment.Center),
                            tint = Color.White
                        )
                    }
                }
                // Remove item button
                TextButton(
                    onClick = { /* Remove item logic */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent)
                ) {
                    Text(text = "Delete", color = Color.Magenta, fontSize = 17.sp)
                }
            }
        }
    }
}




@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    MyFoodAppProjectTheme {
        CartScreen(rememberNavController())
    }
}