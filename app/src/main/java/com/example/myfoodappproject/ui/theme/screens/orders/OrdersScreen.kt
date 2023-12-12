package com.example.myfoodappproject.ui.theme.screens.orders

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.data.Order
import com.example.myfoodappproject.models.UserDataViewModel
import com.example.myfoodappproject.navigation.ROUTE_CART
import com.example.myfoodappproject.navigation.ROUTE_CATEGORIES
import com.example.myfoodappproject.navigation.ROUTE_HOME
import com.example.myfoodappproject.navigation.ROUTE_ORDERS
import com.example.myfoodappproject.navigation.ROUTE_WELCOME
import com.example.myfoodappproject.ui.theme.screens.home.NavigationItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(navController: NavHostController) {
    val userDataViewModel: UserDataViewModel = viewModel() // or viewModelProvider()

    // Observe changes in the orders StateFlow
    val orders by userDataViewModel.orders.collectAsState()


    LaunchedEffect(Unit) {
        val userId = userDataViewModel.getUserId()
        userId?.let {
            userDataViewModel.fetchOrders(it)
        }
    }

    Log.d("OrderScreen", "Orders: $orders")


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
                            Text(text = "My Orders", color = Color.White)
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(it)
                    ) {
                        if (orders.isEmpty()) {
                            // Show a loading indicator or empty state if no orders
                            Text(text = "No orders available")
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(orders) { order ->
                                    OrderItem(order)
                                }
                            }
                        }
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
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.Magenta, RoundedCornerShape(8.dp))
    ) {
        // Display each item in the order
        order.items.forEach { foodItem ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Image of the item
                Image(
                    painter = rememberImagePainter(data = foodItem.image),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Item name
                Text(
                    text = foodItem.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        // Display order number
        Text(
            text = "Order #${order.orderNumber}",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        // Display order status
        Text(
            text = "Status: ${order.status}",
            color = when (order.status) {
                "Delivered" -> Color.Green
                "On its way" -> Color.Blue
                else -> Color.Black
            },
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}


