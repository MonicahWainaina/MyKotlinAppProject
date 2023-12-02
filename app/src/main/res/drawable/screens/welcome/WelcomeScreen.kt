package com.example.foodapp.ui.theme.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodapp.ui.theme.FoodAppTheme
import com.example.foodapp.ui.theme.Pink40
import com.example.foodapp.ui.theme.Pink80
import com.example.wazitoecommerce.R

@Composable
fun WelcomeScreen(onRegisterClick: () -> Unit, onSignInClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.bestfood),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Black transparent overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kikwetu Dishes",
                style = TextStyle(color = Color.White, fontSize = 58.sp, fontWeight = FontWeight.Bold, // Make the text bold
                    fontFamily = FontFamily.Cursive,
                    ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Order Food from the comfort of your home",
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = onSignInClick,
                    colors = ButtonDefaults.buttonColors(Color.Magenta),
                    modifier = Modifier
                        .width(270.dp)
                        .height(48.dp)
                        .background(Color.Transparent)
                ) {
                    Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(8.dp)) // Adjust the space between buttons

                TextButton(
                    onClick = onRegisterClick,
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = Color.Transparent),
                    modifier = Modifier
                        .width(270.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Sign In",fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
}


@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview(){
    FoodAppTheme {
        com.example.foodapp.ui.theme.screens.welcome.WelcomeScreen(onRegisterClick = { /*TODO*/ }) {

        }
    }
}