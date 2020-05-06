package com.ysten.fuxi01;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ysten.annotation.Route;
import com.ysten.arouter.Arouter;


@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Arouter.getInstance().jumpActivity("/login/LoginActivity");
                //ARouter.getInstance().build("/app/MainActivity1").navigation() ;
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
}
