package com.github.library

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView

/**
 * Created by JinJc on 2017/9/1.
 */
interface CustomItemDrawOver {
    fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
}