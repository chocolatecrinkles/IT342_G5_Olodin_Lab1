package com.example.it342mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.it342mobile.ui.LoginScreen
import com.example.it342mobile.ui.DashboardScreen
import com.example.it342mobile.ui.RegisterScreen
import com.example.it342mobile.ui.theme.IT342MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IT342MobileTheme {

                var currentScreen by remember { mutableStateOf("LOGIN") }

                MaterialTheme{
                    when (currentScreen) {
                        "LOGIN" -> LoginScreen(
                            onLoginSuccess = { currentScreen = "DASHBOARD" },
                            onGoToRegister = { currentScreen = "REGISTER" }
                            )
                        "REGISTER" -> RegisterScreen(
                            onRegisterSuccess = { currentScreen = "LOGIN" },
                            onGoToLogin = { currentScreen = "LOGIN" }
                        )
                        "DASHBOARD" -> DashboardScreen (
                            onLogout = { currentScreen = "LOGIN"}
                        )
                    }
                }
            }
        }
    }
}
