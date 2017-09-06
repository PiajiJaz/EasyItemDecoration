package com.whereinow.gankandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.github.easyitemdecoration.R
import kotlinx.android.synthetic.main.app_bar_main.view.*

/**
 * Created by JinJc on 2017/8/17.
 */
open class BaseActivity : AppCompatActivity() {

    lateinit var tbMain: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun initToolbar(title: String?, showBack: Boolean) {
        tbMain = findViewById(R.id.tbMain) as Toolbar
        if (tbMain.tvTitle != null) {
            if (null == title || title.isEmpty())
                tbMain.tvTitle.text = ""
            else
                tbMain.tvTitle.text = title
        }
        setSupportActionBar(tbMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
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

}