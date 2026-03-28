package com.github.foodiestudio.devtools.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.github.foodiestudio.devtools.internal.ui.KibanaQuerySheet

class KibanaQueryWidget(
    defaultUrl: String,
    defaultIndex: String,
    defaultFilters: List<Pair<String, String>> = emptyList()
) : SheetWidget() {
    override val route: String = "/kibana"

    override val displayName: String = "Kibana Query Builder"

    override val content: @Composable (NavBackStackEntry) -> Unit = {
        KibanaQuerySheet(
            Modifier.fillMaxSize(),
            kibanaBaseUrl = defaultUrl,
            kibanaIndex = defaultIndex,
            filterParams = defaultFilters
        )
    }
}