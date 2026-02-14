package com.example.it342mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.it342mobile.ui.LoginScreen
import com.example.it342mobile.ui.theme.IT342MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IT342MobileTheme {
                LoginScreen(
                    onLoginSuccess = {
                        // TODO: Navigate to Dashboard screen
                    }
                )
            }
        }
    }
}
