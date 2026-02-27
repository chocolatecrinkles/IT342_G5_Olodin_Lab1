package com.example.it342mobile.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// your app imports
import com.example.it342mobile.data.api.ApiClient
import com.example.it342mobile.data.TokenManager
import com.example.it342mobile.data.api.UserApi
import com.example.it342mobile.data.model.UserProfile
@Composable
fun DashboardScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }

    var profile by remember { mutableStateOf<UserProfile?>(null) }

    LaunchedEffect(Unit) {
        val token = tokenManager.getToken()
        if (token == null) {
            onLogout()
            return@LaunchedEffect
        }

        val api = ApiClient.retrofit.create(UserApi::class.java)
        api.getMe("Bearer $token").enqueue(object : Callback<UserProfile> {
            override fun onResponse(
                call: Call<UserProfile>,
                response: Response<UserProfile>
            ) {
                if (response.isSuccessful) {
                    profile = response.body()
                } else {
                    tokenManager.clearToken()
                    onLogout()
                }
            }

            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    profile?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            Text("Email: ${it.email}")
            Text("Name: ${it.firstname} ${it.lastname}")

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    tokenManager.clearToken()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }
}