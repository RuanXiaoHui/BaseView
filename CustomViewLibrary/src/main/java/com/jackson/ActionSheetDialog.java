package com.jackson;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jackson.dialog.OnSheetItemClickListener;
import com.jackson.dialog.SheetItem;
import com.jackson.dialog.SheetItemColor;
import com.jackson.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2018/2/19.
 * 自定义仿IOS样式Dialog
 */
public class ActionSheetDialog {

    private Context context;
    private Dialog mDialog;
    private TextView tvTitle;
    private TextView tvCancel;
    private ScrollView scrollView;
    private LinearLayout lltContent;
    private Display display;
    private List<SheetItem> sheetItemList;
    private boolean isShowTitle = false;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog Builder() {
        //获取Dialog的布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);
        view.setMinimumWidth(display.getWidth());
        //初始化View
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        lltContent = (LinearLayout) view.findViewById(R.id.lltContent);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);

        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(view);

        Window mDialogWindow = mDialog.getWindow();
        mDialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = mDialogWindow.getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        mDialogWindow.setAttributes(layoutParams);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        return this;
    }

    public ActionSheetDialog addSheetItem(String itemName, SheetItemColor itemColor, OnSheetItemClickListener listener) {

        if (sheetItemList == null) {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(itemName, itemColor, listener));
        return this;
    }

    public void show() {
        setSheetItems();
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();
        // 控制高度
        if (size>5) {
            LayoutParams parms = scrollView.getLayoutParams();
            parms.height = display.getHeight() / 2;
            scrollView.setLayoutParams(parms);
        }
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String itemName = sheetItem.getName();
            SheetItemColor sheetColor = sheetItem.getColor();
            final OnSheetItemClickListener listener = sheetItem.getListener();

            TextView tvItem = new TextView(context);
            tvItem.setGravity(Gravity.CENTER);
            tvItem.setText(itemName);
            tvItem.setTextSize(18);


            if (size == 1) {
                if (isShowTitle) {
                    tvItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    tvItem.setBackgroundResource(R.drawable.actionsheet_top_selector);
                }
            } else {
                if (isShowTitle) {
                    if (i >= 1 && i < size) {
                        tvItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        tvItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        tvItem.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        tvItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        tvItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }

            //字体颜色
            if (null != sheetColor) {
                tvItem.setTextColor(Color.parseColor(sheetColor.getName()));
            } else {
                tvItem.setTextColor(Color.parseColor(SheetItemColor.BLUE.getName()));
            }

            //高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (scale * 45 + 0.5f);
            tvItem.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height));

            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    listener.onClick(index);
                }
            });

            lltContent.addView(tvItem);
        }

    }


    /****
     * 设置标题
     */
    public ActionSheetDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            isShowTitle = true;
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /****
     * dialog.setCancelable(false);
     dialog弹出后会点击屏幕或物理返回键，dialog不消失
     */
    public ActionSheetDialog setCancelable(boolean isCancel) {
        mDialog.setCancelable(isCancel);
        return this;
    }

    /****
     *dialog.setCanceledOnTouchOutside(false);
     dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
     */
    public ActionSheetDialog setCanceledOnTouchOutside(boolean isCancel) {
        mDialog.setCanceledOnTouchOutside(isCancel);
        return this;
    }


}
