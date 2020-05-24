package com.example.user.fragmentbacktask.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.user.fragmentbacktask.utils.StringUtils;

public class ScrollViewContainer extends RelativeLayout {

	public static final int AUTO_UP = 0;
	public static final int AUTO_DOWN = 1;
	public static final int DONE = 2;
	public static final float SPEED = 8.5f;

	private boolean isMeasured = false;
	// 用于计算手滑动的速度
	private VelocityTracker vt;

	private int mViewHeight;
	private int mViewWidth;

	private ScrollView topView;
	private ScrollView bottomView;
	private View centerTitle;

	private boolean canPullDowm;
	private boolean canPullUp;
	private int state = DONE;

	// 记录当前展示的是哪个View, 0是topView, 1是bottomView
	private int mCurrentViewIndex = 0;

	// 手滑动距离，这个是控制布局的主要变量
	private float mMoveLen;
	private MyTimer mTimer;
	private float mLastY;
	private float headerHeight;
	// private long lastTime;
	// 用于控制是否变动布局的另一个条件，mEvents==0时布局可以拖拽了
	// mEvents==-1时可以舍弃讲要到来的一个move事件，
	// 这点是去除多点拖动剧变的关键
	private int mEvents;

	private OnViewDragListener onViewDragListener;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mMoveLen != 0 || mMoveLen != -mViewHeight + headerHeight) {
				if (state == AUTO_UP) {
					mMoveLen -= SPEED;// 手指up之后，每2ms上滑8.5个像素
					if (mMoveLen <= -mViewHeight + headerHeight) {// 上滑到顶部
						mMoveLen = -mViewHeight + headerHeight;
						state = DONE;
						mCurrentViewIndex = 1;
						if (null != onViewDragListener) {
							onViewDragListener.drag();
						}
					}
				} else if (state == AUTO_DOWN) {
					mMoveLen += SPEED;
					if (mMoveLen >= 0) {
						mMoveLen = 0;
						state = DONE;
						mCurrentViewIndex = 0;
					}
				} else {
					mTimer.cencel();
				}
				requestLayout();// 移动top center bottom view的位置
			}
		}
	};

	public ScrollViewContainer(Context context) {
		this(context, null);
	}

	public ScrollViewContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollViewContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		mTimer = new MyTimer(handler);
		headerHeight = StringUtils.dp2px(50);
	}

	class MyTimer {
		private Handler handler;
		private Timer timer;
		private MyTask mTask;

		public MyTimer(Handler handler) {
			this.handler = handler;
			timer = new Timer();
		}

		public void schedule(long period) {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
			mTask = new MyTask(handler);
			timer.schedule(mTask, 0, period);
		}

		public void cencel() {
			if (mTask != null) {
				mTask.cancel();
				mTask = null;
			}
		}
	}

	class MyTask extends TimerTask {

		private Handler handler;

		public MyTask(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			handler.obtainMessage().sendToTarget();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!isMeasured) {
			isMeasured = true;
			mViewHeight = getMeasuredHeight();
			mViewWidth = getMeasuredWidth();
			topView = (ScrollView) getChildAt(0);
			centerTitle = getChildAt(1);
			bottomView = (ScrollView) getChildAt(2);
			topView.setOnTouchListener(topViewTouchListener);
			bottomView.setOnTouchListener(bottomViewTouchListener);
		}
	}

	private OnTouchListener topViewTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			checkState();
			return false;
		}
	};

	private void checkState() {
		if (topView.getScrollY() >= topView.getChildAt(0).getMeasuredHeight()
				- topView.getMeasuredHeight()
				&& mCurrentViewIndex == 0) {
			canPullUp = true;
		} else {
			canPullUp = false;
		}
		if (bottomView.getScrollY() <= 0 && mCurrentViewIndex == 1) {
			canPullDowm = true;
		} else {
			canPullDowm = false;
		}
	}

	private OnTouchListener bottomViewTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			checkState();
			return false;
		}
	};

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int cbottom = topView.getMeasuredHeight() + (int) mMoveLen
				+ centerTitle.getMeasuredHeight();
		if (cbottom <= centerTitle.getMeasuredHeight()) {
			cbottom = centerTitle.getMeasuredHeight();
		}
		int ctop = topView.getMeasuredHeight() + (int) mMoveLen;
		if (ctop <= 0) {
			ctop = 0;
		}
		topView.layout(0, (int) mMoveLen, mViewWidth, ctop);
		centerTitle.layout(0, ctop, mViewWidth, cbottom);
		bottomView.layout(0, cbottom, mViewWidth, bottomView.getMeasuredHeight());
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			checkState();
			if (vt == null) {
				vt = VelocityTracker.obtain();
			} else {
				vt.clear();
			}
			mLastY = ev.getY();
			vt.addMovement(ev);
			mEvents = 0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
			mEvents = -1;
			break;
		case MotionEvent.ACTION_MOVE:
			vt.addMovement(ev);
			if (canPullUp && mCurrentViewIndex == 0 && mEvents == 0) {
				mMoveLen += (ev.getY() - mLastY);
				if (mMoveLen > 0) {
					mMoveLen = 0;
					mCurrentViewIndex = 0;// 目前视图到top
				} else if (mMoveLen < -mViewHeight + headerHeight) {
					mMoveLen = -mViewHeight + headerHeight;
					mCurrentViewIndex = 1;// 目前视图到bottom
				}
				if (mMoveLen < -8) {
					ev.setAction(MotionEvent.ACTION_CANCEL);
				}
				requestLayout();
			} else if (canPullDowm && mCurrentViewIndex == 1 && mEvents == 0) {
				mMoveLen += (ev.getY() - mLastY);
				if (mMoveLen < -mViewHeight + headerHeight) {
					mMoveLen = -mViewHeight + headerHeight;
					mCurrentViewIndex = 1;
				} else if (mMoveLen > 0) {
					mMoveLen = 0;
					mCurrentViewIndex = 0;
				}
				if (mMoveLen > 8 - mViewHeight + headerHeight) {
					ev.setAction(MotionEvent.ACTION_CANCEL);
				}
				requestLayout();
			} else {
				mEvents++;
			}
			mLastY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			mLastY = ev.getY();
			vt.addMovement(ev);
			vt.computeCurrentVelocity(700);
			float mYV = vt.getYVelocity();
			if (mMoveLen == 0 || mMoveLen == -mViewHeight + headerHeight) {
				break;
			}

			if (Math.abs(mYV) < 500) {// 速度慢的时候，根据手指滑动的距离指定state
				if (mMoveLen <= -mViewHeight / 2) {
					state = AUTO_UP;
				} else if (mMoveLen > -mViewHeight / 2) {
					state = AUTO_DOWN;
				}
			} else {// 速度快的时候，根据速度来指定state
				if (mYV < 0) {
					state = AUTO_UP;
				} else {
					state = AUTO_DOWN;
				}
			}
			mTimer.schedule(2);// 根据state移动view
			try {
				vt.recycle();
			} catch (Exception e) {
				e.printStackTrace();
			}
			vt = null;
			break;
		}
		try {
			super.dispatchTouchEvent(ev);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	public interface OnViewDragListener {
		void drag();
	}

	public void setOnViewDragListener(OnViewDragListener onViewDragListener) {
		this.onViewDragListener = onViewDragListener;
	}

}
