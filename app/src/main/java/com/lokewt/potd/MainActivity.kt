package com.lokewt.potd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.lokewt.potd.ui.screens.proverb.ProverbScreen
import com.lokewt.potd.ui.screens.settings.SettingsScreen
import com.lokewt.potd.ui.theme.ProverbOTDTheme
import com.lokewt.potd.icons.settings
import com.lokewt.potd.icons.home
import kotlinx.coroutines.launch

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    PROVERB("proverb", "Proverb", home, "Proverb"),
    SETTINGS("settings", "Settings", settings, "Settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProverbOTDTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val backStack = remember { mutableStateListOf<Any>("proverb") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = backStack.last() == destination.route,
                        onClick = {
                            backStack.add(destination.route)
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavDisplay(
                backStack = backStack,
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeLastOrNull()
                    }
                },
                entryProvider = { key ->
                   when (key) {
                       "proverb" -> NavEntry(key) { ProverbScreen() }
                       "settings" -> NavEntry(key) { SettingsScreen() }
                       else -> NavEntry(key) { ProverbScreen() }
                   }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProverbPreview() {
    ProverbScreen()
}