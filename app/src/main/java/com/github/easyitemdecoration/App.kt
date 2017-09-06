package com.github.easyitemdecoration

import android.app.Application
import com.github.easyitemdecoration.util.Prefs

/**
 * Created by JinJc on 2017/9/5.
 */
class App : Application() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}

val prefs: Prefs by lazy {
    App.prefs!!
}