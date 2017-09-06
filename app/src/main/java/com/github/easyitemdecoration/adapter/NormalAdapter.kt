package com.github.easyitemdecoration.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.easyitemdecoration.R
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * Created by JinJc on 2017/8/30.
 */
class NormalAdapter(var layout: Int, var dataList: ArrayList<String>) : RecyclerView.Adapter<NormalAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layout, parent, false)
//        val params = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        itemView.layoutParams = params
        return MainViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val content = dataList[position]
        holder.itemView.tvContent.text = content
    }


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}