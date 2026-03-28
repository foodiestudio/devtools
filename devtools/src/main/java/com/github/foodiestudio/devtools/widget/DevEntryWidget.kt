package com.github.foodiestudio.devtools.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry

sealed interface DevEntryWidget {

    val route: String

    val displayName: String

    val content: @Composable (NavBackStackEntry) -> Unit
}

abstract class DialogWidget(val dialogProperties: DialogProperties = DialogProperties()) : DevEntryWidget

abstract class SheetWidget : DevEntryWidget

abstract class ScreenWidget : DevEntryWidget
