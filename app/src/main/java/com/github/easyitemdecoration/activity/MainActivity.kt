package com.github.easyitemdecoration.activity

import android.graphics.*
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.*
import android.view.MenuItem
import com.github.easyitemdecoration.R
import com.github.easyitemdecoration.adapter.NormalAdapter
import com.github.easyitemdecoration.prefs
import com.github.library.CustomItemDrawOver
import com.github.library.EasyItemDecoration
import com.whereinow.gankandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity() {

    var dataList: ArrayList<String> = ArrayList()
    var dividerEnable: Boolean? = null
    var dividerOutFrameEnable: Boolean? = null
    var dividerOrientation: Int? = null
    var itemStyle: Boolean? = null
    var dividerColor: Int? = null
    var dividerWidth: Float? = null
    var dividerLeft: Float? = null
    var dividerRight: Float? = null
    var dividerTop: Float? = null
    var dividerBottom: Float? = null
    var type = 0

    init {
        for (i in 0..99)
            dataList.add(i.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar(resources.getString(R.string.app_name), true)
        initDrawer()
        refreshLinearLayout()
    }

    fun refreshSettingData() {
        dividerEnable = prefs.dividerEnable
        dividerOutFrameEnable = prefs.dividerOutFrameEnable
        dividerOrientation = prefs.dividerOrientation
        itemStyle = prefs.itemStyle
        dividerColor = prefs.dividerColor
        dividerWidth = prefs.dividerWidth
        dividerLeft = prefs.dividerLeft
        dividerRight = prefs.dividerRight
        dividerTop = prefs.dividerTop
        dividerBottom = prefs.dividerBottom
    }

    fun getItemDecorationBuilder(): EasyItemDecoration.Builder {
        val builder = EasyItemDecoration.Builder()
                .setDrawDivider(dividerEnable!!)
                .setDividerWidth(dividerWidth!!)
                .setDrawOutFrame(dividerOutFrameEnable!!)
                .setLeft(dividerLeft!!)
                .setRight(dividerRight!!)
                .setTop(dividerTop!!)
                .setBottom(dividerBottom!!)
                .setDividerColor(dividerColor!!)
//                .setDrawStartDivider(true)
//                .setDrawEndDivider(true)
//                .setCustomItemDrawOver(object : CustomItemDrawOver {
//                    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                        val linearGradient = LinearGradient(0f, 0f, 0f, 100f, intArrayOf(Color.WHITE, 0), null,
//                                Shader.TileMode.CLAMP)
//                        val paint = Paint()
//                        paint.shader = linearGradient
//                        c.drawRect(0f, 0f, parent.right.toFloat(), 100f, paint)
//                    }
//                })
        return builder
    }

    fun getItemLayout(): Int {
        val layout = when (dividerOrientation) {
            OrientationHelper.VERTICAL -> if (itemStyle!!) R.layout.item_card_grid_vertical else R.layout.item_flat_grid_vertical
            OrientationHelper.HORIZONTAL -> if (itemStyle!!) R.layout.item_card_grid_horizontal else R.layout.item_flat_grid_horizontal
            else -> if (itemStyle!!) R.layout.item_card_grid_vertical else R.layout.item_flat_grid_vertical
        }
        return layout
    }

    fun refreshLinearLayout() {
        refreshSettingData()
        rvMain.layoutManager = LinearLayoutManager(baseContext, dividerOrientation!!, false)
        rvMain.adapter = NormalAdapter(getItemLayout(), dataList)
        rvMain.addItemDecoration(getItemDecorationBuilder().build())
    }

    fun refreshGridLayout() {
        refreshSettingData()
        rvMain.layoutManager = GridLayoutManager(baseContext, 3, dividerOrientation!!, false)
        rvMain.adapter = NormalAdapter(getItemLayout(), dataList)
        rvMain.addItemDecoration(getItemDecorationBuilder().build())
    }

    fun refreshStaggeredLayout() {
        refreshSettingData()
        rvMain.layoutManager = StaggeredGridLayoutManager(3, dividerOrientation!!)
        rvMain.adapter = NormalAdapter(getItemLayout(), dataList)
        rvMain.addItemDecoration(getItemDecorationBuilder().build())
    }

    fun refreshItemDecoration() {
        rvMain.removeItemDecoration(rvMain.getItemDecorationAt(0))
        when (type) {
            0 -> refreshLinearLayout()
            1 -> refreshGridLayout()
            2 -> refreshStaggeredLayout()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshItemDecoration()
    }

    fun initDrawer() {
        val toggle = ActionBarDrawerToggle(this, dlMain, tbMain,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        dlMain.setDrawerListener(toggle)
        toggle.syncState()

        navContent.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navLinear -> {
                    rvMain.removeItemDecoration(rvMain.getItemDecorationAt(0))
                    refreshLinearLayout()
                    type = 0
                }
                R.id.navGrid -> {
                    rvMain.removeItemDecoration(rvMain.getItemDecorationAt(0))
                    refreshGridLayout()
                    type = 1
                }
                R.id.navStaggered -> {
                    rvMain.removeItemDecoration(rvMain.getItemDecorationAt(0))
                    refreshStaggeredLayout()
                    type = 2
                }
                R.id.navSetting -> SettingActivity.startActivity(this)
            }
            dlMain.closeDrawer(GravityCompat.START)
            true
        }
        navContent.setCheckedItem(R.id.navLinear)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
