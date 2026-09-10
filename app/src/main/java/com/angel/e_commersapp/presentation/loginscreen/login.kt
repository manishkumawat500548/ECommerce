package com.angel.e_commersapp.presentation.loginscreen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.angel.e_commersapp.R
import com.angel.e_commersapp.navigation.Routes
import com.angel.e_commersapp.presentation.auth.AuthViewModel
import com.angel.e_commersapp.ui.theme.blue
import com.angel.e_commersapp.util.CustomTextField
import com.angel.e_commersapp.util.GoogleSignInHelper
import com.angel.e_commersapp.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: LogInViewModel = viewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var userNameAndEmail by viewModel.userNameAndEmail
    var userPassword by viewModel.userPassword
    var showError by viewModel.showError
    var errorMessage by viewModel.errorMessage
    var visible by viewModel.visible

    val icon = if (visible) R.drawable.visibility else R.drawable.visible

    val context = LocalContext.current
    val authState by authViewModel.authState.collectAsState()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        authViewModel.signInWithGoogle(account)
                    } else {
                        showError = true
                        errorMessage = "Google sign-in failed:Account is null"
                    }
                } catch (e: ApiException) {
                    showError = true
                    errorMessage = "Google sign-in failed:${e.statusCode}-${e.message}"
                }
            }

            Activity.RESULT_CANCELED -> {
                showError = true
                errorMessage = "Google sign-in cancelled"
            }

            else -> {
                showError = true
                errorMessage = "Google sign-in failed with result code:${result.resultCode}"
            }
        }

    }
    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is Result.Success -> {

                navController.navigate(Routes.ProductListScreen) {
                    popUpTo(Routes.LoginScreen) { inclusive = true }
                }
            }

            is Result.Failure -> {
                showError = true
                errorMessage = currentState.message
            }

            Result.Idle, Result.Loading -> {

            }
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
                "Welcome\nBack!",
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
                }, modifier = Modifier.fillMaxWidth(),
                textColor = Color.Gray,
                containerColor = Color.Transparent,
                cursorColor = Color(0xff4392f9),
                focusedIndicatorColor = Color(0xff4392f9),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
                singleLine = true

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
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                textColor = Color.Gray,
                containerColor = Color.Transparent,
                cursorColor = Color(0xff4392f9),
                focusedIndicatorColor = Color(0xff4392f9),
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
                singleLine = true
            )

            Spacer(Modifier.height(10.dp))
            Text(
                "Forget Password?",
                color = blue,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        navController.navigate(Routes.ForgetPasswordScreen)
                    }),
                textAlign = TextAlign.End
            )
            Spacer(Modifier.height(20.dp))
            if (showError) {
                Text(errorMessage, color = Color.Red)
            }

            Spacer(Modifier.height(40.dp))
            Button(
                onClick = {
                    if (userNameAndEmail.isNotBlank() && userPassword.isNotBlank()) {
                        authViewModel.login(userNameAndEmail, userPassword)
                    }
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = blue,
                    contentColor = Color.White
                )
            ) {
                if (authState is Result.Loading) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                } else {
                    Text("Login", fontSize = 18.sp, color = Color.White)
                }
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
                        .size(60.dp).clip(CircleShape)
                        .clickable(onClick = {
                            val googleSignInClient =
                                GoogleSignInHelper.getGoogleSignInClient(context)
                            val signInIntent = googleSignInClient.signInIntent
                            googleSignInLauncher.launch(signInIntent)
                        }),
                    color = Color(0xfffcf3f6),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, color = blue)
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
                Text("Create an Account  ", color = Color.Gray)
                Text(
                    "Sign Up",
                    textDecoration = TextDecoration.Underline,
                    color = blue,
                    fontSize = 16.sp, modifier = Modifier.clickable(onClick = {navController.navigate(
                        Routes.SignUpScreen)})
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun View(modifier: Modifier = Modifier) {
//    LogInScreen()
//
//}