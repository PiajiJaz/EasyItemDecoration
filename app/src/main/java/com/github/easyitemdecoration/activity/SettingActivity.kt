package com.github.easyitemdecoration.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.widget.CompoundButton
import com.gc.materialdesign.views.Slider
import com.gc.materialdesign.widgets.ColorSelector
import com.github.easyitemdecoration.R
import com.github.easyitemdecoration.prefs
import com.whereinow.gankandroid.base.BaseActivity
import kotlinx.android.synthetic.main.content_setting.*

/**
 * Created by JinJc on 2017/9/4.
 */
class SettingActivity : BaseActivity() {

    var dividerColor: Int = prefs.dividerColor

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent()
            intent.setClass(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initToolbar(resources.getString(R.string.setting), true)
        initContent()
        initListener()
    }

    fun initContent() {
        swOrientation.isChecked = when (prefs.dividerOrientation) {
            OrientationHelper.HORIZONTAL -> true
            else -> false
        }
        swStyle.isChecked = prefs.itemStyle
        swDraw.isChecked = prefs.dividerEnable
        swDrawOutFrame.isChecked = prefs.dividerOutFrameEnable
        vColor.setBackgroundColor(dividerColor)

        val sldList = ArrayList<Slider>()
        sldList.add(sldWidth)
        sldList.add(sldLeft)
        sldList.add(sldRight)
        sldList.add(sldTop)
        sldList.add(sldBottom)
        for (item in sldList) {
            item.max = 20
            item.min = 0
            item.isShowNumberIndicator = true
            item.drawingCacheBackgroundColor = ContextCompat.getColor(this, R.color.colorAccent)
            item.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
        sldWidth.value = prefs.dividerWidth.toInt()
        sldLeft.value = prefs.dividerLeft.toInt()
        sldRight.value = prefs.dividerRight.toInt()
        sldTop.value = prefs.dividerTop.toInt()
        sldBottom.value = prefs.dividerBottom.toInt()
    }

    fun initListener() {
        swOrientation.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> prefs.dividerOrientation = OrientationHelper.HORIZONTAL
                false -> prefs.dividerOrientation = OrientationHelper.VERTICAL
            }
        }
        swStyle.setOnCheckedChangeListener { _, isChecked ->
            prefs.itemStyle = isChecked
        }
        swDraw.setOnCheckedChangeListener { _, isChecked ->
            prefs.dividerEnable = isChecked
        }
        swDrawOutFrame.setOnCheckedChangeListener { _, isChecked ->
            prefs.dividerOutFrameEnable = isChecked
        }
        vColor.setOnClickListener {
            val selector = ColorSelector(this, dividerColor, ColorSelector.OnColorSelectedListener { color ->
                prefs.dividerColor = color
                dividerColor = color
                vColor.setBackgroundColor(dividerColor)
                Log.d("SelectedColor", color.toString())
            })
            selector.setOnCancelListener {
                Log.d("SelectedColor", dividerColor.toString())
                vColor.setBackgroundColor(dividerColor)
            }
            selector.show()
        }
        sldWidth.onValueChangedListener = Slider.OnValueChangedListener { value ->
            prefs.dividerWidth = value.toFloat()
        }
        sldLeft.onValueChangedListener = Slider.OnValueChangedListener { value ->
            prefs.dividerLeft = value.toFloat()
        }
        sldRight.onValueChangedListener = Slider.OnValueChangedListener { value ->
            prefs.dividerRight = value.toFloat()
        }
        sldTop.onValueChangedListener = Slider.OnValueChangedListener { value ->
            prefs.dividerTop = value.toFloat()
        }
        sldBottom.onValueChangedListener = Slider.OnValueChangedListener { value ->
            prefs.dividerBottom = value.toFloat()
        }
    }

}