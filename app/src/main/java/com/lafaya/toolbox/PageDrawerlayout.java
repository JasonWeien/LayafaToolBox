package com.lafaya.toolbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JeffYoung on 2016/10/27.
 **/
public class PageDrawerlayout {
    private Button button_menu_backhome;
    private ToggleButton switch_menu_door_connect,switch_menu_lock,switch_menu_light;
    private ImageButton button_menu_exit,button_menu_about,button_menu_help,button_menu_info;
    private LinearLayout layout_switch_menu_light,layout_switch_menu_lock;

    public void activityDrawerlayout(final Activity activity,final Handler handler){

        button_menu_backhome = (Button)activity.findViewById(R.id.button_menu_backhome);

        switch_menu_door_connect = (ToggleButton)activity.findViewById(R.id.switch_menu_door_connect);
        switch_menu_lock = (ToggleButton)activity.findViewById(R.id.switch_menu_lock);
        switch_menu_light = (ToggleButton)activity.findViewById(R.id.switch_menu_light);

        button_menu_info = (ImageButton)activity.findViewById(R.id.button_menu_info);
        button_menu_help = (ImageButton)activity.findViewById(R.id.button_menu_help);
        button_menu_about = (ImageButton)activity.findViewById(R.id.button_menu_about);
        button_menu_exit = (ImageButton)activity.findViewById(R.id.button_menu_exit);


        layout_switch_menu_light = (LinearLayout)activity.findViewById(R.id.layout_switch_menu_light);
        layout_switch_menu_lock = (LinearLayout)activity.findViewById(R.id.layout_switch_menu_lock);

        switch_menu_door_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断门是否已经连接，若连接，则断开，否则连接
                if(MainActivity.doorStatus.door_connect){
                    MainActivity.bluetoothComm.connectDevice(activity,false,handler);
                    switch_menu_door_connect.setChecked(true);

                }else{
                    MainActivity.bluetoothComm.connectDevice(activity,true,handler);
                    switch_menu_door_connect.setChecked(false);
                }

            }
        });

        switch_menu_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断锁是否已经打开，若打开，则关闭，否则打开

                if(MainActivity.doorStatus.lock_status){
                    switch_menu_lock.setChecked(true);
                }else{
                    switch_menu_lock.setChecked(false);
                }
                if(MainActivity.pageHomelayout.home_check_list == 0){

                    //调用发送函数
                    MainActivity.doorStatus.lockTurnOn_Off(activity,true);
                }

            }
        });

        switch_menu_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(MainActivity.doorStatus.light_status){
                    switch_menu_light.setChecked(true);
                }else{
                    switch_menu_light.setChecked(false);
                }
                if(MainActivity.pageHomelayout.home_check_list == 0){
                    //调用发送函数
                    MainActivity.doorStatus.lightTurnOn_Off(activity,true);
                }

            }
        });

        //返回主页操作
        button_menu_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",1);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        button_menu_backhome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((Button)view).setTextColor(activity.getResources().getColor(R.color.colorPrimary));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((Button)view).setTextColor(activity.getResources().getColor(R.color.colorString));
                }
                return false;
            }
        });


        //退出
        button_menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog(activity);
            }
        });
        button_menu_exit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });

        //进入自动门信息界面
        button_menu_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",6);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        button_menu_info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });
        //进入帮助界面
        button_menu_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",7);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        button_menu_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });
        //进入关于界面
        button_menu_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",8);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        button_menu_about.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });
    }

   public void UpdateDrawerlayout(){
       // update show
       if(MainActivity.doorStatus.door_connect){
           switch_menu_door_connect.setChecked(true);
       }else{
           switch_menu_door_connect.setChecked(false);
       }
       if(MainActivity.doorStatus.light_status){
           switch_menu_light.setChecked(true);
       }else{
           switch_menu_light.setChecked(false);
       }
       if(MainActivity.doorStatus.lock_status){
           switch_menu_lock.setChecked(true);
       }else{
           switch_menu_lock.setChecked(false);
       }

       if(MainActivity.doorStatus.revolvingdoortype){
          layout_switch_menu_light.setVisibility(View.VISIBLE);
           layout_switch_menu_lock.setVisibility(View.VISIBLE);
       }else {
           layout_switch_menu_light.setVisibility(View.GONE);
           layout_switch_menu_lock.setVisibility(View.GONE);
       }
   }

    /*
   * 退出程序，窗口弹出，并选择
   * */
    public void exitDialog(final Activity activity){
        Dialog dialog = new AlertDialog.Builder(activity).setTitle("退出程序").setMessage("确定退出当前应用吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                activity.finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();
        dialog.show();
    }


}
