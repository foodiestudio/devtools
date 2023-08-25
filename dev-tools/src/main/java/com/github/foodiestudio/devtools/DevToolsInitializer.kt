package com.github.foodiestudio.devtools

import android.content.Context
import androidx.startup.Initializer
import com.github.foodiestudio.devtools.widget.AppStorageWidget
import com.github.foodiestudio.devtools.widget.KibanaQueryWidget

class DevToolsInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        DevToolsManager.init(
            context.applicationContext, listOf(
                AppStorageWidget(),
                KibanaQueryWidget()
            )
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}