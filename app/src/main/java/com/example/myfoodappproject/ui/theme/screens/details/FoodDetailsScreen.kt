@file:OptIn(ExperimentalCoilApi::class)

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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myfoodappproject.ui.theme.screens.home.FoodItem


//@Composable
//fun FoodDetailsScreen(foodItem: FoodItem) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Display the image
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(300.dp)
//        ) {
//            Image(
//                painter = rememberImagePainter(data = foodItem.image),
//                contentDescription = "Food Image",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        }
////        Spacer(modifier = Modifier.height(20.dp))
//        // Display food details
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 5.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = foodItem.name,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//
//                Text(
//                    text = "Kshs: ${foodItem.price}",
//                    fontSize = 20.sp,
//                    color = Color.Magenta,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(vertical = 4.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(
//                text = foodItem.description,
//                fontSize = 16.sp,
//                color = Color.Black,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            // Quantity order section
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Text indicating quantity order
//                Text("Quantity Order", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                        Row(
//                            modifier = Modifier
//                                .padding(vertical = 13.dp).background(Color.LightGray),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            // Add icon
//                            Box(
//                                modifier = Modifier
//                                    .size(37.dp)
//                                    .background(Color.White, shape = RoundedCornerShape(10.dp))
//                            ) {
//                                IconButton(
//                                    onClick = { /* Handle button click */ },
//                                    modifier = Modifier.fillMaxSize(), // Fill the Box with the IconButton
//                                ) {
//                                    Icon(Icons.Default.Remove, contentDescription = "Remove")
//                                }
//                            }
//                            // Quantity indicator
//                            Text(
//                                text = "1", // Update with the actual quantity value
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Color.Black,
//                                modifier = Modifier.padding(horizontal = 16.dp)
//                            )
//
//                            Box(
//                                modifier = Modifier
//                                    .size(37.dp)
//                                    .background(Color.White, shape = RoundedCornerShape(10.dp))
//                            ) {
//                                IconButton(
//                                    onClick = { /* Handle button click */ },
//                                    modifier = Modifier.fillMaxSize(), // Fill the Box with the IconButton
//                                ) {
//                                    Icon(Icons.Default.Add, contentDescription = "Add")
//                                }
//                            }
//                        }
//
//
//
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//            // Add to cart button
//            Button(
//                onClick = { /* Handle adding to cart */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp),
//                colors = ButtonDefaults.buttonColors(Color.Magenta)
//            ) {
//                Text(text = "Add to Cart \"Kshs: ${foodItem.price}\"")
//            }
//        }
//    }
//}

@Composable
fun FoodDetailsScreen(foodItem: FoodItem) {
    var quantity by remember { mutableStateOf(1) }
    var totalPrice = quantity * foodItem.price

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
        Spacer(modifier = Modifier.height(20.dp))

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
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = foodItem.description,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
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
                Spacer(modifier = Modifier.height(10.dp))
                // Updated Add to Cart button with total price
                Button(
                    onClick = { /* Handle adding to cart */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(Color.Magenta)
                ) {
                    Text(text = "Add to Cart : Kshs:$totalPrice")
                }
            }
        }

