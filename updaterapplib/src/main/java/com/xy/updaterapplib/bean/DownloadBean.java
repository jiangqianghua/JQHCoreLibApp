package com.xy.updaterapplib.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DownloadBean implements Serializable {
    public String title;
    public String content;
    public String url;
    public String md5;
    public String versionCode;

    /**
     {
     "title": "1.0.0更新了!",
     "content": "1.更新了阅读体验;\n, 2. 上线了更新操作;\n 3. 修复了部分bug",
     "url": "http://59.110.162.30/v450_imooc_updater.apk",
     "md5": "1223",
     "versionCode": "1"
     }
     **/

    public static DownloadBean parse(String response){
        try {
            JSONObject repJson = new JSONObject(response);
            String title = repJson.optString("title");
            String content = repJson.optString("content");
            String url = repJson.optString("url");
            String md5 = repJson.optString("md5");
            String versionCode = repJson.getString("versionCode");
            DownloadBean bean = new DownloadBean();
            bean.title = title;
            bean.content = content ;
            bean.url = url ;
            bean.md5 = md5 ;
            bean.versionCode = versionCode ;
            return bean ;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null ;
    }
}
