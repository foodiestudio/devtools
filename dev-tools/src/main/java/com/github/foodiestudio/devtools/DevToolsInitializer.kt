package com.github.foodiestudio.devtools

import android.content.Context
import androidx.startup.Initializer

class DevToolsInitializer : Initializer<DevToolsManager> {
    override fun create(context: Context): DevToolsManager = DevToolsManager.apply {
        init(context.applicationContext)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}