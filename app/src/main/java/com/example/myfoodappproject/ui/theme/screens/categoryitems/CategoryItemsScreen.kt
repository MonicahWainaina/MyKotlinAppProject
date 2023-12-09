@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myfoodappproject.ui.theme.screens.categoryitems

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.ui.theme.MyFoodAppProjectTheme
import com.example.myfoodappproject.ui.theme.screens.home.FoodItem
import com.example.myfoodappproject.ui.theme.screens.home.FoodsViewModel

@Composable
fun FoodItemsByCategory(navController: NavHostController) {
    val viewModel: FoodsViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val categoryName = navBackStackEntry?.arguments?.getString("categoryName")

    val foodItemsByCategory = categoryName?.let {
        viewModel.getFoodItemsByCategory(it)
    } ?: emptyList()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = categoryName.orEmpty(), color = Color.White)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Magenta),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                items(foodItemsByCategory) { foodItem ->
                    FoodItemCard(foodItem = foodItem, navController = navController)
                    Spacer(modifier = Modifier.height(16.dp)) // Space between cards
                }
            }
        },
        floatingActionButton = {
            // If you want to add a FloatingActionButton, you can add it here
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun FoodItemCard(foodItem: FoodItem,navController: NavHostController) {
    // Implement your card layout to display the food item details here
    // Use the foodItem properties like name, image, description, etc., to populate the card
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
                        onClick = {},
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


@Composable
@Preview(showBackground = true)
fun CategoryItemsPreview(){
    MyFoodAppProjectTheme {
        FoodItemsByCategory(rememberNavController())
    }
}