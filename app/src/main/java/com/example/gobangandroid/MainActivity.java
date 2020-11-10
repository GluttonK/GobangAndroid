package com.example.gobangandroid;

import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private VideoView vvMain;
    private Button button;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"因为王家龙不帮我debug所以不能运行",Toast.LENGTH_SHORT).show();
            }
        });


//        vvMain = (VideoView) findViewById(R.id.vv_main);
//        //播放本地视频
//        String videoPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)+"/storage/Pictures/C语言笔记/wjl.mp4";
//        //播放网络视频
//        //String videoPath = "http://vfx.mtime.cn/Video/2019/07/12/mp4/190712140656051701.mp4";
//        //实例化
//        this.vvMain = vvMain;
//        //设置播放地址
//        vvMain.setVideoPath(videoPath);
//        //开始播放
//        vvMain.start();









    }

}