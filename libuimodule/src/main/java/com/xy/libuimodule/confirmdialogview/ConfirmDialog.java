package com.xy.libuimodule.confirmdialogview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xy.libuimodule.R;


public class ConfirmDialog extends Dialog {
    private TextView confirmTitle, confirmContent,leftViewTv,rightViewTv;
    private OnConfirmSelectListener onConfirmSelectListener = null;
    private String confirmTitleText;
    private String confirmContentText;
    private String leftViewText;
    private String rightViewText;
    private boolean clickOutsideCancel=false;

    public ConfirmDialog(Context context, ConfirmBuilder confirmBuilder) {
        super(context, R.style.MyMessageDialog);
        confirmTitleText=confirmBuilder.confirmTitle;
        confirmContentText=confirmBuilder.confirmContent;
        leftViewText=confirmBuilder.leftViewText;
        rightViewText=confirmBuilder.rightViewText;
        clickOutsideCancel=confirmBuilder.clickOutsideCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog);
        init();
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(clickOutsideCancel);
    }

    private void init() {
        confirmTitle = (TextView) findViewById(R.id.confirmTitle);
        confirmTitle.setText(confirmTitleText);
        confirmContent = (TextView) findViewById(R.id.confirmContent);
        confirmContent.setText(confirmContentText);
        leftViewTv = (TextView) findViewById(R.id.leftViewTv);
        leftViewTv.setText(leftViewText);
        rightViewTv = (TextView) findViewById(R.id.rightViewTv);
        rightViewTv.setText(rightViewText);

        leftViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onConfirmSelectListener != null) {
                    onConfirmSelectListener.onLeftClick();
                }
            }
        });
        rightViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onConfirmSelectListener != null) {
                    onConfirmSelectListener.onRightClick();
                }
            }
        });
    }


    public void setOnConfirmSelectListener(OnConfirmSelectListener onConfirmSelectListener) {
        this.onConfirmSelectListener = onConfirmSelectListener;
    }

    public interface OnConfirmSelectListener {
        void onLeftClick();

        void onRightClick();
    }

    public void setDialogLocation(int X, int Y) {
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        //显示的坐标
        lp.x = X;
        lp.y = Y;
        dialogWindow.setAttributes(lp);
    }

}
