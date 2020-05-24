package com.example.user.fragmentbacktask.messager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.user.fragmentbacktask.IBookManager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyuxi on 2019-08-28
 * Title:
 */
public class RemoteService extends Service {
    private final List<Book> mBookList;
    private RemoteCallbackList<IRemoteServiceCallback>
            mCallBacks = new RemoteCallbackList<>();

    private IBookManager.Stub mBinder;
    private static final int REPORT_MSG = 1;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REPORT_MSG:
                    final int N = mCallBacks.beginBroadcast();
                    for (int i = 0; i < N; i++) {
                        try {
                            mCallBacks.getBroadcastItem(i).valueChange(i * 10);
                        } catch (RemoteException e) {
                            // The RemoteCallbackList will take care of removing
                            // the dead object for us.
                        }
                    }
                    mCallBacks.finishBroadcast();
                    break;
            }
        }
    };

    public RemoteService() {
        mBookList = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new BookManagerImpl();
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallBacks.kill();

    }

    private class BookManagerImpl extends IBookManager.Stub {
        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBookList) {
                return mBookList;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBookList) {
                if (!mBookList.contains(book)) {
                    mBookList.add(book);
                    mHandler.sendEmptyMessage(REPORT_MSG);
                }
                Logger.d("remoteservice:addBook " + book.getName());
            }
        }

        @Override
        public void registerCallback(IRemoteServiceCallback cb) throws RemoteException {
            if (cb != null) {
                mCallBacks.register(cb);
            }
        }

        @Override
        public void unregisterCallback(IRemoteServiceCallback cb) throws RemoteException {
            if (cb != null) {
                mCallBacks.unregister(cb);
            }
        }

        @Override
        public void linkToDeath(@NonNull DeathRecipient recipient, int flags) {
            super.linkToDeath(recipient, flags);
        }

        @Override
        public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
            return super.unlinkToDeath(recipient, flags);
        }
    }
}
