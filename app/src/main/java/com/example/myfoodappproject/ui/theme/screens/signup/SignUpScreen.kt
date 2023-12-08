package com.example.foodapp.ui.theme.screens.signup

import android.content.Context
import android.util.Patterns
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfoodappproject.R
import com.example.myfoodappproject.navigation.ROUTE_LOGIN
import com.example.myfoodappproject.ui.theme.MyFoodAppProjectTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
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
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            text = "Enter name",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Magenta, // Change the focused border color
                        unfocusedBorderColor = Color.White // Change the unfocused border color
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

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
                        keyboardType = KeyboardType.Email
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Magenta, // Change the focused border color
                        unfocusedBorderColor = Color.White // Change the unfocused border color
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))
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
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Magenta, // Change the focused border color
                        unfocusedBorderColor = Color.White // Change the unfocused border color
                    )
                )

                Spacer(modifier = Modifier.height(50.dp))
                // TextField with icon and label for Email
                // ...

                // TextField with icon and label for Password
                // ...


                // Button for Register
                val auth = FirebaseAuth.getInstance()
                // Inside your SignUpScreen composable
                val context = LocalContext.current

                RegisterButton(
                    context = context,
                    onClick = {
                        // Validate fields before attempting registration
                        // Validate fields before attempting registration
                        if (name.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6) {
                            // Check if email exists and register user if not
                            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val signInMethods = task.result?.signInMethods
                                    if (signInMethods.isNullOrEmpty()) {
                                        // Email doesn't exist, proceed with registration
                                        auth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener { registerTask ->
                                                if (registerTask.isSuccessful) {
                                                    // Registration successful, add user data to the database
                                                    val firebaseUser = auth.currentUser
                                                    val firebaseDatabase = Firebase.database
                                                    val userReference = firebaseDatabase.getReference("Users").child(firebaseUser?.uid ?: "")
                                                    val userData = User(name, email)

                                                    userReference.setValue(userData)
                                                        .addOnCompleteListener { userCreationTask ->
                                                            if (userCreationTask.isSuccessful) {
                                                                // User data successfully added to the database
                                                                // Show toast and navigate to login screen
                                                                Toast.makeText(
                                                                    context,
                                                                    "Registration Successful",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                                navController.navigate(ROUTE_LOGIN)
                                                            } else {
                                                                // Handle failure to add user data to the database
                                                                Toast.makeText(
                                                                    context,
                                                                    "Error adding user data to database",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                } else {
                                                    // Registration failed
                                                    Toast.makeText(
                                                        context,
                                                        "Registration Failed",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                    } else {
                                        // Email already exists
                                        Toast.makeText(
                                            context,
                                            "User with this email already exists!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    // Error fetching sign-in methods
                                    Toast.makeText(
                                        context,
                                        "Error checking email existence!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }  else {
                            // Fields are incomplete or invalid
                        }
                    },
                )

                // Text for Already Registered
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Already registered? ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                    TextButton(
                        onClick = { navController.navigate(ROUTE_LOGIN) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Magenta,
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(text = "Sign In", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }

                }
            }
        }
    }
}
@Composable
fun RegisterButton(
    context: Context,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(Color.Magenta)
    ) {
        Text(text = "Register", fontWeight = FontWeight.Bold, fontSize = 17.sp)
    }
}
data class User(
    val name: String = "",
    val email: String = ""
)


@Composable
@Preview(showBackground = true)
fun SignUpSccreenPreview(){
    MyFoodAppProjectTheme {
        SignUpScreen(rememberNavController())
        }
    }

