package com.example.myfoodappproject.ui.theme.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Card
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
fun CategoriesScreen(navController: NavHostController){
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
        val categories = listOf(
            Category("BreakFast", R.drawable.breakfast),
            Category("Lunch", R.drawable.nyamachomaplatter),
            Category("Dinner", R.drawable.popularkenyan)
        )
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
                        onClick = {navController.navigate(ROUTE_WELCOME) },
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
                            Text(text = "Categories", color = Color.White)
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
                   /* Text(
                        modifier = Modifier.padding(it),
                        text = "CategoriesScreen"
                    )*/
                    Column (
                        modifier = Modifier
                            .padding(16.dp).padding(it) // Padding around the Column
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()) // Enable scrolling
                            .padding(bottom = 16.dp) // Padding at the bottom
                    ) {
                        categories.forEach { category ->
                            CategoryCard(category.name, category.imageResId, navController = navController)
                            Spacer(modifier = Modifier.height(16.dp)) // Space between cards
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryCard(categoryName: String, imageResId: Int,navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Occupy entire width
            .aspectRatio(16f/9f) // Aspect ratio
            .clip(RoundedCornerShape(16.dp)) // Rounded corners
            .border(1.dp, Color.Magenta, RoundedCornerShape(16.dp)) // Magenta border
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)) // Shadow
            .padding(8.dp) // Padding inside the card
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clickable {
                navController.navigate("foodItemsByCategory/$categoryName") {
                    launchSingleTop = true
                }
            }
        ) {
            // Image
            androidx.compose.foundation.Image(
                painter = painterResource(id = imageResId),
                contentDescription = categoryName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Dark overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // Adjust alpha for darkness
            )

            // Text overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = categoryName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
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
data class Category(val name: String, val imageResId: Int)

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    MyFoodAppProjectTheme {
        CategoriesScreen(rememberNavController())
    }
}