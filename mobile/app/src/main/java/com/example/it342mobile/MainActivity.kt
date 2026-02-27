package com.example.it342mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.it342mobile.data.TokenManager
import com.example.it342mobile.data.api.ApiClient
import com.example.it342mobile.data.api.UserApi
import com.example.it342mobile.data.model.UserProfile
import com.example.it342mobile.ui.*
import com.example.it342mobile.ui.theme.IT342MobileTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IT342MobileTheme {

                val context = this
                val tokenManager = remember { TokenManager(context) }

                var currentScreen by remember { mutableStateOf("LOGIN") }
                var profile by remember { mutableStateOf<UserProfile?>(null) }

                fun loadProfile() {
                    val token = tokenManager.getToken() ?: return
                    val api = ApiClient.retrofit.create(UserApi::class.java)

                    api.getMe("Bearer $token")
                        .enqueue(object : Callback<UserProfile> {
                            override fun onResponse(
                                call: Call<UserProfile>,
                                response: Response<UserProfile>
                            ) {
                                if (response.isSuccessful) {
                                    profile = response.body()
                                    currentScreen = "DASHBOARD"
                                } else {
                                    tokenManager.clearToken()
                                    currentScreen = "LOGIN"
                                }
                            }

                            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                                tokenManager.clearToken()
                                currentScreen = "LOGIN"
                            }
                        })
                }

                when (currentScreen) {
                    "LOGIN" -> LoginScreen(
                        onLoginSuccess = { loadProfile() },
                        onGoToRegister = { currentScreen = "REGISTER" }
                    )

                    "REGISTER" -> RegisterScreen(
                        onRegisterSuccess = { currentScreen = "LOGIN" },
                        onGoToLogin = { currentScreen = "LOGIN" }
                    )

                    "DASHBOARD" -> profile?.let {
                        DashboardScreen(
                            profile = it,
                            onLogout = {
                                tokenManager.clearToken()
                                profile = null
                                currentScreen = "LOGIN"
                            }
                        )
                    }
                }
            }
        }
    }
}
