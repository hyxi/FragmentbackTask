package com.example.user.fragmentbacktask.view;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.fragment.BaseDialogFragment;
import com.example.user.fragmentbacktask.utils.StringUtils;
import com.example.user.fragmentbacktask.utils.Utils;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class GeneralEditDialog extends BaseDialogFragment implements View.OnClickListener {
    private GeneralEditDialog.Builder builder;

    private EditText et_content;
    private Button tv_cancel;
    private Button tv_confirm;
    private EditCallback mCallBack;

    public static GeneralEditDialog instance(GeneralEditDialog.Builder builder) {
        GeneralEditDialog bottomInfoDialog = new GeneralEditDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("builder", builder);
        bottomInfoDialog.setArguments(bundle);
        return bottomInfoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = getArguments().getParcelable("builder");
        isCenter = true;
    }

    public void setCallBack(EditCallback mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = StringUtils.dp2px(300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow()
                .setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (builder == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.dialog_edit_layout, container, false);
        et_content = view.findViewById(R.id.et_content);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_title = view.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(builder.mTitle)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(builder.mTitle);
        }
        if (!TextUtils.isEmpty(builder.mHint)) {
            et_content.setHint(builder.mHint);
        }
        if (!TextUtils.isEmpty(builder.mContent)) {
            et_content.setText(builder.mContent);
        }
        initListener();
        et_content.post(() -> {
            Utils.showSoftKeyBroad(getContext(), et_content);
        });
        return view;
    }

    private void initListener() {
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 30) {
                    Toast.makeText(getContext(), "收藏夹名字最多为30个字符", Toast.LENGTH_SHORT).show();
                    CharSequence name = s.subSequence(0, 29);
                    et_content.setText(name);
                    et_content.setSelection(name.length());
                }
            }
        });
        tv_confirm.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                String contentString = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(contentString)) {
                    Toast.makeText(getContext(), "收藏夹名字不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mCallBack != null) {
                    mCallBack.confirmEdit(contentString);
                }
                break;
            case R.id.tv_cancel:
                dismissAllowingStateLoss();
                break;
        }
    }

    public void showDialog(FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .add(this, GeneralEditDialog.class.getName())
                .commitAllowingStateLoss();
    }

    public interface EditCallback {
        void confirmEdit(String content);
    }

    public static class Builder implements Parcelable {

        private String mTitle;
        private String mContent;
        private String mHint;

        public Builder() {
        }

        protected Builder(Parcel in) {
            mTitle = in.readString();
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            this.mContent = content;
            return this;
        }

        public Builder setHint(String hint) {
            this.mHint = hint;
            return this;
        }


        public static final Creator<Builder> CREATOR = new Creator<Builder>() {
            @Override
            public Builder createFromParcel(Parcel in) {
                return new Builder(in);
            }

            @Override
            public Builder[] newArray(int size) {
                return new Builder[size];
            }
        };

        public GeneralEditDialog build() {
            return GeneralEditDialog.instance(this);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mTitle);
            dest.writeString(mContent);
            dest.writeString(mHint);
        }
    }
}