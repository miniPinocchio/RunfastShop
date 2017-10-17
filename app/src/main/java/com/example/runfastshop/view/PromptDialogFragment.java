package com.example.runfastshop.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.runfastshop.R;


/**
 * dialogFragment类型的提示框
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/27]
 */
public class PromptDialogFragment extends DialogFragment implements View.OnClickListener {

    private View rootView;

    private TextView tvTitle;

    private TextView tvMessage;

    private TextView tvCancel;

    private TextView tvOK;

    private TextView tvMiddle;

    private View middleLine;

    private OnClickApi cancelClickImpl;

    private OnClickApi okClickImpl;

    private OnClickApi middleClickImpl;

    private CharSequence title;

    private CharSequence message;

    private CharSequence leftText;

    private CharSequence rightText;

    private CharSequence middleText;


    public interface OnClickApi {
        void onClick(DialogFragment dialogFragment, View view);
    }

    public static PromptDialogFragment newInstance(CharSequence title, CharSequence message) {

        PromptDialogFragment dialogFragment = new PromptDialogFragment();
        dialogFragment.title = title;
        dialogFragment.message = message;

        return dialogFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().setCanceledOnTouchOutside(true);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_fragment_prompt, container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onResume();
    }

    /***
     * 初始化控件
     */
    private void initView() {

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);

        tvMessage = (TextView) rootView.findViewById(R.id.tv_message);

        tvCancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);

        tvOK = (TextView) rootView.findViewById(R.id.tv_ok);
        tvOK.setOnClickListener(this);

        tvMiddle = (TextView) rootView.findViewById(R.id.tv_middle);
        tvMiddle.setOnClickListener(this);

        middleLine = rootView.findViewById(R.id.middle_line);

    }

    /***
     * 初始化数据
     */
    private void initData() {

        if (title != null && tvTitle != null) {
            tvTitle.setText(title);
        }

        if (message != null && tvMessage != null) {
            tvMessage.setText(message);
        }

        if (leftText != null && tvCancel != null) {
            tvCancel.setText(leftText);
        }

        if (rightText != null && tvOK != null) {
            tvOK.setText(rightText);
        }

        if (middleText != null && tvMiddle != null) {
            tvMiddle.setText(middleText);
        }

    }

    public void setTitle(CharSequence title) {
        if (TextUtils.equals(this.title, title)) {
            return;
        }
        this.title = title;
        if (title != null && tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setMessage(CharSequence message) {
        if (TextUtils.equals(this.message, message)) {
            return;
        }
        this.message = message;
        if (message != null && tvMessage != null) {
            tvMessage.setText(message);
        }
    }

    /**
     * 设置左边按钮的点击事件
     *
     * @param clickImpl
     */
    public void setLeftButton(OnClickApi clickImpl) {
        setLeftButton(null, clickImpl);
    }

    /**
     * 设置左边按钮的文字
     *
     * @param text
     */
    public void setLeftButton(CharSequence text) {
        setLeftButton(text, null);
    }

    /**
     * 设置左边按钮
     *
     * @param text
     * @param clickImpl
     */
    public void setLeftButton(CharSequence text, OnClickApi clickImpl) {
        this.leftText = text;
        if (text != null && tvCancel != null) {
            tvCancel.setText(text);
        }
        this.cancelClickImpl = clickImpl;
    }

    /**
     * 设置右面按钮的点击事件
     *
     * @param clickImpl
     */
    public void setRightButton(OnClickApi clickImpl) {
        setRightButton(null, clickImpl);
    }

    /***
     * 设置右面按钮的显示文字
     *
     * @param text
     */
    public void setRightButton(CharSequence text) {
        setRightButton(text, null);
    }

    /***
     * 设置右边的按钮
     *
     * @param text
     * @param clickImpl
     */
    public void setRightButton(CharSequence text, OnClickApi clickImpl) {
        this.rightText = text;
        if (text != null && tvOK != null) {
            tvOK.setText(text);
        }
        this.okClickImpl = clickImpl;
    }

    /**
     * 显示中间的按钮
     *
     * @param text
     * @param clickImpl
     */
    public void showMiddleButton(CharSequence text, OnClickApi clickImpl) {
        this.middleText = text;
        if (text != null && tvMiddle != null) {
            tvMiddle.setText(text);
            this.middleClickImpl = clickImpl;
            tvMiddle.setVisibility(View.VISIBLE);
            middleLine.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancel:
                if (cancelClickImpl != null) {
                    cancelClickImpl.onClick(this, v);
                }
                dismiss();
                break;
            case R.id.tv_ok:
                if (okClickImpl != null) {
                    okClickImpl.onClick(this, v);
                }
                dismiss();
                break;
            case R.id.tv_middle:
                if (middleClickImpl != null) {
                    middleClickImpl.onClick(this, v);
                }
                dismiss();
                break;
            default:

                break;
        }

    }
}
