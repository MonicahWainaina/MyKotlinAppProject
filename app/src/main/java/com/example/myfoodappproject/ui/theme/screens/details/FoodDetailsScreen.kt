@file:OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)

package com.example.myfoodappproject.ui.theme.screens.details

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.ui.theme.screens.home.FoodItem
import com.example.myfoodappproject.ui.theme.screens.home.UserDataViewModel

@Composable
fun FoodDetailsScreen(foodItem: FoodItem,navController: NavHostController) {
    var quantity by remember { mutableStateOf(1) }
    var totalPrice = quantity * foodItem.price
    val userDataViewModel: UserDataViewModel = viewModel()
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = foodItem.name, color = Color.White)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp).padding(it)
            ) {
                // ... (existing code remains the same)
                //        // Display the image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(data = foodItem.image),
                        contentDescription = "Food Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }


//         Display food details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = foodItem.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = "Kshs: ${foodItem.price}",
                            fontSize = 20.sp,
                            color = Color.Magenta,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = foodItem.description,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    // Quantity order section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
////                // Text indicating quantity order
                        Text("Quantity Order", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        // ...

                        // Quantity section with Add and Remove icons
                        Row(
                            modifier = Modifier
                                .padding(vertical = 14.dp)
                                .background(Color.LightGray,shape = RoundedCornerShape(10.dp))
                        ) {
                            // Remove icon and functionality
                            Box(
                                modifier = Modifier
                                    .size(37.dp)
                                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                                    .clickable {
                                        if (quantity > 1) {
                                            quantity -= 1
                                            totalPrice = quantity * foodItem.price
                                        }
                                    }
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Remove", modifier = Modifier.align(Alignment.Center))
                            }

                            // Quantity indicator
                            Text(
                                text = "$quantity",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 16.dp).align(alignment = Alignment.CenterVertically),
                            )

                            // Add icon and functionality
                            Box(
                                modifier = Modifier
                                    .size(37.dp)
                                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                                    .clickable {
                                        quantity += 1
                                        totalPrice = quantity * foodItem.price
                                    }
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }

                // ... (existing code remains the same)
                // Updated Add to Cart button with total price
                Button(
                    onClick = {
                        val userId = userDataViewModel.getUserId()
                        userId?.let {
                            userDataViewModel.addToCart(userId, foodItem, quantity, context)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                ) {
                    Text(text = "Add to Cart : Kshs:$totalPrice")
                }
            }
        },
        floatingActionButton = {
            // If you want to add a FloatingActionButton, you can add it here
        },
        floatingActionButtonPosition = FabPosition.End
    )
        }


