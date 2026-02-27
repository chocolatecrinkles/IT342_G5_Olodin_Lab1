package com.example.it342mobile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// your app imports
import com.example.it342mobile.data.api.ApiClient
import com.example.it342mobile.data.TokenManager
import com.example.it342mobile.data.api.UserApi
import com.example.it342mobile.data.model.UserProfile
import com.example.it342mobile.ui.components.AppDialog

@Composable
fun DashboardScreen(
    profile: UserProfile,
    onLogout: () -> Unit
) {
    val initials =
        (profile.firstname.firstOrNull()?.uppercaseChar() ?: '?').toString() +
                (profile.lastname.firstOrNull()?.uppercaseChar() ?: '?').toString()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 360.dp),
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "${profile.firstname} ${profile.lastname}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(profile.email)

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Logout")
                }
            }
        }
    }
}