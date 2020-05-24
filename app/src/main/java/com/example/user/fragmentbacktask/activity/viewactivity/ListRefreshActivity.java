package com.example.user.fragmentbacktask.activity.viewactivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.adapter.ListRefreshAdapter;
import com.example.user.fragmentbacktask.db.DbUtils;
import com.example.user.fragmentbacktask.entity.ListInfo;
import com.example.user.fragmentbacktask.entity.MoiveBean;
import com.example.user.fragmentbacktask.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListRefreshActivity extends Activity {

    private PullToRefreshListView lv_pull_refresh;

    private Context mContext;
    private  ListRefreshAdapter adapter;
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_refresh);
        mContext = this;
        initView();
        initData();
        registerListener();
    }

    private void initView() {
        lv_pull_refresh = (PullToRefreshListView) findViewById(R.id.lv_pull_refresh);
        lv_pull_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initData() {
        adapter = new ListRefreshAdapter(mContext);
        lv_pull_refresh.setAdapter(adapter);
        new GetListDataTask().execute();

    }

    class GetListDataTask extends AsyncTask<Void,Void,ArrayList<MoiveBean>>{
        @Override
        protected ArrayList<MoiveBean> doInBackground(Void... params) {
            String sql = "select imgurl,title,content from moive;";
            try{
                ArrayList<MoiveBean> list = DbUtils.getList(sql,MoiveBean.class,getApplicationContext());
                Thread.sleep(600);
                return list;
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MoiveBean> result) {
            super.onPostExecute(result);
            if(result != null){
                lv_pull_refresh.onRefreshComplete();

                String adurl1 = "http://js.soufunimg.com/jinrong/dai/wap/Images/zhuanti/ZiJinTuoGuan.jpg?v=1.0";
                String adurl2 = "http://js.soufunimg.com/jinrong/dai/wap/Images/zhuanti/wkdz_banner.jpg?v=1.0";
                final ArrayList<String> list = new ArrayList<>(Arrays.asList(adurl1, adurl2));

                ListInfo info = new ListInfo();
                info.setAdlist(list);
                info.setDataList(result);
                adapter.resetData(info);


                View footerView =  LayoutInflater.from(mContext).inflate(R.layout.layout_whole_comment,null);
                lv_pull_refresh.addFooterView(footerView);

                TextView tv_more = (TextView)footerView.findViewById(R.id.tv_more);
                tv_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("button","onclick tv_more");
                    }
                });
            }
        }
    }

    private void registerListener() {
        lv_pull_refresh.setOnScrollListener(scollListener);

        //设置刷新事件
        lv_pull_refresh.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetListDataTask().execute();
            }
        });
    }


    AbsListView.OnScrollListener scollListener = new AbsListView.OnScrollListener() {
        boolean touchState = false;
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
             if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                     &&!isLoading                                        //加载完成
                     && touchState){
                 Log.i("tag","load");
             }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            touchState = visibleItemCount + firstVisibleItem >= totalItemCount;
            if(firstVisibleItem>1){
                adapter.setAdAutoScroll(false);
            }else{
                adapter.setAdAutoScroll(true);
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        adapter.setAdAutoScroll(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(lv_pull_refresh.getFirstVisiblePosition()<1){
            adapter.setAdAutoScroll(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.setAdAutoScroll(false);
    }
}
