package com.jqh.libproxy.httpprocessor;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// json版本
public abstract class HttpCallback<Result> implements ICallback {

    @Override
    public void onSuccess(String result) {
        // 将字符串转转对象
        Class<?> clz = analysisClassInfo(this);
        Gson gson = new Gson();
        Result objResult = (Result) gson.fromJson(result, clz);

        onSuccess(objResult);
    }

    public abstract  void onSuccess(Result objResult);

    @Override
    public void onFailure(String e) {

    }
    // 获取泛型的Class
    private Class<?> analysisClassInfo(Object object) {
        Type getType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)getType).getActualTypeArguments();
        return (Class<?>)params[0];
    }
}
