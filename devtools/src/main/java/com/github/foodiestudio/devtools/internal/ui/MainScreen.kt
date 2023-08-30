package com.github.foodiestudio.devtools.internal.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.github.foodiestudio.devtools.DevToolsManager
import com.github.foodiestudio.devtools.R

@Composable
internal fun MainScreen(navigator: NavHostController) {
    val context = LocalContext.current

    val map: Map<String, String> = remember {
        DevToolsManager.widgets.associate { it.route to it.displayName }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = context.getAppName())
                        Text("DevTools", style = MaterialTheme.typography.caption)
                    }
                },
                modifier = Modifier.statusBarsPadding(),
                actions = {
                    IconButton(onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                "https://github.com/foodiestudio/devtools/issues".toUri()
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
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(map.entries.toList(), key = { it.key }) { (route, displayName) ->
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            navigator.navigate(route)
                        }
                    ) {
                        Text(
                            text = displayName,
                            Modifier
                                .padding(vertical = 32.dp)
                        )
                    }
                }
            }
        }
    )
}

private fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()