package com.example.user.fragmentbacktask.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.RecycleAdapter;
import com.example.user.fragmentbacktask.view.SwitchButton;
import com.example.user.fragmentbacktask.view.recyclecutomview.DividerItemDecoration;
import com.example.user.fragmentbacktask.view.recyclecutomview.OnItemRecListener;

import java.util.ArrayList;

public class RecycleViewActivity extends Activity implements View.OnClickListener {

    private ImageView iv_insert_data,iv_delete,iv_refresh;
    private RecyclerView rv_test_list;
    private ArrayList<String> mDatas;
    private RecycleAdapter adapter;
    private SwitchButton switch_btn;
    private TextView tv_swibtn_value;
    private Context mContext;

    private int lastVisibleItem ,firstVisibleItem,totaVisibleItem;
    private boolean touchstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        initViews();
        initData();
        registerListener();
    }

    private void registerListener() {
        adapter.setOnItemRecClickListener(new OnItemRecListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecycleViewActivity.this,mDatas.get(position)+"//"+position,Toast.LENGTH_SHORT).show();
            }
        });

        switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_swibtn_value.setText("true");
                }else{
                    tv_swibtn_value.setText("false");
                }
            }
        });

    }

    private void initViews() {
        iv_insert_data = (ImageView) findViewById(R.id.iv_insert_data);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        switch_btn = (SwitchButton) findViewById(R.id.switch_btn);
        tv_swibtn_value = (TextView) findViewById(R.id.tv_swibtn_value);

        switch_btn.setThumbSize(60,45);

        iv_insert_data.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        iv_refresh.setOnClickListener(this);
        mContext = this;
        rv_test_list = (RecyclerView) findViewById(R.id.rv_test_list);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for(int i =0;i<100;i++){
            mDatas.add("content data"+ i);
        }

        adapter = new RecycleAdapter(this);
        adapter.addList(mDatas);
        final LinearLayoutManager manager= new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rv_test_list.setLayoutManager(manager);
        //判断是否滑动到尾部或顶部
        //viewholder 承载item视图的子视图  ItemAnimator：负责添加、删除数据时的动画效果
        rv_test_list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rv_test_list.setAdapter(adapter);
        rv_test_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && touchstate) {
                  Toast.makeText(mContext,"load",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                boolean flag = manager.canScrollVertically();
                lastVisibleItem = manager.findLastVisibleItemPosition();
                firstVisibleItem = manager.findFirstVisibleItemPosition();
                totaVisibleItem = adapter.getItemCount();
                touchstate = false;
                if (lastVisibleItem + 1 >= totaVisibleItem) {
                    touchstate = true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_insert_data:
                addData(2);
                break;
            case R.id.iv_delete:
                break;
            case R.id.iv_refresh:
                break;
        }
    }

    private void addData(int pos){
        mDatas.add(pos, "insert One");
        adapter.notifyItemInserted(pos);
        // 加入如下代码保证position的位置正确性
        if (pos != mDatas.size() - 1) {
            adapter.notifyItemRangeChanged(pos, mDatas.size() - pos);
        }
    }

    private void deleteData(int pos){
        mDatas.remove(pos);
        adapter.notifyItemRemoved(pos);
        if(pos != mDatas.size() -1){
            adapter.notifyItemRangeChanged(pos, mDatas.size() - pos);
        }
    }


}
