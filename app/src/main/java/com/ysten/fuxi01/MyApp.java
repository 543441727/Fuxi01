package com.ysten.fuxi01;

import android.app.Application;

import com.ysten.arouter.Arouter;


/**
 * @author wangjitao on 2020/5/5
 * @desc:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }
    private void initARouter(){
        Arouter.getInstance().init(this);
//        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        ARouter.init( this ); // 尽可能早，推荐在Application中初始化
    }
}
