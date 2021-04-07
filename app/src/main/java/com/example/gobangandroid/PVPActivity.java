package com.example.gobangandroid;



import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PVPActivity extends AppCompatActivity {

    private PanelView mPanelView;
    private ArrayList mblackArray;
    private ArrayList mwhiteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvp);
        mPanelView = findViewById(R.id.Panel);
    }


    public void Jump(View view){
        switch (view.getId()) {
            //悔棋
            case R.id.repentance:
                if(mPanelView.getIsWhite()){
                    mblackArray = mPanelView.getBlackArray();
                    mblackArray.remove(mblackArray.size()-1);
                    mPanelView.setBlackArray(mblackArray);
                    mPanelView.invalidate();
                    mPanelView.setIsWhite(false);
                } else {
                    mwhiteArray = mPanelView.getWhiteArray();
                    mwhiteArray.remove(mwhiteArray.size()-1);
                    mPanelView.setWhiteArray(mwhiteArray);
                    mPanelView.invalidate();
                    mPanelView.setIsWhite(true);
                }
                break;
            //重开
            case R.id.restart:
                mPanelView.reStart();
                break;
        }




    }




}
