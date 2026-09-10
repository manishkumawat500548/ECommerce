package com.angel.e_commersapp.presentation.signupscreen

import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angel.e_commersapp.R
import com.angel.e_commersapp.navigation.Routes
import com.angel.e_commersapp.presentation.auth.AuthViewModel
import com.angel.e_commersapp.ui.theme.blue
import com.angel.e_commersapp.util.CustomTextField
import com.angel.e_commersapp.util.Result

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {


    var userNameAndEmail by viewModel.userNameAndEmail
    var userPassword by viewModel.userPassword
    var userConfPassword by viewModel.userConfPassword
    var errorMessage by viewModel.errorMessage
    var visible by remember { mutableStateOf(false) }
    var visible1 by remember { mutableStateOf(false) }
    val icon = if (visible) R.drawable.visibility else R.drawable.visible
    val icon1 = if (visible1) R.drawable.visibility else R.drawable.visible

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is Result.Success -> {
                navController.navigate(Routes.LoginScreen) {
                    popUpTo(Routes.SignUpScreen) { inclusive = true }
                }
                authViewModel.resetAuthState()
            }

            is Result.Failure -> {
                errorMessage = (authState as Result.Failure).message
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .widthIn(max = 450.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            Text(
                "Create an\naccount",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 30.sp
            )

            Spacer(Modifier.height(20.dp))
            CustomTextField(
                value = userNameAndEmail,
                onValueChange = { userNameAndEmail = it },
                placeHolder = { Text("Name/Email", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp), tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                textColor = Color.Gray,
                containerColor = Color.Transparent,
                cursorColor = Color(0xff4392f9),
                focusedIndicatorColor = Color(0xff4392f9),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
            )

            Spacer(Modifier.height(20.dp))
            CustomTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                placeHolder = { Text("Password", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.padlock),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp), tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { visible = !visible }) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp), tint = Color.Gray
                        )
                    }
                },
                singleLine = true,
                textColor = Color.Gray,
                containerColor = Color.Transparent,
                cursorColor = Color(0xff4392f9),
                focusedIndicatorColor = Color(0xff4392f9),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(Modifier.height(20.dp))

            CustomTextField(
                value = userConfPassword,
                onValueChange = { userConfPassword = it },
                placeHolder = { Text("ConfirmPassword", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.padlock),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp), tint = Color.Gray
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { visible1 = !visible1 }) {
                        Icon(
                            painter = painterResource(icon1),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp), tint = Color.Gray
                        )
                    }
                }, singleLine = true,
                textColor = Color.Gray,
                containerColor = Color.Transparent,
                cursorColor = Color(0xff4392f9),
                focusedIndicatorColor = Color(0xff4392f9),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
                visualTransformation = if (visible1) VisualTransformation.None else PasswordVisualTransformation()
            )


            Spacer(Modifier.height(20.dp))
            Row() {
                Text("By clicking the ", color = Color.Gray)
                Text("Register ", color = blue)
                Text("button,you agree", color = Color.Gray)
            }
            Text("to the public offer", color = Color.Gray)
            Spacer(Modifier.height(20.dp))

            if (errorMessage != null) {
                Text(
                    errorMessage ?: "",
                    color = Color.Red
                )
            }

            Spacer(Modifier.height(40.dp))
            Button(
                onClick = {
                    errorMessage = ""

                    if (userNameAndEmail.isBlank()) {
                        errorMessage = "Email is required"
                    } else if (userPassword.isBlank()) {
                        errorMessage = "Password is required"
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(userNameAndEmail).matches()) {
                        errorMessage = "Enter a valid email address"
                    } else if (userConfPassword.isBlank()) {
                        errorMessage = "Confirm password is required"
                    } else if (userPassword != userConfPassword) {
                        errorMessage = "Passwords don't match"
                    } else {
                        authViewModel.signup(userNameAndEmail, userPassword)

                    }
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                )
            ) {
                Text("Create Account", fontSize = 18.sp)
            }

            Spacer(Modifier.height(50.dp))
            Text(
                "- Or Continue With -", color = Color.Gray, modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center

            ) {
                Surface(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable(onClick = {}),
                    color = Color(0xfffcf3f6),
                    shape = CircleShape, border = BorderStroke(1.dp, color = blue)
                ) {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("I Already Have an Account ", color = Color.Gray)
                Text(
                    "Login",
                    textDecoration = TextDecoration.Underline,
                    color = blue,
                    fontSize = 16.sp, modifier = Modifier.clickable(onClick = {navController.navigate(
                        Routes.LoginScreen)})
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun View(modifier: Modifier = Modifier) {
//    SignUpScreen()

//}