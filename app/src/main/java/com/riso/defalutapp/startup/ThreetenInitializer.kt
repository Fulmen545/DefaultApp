package com.riso.defalutapp.startup

import android.content.Context
import androidx.startup.Initializer
import com.jakewharton.threetenabp.AndroidThreeTen

class ThreetenInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        AndroidThreeTen.init(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}