package com.example.it342mobile.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.it342mobile.data.TokenManager
import com.example.it342mobile.data.api.ApiClient
import com.example.it342mobile.data.api.AuthApi
import com.example.it342mobile.data.model.LoginRequest
import com.example.it342mobile.data.response.JwtResponse
import com.example.it342mobile.ui.components.AppDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit
    ) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var showErrorDialog by remember{ mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true

                val api = ApiClient.retrofit.create(AuthApi::class.java)
                val request = LoginRequest(email, password)
                val tokenManager = TokenManager(context)

                api.login(request).enqueue(object : Callback<JwtResponse> {
                    override fun onResponse(
                        call: Call<JwtResponse>,
                        response: Response<JwtResponse>
                    ) {
                        isLoading = false
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            if (token != null) {
                                tokenManager.saveToken(token)
                                onLoginSuccess()
                            }
                        } else {
//                            Toast.makeText(context, "Login failed (${response.code()})", Toast.LENGTH_LONG).show()
                            errorMessage = "Login failed (${response.code()})"
                            showErrorDialog = true
                        }
                    }

                    override fun onFailure(
                        call: Call<JwtResponse>,
                        t: Throwable
                    ) {
                        isLoading = false
                        Log.e("LOGIN", "Network error", t)
//                        Toast.makeText(
//                            context,
//                            "Network error: ${t.localizedMessage}",
//                            Toast.LENGTH_LONG
//                        ).show()
                        errorMessage = "Network error: ${t.localizedMessage}"
                        showErrorDialog = true
                    }
                })
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onGoToRegister){
            Text("Don't have an account? Register")
        }

        if(showErrorDialog) {
            AppDialog(title = "Login Failed", message = errorMessage, confirmText = "OK", onConfirm = { showErrorDialog = false }, onDismiss = { showErrorDialog = false} )
        }
    }
}