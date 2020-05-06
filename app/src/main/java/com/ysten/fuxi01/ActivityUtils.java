package com.ysten.fuxi01;

import com.ysten.arouter.Arouter;
import com.ysten.arouter.IRouter;

public class ActivityUtils implements IRouter {
    @Override
    public void putActivity() {
        //现在手动的添加Activity堆栈管理
        Arouter.getInstance().putActivity("/com/MainActivity",com.ysten.fuxi01.MainActivity.class);
    }
}
