package com.example.user.fragmentbacktask.activity.upgrade;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

public class UpdateManager implements Runnable {
	
	private Handler mFilterHandler;
	private Boolean mIsAutoUpdate;
	private final Object lock = new Object();
	
	public UpdateManager(Handler mFilterHandler) {
		this.mFilterHandler = mFilterHandler;
	}

	public void checkForUpDate() {
		checkForUpDate(true);
	}
	
	public void checkForUpDate(boolean isAutoUpdate) {
		mIsAutoUpdate = isAutoUpdate;	
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			synchronized (lock) {
				Thread.sleep(500);
				Message msg = new Message();
				msg.what = 101;
				msg.obj="版本下载测试";
				mFilterHandler.sendMessage(msg);
//				Map<String, String> pairs = new HashMap<String, String>();
//				pairs.put("messagename", "NewClientUpdate");
//				Upgrade upgrade = HttpApi.getBeanByPullXml(pairs, Upgrade.class);
//				if(upgrade != null) {
//					Message msg = new Message();
//					if(mIsAutoUpdate)
//						msg.what = SoufunConstants.CODE_AUTO_UPDATE;
//					else
//						msg.what = SoufunConstants.CODE_MANUAL_UPDATE;
//					if(upgrade.code.equals("100") && "1".equals(upgrade.isupdate)) {
//						msg.what = SoufunConstants.CODE_FORCE_UPDATE;
//					}
//					msg.obj = upgrade;
//					mFilterHandler.sendMessage(msg);
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
