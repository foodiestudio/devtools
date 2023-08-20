package com.github.foodiestudio.devtools.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.github.foodiestudio.devtools.R

@Composable
fun MainScreen(navigator: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DevTools") },
                modifier = Modifier.statusBarsPadding(),
                actions = {
                    IconButton(onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/foodiestudio/dev-tools")
                            )
                        )
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_github_circle_white_24dp),
                            null,
                            tint = Color.White
                        )
                    }
                })
        },
        content = { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navigator.navigate("/storage")
                    }
                ) {
                    Text(
                        text = "View App Storage",
                            Modifier
                            .padding(vertical = 32.dp)
                    )
                }
            }
        }
    )
}