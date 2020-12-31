package com.xy.jqhcorelibapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.xy.libuimodule.confirmdialogview.ConfirmBuilder;
import com.xy.libuimodule.confirmdialogview.ConfirmDialog;

public class UIActivity extends AppCompatActivity {

    private AppCompatButton confirmBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        confirmBtn = findViewById(R.id.confirm_btn);

        initEvent();
    }

    private void initEvent(){
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmBuilder confirmBuilder = new ConfirmBuilder()
                        .setConfirmTitle("对话框内容")
                        .setLeftViewText("取消")
                        .setRightViewText("确定");
                ConfirmDialog confirmDialog = new ConfirmDialog(UIActivity.this,confirmBuilder);
                confirmDialog.setOnConfirmSelectListener(new ConfirmDialog.OnConfirmSelectListener() {
                    @Override
                    public void onLeftClick() {
                        confirmDialog.dismiss();
                    }

                    @Override
                    public void onRightClick() {
                        // 确定
                    }
                });
                confirmDialog.show();
            }
        });
    }
}
