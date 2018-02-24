package com.jackson;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.jackson.customview.R;

/**
 * Created by Jackson on 2018/2/19.
 * 自定义圆形头像
 */

public class CircleImageView extends AppCompatImageView {

    private int mCircleBorderWidth = 5;
    private int mCircleBorderColor = Color.WHITE;
    private int mViewWidth = 100;
    private int mViewHeight = 100;
    private int mMinWidth = 60;
    private Bitmap bitmap;
    private Paint bgPaint;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            mCircleBorderColor = array.getColor(R.styleable.CircleImageView_CircleBorderColor, mCircleBorderColor);
            mCircleBorderWidth = (int) array.getDimension(R.styleable.CircleImageView_CirCleBorderWidth, mCircleBorderWidth);
            array.recycle();
        }
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(mCircleBorderColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        loadImageView();
        if (bitmap != null) {
            int min = Math.min(mViewWidth, mViewHeight);
            int circleCenter = min / 2;

            bitmap = Bitmap.createScaledBitmap(bitmap, min, min, false);

            canvas.drawCircle(circleCenter + mCircleBorderWidth,
                    circleCenter + mCircleBorderWidth,
                    circleCenter + mCircleBorderWidth,
                    bgPaint);

            canvas.drawBitmap(createCircleImageView(bitmap, min),
                    mCircleBorderWidth,
                    mCircleBorderWidth,
                    null);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        mViewWidth = width - (mCircleBorderWidth * 2);
        mViewHeight = height - (mCircleBorderWidth * 2);

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Math.min(getSuggestedMinimumWidth(), size);
                break;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Math.min(getSuggestedMinimumHeight(), size);
                break;
        }
        return result;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return mMinWidth;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return mMinWidth;
    }

    /****
     * 设置边框的颜色
     * @param borderColor 颜色
     */
    public void setBorderColor(int borderColor) {
        if (bgPaint != null)
            bgPaint.setColor(borderColor);
        invalidate();
    }

    /***
     * 设置边框的宽度
     * @param circleWidth 宽度
     */

    public void setBorderWidth(int circleWidth) {
        mCircleBorderWidth = circleWidth;
        invalidate();
    }

    /****
     * 获得ImageView的位图
     */
    private void loadImageView() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            bitmap = bitmapDrawable.getBitmap();
        }
    }

    private Bitmap createCircleImageView(Bitmap source, int min) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        //产生一个同样大小的画布
        Canvas canvas = new Canvas(target);
        //绘制圆形
        canvas.drawCircle(min / 2, min / 2, min / 2, mPaint);
        //使用SRC_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图片
        canvas.drawBitmap(source, 0, 0, mPaint);
        return target;
    }

    /***
     * 控件进行销毁的时候我们要对资源进行一下释放
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        bitmap.recycle();
    }
}
