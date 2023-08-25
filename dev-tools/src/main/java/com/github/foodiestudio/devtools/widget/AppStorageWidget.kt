package com.github.foodiestudio.devtools.widget

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.github.foodiestudio.devtools.internal.ui.AppFilesScreen

class AppStorageWidget : ScreenWidget() {
    override val route: String = "/storage"

    override val displayName: String = "View App Storage"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        AppFilesScreen()
    }
}