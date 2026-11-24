package com.lokewt.potd.ui.screens.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    val notifications_enabled = viewModel.notifications_enabled
    val needsPermission = ContextCompat.checkSelfPermission(
                LocalContext.current, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        granted ->
            if (granted) viewModel.enableNotifications()
    }

    LaunchedEffect(Unit) {
        viewModel.loadSettings()
    }

    Column(
        modifier = Modifier.padding(12.dp, 24.dp, 12.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Enable Notifications")
            Switch(
                checked = notifications_enabled,
                onCheckedChange = {
                    when {
                        notifications_enabled -> viewModel.disableNotifications()
                        needsPermission -> permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        else -> viewModel.enableNotifications()
                    }
                }
            )
        }
    }
}
