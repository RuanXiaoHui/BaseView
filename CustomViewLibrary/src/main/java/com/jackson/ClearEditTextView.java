package com.jackson;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.jackson.customview.R;


/**
 * Created by Jackson on 2018/2/23.
 */

public class ClearEditTextView extends AppCompatEditText implements TextWatcher {

    private Drawable delIcon;

    private boolean hasFocus;

    public ClearEditTextView(Context context) {
        super(context);
        init();
    }

    public ClearEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        delIcon = getCompoundDrawables()[2];
        if (delIcon == null) {
            delIcon = getResources().getDrawable(R.drawable.icon_deltext);
        }
        delIcon.setBounds(0, 0, delIcon.getIntrinsicWidth(),
                delIcon.getIntrinsicHeight());

        //默认设置隐藏图标
        setClearIconVisible();
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    private void setClearIconVisible() {
        Drawable right = length() > 0 && hasFocus ? delIcon : null;
        setCompoundDrawables(getCompoundDrawables()[0], null, right, null);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        setClearIconVisible();
    }

    /**
     * 焦点变更监听
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        this.hasFocus = focused;
        setClearIconVisible();
    }

    /**
     * 当手指抬起的位置在clean的图标的区域
     * 我们将此视为进行清除操作
     * getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离
     * 于是:
     * getWidth() - getTotalPaddingRight()表示:
     * 控件左边到clean的图标左边缘的区域
     * getWidth() - getPaddingRight()表示:
     * 控件左边到clean的图标右边缘的区域
     * 所以这两者之间的区域刚好是clean的图标的区域
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (delIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() > getWidth() - delIcon.getIntrinsicWidth() - getPaddingRight() &&
                    event.getX() < getWidth() - getPaddingRight()) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }


    /***
     * 释放资源
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        delIcon.setCallback(null);
    }
}
