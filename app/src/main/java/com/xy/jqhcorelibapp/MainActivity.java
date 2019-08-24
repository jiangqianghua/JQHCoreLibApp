package com.xy.jqhcorelibapp;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.xy.updaterapplib.AppUpdater;
import com.xy.updaterapplib.net.INetCallBack;

public class MainActivity extends AppCompatActivity {
    // 更新app按钮
    private AppCompatButton updateAppBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateAppBtn = findViewById(R.id.update_app_btn);

        initEvent();
    }


    private void initEvent(){
        updateAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUpdater.getInstance().getNetManager().get("", new INetCallBack() {
                    @Override
                    public void success(String response) {
                        //
                    }

                    @Override
                    public void failed(Throwable throwable) {

                    }
                });
            }
        });
    }
}
