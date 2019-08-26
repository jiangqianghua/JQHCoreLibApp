package com.xy.libuimodule.confirmdialogview;

import android.content.Context;

public class ConfirmBuilder {
    String confirmTitle;
    String confirmContent;
    String leftViewText;
    String rightViewText;
    private Context context;
    boolean clickOutsideCancel=false;

    public ConfirmBuilder with(Context context){
        this.context=context;
        return this;
    }

    public ConfirmBuilder setClickOutsideCancel(boolean clickOutsideCancel){
        this.clickOutsideCancel=clickOutsideCancel;
        return this;
    }

    public ConfirmBuilder setConfirmTitle(String confirmTitle) {
        this.confirmTitle = confirmTitle;
        return this;
    }

    public ConfirmBuilder setConfirmContent(String confirmContent) {
        this.confirmContent = confirmContent;
        return this;
    }

    public ConfirmBuilder setLeftViewText(String leftViewText) {
        this.leftViewText = leftViewText;
        return this;
    }

    public ConfirmBuilder setRightViewText(String rightViewText) {
        this.rightViewText = rightViewText;
        return this;
    }

    public ConfirmDialog build(){
        return new ConfirmDialog(context,this);
    }
}
