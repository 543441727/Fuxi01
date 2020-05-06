package com.ysten.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

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

    public void putActivity(String activityName ,Class cls){
        if (cls != null && !TextUtils.isEmpty(activityName)){
            activityMap.put(activityName,cls) ;
        }
    }

    public void init(Context context){
        mContext = context ;
        List<String> className =getAllActivityUtils("com.ysten.test");
        Log.d("wangjitao" ,"className "+className);
        for (String cls : className ) {
            try {
                Class<?> aClass =  Class.forName(cls);
                if (IRouter.class.isAssignableFrom(aClass)){
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            }catch (Exception e ){

            }

        }
    }

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

    public List<String> getAllActivityUtils(String packageName){
        List<String> list = new ArrayList<>() ;
        String path  ;
        try {
            path = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), 0).sourceDir;
            DexFile dexFile = null ;
            dexFile = new DexFile(path);
            Enumeration enumeration = dexFile.entries() ;
            while(enumeration.hasMoreElements()){
                String name = (String) enumeration.nextElement();
                if (name.contains(packageName)){
                    list.add(name) ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list ;
    }

}
