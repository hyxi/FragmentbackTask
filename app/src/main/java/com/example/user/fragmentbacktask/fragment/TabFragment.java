package com.example.user.fragmentbacktask.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;

import androidx.fragment.app.Fragment;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class TabFragment extends Fragment
{
	public static final String TITLE = "title";
	private String mTitle = "Defaut Value";
	private ListView mListView;
	// private TextView mTextView;
	private List<String> mDatas = new ArrayList<String>();

	public static TabFragment newInstance(String title) {
		TabFragment tabFragment = new TabFragment();
		Bundle bundle = new Bundle();
		bundle.putString(TITLE, title);
		tabFragment.setArguments(bundle);
		return tabFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mTitle = getArguments().getString(TITLE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab, container, false);
		initView(view);
		initData();
		registerListener();
		return view;
	}

	private void registerListener() {

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getActivity(),"positon+"+position,Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initData() {
		for (int i = 0; i < 50; i++) {
			mDatas.add(mTitle+" -> " + i);
		}
		mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
				R.layout.nav_listview_item, R.id.id_info, mDatas) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				//Log.e("tag", "convertView = " + convertView);
				return super.getView(position, convertView, parent);
			}
		});
	}

	private void initView(View view) {
		mListView = (ListView) view
				.findViewById(R.id.id_stickynavlayout_innerscrollview);
	}


}
