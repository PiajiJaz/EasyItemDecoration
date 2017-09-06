package com.github.library

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.*
import android.util.Log
import android.util.TypedValue
import android.view.View

/**
 * Created by JinJc on 2017/8/29.
 */

open class EasyItemDecoration(build: Builder) : RecyclerView.ItemDecoration() {
    private val TAG = EasyItemDecoration::class.java.simpleName
    private var dividerWidth: Int = 0
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    private var isDrawDivider = false
    private var isDrawOutBorder = false
    private var isDrawStartDivider = false
    private var isDrawEndDivider = false
    private var dividerColor: Int? = null
    private var dividerDrawable: Drawable? = null
    private var customItemDrawOver: CustomItemDrawOver? = null
    private var paint = Paint()

    init {
        this.dividerWidth = build.dividerWidth
        this.left = build.left ?: 0
        this.top = build.top ?: 0
        this.right = build.right ?: 0
        this.bottom = build.bottom ?: 0
        this.isDrawDivider = build.isDrawDivider
        this.isDrawOutBorder = build.isDrawOutBorder
        this.isDrawStartDivider = build.isDrawStartDivider
        this.isDrawEndDivider = build.isDrawEndDivider
        this.dividerColor = build.dividerColor
        this.dividerDrawable = build.dividerDrawable
        this.customItemDrawOver = build.customItemDrawOver

        paint.color = dividerColor ?: Color.TRANSPARENT
    }

    fun getSpanCount(parent: RecyclerView): Int {
        val layoutManager = parent.layoutManager
        val spanCount = when (layoutManager) {
            is GridLayoutManager -> layoutManager.spanCount
            is StaggeredGridLayoutManager -> layoutManager.spanCount
            else -> 1
        }
        return spanCount
    }

    fun getRowCount(parent: RecyclerView, index: Int): Int {
        var rowCount = index % getSpanCount(parent)
        rowCount = when {
            rowCount > 0 -> index / getSpanCount(parent) + 1
            rowCount == 0 -> index / getSpanCount(parent)
            else -> index / getSpanCount(parent)
        }
        return rowCount
    }

    fun getColCount(parent: RecyclerView, index: Int): Int {
        var colCount = (index + 1) % getSpanCount(parent)
        if (colCount == 0)
            colCount = getSpanCount(parent)
        return colCount
    }

    fun isFirstCol(parent: RecyclerView, index: Int): Boolean {
        val childCol = getColCount(parent, index)
        return childCol == 1
    }

    fun isLastCol(parent: RecyclerView, index: Int): Boolean {
        val parentCol = getSpanCount(parent)
        val childCol = getColCount(parent, index)
        return childCol == parentCol
    }

    fun isFirstRow(parent: RecyclerView, index: Int): Boolean {
        val childRow = getRowCount(parent, index + 1)
        return childRow == 1
    }

    fun isLastRow(parent: RecyclerView, index: Int): Boolean {
        val parentRow = getRowCount(parent, parent.adapter.itemCount)
        val childRow = getRowCount(parent, index + 1)
        return childRow == parentRow
    }

    fun getOrientation(parent: RecyclerView): Int {
        val layoutManager = parent.layoutManager
        val orientation = when (layoutManager) {
            is GridLayoutManager -> layoutManager.orientation
            is StaggeredGridLayoutManager -> layoutManager.orientation
            else -> (layoutManager as LinearLayoutManager).orientation
        }
        return orientation
    }

    fun setRectParams(rect: Rect, left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        rect.left = left
        rect.top = top
        rect.right = right
        rect.bottom = bottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val spanCount = getSpanCount(parent)

        if (spanCount > 1) {
            when (getOrientation(parent)) {
                OrientationHelper.HORIZONTAL -> {
                    setRectParams(outRect, 0, dividerWidth / 2, dividerWidth, dividerWidth / 2)
                    if (isFirstRow(parent, position))
                        outRect.left = left
                    if (isLastRow(parent, position))
                        outRect.right = right
                    if (isFirstCol(parent, position))
                        outRect.top = top
                    if (isLastCol(parent, position))
                        outRect.bottom = bottom
                }
                OrientationHelper.VERTICAL -> {
                    setRectParams(outRect, dividerWidth / 2, dividerWidth / 2, dividerWidth / 2, dividerWidth / 2)
                    if (isFirstRow(parent, position))
                        outRect.top = top
                    if (isLastRow(parent, position))
                        outRect.bottom = bottom
                    if (isFirstCol(parent, position))
                        outRect.left = left
                    if (isLastCol(parent, position))
                        outRect.right = right
                }
            }
            Log.d(TAG, "index->$position left->${outRect.left} right->${outRect.right} top->${outRect.top} bottom->${outRect.bottom}")
        } else {
            when (getOrientation(parent)) {
                OrientationHelper.HORIZONTAL -> {
                    setRectParams(outRect, dividerWidth, top, 0, bottom)
                    if (isFirstRow(parent, position))
                        outRect.left = left
                    if (isLastRow(parent, position))
                        outRect.right = right
                }
                OrientationHelper.VERTICAL -> {
                    setRectParams(outRect, left, dividerWidth, right)
                    if (isFirstRow(parent, position))
                        outRect.top = top
                    if (isLastRow(parent, position))
                        outRect.bottom = bottom
                }
            }
        }
    }

    fun drawLeftDivider(c: Canvas, child: View, outTop: Boolean, outBottom: Boolean) {
        if (null != dividerDrawable) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val width = (dividerDrawable as Drawable).intrinsicWidth
            val left = child.left - params.leftMargin - width
            val top = if (outTop)
                child.top - params.topMargin - dividerWidth
            else
                child.top - params.topMargin
            val right = child.left - params.leftMargin
            val bottom = if (outBottom)
                child.bottom + params.bottomMargin + dividerWidth
            else
                child.bottom + params.bottomMargin
            dividerDrawable!!.setBounds(left, top, right, bottom)
            dividerDrawable!!.draw(c)
        } else if (null != dividerColor) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin - dividerWidth
            val top = if (outTop)
                child.top - params.topMargin - dividerWidth
            else
                child.top - params.topMargin
            val right = child.left - params.leftMargin
            val bottom = if (outBottom)
                child.bottom + params.bottomMargin + dividerWidth
            else
                child.bottom + params.bottomMargin
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    fun drawRightDivider(c: Canvas, child: View, outTop: Boolean, outBottom: Boolean) {
        if (null != dividerDrawable) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val width = (dividerDrawable as Drawable).intrinsicWidth
            val left = child.right + params.rightMargin
            val top = if (outTop)
                child.top - params.topMargin - dividerWidth
            else
                child.top - params.topMargin
            val right = left + width
            val bottom = if (outBottom)
                child.bottom + params.bottomMargin + dividerWidth
            else
                child.bottom + params.bottomMargin
            dividerDrawable!!.setBounds(left, top, right, bottom)
            dividerDrawable!!.draw(c)
        } else if (null != dividerColor) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val top = if (outTop)
                child.top - params.topMargin - dividerWidth
            else
                child.top - params.topMargin
            val right = left + dividerWidth
            val bottom = if (outBottom)
                child.bottom + params.bottomMargin + dividerWidth
            else
                child.bottom + params.bottomMargin
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    fun drawTopDivider(c: Canvas, child: View, outLeft: Boolean, outRight: Boolean) {
        if (null != dividerDrawable) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val height = (dividerDrawable as Drawable).intrinsicHeight
            val left = if (outLeft)
                child.left - params.leftMargin - dividerWidth
            else
                child.left - params.leftMargin
            val top = child.top - params.topMargin - height
            val right = if (outRight)
                child.right + params.rightMargin + dividerWidth
            else
                child.right + params.rightMargin
            val bottom = child.top - params.topMargin
            dividerDrawable!!.setBounds(left, top, right, bottom)
            dividerDrawable!!.draw(c)
        } else if (null != dividerColor) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = if (outLeft)
                child.left - params.leftMargin - dividerWidth
            else
                child.left - params.leftMargin
            val top = child.top - params.topMargin - dividerWidth
            val right = if (outRight)
                child.right + params.rightMargin + dividerWidth
            else
                child.right + params.rightMargin
            val bottom = child.top - params.topMargin
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    fun drawBottomDivider(c: Canvas, child: View, outLeft: Boolean, outRight: Boolean) {
        if (null != dividerDrawable) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val height = (dividerDrawable as Drawable).intrinsicHeight
            val left = if (outLeft)
                child.left - params.leftMargin - dividerWidth
            else
                child.left - params.leftMargin
            val top = child.bottom + params.bottomMargin
            val right = if (outRight)
                child.right + params.rightMargin + dividerWidth
            else
                child.right + params.rightMargin
            val bottom = top + height
            dividerDrawable!!.setBounds(left, top, right, bottom)
            dividerDrawable!!.draw(c)
        } else if (null != dividerColor) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = if (outLeft)
                child.left - params.leftMargin - dividerWidth
            else
                child.left - params.leftMargin
            val top = child.bottom + params.bottomMargin
            val right = if (outRight)
                child.right + params.rightMargin + dividerWidth
            else
                child.right + params.rightMargin
            val bottom = top + dividerWidth
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (isDrawDivider) {
            val spanCount = getSpanCount(parent)

            if (spanCount > 1) {
                when (getOrientation(parent)) {
                    OrientationHelper.HORIZONTAL -> {
                        for (i in 0..parent.childCount - 1) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (isDrawOutBorder) {
                                drawLeftDivider(c, child, true, true)
                                drawTopDivider(c, child, true, true)
                                drawRightDivider(c, child, true, true)
                                drawBottomDivider(c, child, true, true)
                            } else {
                                if (isFirstRow(parent, index)) {
                                    if (isLastCol(parent, index)) {
                                        drawRightDivider(c, child, false, false)
                                    } else {
                                        drawBottomDivider(c, child, false, true)
                                        drawRightDivider(c, child, false, true)
                                    }
                                } else if (isLastRow(parent, index)) {
                                    if (!isLastCol(parent, index)) {
                                        drawBottomDivider(c, child, false, false)
                                    }
                                } else if (isFirstCol(parent, index)) {
                                    drawBottomDivider(c, child, false, true)
                                    drawRightDivider(c, child, false, true)
                                } else if (isLastCol(parent, index)) {
                                    drawRightDivider(c, child, false, false)
                                } else {
                                    drawBottomDivider(c, child, false, true)
                                    drawRightDivider(c, child, false, true)
                                }
                            }
                        }
                    }
                    OrientationHelper.VERTICAL -> {
                        for (i in 0..parent.childCount - 1) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (isDrawOutBorder) {
                                drawLeftDivider(c, child, true, true)
                                drawTopDivider(c, child, true, true)
                                drawRightDivider(c, child, true, true)
                                drawBottomDivider(c, child, true, true)
                            } else {
                                if (isFirstRow(parent, index)) {
                                    if (isLastCol(parent, index)) {
                                        drawBottomDivider(c, child, false, false)
                                    } else {
                                        drawBottomDivider(c, child, false, true)
                                        drawRightDivider(c, child, false, true)
                                    }
                                } else if (isLastRow(parent, index)) {
                                    if (!isLastCol(parent, index)) {
                                        drawRightDivider(c, child, false, false)
                                    }
                                } else if (isFirstCol(parent, index)) {
                                    drawRightDivider(c, child, false, true)
                                    drawBottomDivider(c, child, false, true)
                                } else if (isLastCol(parent, index)) {
                                    drawBottomDivider(c, child, false, false)
                                } else {
                                    drawBottomDivider(c, child, false, true)
                                    drawRightDivider(c, child, false, true)
                                }
                            }
                        }
                    }
                }
            } else {
                when (getOrientation(parent)) {
                    OrientationHelper.HORIZONTAL -> {
                        for (i in 0..parent.childCount - 1) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (isFirstRow(parent, index)) {
                                drawRightDivider(c, child, false, false)
                                if (isDrawStartDivider)
                                    drawLeftDivider(c, child, false, false)
                            } else if (isLastRow(parent, index)) {
                                if (isDrawEndDivider)
                                    drawRightDivider(c, child, false, false)
                            } else {
                                drawLeftDivider(c, child, false, false)
                                drawRightDivider(c, child, false, false)
                            }
                        }
                    }
                    OrientationHelper.VERTICAL -> {
                        for (i in 0..parent.childCount - 1) {
                            val child = parent.getChildAt(i)
                            val index = parent.getChildAdapterPosition(child)
                            if (isFirstRow(parent, index)) {
                                drawBottomDivider(c, child, false, false)
                                if (isDrawStartDivider)
                                    drawTopDivider(c, child, false, false)
                            } else if (isLastRow(parent, index)) {
                                if (isDrawEndDivider)
                                    drawBottomDivider(c, child, false, false)
                            } else {
                                drawTopDivider(c, child, false, false)
                                drawBottomDivider(c, child, false, false)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (null != customItemDrawOver) {
            customItemDrawOver!!.onDrawOver(c, parent, state)
        }
    }

    class Builder {
        var dividerWidth: Int = 0
        var left: Int? = 0
        var top: Int? = 0
        var right: Int? = 0
        var bottom: Int? = 0
        var isDrawDivider: Boolean = false
        var isDrawOutBorder: Boolean = false
        var dividerColor: Int? = null
        var dividerDrawable: Drawable? = null
        var isDrawStartDivider: Boolean = false
        var isDrawEndDivider: Boolean = false
        var customItemDrawOver: CustomItemDrawOver? = null

        fun dp2px(dpVal: Float): Int {
            return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, Resources.getSystem().displayMetrics) + 0.5f).toInt()
        }

        fun setLeft(left: Float): Builder {
            this.left = dp2px(left)
            return this
        }

        fun setTop(top: Float): Builder {
            this.top = dp2px(top)
            return this
        }

        fun setRight(right: Float): Builder {
            this.right = dp2px(right)
            return this
        }

        fun setBottom(bottom: Float): Builder {
            this.bottom = dp2px(bottom)
            return this
        }

        fun setDividerWidth(dividerWidth: Float): Builder {
            this.dividerWidth = dp2px(dividerWidth)
            return this
        }

        fun setDividerColor(dividerColor: Int): Builder {
            this.dividerColor = dividerColor
            return this
        }

        fun setDividerDrawable(dividerDrawable: Drawable): Builder {
            this.dividerDrawable = dividerDrawable
            return this
        }

        fun setDrawDivider(isDrawDivider: Boolean): Builder {
            this.isDrawDivider = isDrawDivider
            return this
        }

        fun setDrawStartDivider(isDrawStartDivider: Boolean): Builder {
            when (this.isDrawDivider) {
                true -> this.isDrawStartDivider = isDrawStartDivider
                false -> this.isDrawStartDivider = false
            }
            return this
        }

        fun setDrawEndDivider(isDrawEndDivider: Boolean): Builder {
            when (this.isDrawDivider) {
                true -> this.isDrawEndDivider = isDrawEndDivider
                false -> this.isDrawEndDivider = false
            }
            return this
        }

        fun setDrawOutFrame(isDrawOutBorder: Boolean): Builder {
            this.isDrawOutBorder = isDrawOutBorder
            return this
        }

        fun setCustomItemDrawOver(customItemDrawOver: CustomItemDrawOver): Builder {
            this.customItemDrawOver = customItemDrawOver
            return this
        }

        fun build(): EasyItemDecoration {
            return EasyItemDecoration(this)
        }
    }
}
