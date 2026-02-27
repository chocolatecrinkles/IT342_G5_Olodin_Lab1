package com.example.it342mobile.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun AppDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(confirmText)
            }
        },
        title = { Text(title) },
        text = { Text(message) }
    )
}