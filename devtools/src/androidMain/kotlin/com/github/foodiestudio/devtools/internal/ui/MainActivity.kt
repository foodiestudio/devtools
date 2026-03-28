package com.github.foodiestudio.devtools.internal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.github.foodiestudio.devtools.DevToolsManager
import com.github.foodiestudio.devtools.widget.DialogWidget
import com.github.foodiestudio.devtools.widget.SheetWidget
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    private val blueGrey700 = Color(0xFF455A64)

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setStatusBarColor(color = blueGrey700)
                systemUiController.setNavigationBarColor(color = blueGrey700.copy(alpha = 0.35f))
                onDispose {}
            }
            MaterialTheme(
                colors = lightColors(
                    primary = blueGrey700,
                )
            ) {
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)
                ModalBottomSheetLayout(
                    bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ) {
                    NavHost(navController = navController, startDestination = "/") {
                        composable("/") { MainScreen(navController) }
                        DevToolsManager.widgets.forEach { widget ->
                            when (widget) {
                                is DialogWidget -> dialog(
                                    widget.route,
                                    dialogProperties = widget.dialogProperties
                                ) {
                                    widget.content.invoke(it)
                                }

                                is SheetWidget -> bottomSheet(widget.route) {
                                    widget.content.invoke(it)
                                }

                                else -> composable(widget.route) {
                                    widget.content.invoke(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
