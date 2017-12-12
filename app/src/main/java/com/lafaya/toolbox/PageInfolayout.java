package com.lafaya.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JeffYoung on 2016/11/3.
 **/
public class PageInfolayout {
    private ImageButton button_info_version,button_info_runtime,button_info_sensor,button_info_errorcode;
    private LinearLayout activity_info_version,activity_info_runtime,activity_info_sensor,activity_info_errorcode;
    private LinearLayout activity_info_sensor_lafaya;
//    private TextView text_info_activate;
    private Activity activity;
    private Handler handler;


    //version
    private TextView text_info_revolving_version,text_info_sliding_version,text_info_leftwing_version,text_info_rigthwing_version;
    private TextView text_info_revolving_version_name,text_info_sliding_version_name,text_info_leftwing_version_name,text_info_rigthwing_version_name;

    //runttime
    private TextView text_info_revolving_runtimecurrent,text_info_sliding_runtimecurrent,text_info_leftwing_runtimecurrent,text_info_rigthwing_runtimecurrent;
    private TextView text_info_revolving_runtimehistory,text_info_sliding_runtimehistory,text_info_leftwing_runtimehistory,text_info_rigthwing_runtimehistory;

    private LinearLayout layout_info_revolving_runtimecurrent,layout_info_leftwing_runtimecurrent,layout_info_rigthwing_runtimecurrent;
    private TextView text_info_sliding_runtimecurrent_name;
    //sensor
    private TextView text_info_sensor1,text_info_sensor2,text_info_sensor3,text_info_sensor4,text_info_sensor5,text_info_sensor6;
    private TextView text_info_sensor7,text_info_sensor8,text_info_sensor9,text_info_sensor10,text_info_sensor11,text_info_sensor12;
    private TextView text_info_sensor13,text_info_sensor14,text_info_sensor15,text_info_sensor16,text_info_sensor17,text_info_sensor18;
    private TextView text_info_sensor19,text_info_sensor20;

    //lafaya senso
    private TextView text_info_la_dip1,text_info_la_dip2,text_info_la_dip3,text_info_la_dip4,text_info_la_dip5,
            text_info_la_dip6,text_info_la_dip7,text_info_la_dip8,text_info_la_dip9,text_info_la_dip10;
    private TextView text_info_la_sensor1,text_info_la_sensor2,text_info_la_sensor3,text_info_la_sensor4,
            text_info_la_sensor5,text_info_la_sensor6,text_info_la_sensor7,text_info_la_sensor8;

    private char[] lafayasensor_status = new char[]{0x00,0x00};

    //error code


    private AutoCountListView list_info_errorcode;

    private int info_layout_flag = 0;

    //软件版本检测标志，只需要查一次
    private boolean info_version_checked = false;

    //运行次数检测标志
    private boolean info_runtimecurrent_checked = false;
    private boolean info_runtimehistory_checked = false;

    private boolean info_checked = false;

    //Timer
    private Timer infocheckTimer;


    private int info_update_check = 0;//1 revolving, 2 sliding, 3 leftwing, 4 rightwing

    public void activityInfolayout(Activity inactivity, Handler inhandler){
        activity = inactivity;
        handler = inhandler;
        button_info_version = (ImageButton)activity.findViewById(R.id.button_info_version);
        button_info_runtime = (ImageButton)activity.findViewById(R.id.button_info_runtime);
        button_info_sensor = (ImageButton)activity.findViewById(R.id.button_info_sensor);
        button_info_errorcode = (ImageButton)activity.findViewById(R.id.button_info_errorcode);
        activity_info_version = (LinearLayout)activity.findViewById(R.id.activity_info_version);
        activity_info_runtime = (LinearLayout)activity.findViewById(R.id.activity_info_runtime);
        activity_info_sensor = (LinearLayout)activity.findViewById(R.id.activity_info_sensor);
        activity_info_sensor_lafaya = (LinearLayout)activity.findViewById(R.id.activity_info_sensor_lafaya);
        activity_info_errorcode = (LinearLayout)activity.findViewById(R.id.activity_info_errorcode);

        //version text
        text_info_revolving_version = (TextView)activity.findViewById(R.id.text_info_revolving_version);
        text_info_sliding_version = (TextView)activity.findViewById(R.id.text_info_sliding_version);
        text_info_leftwing_version = (TextView)activity.findViewById(R.id.text_info_leftwing_version);
        text_info_rigthwing_version = (TextView)activity.findViewById(R.id.text_info_rigthwing_version);

        text_info_revolving_version_name = (TextView)activity.findViewById(R.id.text_info_revolving_version_name);
        text_info_sliding_version_name = (TextView)activity.findViewById(R.id.text_info_sliding_version_name);
        text_info_leftwing_version_name = (TextView)activity.findViewById(R.id.text_info_leftwing_version_name);
        text_info_rigthwing_version_name = (TextView)activity.findViewById(R.id.text_info_rigthwing_version_name);
        //runtime current
        text_info_revolving_runtimecurrent = (TextView)activity.findViewById(R.id.text_info_revolving_runtimecurrent);
        text_info_sliding_runtimecurrent = (TextView)activity.findViewById(R.id.text_info_sliding_runtimecurrent);
        text_info_leftwing_runtimecurrent = (TextView)activity.findViewById(R.id.text_info_leftwing_runtimecurrent);
        text_info_rigthwing_runtimecurrent = (TextView)activity.findViewById(R.id.text_info_rigthwing_runtimecurrent);
        //runtime history
        text_info_revolving_runtimehistory = (TextView)activity.findViewById(R.id.text_info_revolving_runtimehistory);
        text_info_sliding_runtimehistory = (TextView)activity.findViewById(R.id.text_info_sliding_runtimehistory);
        text_info_leftwing_runtimehistory = (TextView)activity.findViewById(R.id.text_info_leftwing_runtimehistory);
        text_info_rigthwing_runtimehistory = (TextView)activity.findViewById(R.id.text_info_rigthwing_runtimehistory);

        layout_info_revolving_runtimecurrent = (LinearLayout)activity.findViewById(R.id.layout_info_revolving_runtimecurrent);
        layout_info_leftwing_runtimecurrent = (LinearLayout)activity.findViewById(R.id.layout_info_leftwing_runtimecurrent);
        layout_info_rigthwing_runtimecurrent = (LinearLayout)activity.findViewById(R.id.layout_info_rigthwing_runtimecurrent);
        text_info_sliding_runtimecurrent_name = (TextView)activity.findViewById(R.id.text_info_sliding_runtimecurrent_name);

        //sensor
        text_info_sensor1 = (TextView)activity.findViewById(R.id.text_info_sensor1);
        text_info_sensor2 = (TextView)activity.findViewById(R.id.text_info_sensor2);
        text_info_sensor3 = (TextView)activity.findViewById(R.id.text_info_sensor3);
        text_info_sensor4 = (TextView)activity.findViewById(R.id.text_info_sensor4);
        text_info_sensor5 = (TextView)activity.findViewById(R.id.text_info_sensor5);
        text_info_sensor6 = (TextView)activity.findViewById(R.id.text_info_sensor6);
        text_info_sensor7 = (TextView)activity.findViewById(R.id.text_info_sensor7);
        text_info_sensor8 = (TextView)activity.findViewById(R.id.text_info_sensor8);
        text_info_sensor9 = (TextView)activity.findViewById(R.id.text_info_sensor9);
        text_info_sensor10 = (TextView)activity.findViewById(R.id.text_info_sensor10);
        text_info_sensor11 = (TextView)activity.findViewById(R.id.text_info_sensor11);
        text_info_sensor12 = (TextView)activity.findViewById(R.id.text_info_sensor12);
        text_info_sensor13 = (TextView)activity.findViewById(R.id.text_info_sensor13);
        text_info_sensor14 = (TextView)activity.findViewById(R.id.text_info_sensor14);
        text_info_sensor15 = (TextView)activity.findViewById(R.id.text_info_sensor15);
        text_info_sensor16 = (TextView)activity.findViewById(R.id.text_info_sensor16);
        text_info_sensor17 = (TextView)activity.findViewById(R.id.text_info_sensor17);
        text_info_sensor18 = (TextView)activity.findViewById(R.id.text_info_sensor18);
        text_info_sensor19 = (TextView)activity.findViewById(R.id.text_info_sensor19);
        text_info_sensor20 = (TextView)activity.findViewById(R.id.text_info_sensor20);
        sensormobileShwoUpdate("000000000000");
        sensorfixedShwoUpdate("00000000");

        //lafaya sensor
        text_info_la_dip1 = (TextView)activity.findViewById(R.id.text_info_la_dip1);
        text_info_la_dip2 = (TextView)activity.findViewById(R.id.text_info_la_dip2);
        text_info_la_dip3 = (TextView)activity.findViewById(R.id.text_info_la_dip3);
        text_info_la_dip4 = (TextView)activity.findViewById(R.id.text_info_la_dip4);
        text_info_la_dip5 = (TextView)activity.findViewById(R.id.text_info_la_dip5);
        text_info_la_dip6 = (TextView)activity.findViewById(R.id.text_info_la_dip6);
        text_info_la_dip7 = (TextView)activity.findViewById(R.id.text_info_la_dip7);
        text_info_la_dip8 = (TextView)activity.findViewById(R.id.text_info_la_dip8);
        text_info_la_dip9 = (TextView)activity.findViewById(R.id.text_info_la_dip9);
        text_info_la_dip10 = (TextView)activity.findViewById(R.id.text_info_la_dip10);

        text_info_la_sensor1 = (TextView)activity.findViewById(R.id.text_info_la_sensor1);
        text_info_la_sensor2 = (TextView)activity.findViewById(R.id.text_info_la_sensor2);
        text_info_la_sensor3 = (TextView)activity.findViewById(R.id.text_info_la_sensor3);
        text_info_la_sensor4 = (TextView)activity.findViewById(R.id.text_info_la_sensor4);
        text_info_la_sensor5 = (TextView)activity.findViewById(R.id.text_info_la_sensor5);
        text_info_la_sensor6 = (TextView)activity.findViewById(R.id.text_info_la_sensor6);
        text_info_la_sensor7 = (TextView)activity.findViewById(R.id.text_info_la_sensor7);
        text_info_la_sensor8 = (TextView)activity.findViewById(R.id.text_info_la_sensor8);

        sensorlafayaShowUpdate(lafayasensor_status);


        //error code
        list_info_errorcode = (AutoCountListView)activity.findViewById(R.id.list_info_errorcode);
        if(MainActivity.doorStatus.revolvingdoortype){
            errocodeShowUpdate("00--00--00--00--00--00--00--00--00--00--");
        }else {
            errocodeShowUpdate("00000000000000000000");
        }


        button_info_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info_layout_flag == 1){
                    info_layout_flag = 0;
                    info_update_check = 0;
                }else if(info_update_check == 0){
                    if((info_layout_flag == 3) && (!MainActivity.doorStatus.revolvingdoortype)){
                        MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID, 0), activity);
                    }
                    info_layout_flag = 1;
                    //未查询过，查询
                    if(!info_version_checked){
                        if(MainActivity.doorStatus.revolvingdoortype) {
                            info_update_check = 1;
                            MainActivity.doorStatus.readVersion(activity, "01");
                        }else{
                            info_update_check = 2;
                            MainActivity.doorStatus.readVersion(activity, "05");
                        }
                    }
                }else {
                    MainActivity.bluetoothComm.communicating();
                }
                UpdataInfolayout(false);
                if(info_checked){
                    infocheckTimer.cancel();
                    info_checked = false;
                }
            }
        });

        button_info_runtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info_layout_flag == 2){
                    info_layout_flag = 0;
                    info_update_check = 0;
                    if(info_checked){
                        infocheckTimer.cancel();
                        info_checked = false;
                    }
                }else if(info_update_check == 0){
                    if((info_layout_flag == 3) && (!MainActivity.doorStatus.revolvingdoortype)){
                        MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID, 0), activity);
                    }
                    info_layout_flag = 2;
                    //infoCheckStart();
                    //未查询过，查询
                    info_runtimecurrent_checked = true;
                    info_runtimehistory_checked = false;
                    if (MainActivity.doorStatus.revolvingdoortype) {
                        info_update_check = 1;
                        MainActivity.doorStatus.readRuntimeCurrent(activity, "01");
                    } else {
                        info_update_check = 2;
                        MainActivity.doorStatus.readRuntimeCurrent(activity, "05");
                    }
                }else {
                    MainActivity.bluetoothComm.communicating();
                }
                UpdataInfolayout(false);

            }
        });

        button_info_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(info_layout_flag == 3){
                    info_layout_flag = 0;
                    info_update_check = 0;
                    if(!MainActivity.doorStatus.revolvingdoortype){
                        MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID, 0), activity);
                    }
                    if(info_checked){
                        infocheckTimer.cancel();
                        info_checked = false;
                    }

                }else if(info_update_check == 0){
                    info_layout_flag = 3;
                    if(!info_checked){
                        //infoCheckStart();
                        info_update_check = 1;
                        if(MainActivity.doorStatus.revolvingdoortype) {
                            MainActivity.doorStatus.readSensorStatus(activity, true);
                        }else {
                            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID, 1), activity);
                        }
                    }
                }else {
                    MainActivity.bluetoothComm.communicating();
                }
                UpdataInfolayout(false);

            }
        });

        button_info_errorcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(info_layout_flag == 4){
                    info_layout_flag = 0;
                    info_update_check = 0;
                }else if(info_update_check == 0){
                    if((info_layout_flag == 3) && (!MainActivity.doorStatus.revolvingdoortype)){
                        MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID, 0), activity);
                    }
                    info_layout_flag = 4;
                    info_update_check = 1;
                    MainActivity.doorStatus.errorhistoryQuery(activity);
                }else {
                    MainActivity.bluetoothComm.communicating();
                }
                UpdataInfolayout(false);
                if(info_checked){
                    infocheckTimer.cancel();
                    info_checked = false;
                }

            }
        });

        button_info_version.setOnTouchListener(new View.OnTouchListener() {
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
        button_info_runtime.setOnTouchListener(new View.OnTouchListener() {
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
        button_info_sensor.setOnTouchListener(new View.OnTouchListener() {
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
        button_info_errorcode.setOnTouchListener(new View.OnTouchListener() {
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

    public void UpdataInfolayout(boolean flag){
        if(flag){
            info_layout_flag = 0;
            info_runtimecurrent_checked = false;
            info_runtimehistory_checked = false;
            info_update_check = 0;
            //在其它界面中，停止定时查询传感器状态
            if(info_checked){
                infocheckTimer.cancel();
                info_checked = false;
            }
        }
        switch (info_layout_flag){
            case 0:
                activity_info_version.setVisibility(View.GONE);
                activity_info_runtime.setVisibility(View.GONE);
                activity_info_sensor.setVisibility(View.GONE);
                activity_info_sensor_lafaya.setVisibility(View.GONE);
                activity_info_errorcode.setVisibility(View.GONE);
                break;
            case 1:
                activity_info_version.setVisibility(View.VISIBLE);
                activity_info_runtime.setVisibility(View.GONE);
                activity_info_sensor.setVisibility(View.GONE);
                activity_info_sensor_lafaya.setVisibility(View.GONE);
                activity_info_errorcode.setVisibility(View.GONE);
                break;
            case 2:
                activity_info_version.setVisibility(View.GONE);
                activity_info_runtime.setVisibility(View.VISIBLE);
                activity_info_sensor.setVisibility(View.GONE);
                activity_info_sensor_lafaya.setVisibility(View.GONE);
                activity_info_errorcode.setVisibility(View.GONE);
                break;
            case 3:
                activity_info_version.setVisibility(View.GONE);
                activity_info_runtime.setVisibility(View.GONE);
                if(MainActivity.doorStatus.revolvingdoortype) {
                    activity_info_sensor.setVisibility(View.VISIBLE);
                    activity_info_sensor_lafaya.setVisibility(View.GONE);
                }else {
                    activity_info_sensor.setVisibility(View.GONE);
                    activity_info_sensor_lafaya.setVisibility(View.VISIBLE);
                }
                activity_info_errorcode.setVisibility(View.GONE);
                break;
            case 4:
                activity_info_version.setVisibility(View.GONE);
                activity_info_runtime.setVisibility(View.GONE);
                activity_info_sensor.setVisibility(View.GONE);
                activity_info_sensor_lafaya.setVisibility(View.GONE);
                activity_info_errorcode.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        if(MainActivity.doorStatus.revolvingdoortype){
            text_info_revolving_version.setVisibility(View.VISIBLE);
            text_info_leftwing_version.setVisibility(View.VISIBLE);
            text_info_rigthwing_version.setVisibility(View.VISIBLE);
            text_info_revolving_version_name.setVisibility(View.VISIBLE);
            text_info_leftwing_version_name.setVisibility(View.VISIBLE);
            text_info_rigthwing_version_name.setVisibility(View.VISIBLE);
            text_info_sliding_version_name.setText("中间门软件版本");
            text_info_revolving_runtimecurrent.setVisibility(View.VISIBLE);
            text_info_leftwing_runtimecurrent.setVisibility(View.VISIBLE);
            text_info_rigthwing_runtimecurrent.setVisibility(View.VISIBLE);
            text_info_revolving_runtimehistory.setVisibility(View.VISIBLE);
            text_info_leftwing_runtimehistory.setVisibility(View.VISIBLE);
            text_info_rigthwing_runtimehistory.setVisibility(View.VISIBLE);

            layout_info_revolving_runtimecurrent.setVisibility(View.VISIBLE);
            layout_info_rigthwing_runtimecurrent.setVisibility(View.VISIBLE);
            layout_info_leftwing_runtimecurrent.setVisibility(View.VISIBLE);
            text_info_sliding_runtimecurrent_name.setText("中间门运行次数");

        }else {
            text_info_revolving_version.setVisibility(View.GONE);
            text_info_leftwing_version.setVisibility(View.GONE);
            text_info_rigthwing_version.setVisibility(View.GONE);
            text_info_revolving_version_name.setVisibility(View.GONE);
            text_info_leftwing_version_name.setVisibility(View.GONE);
            text_info_rigthwing_version_name.setVisibility(View.GONE);
            text_info_sliding_version_name.setText("自动门软件版本");
            text_info_revolving_runtimecurrent.setVisibility(View.GONE);
            text_info_leftwing_runtimecurrent.setVisibility(View.GONE);
            text_info_rigthwing_runtimecurrent.setVisibility(View.GONE);
            text_info_revolving_runtimehistory.setVisibility(View.GONE);
            text_info_leftwing_runtimehistory.setVisibility(View.GONE);
            text_info_rigthwing_runtimehistory.setVisibility(View.GONE);

            layout_info_revolving_runtimecurrent.setVisibility(View.GONE);
            layout_info_rigthwing_runtimecurrent.setVisibility(View.GONE);
            layout_info_leftwing_runtimecurrent.setVisibility(View.GONE);
            text_info_sliding_runtimecurrent_name.setText("自动门运行次数");
        }

    }
    public void CleanInfolayout(){

        info_layout_flag = 0;
        info_update_check = 0;
        info_version_checked = false;
        text_info_revolving_version.setText("Ver    " + "----");
        text_info_sliding_version.setText("Ver    " + "----");
        text_info_leftwing_version.setText("Ver    " + "----");
        text_info_rigthwing_version.setText("Ver    " + "----");

        info_runtimecurrent_checked = false;
        info_runtimehistory_checked = false;
        text_info_revolving_runtimecurrent.setText("0" + "次");
        text_info_sliding_runtimecurrent.setText("0" + "次");
        text_info_leftwing_runtimecurrent.setText("0" + "次");
        text_info_rigthwing_runtimecurrent.setText("0" + "次");
        text_info_revolving_runtimehistory.setText("0" + "次");
        text_info_sliding_runtimehistory.setText("0" + "次");
        text_info_leftwing_runtimehistory.setText("0" + "次");
        text_info_rigthwing_runtimehistory.setText("0" + "次");

        if(MainActivity.doorStatus.revolvingdoortype){
            sensormobileShwoUpdate("000000000000");
            sensorfixedShwoUpdate("00000000");
        }else {
            lafayasensor_status[0] = 0x00;
            lafayasensor_status[1] = 0x00;
            sensorlafayaShowUpdate(lafayasensor_status);
        }

        if(MainActivity.doorStatus.revolvingdoortype) {
            errocodeShowUpdate("00--00--00--00--00--00--00--00--00--00--");
        }else {
            errocodeShowUpdate("00000000000000000000");
        }

        UpdataInfolayout(false);

    }
//显示版本号
    public void versionShowUpdate(int flag,String version){
        switch (flag){
            case 1:
                text_info_revolving_version.setText("Ver    " + version.substring(version.length()-4,version.length()-3)+ '.'+ version.substring(version.length()-3,version.length()-2)+ '.' + version.substring(version.length()-2,version.length()-1));
                break;
            case 2:
                text_info_sliding_version.setText("Ver    " + version.substring(version.length()-4,version.length()-3)+ '.'+ version.substring(version.length()-3,version.length()-2)+ '.' + version.substring(version.length()-2,version.length()-1));;
                break;
            case 3:
                text_info_leftwing_version.setText("Ver    " + version.substring(version.length()-4,version.length()-3)+ '.'+ version.substring(version.length()-3,version.length()-2)+ '.' + version.substring(version.length()-2,version.length()-1));;
                break;
            case 4:
                text_info_rigthwing_version.setText("Ver    " + version.substring(version.length()-4,version.length()-3)+ '.'+ version.substring(version.length()-3,version.length()-2)+ '.' + version.substring(version.length()-2,version.length()-1));;
                break;
            default:
                break;
        }
    }
//显示当前运行次数
    public void runtimecurrentShowUpdate(int flag,long runtime){

        switch (flag){
            case 1:
                text_info_revolving_runtimecurrent.setText(Long.toString(runtime) + "次");
                break;
            case 2:
                text_info_sliding_runtimecurrent.setText(Long.toString(runtime) + "次");
                break;
            case 3:
                text_info_leftwing_runtimecurrent.setText(Long.toString(runtime) + "次");
                break;
            case 4:
                text_info_rigthwing_runtimecurrent.setText(Long.toString(runtime) + "次");
                break;
            default:
                break;
        }
    }
//显示累计运行次数
    public void runtimehistoryShowUpdate(int flag, long runtime){

        switch (flag){
            case 1:
                text_info_revolving_runtimehistory.setText(Long.toString(runtime) + "次");
                break;
            case 2:
                text_info_sliding_runtimehistory.setText(Long.toString(runtime) + "次");
                break;
            case 3:
                text_info_leftwing_runtimehistory.setText(Long.toString(runtime) + "次");
                break;
            case 4:
                text_info_rigthwing_runtimehistory.setText(Long.toString(runtime) + "次");
                break;
            default:
                break;
        }
    }

//传感器状态显示，每隔2秒就查询一次
    public void sensorfixedShwoUpdate(String textstring){
        if(textstring.substring(textstring.length()-1,textstring.length()).equals("0")){
            text_info_sensor10.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor10.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor10.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor10.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-2,textstring.length()-1).equals("0")){
            text_info_sensor9.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor9.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor9.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor9.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-3,textstring.length()-2).equals("0")){
            text_info_sensor8.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor8.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor8.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor8.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-4,textstring.length()-3).equals("0")){
            text_info_sensor7.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor7.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor7.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor7.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-5,textstring.length()-4).equals("0")){
            text_info_sensor6.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor6.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor6.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor6.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-6,textstring.length()-5).equals("0")){
            text_info_sensor5.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor5.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor5.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor5.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-7,textstring.length()-6).equals("0")){
            text_info_sensor4.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor4.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor4.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor4.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-8,textstring.length()-7).equals("0")){
            text_info_sensor3.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor3.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor3.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor3.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }
    public void sensormobileShwoUpdate(String textstring){
        if(textstring.substring(textstring.length()-1,textstring.length()).equals("0")){
            text_info_sensor20.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor20.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor20.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor20.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-2,textstring.length()-1).equals("0")){
            text_info_sensor19.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor19.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor19.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor19.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-3,textstring.length()-2).equals("0")){
            text_info_sensor18.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor18.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor18.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor18.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-4,textstring.length()-3).equals("0")){
            text_info_sensor17.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor17.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor17.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor17.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-5,textstring.length()-4).equals("0")){
            text_info_sensor2.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor2.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor2.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor2.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-6,textstring.length()-5).equals("0")){
            text_info_sensor1.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor1.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor1.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor1.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-7,textstring.length()-6).equals("0")){
            text_info_sensor16.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor16.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor16.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor16.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }

        if(textstring.substring(textstring.length()-8,textstring.length()-7).equals("0")){
            text_info_sensor15.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor15.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor15.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor15.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-9,textstring.length()-8).equals("0")){
            text_info_sensor14.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor14.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor14.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor14.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-10,textstring.length()-9).equals("0")){
            text_info_sensor13.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor13.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor13.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor13.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-11,textstring.length()-10).equals("0")){
            text_info_sensor12.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor12.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor12.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor12.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if(textstring.substring(textstring.length()-12,textstring.length()-11).equals("0")){
            text_info_sensor11.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_sensor11.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_sensor11.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_sensor11.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

    public void sensorlafayaShowUpdate(char[] sensorstatus){
        if((sensorstatus[0] & 0x01) == 0x01){
            text_info_la_dip1.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip1.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip1.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip1.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x02) == 0x02){
            text_info_la_dip2.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip2.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip2.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip2.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x04) == 0x04){
            text_info_la_dip3.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip3.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip3.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip3.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x08) == 0x08){
            text_info_la_dip4.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip4.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip4.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip4.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x10) == 0x10){
            text_info_la_dip5.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip5.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip5.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip5.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x20) == 0x20){
            text_info_la_dip6.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip6.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip6.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip6.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x40) == 0x40){
            text_info_la_dip7.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip7.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip7.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip7.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[0] & 0x80) == 0x80){
            text_info_la_dip8.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip8.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_dip8.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_dip8.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
//        if(textstring.substring(textstring.length()-8,textstring.length()-7).equals("0")){
            text_info_la_dip9.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip9.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
 //       }else {
 //           text_info_la_dip9.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
 //           text_info_la_dip9.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
  //      }
//        if(textstring.substring(textstring.length()-8,textstring.length()-7).equals("0")){
            text_info_la_dip10.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_dip10.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
 //       }else {
  //          text_info_la_dip10.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
  //          text_info_la_dip10.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
  //      }
        if((sensorstatus[1] & 0x40) == 0x40){
            text_info_la_sensor1.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor1.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor1.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor1.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x80) == 0x80){
            text_info_la_sensor2.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor2.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor2.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor2.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x10) == 0x10){
            text_info_la_sensor3.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor3.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor3.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor3.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x08) == 0x08){
            text_info_la_sensor4.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor4.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor4.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor4.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x20) == 0x20){
            text_info_la_sensor5.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor5.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor5.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor5.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x04) == 0x04){
            text_info_la_sensor6.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor6.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor6.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor6.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x02) == 0x02){
            text_info_la_sensor7.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor7.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor7.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor7.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
        if((sensorstatus[1] & 0x01) == 0x01){
            text_info_la_sensor8.setTextColor(activity.getResources().getColor(R.color.colorString));
            text_info_la_sensor8.setBackgroundColor(activity.getResources().getColor(R.color.colorSensor));
        }else {
            text_info_la_sensor8.setTextColor(activity.getResources().getColor(R.color.colorStringWhite));
            text_info_la_sensor8.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

//接收到数据

    public void infoVersionReceive(String textstring){
        switch (info_update_check){
            case 1:
                versionShowUpdate(info_update_check,textstring);
                //info_update_check = 0;
                //info_version_checked = true;
                info_update_check = 2;
                MainActivity.doorStatus.readVersion(activity, "05");
                break;
            case 2:
                versionShowUpdate(info_update_check,textstring);
                if(MainActivity.doorStatus.revolvingdoortype) {
                    info_update_check = 3;
                    MainActivity.doorStatus.readVersion(activity, "03");
                }else {
                    info_update_check = 0;
                    info_version_checked = true;
                }
                //info_update_check = 0;
                //info_version_checked = true;
                //info_update_check = 3;
                //infoUpdateSend();
                break;
            case 3:
                versionShowUpdate(info_update_check,textstring);
                info_update_check = 4;
                MainActivity.doorStatus.readVersion(activity,"04");
                //infoUpdateSend();
                break;
            case 4:
                versionShowUpdate(info_update_check,textstring);
                info_update_check = 0;
                info_version_checked = true;
                break;
            default:
                break;
        }
    }

    public void infoRuntimeReceive(String textstring){

        switch (info_update_check){
            case 1:
                if(info_runtimecurrent_checked){
                    runtimecurrentShowUpdate(info_update_check,dataStringtoLong(textstring));
                    info_runtimehistory_checked = true;
                    info_runtimecurrent_checked = false;
                    MainActivity.doorStatus.readRuntimeHistory(activity, "01");
                }else{
                    runtimehistoryShowUpdate(info_update_check,dataStringtoLong(textstring));
                    info_runtimehistory_checked = false;
                    info_runtimecurrent_checked = true;
                    info_update_check = 2;
                    MainActivity.doorStatus.readRuntimeCurrent(activity, "05");
                }
                //info_update_check = 2;
                //MainActivity.doorStatus.readVersion(activity,"05");
                break;
            case 2:

                if (info_runtimecurrent_checked) {
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimecurrentShowUpdate(info_update_check, Long.parseLong(textstring));
                    info_runtimehistory_checked = true;
                    info_runtimecurrent_checked = false;
                    MainActivity.doorStatus.readRuntimeHistory(activity, "05");
                } else {
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimehistoryShowUpdate(info_update_check, Long.parseLong(textstring));
                    if(MainActivity.doorStatus.revolvingdoortype) {
                        info_runtimehistory_checked = false;
                        info_runtimecurrent_checked = true;
                        info_update_check = 3;
                        MainActivity.doorStatus.readRuntimeCurrent(activity, "03");
                    }else {
                        info_runtimehistory_checked = false;
                        info_runtimecurrent_checked = false;
                        info_update_check = 0;
                        infoCheckStart();
                    }
                }
                break;
            case 3:
                if(info_runtimecurrent_checked){
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimecurrentShowUpdate(info_update_check,Long.parseLong(textstring));
                    info_runtimehistory_checked = true;
                    info_runtimecurrent_checked = false;
                    MainActivity.doorStatus.readRuntimeHistory(activity, "03");
                }else{
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimehistoryShowUpdate(info_update_check,Long.parseLong(textstring));
                    info_runtimehistory_checked = false;
                    info_runtimecurrent_checked = true;
                    info_update_check = 4;
                    MainActivity.doorStatus.readRuntimeCurrent(activity, "04");
                }
                break;
            case 4:
                if(info_runtimecurrent_checked){
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimecurrentShowUpdate(info_update_check,Long.parseLong(textstring));
                    info_runtimehistory_checked = true;
                    info_runtimecurrent_checked = false;
                    MainActivity.doorStatus.readRuntimeHistory(activity, "04");
                }else{
                    //Toast.makeText(activity, textstring,Toast.LENGTH_LONG).show();
                    runtimehistoryShowUpdate(info_update_check,Long.parseLong(textstring));
                    info_runtimehistory_checked = false;
                    info_runtimecurrent_checked = false;
                    info_update_check = 0;
                    infoCheckStart();

                }
                break;
            default:
                break;
        }
    }
    public void infoSensorReceive(String textstring){
        if(MainActivity.doorStatus.revolvingdoortype) {
            switch (info_update_check) {
                case 1://固定部分
                    sensorfixedShwoUpdate(textstring);
                    info_update_check = 2;
                    MainActivity.doorStatus.readSensorStatus(activity, false);
                    break;
                case 2://移动部分
                    sensormobileShwoUpdate(textstring);
                    info_update_check = 0;
                    infoCheckStart();
                    break;
                default:
                    break;
            }
        }else {
            lafayasensor_status = sensorstatus_check(textstring);
            sensorlafayaShowUpdate(lafayasensor_status);
        }
    }

    public void infoErrorcodeReceive(String textstring){
        switch (info_update_check){
            case 1://固定部分
                errocodeShowUpdate(textstring);
                info_update_check = 0;
                break;
            default:
                break;
        }
    }

    private char[] sensorstatus_check(String textstring){
        char[] tempstatus = new char[]{0x00,0x00};
        char temp[];
        temp = IntegertoChar(Integer.parseInt(textstring.substring(1,2)));
        tempstatus[0] = temp[1];

/*        tempstatus[0] = IntegertoChar(Integer.getInteger(textstring.substring(0,2)))[1];
        temp = IntegertoChar(Integer.getInteger(textstring.substring(2,4)))[1];
        temp = temp << 5;
        tempstatus[1] = (char)(temp  & 0xE0);

        temp = IntegertoChar(Integer.getInteger(textstring.substring(4,6)))[1];
        temp = temp >> 3;
        tempstatus[1] += (char)(temp  & 0x18);
        temp = IntegertoChar(Integer.getInteger(textstring.substring(4,6)))[1];
        temp = temp >> 1;
        tempstatus[1] += (char)(temp  & 0x04);

        temp = IntegertoChar(Integer.getInteger(textstring.substring(6,8)))[1];
        temp = temp >> 5;
        tempstatus[1] += (char)(temp  & 0x03);*/
        return tempstatus;
    }

    public void infoCheck(){
        if(info_update_check == 0){
            infocheckTimer.cancel();
            info_checked = false;
            if(info_layout_flag == 2){
                info_runtimecurrent_checked = true;
                info_runtimehistory_checked = false;
                if(MainActivity.doorStatus.revolvingdoortype) {
                    info_update_check = 1;
                    MainActivity.doorStatus.readRuntimeCurrent(activity, "01");
                }else {
                    info_update_check = 2;
                    MainActivity.doorStatus.readRuntimeCurrent(activity, "05");
                }
            }else if(info_layout_flag == 3){
                if(MainActivity.doorStatus.revolvingdoortype) {
                    info_update_check = 1;
                    MainActivity.doorStatus.readSensorStatus(activity, true);
                }else {
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaMonitor(CommandLafaya.sendrightwingID,1),activity);
                }
            }else {
                UpdataInfolayout(false);
            }
        }
    }
    private void infoCheckStart(){
//        Handler rehandler = new Handler();
//        rehandler.postDelayed(new Runnable() {
 //           @Override
 //           public void run() {
//                Message msg = new Message();
//                msg.what = MainActivity.INFO_CHECK;
//                handler.sendMessage(msg);
//            }
//        }, 2000);

       info_checked = true;
        infocheckTimer = new Timer();
        TimerTask taskinfo = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MainActivity.INFO_CHECK;
                handler.sendMessage(msg);
            }
        };
        if((!MainActivity.doorStatus.revolvingdoortype) && info_layout_flag == 3){
            //查询间隔，每隔3000ms查询一次
            infocheckTimer.schedule(taskinfo, 100, 100);
        }else {
            //查询间隔，每隔3000ms查询一次
            infocheckTimer.schedule(taskinfo, 3000, 3000);
        }
    }


    public void infoCommunicationfailure(){
        if(info_update_check != 0) {
            if (info_runtimecurrent_checked) {
                info_runtimecurrent_checked = false;
            }
            if (info_runtimehistory_checked) {
                info_runtimehistory_checked = false;
            }
            info_update_check = 0;
        }
        if(info_checked){
            info_checked = false;
            infocheckTimer.cancel();
        }
    }

    private long dataStringtoLong(String textstring){
        char[] data = MainActivity.bluetoothComm.commandLafaya.AsciiToHex(textstring.toCharArray());
        int datatemp;
        long runtime;
        datatemp = data[2];
        datatemp = (datatemp << 8) + data[3];
        runtime = ((long)datatemp << 16);
        datatemp = data[0];
        datatemp = (datatemp << 8) + data[1];
        runtime += ((long)datatemp);
        return  runtime;
    }


    //报警信息显示
    private void errocodeShowUpdate(String textstring){
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

        String code;
        for(int i = 0; i < 10; i++){
            if(MainActivity.doorStatus.revolvingdoortype) {
                code = textstring.substring((textstring.length() - (i + 1) * 4 + 2), (textstring.length() - i * 4));
            }else{
                code = textstring.substring((textstring.length() - (i+1) * 2), (textstring.length() - i * 2));
            }
            mylist.add(AddInfoItem("——"+ code + "——",errorcodeConnect(code)));
//            mylist.add(AddInfoItem("——00——","正常运行"));
        }
        list_info_errorcode.setAdapter(mSchedule(activity,mylist));
        AutoCountListView.setListViewHeightBasedOnChildren(list_info_errorcode);

    }

    //写入数据
    private SimpleAdapter mSchedule(Activity activity, ArrayList<HashMap<String, String>> mylist){
        //生成适配器，数组===》ListItem
        SimpleAdapter tempSchedule = new SimpleAdapter(activity, //没什么解释
                mylist,//数据来源
                R.layout.list_errorcode,//ListItem的XML实现
                //动态数组与ListItem对应的子项
                new String[] {"text_errorcodelist_code", "text_errorcodelist_content"},
                //ListItem的XML文件里面的两个TextView ID
                new int[] {R.id.text_errorcodelist_code,R.id.text_errorcodelist_content});
        return tempSchedule;
    }

    private HashMap<String, String>  AddInfoItem(String titile,String info){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("text_errorcodelist_code", titile);
        map.put("text_errorcodelist_content", info);
        return map;
    }

//报警代码
    private String errorcodeConnect(String code){
        if(MainActivity.doorStatus.revolvingdoortype){
            if(code.equals("01")){
                return("急停按扭被按下");
            }else if(code.equals("02")){
                return("危险区1夹人");
            }else if(code.equals("03")){
                return("危险区2夹人");
            }else if(code.equals("04")){
                return("门翼1撞人");
            }else if(code.equals("05")){
                return("门翼2撞人");
            }else if(code.equals("06")){
                return("展箱1撞人");
            }else if(code.equals("07")){
                return("展箱2撞人");
            }else if(code.equals("08")){
                return("编码器/定位开关故障");
            }else{
                return ("正常运行");
            }
        }else {
            if(code.equals("01")){
                return("自动门自学习");
            }else if(code.equals("02")){
                return("自动门自恢复");
            }else if(code.equals("03")){
                return("自动门电压过低");
            }else if(code.equals("04")){
                return("自动门开门撞人");
            }else if(code.equals("05")){
                return("自动门关门撞人");
            }else if(code.equals("06")){
                return("自动门温度报警");
            }else if(code.equals("07")){
                return("自动门电锁异常");
            }else if(code.equals("08")){
                return("自动门温度过热");
            }else if(code.equals("09")){
                return("自动门电机过热");
            }else if(code.equals("0A")){
                return("自动门硬件过流");
            }else if(code.equals("0B")){
                return("自动门霍尔传感器故障");
            }else if(code.equals("0C")){
                return("自动门未找到中心定位信号");
            }else if(code.equals("0D")){
                return("自动门无刷电机卡死");
            }else if(code.equals("0E")){
                return("自动门CAN通信错误");
            }else if(code.equals("0F")){
                return("自动门电机故障");
            }else if(code.equals("10")){
                return("自动门行程错误");
            }else if(code.equals("11")){
                return("自动门试用期结束");
            }else if(code.equals("12")){
                return("自动门硬件故障");
            }else if(code.equals("13")){
                return("------");
            }else if(code.equals("14")){
                return("自动门编码器反向复位");
            }else if(code.equals("15")){
                return("自动门行程错误复位");
            }else if(code.equals("16")){
                return("自动门编码器故障");
            }else if(code.equals("17")){
                return("自动门人工复位");
            }else if(code.equals("18")){
                return("自动门DIP拨动复位");
            }else if(code.equals("19")){
                return("自动门闭锁失败");
            }else if(code.equals("1A")){
                return("自动门锁被撬开");
            }else if(code.equals("20")){
                return("自动门系统被禁止");
            }else{
                return ("正常运行");
            }
        }
    }


    private char[] IntegertoChar(int data){
        char[] temp = {0xFF,0xFF};
        temp[1] &= ((char)data);
        temp[0] &= ((char)data >> 8);
        return temp;
    }

}
