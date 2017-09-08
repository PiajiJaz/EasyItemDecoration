package com.github.easyitemdecoration.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.easyitemdecoration.R;
import com.github.easyitemdecoration.adapter.JavaAdapter;
import com.github.library.EasyItemDecoration;
import com.whereinow.gankandroid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Demo
 * Created by JinJc on 2017/9/8.
 */

public class JavaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(i + "");
        }

        RecyclerView rvMain = (RecyclerView) findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.setAdapter(new JavaAdapter(dataList));
        EasyItemDecoration.Builder builder = new EasyItemDecoration.Builder();
        EasyItemDecoration itemDecoration = builder.setDrawDivider(true)
                .setDividerWidth(0.5f)
                .setRight(10f)
                .setLeft(10f)
                .setDividerColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
                .build();
        rvMain.addItemDecoration(itemDecoration);
    }
}
