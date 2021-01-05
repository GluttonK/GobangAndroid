package com.example.gobangandroid;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {//继承自Activity
    WuziqiPanel myView;//myView的引用
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        myView =  new WuziqiPanel(this,null);//初始化自定义View
        this.setContentView(myView);//设置当前的用户界面
    }
}
