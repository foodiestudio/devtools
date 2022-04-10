package com.github.foodiestudio.devtools

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat

object DevToolsManager {

    private var initialized = false

    /**
     * add shortcut for app
     */
    fun init(context: Context) {
        if (initialized) {
            return
        }
        val shortcut = ShortcutInfoCompat.Builder(context, "id-dev-tools")
            .setShortLabel("Dev Tools")
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_dev_tools_shotcut))
            .setIntent(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/foodiestudio/dev-tools")
                )
            )
            .build()

        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
        initialized = true
    }
}