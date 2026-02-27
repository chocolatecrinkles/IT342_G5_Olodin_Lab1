package com.example.it342mobile.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = firstname, onValueChange = { firstname = it }, label = { Text("First name") })
        OutlinedTextField(value = lastname, onValueChange = { lastname = it }, label = { Text("Last name") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
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
                                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                                onRegisterSuccess()
                            } else {
                                Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()
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
    }


}