package com.jackson.baseview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jackson.ActionSheetDialog;
import com.jackson.CircleImageView;
import com.jackson.dialog.OnSheetItemClickListener;
import com.jackson.dialog.SheetItemColor;

public class MainActivity extends AppCompatActivity {

    private CircleImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ivLogo = (CircleImageView) findViewById(R.id.ivLogo);
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ActionSheetDialog(MainActivity.this).Builder()
                        .addSheetItem("相册", SheetItemColor.RED, new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int witch) {

                                Toast.makeText(MainActivity.this, "相册", Toast.LENGTH_SHORT).show();
                            }
                        }).addSheetItem("相机", SheetItemColor.BLUE, new OnSheetItemClickListener() {
                    @Override
                    public void onClick(int witch) {
                        Toast.makeText(MainActivity.this, "相机", Toast.LENGTH_SHORT).show();


                    }
                }).show();

            }
        });
    }
}
