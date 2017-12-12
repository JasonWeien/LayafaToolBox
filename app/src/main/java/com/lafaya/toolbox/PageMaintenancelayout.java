package com.lafaya.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by JeffYoung on 2016/11/1.
 */
public class PageMaintenancelayout {
    private TextView text_maintenance_name,text_maintenance_info;
    private Button button_maintenance_enter,button_maintenance_cancle;
    private ImageButton button_maintenance_restart,button_maintenance_reset;
    private LinearLayout layout_maintenance_button;
    private int selcet_flag = 0;

    private Handler handler;
    private Activity activity;

    public void activityMaintenancelayout(final Activity inactivity, Handler inhandler){
        activity = inactivity;
        handler = inhandler;

        text_maintenance_info = (TextView)activity.findViewById(R.id.text_maintenance_info);
        text_maintenance_name = (TextView)activity.findViewById(R.id.text_maintenance_name);
        layout_maintenance_button = (LinearLayout)activity.findViewById(R.id.layout_maintenance_button);
        button_maintenance_cancle = (Button)activity.findViewById(R.id.button_maintenance_cancle);
        button_maintenance_enter = (Button)activity.findViewById(R.id.button_maintenance_enter);
        button_maintenance_restart = (ImageButton)activity.findViewById(R.id.button_maintenance_restart);
        button_maintenance_reset = (ImageButton)activity.findViewById(R.id.button_maintenance_reset);
        showMaintenance();

        button_maintenance_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartSelected();
                selcet_flag = 2;
            }
        });

        button_maintenance_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSelected();
                selcet_flag = 1;
            }
        });
        button_maintenance_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMaintenance();
            }
        });
        button_maintenance_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetStart(selcet_flag);
/*
                //发送命令
                MainActivity.doorStatus.doorReset(activity,selcet_flag,"01");
                //等待
                showMaintenance();
                if(MainActivity.doorStatus.door_connect) {
                    Message msg = new Message();
                    msg.what = MainActivity.LAYOUTSHOW;
                    Bundle bundle = new Bundle();
                    bundle.putInt("layoutnumber", 9);
                    if (selcet_flag == 1) {
                        bundle.putString("tesxshow", "恢复出厂设置");
                        bundle.putInt("time", 200);
                    } else {
                        bundle.putString("tesxshow", "重置自动门");
                        bundle.putInt("time", 100);
                    }
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }*/
            }
        });

    }



    private void showMaintenance(){
        layout_maintenance_button.setVisibility(View.VISIBLE);
        text_maintenance_name.setVisibility(View.GONE);
        text_maintenance_info.setVisibility(View.GONE);
        button_maintenance_cancle.setVisibility(View.GONE);
        button_maintenance_enter.setVisibility(View.GONE);
    }
    private void restartSelected()
    {
        layout_maintenance_button.setVisibility(View.GONE);
        text_maintenance_name.setText("重置自动门");
        text_maintenance_info.setText(R.string.restart_info);
        text_maintenance_info.setVisibility(View.VISIBLE);
        text_maintenance_name.setVisibility(View.VISIBLE);
        button_maintenance_cancle.setVisibility(View.VISIBLE);
        button_maintenance_enter.setVisibility(View.VISIBLE);
    }

    private void resetSelected()
    {
        layout_maintenance_button.setVisibility(View.GONE);
        text_maintenance_name.setText("恢复出厂设置");
        text_maintenance_info.setText(R.string.reset_info);
        text_maintenance_info.setVisibility(View.VISIBLE);
        text_maintenance_name.setVisibility(View.VISIBLE);
        button_maintenance_cancle.setVisibility(View.VISIBLE);
        button_maintenance_enter.setVisibility(View.VISIBLE);
    }

    public void resetPorgress(){
        selcet_flag++;
        if(selcet_flag < 4){
            Handler rehandler = new Handler();
            rehandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = MainActivity.PLC_RESET;
                    Bundle bundle = new Bundle();
                    bundle.putInt("reset_flag",selcet_flag);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            },1600);
        }else{
            selcet_flag = 0;
        }
    }

    public void resetStart(int flag){
        selcet_flag = flag;
        //发送命令
        if(MainActivity.doorStatus.revolvingdoortype) {
            MainActivity.doorStatus.doorReset(activity, selcet_flag, "01");
            //等待
            showMaintenance();
            if(MainActivity.doorStatus.door_connect) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber", 9);
                if (selcet_flag == 1) {
                    bundle.putString("tesxshow", "恢复出厂设置");
                    bundle.putInt("time", 200);
                } else {
                    bundle.putString("tesxshow", "重置自动门");
                    bundle.putInt("time", 100);
                }
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }else {
            MainActivity.doorStatus.doorReset(activity, selcet_flag, "05");
            showMaintenance();
        }
    }

}
