package com.jackson;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by Jackson on 2018/1/16.
 * 注意点：
 * 1.如果设置DrawableRight  那么偏移要向左偏移，也就是应该是负数
 * 2.设置如果设置DrawableRight，此时我们要注意一件事，就是setGravity（），当我们什么都没设置的
 * 时候，他是默认在start|top上
 */

public class DrawCenterTextView extends AppCompatTextView {

    public DrawCenterTextView(Context context) {
        super(context);
    }

    public DrawCenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawCenterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        //LEFT
        Drawable drawLeft = drawables[0];
        if (drawLeft != null) {
            setGravity(Gravity.START | Gravity.CENTER);
            int drawWidth = drawLeft.getIntrinsicWidth();
            float textWidth = getPaint().measureText(getText().toString());
            int paddingWidth = getCompoundDrawablePadding();
            float togetherWidth = drawWidth + textWidth + paddingWidth;
            canvas.translate((getWidth() - togetherWidth) / 2.0f, 0);
        }

        //TOP
        Drawable drawableTop = drawables[1];
        if (drawableTop != null) {
            setGravity(Gravity.CENTER | Gravity.TOP);
            Rect rect = new Rect();
            getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
            float textHeight = rect.height();
            int DrawableWidth = drawableTop.getIntrinsicHeight();
            int PaddingHeight = getCompoundDrawablePadding();
            float bodyHeight = DrawableWidth + textHeight + PaddingHeight;
            canvas.translate(0, (getHeight() - bodyHeight) / 2);
        }
        //RIGHT
        Drawable drawRight = drawables[2];
        if (drawRight != null) {
            setGravity(Gravity.END | Gravity.CENTER);
            int drawWidth = drawRight.getIntrinsicWidth();
            int paddingWidth = getCompoundDrawablePadding();
            float textWidth = getPaint().measureText(getText().toString());
            float togetherWidth = drawWidth + textWidth + paddingWidth;
            canvas.translate(-(getWidth() - togetherWidth) / 2.0f, 0);
        }
        //BOTTOM
        Drawable drawableBottom = drawables[3];
        if (drawableBottom != null) {
            setGravity(Gravity.CENTER | Gravity.BOTTOM);
            Rect rect = new Rect();
            getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
            float textHeight = rect.height();
            int DrawableWidth = drawableBottom.getIntrinsicHeight();
            int PaddingHeight = getCompoundDrawablePadding();
            float bodyHeight = DrawableWidth + textHeight + PaddingHeight;
            canvas.translate(0, -(getHeight() - bodyHeight) / 2);
        }

        super.onDraw(canvas);
    }
}
