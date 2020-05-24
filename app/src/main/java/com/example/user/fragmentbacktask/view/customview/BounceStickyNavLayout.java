package com.example.user.fragmentbacktask.view.customview;

import android.content.Context;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.example.user.fragmentbacktask.R;

public class BounceStickyNavLayout extends LinearLayout {

	private View mTop;
	private View mNav;
	private ViewPager mViewPager;

	private int mTopViewHeight;
	private ViewGroup mInnerScrollView;
	private boolean isTopHidden = false;

	private OverScroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mTouchSlop;
	private int mMaximumVelocity, mMinimumVelocity;

	private float mLastY;
	private boolean mDragging;

	private boolean isInControl = false;

	public BounceStickyNavLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);

		mScroller = new OverScroller(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mMaximumVelocity = ViewConfiguration.get(context)
				.getScaledMaximumFlingVelocity();
		mMinimumVelocity = ViewConfiguration.get(context)
				.getScaledMinimumFlingVelocity();

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		Log.i("navLayout","onFinishInflate");
		mTop = getChildAt(0);
		mNav = getChildAt(1);
		View view = getChildAt(2);
		if (!(view instanceof ViewPager)) {
			throw new RuntimeException(
					"id_stickynavlayout_viewpager show used by ViewPager !");
		}
		mViewPager = (ViewPager) view;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
		params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
		Log.i("navLayout","onMeasure");
		Log.i("navLayout","height"+getMeasuredHeight() +"center://"+mNav.getMeasuredHeight());

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTopViewHeight = mTop.getMeasuredHeight();
		Log.i("navLayout","onsizeChange");
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		float y = ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i("navLayout","dispatchTouchEvent ACTION_DOWN");
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dy = y - mLastY;
			getCurrentScrollView();
			Log.i("navLayout","dispatchTouchEvent ACTION_MOVE");
			if (mInnerScrollView instanceof ScrollView) {
				if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0
						&& !isInControl) {
					isInControl = true;
					ev.setAction(MotionEvent.ACTION_CANCEL);
					MotionEvent ev2 = MotionEvent.obtain(ev);
					dispatchTouchEvent(ev);
					ev2.setAction(MotionEvent.ACTION_DOWN);
					return dispatchTouchEvent(ev2);
				}
			} else if (mInnerScrollView instanceof ListView) {

				ListView lv = (ListView) mInnerScrollView;
				View c = lv.getChildAt(lv.getFirstVisiblePosition());
				if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
						&& dy > 0) {
					isInControl = true;
					ev.setAction(MotionEvent.ACTION_CANCEL);
					MotionEvent ev2 = MotionEvent.obtain(ev);
					dispatchTouchEvent(ev);
					ev2.setAction(MotionEvent.ACTION_DOWN);
					return dispatchTouchEvent(ev2);
				}
			} else if (mInnerScrollView instanceof RecyclerView) {

				RecyclerView rv = (RecyclerView) mInnerScrollView;

				if (!isInControl && ViewCompat.canScrollVertically(rv, -1) && isTopHidden
						&& dy > 0) {
					isInControl = true;
					ev.setAction(MotionEvent.ACTION_CANCEL);
					MotionEvent ev2 = MotionEvent.obtain(ev);
					dispatchTouchEvent(ev);
					ev2.setAction(MotionEvent.ACTION_DOWN);
					return dispatchTouchEvent(ev2);
				}
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 执行次数有限制
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i("navLayout","onInterceptTouchEvent ACTION_DOWN");
			mLastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("navLayout","onInterceptTouchEvent ACTION_MOVE");
			float dy = y - mLastY;
			getCurrentScrollView();
			if (Math.abs(dy) > mTouchSlop) {
				mDragging = true;
				if (mInnerScrollView instanceof ScrollView) {
					// 如果topView没有隐藏 拦截
					// 或sc的scrollY = 0 && topView隐藏 && 下拉，则拦截
					if (!isTopHidden
							|| (mInnerScrollView.getScrollY() == 0
									&& isTopHidden && dy > 0)) {
						initVelocityTrackerIfNotExists();
						mVelocityTracker.addMovement(ev);
						mLastY = y;
						return true;
					}
				} else if (mInnerScrollView instanceof ListView) {

					ListView lv = (ListView) mInnerScrollView;
					View c = lv.getChildAt(lv.getFirstVisiblePosition());
					// 如果topView没有隐藏
					// 或sc的listView在顶部 && topView隐藏 && 下拉，则拦截

					if (!isTopHidden || //
							(c != null && c.getTop() == 0
									&& dy > 0)) {
						initVelocityTrackerIfNotExists();
						mVelocityTracker.addMovement(ev);
						mLastY = y;
						return true;
					}
				} else if (mInnerScrollView instanceof RecyclerView) {
					RecyclerView rv = (RecyclerView) mInnerScrollView;
					if (!isTopHidden || (!ViewCompat.canScrollVertically(rv, -1) && isTopHidden && dy > 0)) {
						initVelocityTrackerIfNotExists();
						mVelocityTracker.addMovement(ev);
						mLastY = y;
						return true;
					}
				}

			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			Log.i("navLayout","onInterceptTouchEvent ACTION_UP");
			mDragging = false;
			recycleVelocityTracker();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	private void getCurrentScrollView() {

		int currentItem = mViewPager.getCurrentItem();
		PagerAdapter adapter = mViewPager.getAdapter();
		if (adapter instanceof FragmentPagerAdapter) {
			FragmentPagerAdapter fadapter = (FragmentPagerAdapter) adapter;
			Fragment item = (Fragment) fadapter.instantiateItem(mViewPager,
					currentItem);
			View view = item.getView();
			if(view != null) {
				mInnerScrollView = (ViewGroup) (view.findViewById(R.id.id_stickynavlayout_innerscrollview));
			}
		} else if (adapter instanceof FragmentStatePagerAdapter) {
			FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) adapter;
			Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
					currentItem);
			View view = item.getView();
			if(view != null) {
				mInnerScrollView = (ViewGroup) (view.findViewById(R.id.id_stickynavlayout_innerscrollview));
			}
		}

	}
    int moveDis = 0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		initVelocityTrackerIfNotExists();
		mVelocityTracker.addMovement(event);
		int action = event.getAction();
		float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i("navLayout","onTouchEvent ACTION_DOWN");
			if (!mScroller.isFinished())
				mScroller.abortAnimation();
			mLastY = y;
			return true;
		case MotionEvent.ACTION_MOVE:
			Log.i("navLayout","onTouchEvent ACTION_MOVE");
			float dy = y - mLastY;
            moveDis += -(int) dy;
			Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);

			if (!mDragging && Math.abs(dy) > mTouchSlop) {
				mDragging = true;
			}
			if (mDragging) {
				scrollBy(0,  -(int) dy);

				// 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
				if (getScrollY() == mTopViewHeight && dy < 0) {
					event.setAction(MotionEvent.ACTION_DOWN);
					dispatchTouchEvent(event);
					isInControl = false;
				}
			}

			mLastY = y;
			break;
		case MotionEvent.ACTION_CANCEL:
			mDragging = false;
			recycleVelocityTracker();
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i("navLayout","onTouchEvent ACTION_UP");
			mDragging = false;
			if(!isTopHidden){ //顶部view滑动反弹效果
				if (2 * getScrollY() < mTopViewHeight) {
					scrollTo(0, 0);
				} else {
					if (getScrollY() != 0)
						scrollTo(0, mTopViewHeight);
					Log.i("topheight",mTopViewHeight+"");
				}
				moveDis = 0;
			}
			mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
			int velocityY = (int) mVelocityTracker.getYVelocity();
			if (Math.abs(velocityY) > mMinimumVelocity) {
				fling(-velocityY);
			}
			recycleVelocityTracker();
			break;
		}
		return super.onTouchEvent(event);
	}

	public void fling(int velocityY) {
		mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
		invalidate();
	}


	@Override
	public void scrollTo(int x, int y) {
		Log.i("navLayout","scrollTo");
		if (y < 0) {
			y = 0;
		}
		if (y > mTopViewHeight) {
			y = mTopViewHeight;
		}
		if (y != getScrollY()) {
			super.scrollTo(x, y);
		}
		isTopHidden = getScrollY() == mTopViewHeight;
	}

	@Override
	public void computeScroll() {
		Log.i("navLayout","computeScroll");
		// 先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {
			// 这里调用View的scrollTo()完成实际的滚动
			scrollTo(0, mScroller.getCurrY());
			invalidate();
		}
	}

	private void initVelocityTrackerIfNotExists() {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
	}

	private void recycleVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}
}
