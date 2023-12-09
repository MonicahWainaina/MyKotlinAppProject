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
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfoodappproject.R
import com.example.myfoodappproject.navigation.ROUTE_CART
import com.example.myfoodappproject.navigation.ROUTE_CATEGORIES
import com.example.myfoodappproject.navigation.ROUTE_HOME
import com.example.myfoodappproject.navigation.ROUTE_ORDERS
import com.example.myfoodappproject.navigation.ROUTE_WELCOME
import com.example.myfoodappproject.ui.theme.MyFoodAppProjectTheme
import com.example.myfoodappproject.ui.theme.screens.home.NavigationItem
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
                    /*Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .height(620.dp)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(370.dp)
                                    .padding(vertical = 9.dp)
                                    .padding(it)
                                    .shadow(
                                        12.dp,
                                        shape = RoundedCornerShape(12.dp),
                                        ambientColor = Color.Magenta,
                                        spotColor = Color.Magenta
                                    ),
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(Color.White)
                            ) {
                                Row(
                                    modifier = Modifier.width(370.dp),
                                    verticalAlignment = Alignment.CenterVertically
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
                                            painter = painterResource(id = R.drawable.samosas),
                                            contentDescription = "Item Image",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    // Item name and price
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "uhuhub",
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )

                                        // Price of the item

                                    }

                                    // Vertical column for price, quantity icons, and remove button
                                    Column(
                                        modifier = Modifier.padding(start = 8.dp),
                                    ) {
                                        // Price
                                        Text(
                                            text = "Kshs : 2",
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                        Spacer(modifier = Modifier.height(15.dp))
                                        // Quantity icons
                                        // Add/subtract quantity icons logic here
                                        // Replace with your actual icons and logic
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
                                        // Text button to remove item from cart

                                        TextButton(
                                            onClick = { *//* Remove item from cart logic *//* },
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent)
                                        ) {
                                            Text(
                                                text = "Delete",
                                                color = Color.Magenta
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
                                    text = "Kshs", // Assuming totalPrice is a String or a formatted value
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Magenta
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Checkout button
                            Button(
                                onClick = { *//* Handle checkout logic *//* },
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
                    }*/
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
fun CartScreen() {
    val cartItems = listOf(
        CartItem("Item 1", "Kshs 100", R.drawable.kenyanfood),
        CartItem("Item 2", "Kshs 150", R.drawable.pancakes),
        // Add more items as needed
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(cartItems) { item ->
            CartItemCard(item)
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
                        text = "Kshs", // Assuming totalPrice is a String or a formatted value
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
fun CartItemCard(cartItem: CartItem) {
    var quantity by remember { mutableStateOf(1) }
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
                    painter = painterResource(id = cartItem.imageResId),
                    contentDescription = "Item Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = cartItem.name,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            // Item name, price, quantity, and remove button
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End

            ) {

                Text(
                    text = cartItem.price,
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

data class CartItem(val name: String, val price: String, val imageResId: Int)

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    MyFoodAppProjectTheme {
        CartScreen(rememberNavController())
    }
}