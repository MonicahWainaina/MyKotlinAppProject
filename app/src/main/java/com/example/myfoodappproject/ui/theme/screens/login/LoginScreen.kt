package com.example.foodapp.ui.theme.screens.login

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myfoodappproject.R
import com.example.myfoodappproject.navigation.ROUTE_HOME
import com.example.myfoodappproject.navigation.ROUTE_SIGNUP
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, auth: FirebaseAuth) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ... (existing code)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.pink),
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Title and Register text
            Column(
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Kikwetu Dishes",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 52.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // TextFields for Full Name, Email, and Password
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TextField with icon and label for Full Name
                // Similar ones can be created for Email and Password


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Enter email",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            signIn(email, password, auth, navController, context)
                        }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Magenta, // Change the focused border color
                        unfocusedBorderColor = Color.White // Change the unfocused border color
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            text = "Enter password",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            signIn(email, password, auth, navController, context)
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Magenta, // Change the focused border color
                        unfocusedBorderColor = Color.White // Change the unfocused border color
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))
                // TextField with icon and label for Email
                // ...

                // TextField with icon and label for Password
                // ...
            }

            // Button for Sign In
            Button(
                onClick = {
                    signIn(email, password, auth, navController, context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 5.dp),
                colors = ButtonDefaults.buttonColors(Color.Magenta)
            ) {
                Text(text = "Sign In", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }

            // Text for Register
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                TextButton(
                    onClick = { navController.navigate(ROUTE_SIGNUP) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Magenta,
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                }
            }
        }
    }
}


fun signIn(email: String, password: String,auth: FirebaseAuth,navController: NavHostController,context: Context)
{
    if (email.isNotEmpty() && password.isNotEmpty()) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to Home screen
                    navController.navigate(ROUTE_HOME)
                } else {
                    // If sign in fails, display a message
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    } else {
        // Empty fields, display a message
        Toast.makeText(
            context,
            "Please fill in all fields.",
            Toast.LENGTH_SHORT
        ).show()
    }
}