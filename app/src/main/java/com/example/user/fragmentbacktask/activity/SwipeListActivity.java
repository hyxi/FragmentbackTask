package com.example.user.fragmentbacktask.activity;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwipeListActivity extends AppCompatActivity {

    SwipeMenuListView lv_swipe_menu;
    List<String> simulateData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list);
        lv_swipe_menu = (SwipeMenuListView) findViewById(R.id.lv_swipe_menu);
        initData();
        setListener();
    }

    private void initData() {
        String[] datas = {"American Museum of Natural History", "Apollo Theater", "Bank of America Tower",
                "Battery Park", "Rose Center forEarth Monument", "Castle Clinton", "The Sphere", "Brill Building",
                "East Coast Memorial", "Fadingdemo", "Battery Park", "Rose Center forEarth Monument", "Castle Clinton", "The Sphere",
                "Brill Building"};
        simulateData = new ArrayList<>(Arrays.asList(datas));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, simulateData);
        lv_swipe_menu.setAdapter(adapter);
    }

    private void setListener() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(StringUtils.dp2px(90));
                // set item title
                openItem.setTitle("Open");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(StringUtils.dp2px(90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        lv_swipe_menu.setMenuCreator(creator);
        lv_swipe_menu.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        Log.i("tag","open "+position);
                        break;
                    case 1:
                        Log.i("tag","delete "+position);
                        break;
                }

                return false;
            }
        });

    }
}
