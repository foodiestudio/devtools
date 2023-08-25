package com.github.foodiestudio.devtools

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.github.foodiestudio.devtools.internal.ui.MainActivity
import com.github.foodiestudio.devtools.widget.DevEntryWidget

object DevToolsManager {

    private var initialized = false

    internal lateinit var widgets: List<DevEntryWidget>

    /**
     * add shortcut for app
     */
    fun init(context: Context, widgets: List<DevEntryWidget>) {
        if (initialized) {
            return
        }
        this.widgets = widgets
        val shortcut = ShortcutInfoCompat.Builder(context, "id-dev-tools")
            .setShortLabel("Dev Tools")
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_dev_tools_shotcut))
            .setIntent(
                Intent(context, MainActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                }
            )
            .build()

        ShortcutManagerCompat.pushDynamicShortcut(context, shortcut)
        initialized = true
    }
}