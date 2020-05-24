package com.example.user.fragmentbacktask.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.fragmentbacktask.R;

/**
 * 自定义Dialog 项目中用必须用此Dialog
 * 
 * @author
 *
 * -------------------------------
 * V7.9.2
 *
 * 修改自定义Dialog样式
 *
 * @author luoxi
 * -------------------------------
 */
public class GeneralDialog extends Dialog {

	public GeneralDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public GeneralDialog(Context context, int theme) {
		super(context, theme);
	}

	public GeneralDialog(Context context) {
		super(context);
	}

	public static class Builder {
		private Context mContext;
		private Integer mIconId;
		private String mTitle;
		private String mMessage;
		private String mPositiveButtonText;
		private String mNegativeButtonText;
		private View mContentView;
		private boolean mCancelable = true;

		private DialogInterface.OnClickListener mPositiveButtonListener, mNegativeButtonListener;

		public void setCancelable(boolean cancelable) {
			mCancelable = cancelable;
		}

		/**
		 * Constructor using a context and theme for this builder and the
		 * {@link AlertDialog} it creates.
		 */
		public Builder(Context context) {
			mContext = context;
		}

		public Builder setTitle(int titleId) {
			mTitle = (String) mContext.getText(titleId);
			return this;
		}

		public Builder setTitle(String title) {
			mTitle = title;
			return this;
		}

		public Builder setMessage(int messageId) {
			mMessage = (String) mContext.getText(messageId);
			return this;
		}

		public Builder setMessage(String message) {
			mMessage = message;
			return this;
		}

		public Builder setIcon(int iconId) {
			mIconId = iconId;
			return this;
		}

//		public Builder setItems(){
//
//		}

		public Builder setContentView(View v) {
			mContentView = v;
			return this;
		}

		public Builder setContentView(int contentViewId) {
			mContentView = LayoutInflater.from(mContext).inflate(contentViewId, null);
			return this;
		}

		public Builder setPositiveButton(int textId, final OnClickListener listener) {
			mPositiveButtonText = (String) mContext.getText(textId);
			mPositiveButtonListener = listener;
			return this;
		}

		public Builder setPositiveButton(String text, final OnClickListener listener) {
			mPositiveButtonText = text;
			mPositiveButtonListener = listener;
			return this;
		}

		public Builder setNegativeButton(int textId, final OnClickListener listener) {
			mNegativeButtonText = (String) mContext.getText(textId);
			mNegativeButtonListener = listener;
			return this;
		}

		public Builder setNegativeButton(String text, final OnClickListener listener) {
			mNegativeButtonText = text;
			mNegativeButtonListener = listener;
			return this;
		}

		public GeneralDialog create() {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final GeneralDialog dialog = new GeneralDialog(mContext, R.style.fullscreen_dialog);
			View layout = inflater.inflate(R.layout.soufun_dialog, null);
			dialog.setContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//			ImageView iv_icon = (ImageView) layout.findViewById(R.id.iv_icon);
//			if (mIconId != null) {
//				iv_icon.setImageResource(mIconId);
//				iv_icon.setVisibility(View.VISIBLE);
//			}
			if (mTitle != null) {
				layout.findViewById(R.id.v_below_title).setVisibility(View.VISIBLE);
				layout.findViewById(R.id.rl_title).setVisibility(View.VISIBLE);
				((TextView) layout.findViewById(R.id.tv_title)).setText(mTitle);
			}

//			ViewGroup bottom = (ViewGroup) layout.findViewById(R.id.ll_viewgroup);
//			View contentView = inflater.inflate(R.layout.dialog_content, null);
//			bottom.addView(contentView);

			if (mPositiveButtonText != null && mNegativeButtonText != null) {
				TextView tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);
				tv_cancel.setText(mPositiveButtonText);
				if (mPositiveButtonListener != null) {
					tv_cancel.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							mPositiveButtonListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
						}
					});
				}
				TextView tv_confirm = (TextView) layout.findViewById(R.id.tv_confirm);
				tv_confirm.setText(mNegativeButtonText);
				if (mNegativeButtonListener != null) {
					tv_confirm.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							mNegativeButtonListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
						}
					});
				}
			} else if (mPositiveButtonText == null && mNegativeButtonText == null) {
				layout.findViewById(R.id.ll_two_button).setVisibility(View.GONE);
				layout.findViewById(R.id.ll_one_button).setVisibility(View.GONE);
			} else {
				layout.findViewById(R.id.ll_two_button).setVisibility(View.GONE);
				layout.findViewById(R.id.ll_one_button).setVisibility(View.VISIBLE);
				TextView tv_one = (TextView) layout.findViewById(R.id.tv_one);
				if (mPositiveButtonText != null) {
					tv_one.setText(mPositiveButtonText);
				} else if (mNegativeButtonText != null) {
					tv_one.setText(mNegativeButtonText);
				}

				if (mPositiveButtonListener != null) {
					tv_one.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							mPositiveButtonListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
						}
					});
				} else if (mNegativeButtonListener != null) {
					tv_one.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							mNegativeButtonListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
						}
					});
				}
			}

			if (mMessage != null) {
				layout.findViewById(R.id.v_below_reminder).setVisibility(View.VISIBLE);
				layout.findViewById(R.id.rl_message).setVisibility(View.VISIBLE);
				((TextView) layout.findViewById(R.id.tv_message)).setText(mMessage);
			} else if (mContentView != null) {
				LinearLayout bodyView = ((LinearLayout) layout.findViewById(R.id.ll_custom));
				bodyView.removeAllViews();
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(0, 10, 0, 0);
				bodyView.addView(mContentView, layoutParams);
			}
			dialog.setContentView(layout);
			dialog.setCancelable(mCancelable);
			return dialog;
		}

		public GeneralDialog show() {
			GeneralDialog dialog = create();
			dialog.show();
			return dialog;
		}

	}
}
