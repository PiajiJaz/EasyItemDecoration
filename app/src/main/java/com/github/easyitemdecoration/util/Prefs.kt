package com.github.easyitemdecoration.util

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
import android.support.v7.widget.OrientationHelper
import com.github.easyitemdecoration.R

/**
 * Created by JinJc on 2017/9/5.
 */
class Prefs(val context: Context) {
    val PREFS_FILENAME = Prefs::class.java.`package`.name!!
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    val RECYCLER_ORIENTATION = "dividerOrientation"
    var dividerOrientation: Int
        get() = prefs.getInt(RECYCLER_ORIENTATION, OrientationHelper.VERTICAL)
        set(value) = prefs.edit().putInt(RECYCLER_ORIENTATION, value).apply()

    val ITEM_STYLE = "itemStyle"
    var itemStyle: Boolean
        get() = prefs.getBoolean(ITEM_STYLE, false)
        set(value) = prefs.edit().putBoolean(ITEM_STYLE, value).apply()

    val DIVIDER_ENABLE = "dividerEnable"
    var dividerEnable: Boolean
        get() = prefs.getBoolean(DIVIDER_ENABLE, false)
        set(value) = prefs.edit().putBoolean(DIVIDER_ENABLE, value).apply()

    val DIVIDER_OUT_FRAME_ENABLE = "dividerOutFrameEnable"
    var dividerOutFrameEnable: Boolean
        get() = prefs.getBoolean(DIVIDER_OUT_FRAME_ENABLE, false)
        set(value) = prefs.edit().putBoolean(DIVIDER_OUT_FRAME_ENABLE, value).apply()

    val DIVIDER_COLOR = "dividerColor"
    var dividerColor: Int
        get() = prefs.getInt(DIVIDER_COLOR, ContextCompat.getColor(context, R.color.colorDivider))
        set(value) = prefs.edit().putInt(DIVIDER_COLOR, value).apply()

    val DIVIDER_WIDTH = "dividerWidth"
    var dividerWidth: Float
        get() = prefs.getFloat(DIVIDER_WIDTH, 0f)
        set(value) = prefs.edit().putFloat(DIVIDER_WIDTH, value).apply()

    val DIVIDER_LEFT = "dividerLeft"
    var dividerLeft: Float
        get() = prefs.getFloat(DIVIDER_LEFT, 0f)
        set(value) = prefs.edit().putFloat(DIVIDER_LEFT, value).apply()

    val DIVIDER_RIGHT = "dividerRight"
    var dividerRight: Float
        get() = prefs.getFloat(DIVIDER_RIGHT, 0f)
        set(value) = prefs.edit().putFloat(DIVIDER_RIGHT, value).apply()

    val DIVIDER_TOP = "dividerTop"
    var dividerTop: Float
        get() = prefs.getFloat(DIVIDER_TOP, 0f)
        set(value) = prefs.edit().putFloat(DIVIDER_TOP, value).apply()

    val DIVIDER_BOTTOM = "dividerBottom"
    var dividerBottom: Float
        get() = prefs.getFloat(DIVIDER_BOTTOM, 0f)
        set(value) = prefs.edit().putFloat(DIVIDER_BOTTOM, value).apply()
}