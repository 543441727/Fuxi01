package com.ysten.fuxi01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

/**
 * @author wangjitao on 2020/5/5
 * @desc:
 */
public class Arouter {

    private static Arouter instance ;
    private  Context mContext;
    private static Map<String,Class<? extends Activity>> activityMap ;

    public static Arouter getInstance(){
        if (instance == null){
            synchronized (Arouter.class){
                instance = new Arouter() ;
                activityMap = new ArrayMap<>();
            }

        }
        return instance ;
    }

    /**
     *  将activity压入
     * @param activityName
     * @param cls
     */
    public void putActivity(String activityName ,Class cls){
        if (cls != null && !TextUtils.isEmpty(activityName)){
            activityMap.put(activityName,cls) ;
        }
    }

    /**
     * 通过之前定义的path就行启动
     * @param activityName
     */
    public void jumpActivity(String activityName){
        jumpActivity(activityName,null);
    }

    public void jumpActivity(String activityName, Bundle bundle){
        Intent intent = new Intent() ;
        Class<? extends Activity> aCls = activityMap.get(activityName);
        if (aCls == null){
            Log.e("wangjitao" ," error -- > can not find activityName "+activityName);
            return;
        }
        if (bundle != null){
            intent.putExtras(bundle);
        }
        intent.setClass(mContext ,aCls) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
