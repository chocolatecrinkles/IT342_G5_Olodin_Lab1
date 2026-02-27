package com.example.it342mobile.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.it342mobile.data.api.ApiClient
import com.example.it342mobile.data.api.AuthApi
import com.example.it342mobile.data.model.RegisterRequest
import com.example.it342mobile.data.response.MessageResponse
import com.example.it342mobile.ui.components.AppDialog

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onGoToLogin: () -> Unit
    ) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = firstname, onValueChange = { firstname = it }, label = { Text("First name") }, modifier = Modifier.fillMaxWidth() )
        OutlinedTextField(value = lastname, onValueChange = { lastname = it }, label = { Text("Last name") }, modifier = Modifier.fillMaxWidth() )
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth() )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                val api = ApiClient.retrofit.create(AuthApi::class.java)

                api.register(RegisterRequest(email, password, firstname, lastname))
                    .enqueue(object : Callback<MessageResponse> {
                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {
                            isLoading = false
                            if (response.isSuccessful) {
                                dialogTitle = "Registration Successful"
                                dialogMessage = "Your account has been successfully created."
                                isSuccess = true
                                showDialog = true
                            } else {
                                dialogTitle = "Registration Failed"
                                dialogMessage = "Please try again."
                                isSuccess = false
                                showDialog = true
                            }
                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                            isLoading = false
                            Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
                        }
                    })
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Registering..." else "Register")
        }

        TextButton(onClick = onGoToLogin) {
            Text("Already have an account? Login")
        }

        if(showDialog){
            AppDialog(
                title = dialogTitle,
                message = dialogMessage,
                confirmText = if(isSuccess) "Go to Login page" else "Close",
                onConfirm = { showDialog = false
                    if (isSuccess) onRegisterSuccess()
                },
                onDismiss = { showDialog = false}
            )
        }
    }


}