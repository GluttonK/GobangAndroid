package com.example.gobangandroid;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button pvpbtn;
    private Button pvebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pvpbtn = findViewById(R.id.pvp);
        pvebtn = findViewById(R.id.pve);
    }

    public void Choose(View view){
        switch (view.getId()) {
            case R.id.pvp:
                Intent intent1 = new Intent(MainActivity.this,PVPActivity.class);
                startActivity(intent1);
                break;
            case R.id.pve:
                Intent intent2 = new Intent(MainActivity.this,PVEActivity.class);
                startActivity(intent2);
                break;
        }
    }


}
