package com.lafaya.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zl.reik.dilatingdotsprogressbar.DilatingDotDrawable;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

/**
 * Created by JeffYoung on 2016/10/31.
 */
public class PageHomelayout {
    private ImageButton button_home_light,button_home_door,button_home_lock;
//    private boolean light_on_flage = false,door_connect_flage = false,lock_on_flage = false;
    private ImageView image_home_mode;
    private TextView text_home_mode;

    private TextView text_home_error_name,text_home_error_number;
    private ImageView home_error_image;

    private DilatingDotsProgressBar checkstatusprogress;

    private int[] modeimage = new int[]{
            R.drawable.icon_big_mode_normal,R.drawable.icon_big_mode_summer,R.drawable.icon_big_mode_winter,
            R.drawable.icon_big_mode_auto,R.drawable.icon_big_mode_exit,R.drawable.icon_big_mode_open,
            R.drawable.icon_big_mode_manual,R.drawable.icon_big_mode_check};
    private String[] modetext = new String[]{
            "常转模式","夏季模式","冬季模式",
            "自动模式","单向模式","常开模式",
            "手动模式","自测模式"
    };

    private int[] lafayamodeimage = new int[]{
            R.drawable.icon_big_mode_auto,R.drawable.icon_big_mode_open,R.drawable.icon_big_mode_close,
            R.drawable.icon_big_mode_exit};
    private String[] lafayamodetext = new String[]{
            "自动模式","常开模式","常闭模式","单向模式"
    };

    public boolean home_check_flag = false;
    public int home_check_list = 0;//0=OK,1==查询运行模式,2==查询故障代码,3==查询锁定状态, 4==查询灯光状态, 5==查询门类型,
    public int home_recheck_count = 0;

    private Activity activity;
    private Handler handler;


    //layout 配置
    public void activityHomelayout( Activity inactivity, Handler inhandler){
        activity = inactivity;
        handler = inhandler;

        button_home_door = (ImageButton)activity.findViewById(R.id.button_home_door);
        button_home_light = (ImageButton)activity.findViewById(R.id.button_home_light);
        button_home_lock = (ImageButton)activity.findViewById(R.id.button_home_lock);

        image_home_mode = (ImageView)activity.findViewById(R.id.image_home_mode);
        text_home_mode = (TextView)activity.findViewById(R.id.text_home_mode);

        checkstatusprogress = (DilatingDotsProgressBar)activity.findViewById(R.id.checkstatusprogress);
        checkstatusprogress.hideNow();

        text_home_error_name = (TextView)activity.findViewById(R.id.text_home_error_name);
        text_home_error_number = (TextView)activity.findViewById(R.id.text_home_error_number);
        home_error_image = (ImageView)activity.findViewById(R.id.home_error_image);



        //灯光按扭
        button_home_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用发送函数
                if(home_check_list == 0){
                    MainActivity.doorStatus.lightTurnOn_Off(activity,true);
                }
            }
        });
        //连接设备
        button_home_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到设备选择
                if(MainActivity.doorStatus.door_connect){
                    MainActivity.bluetoothComm.connectDevice(activity,false,handler);
                }else{
                    if(home_check_list == 0) {
                        MainActivity.bluetoothComm.connectDevice(activity, true, handler);
                    }
                }
            }
        });
        //锁定设置
        button_home_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(home_check_list == 0){
                    //调用发送函数
                    MainActivity.doorStatus.lockTurnOn_Off(activity,true);
                }

            }
        });
        //
        image_home_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!home_check_flag){
                    CheckStatusStart(handler);
                }
            }
        });

        // connect door button's  image change
        button_home_door.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.door_off));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.door_on));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.door_on));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.door_off));
                    }
                }
                return false;
            }
        });

        // connect door button's  image change
        button_home_light.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.light_off));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.light_on));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.light_on));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.light_off));
                    }
                }
                return false;
            }
        });

        // connect door button's  image change
        button_home_lock.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_lock_off));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_lock_on));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(MainActivity.doorStatus.door_connect){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_lock_on));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_lock_off));
                    }
                }
                return false;
            }
        });
    }

    //界面更新
    public void UpdateHomelayout(){
        if(MainActivity.doorStatus.revolvingdoortype) {
            image_home_mode.setImageResource(modeimage[MainActivity.doorStatus.modecurrent]);
            text_home_mode.setText(modetext[MainActivity.doorStatus.modecurrent]);
        }else{
            image_home_mode.setImageResource(lafayamodeimage[MainActivity.doorStatus.modecurrent]);
            text_home_mode.setText(lafayamodetext[MainActivity.doorStatus.modecurrent]);
        }

        if(MainActivity.doorStatus.door_connect){
            button_home_door.setImageResource(R.drawable.door_on);
        }else{
            button_home_door.setImageResource(R.drawable.door_off);
        }
        if(MainActivity.doorStatus.revolvingdoortype) {
            button_home_light.setVisibility(View.VISIBLE);
            button_home_lock.setVisibility(View.VISIBLE);
            if (MainActivity.doorStatus.light_status) {
                button_home_light.setImageResource(R.drawable.light_on);
            } else {
                button_home_light.setImageResource(R.drawable.light_off);
            }
            if (MainActivity.doorStatus.lock_status) {
                button_home_lock.setImageResource(R.drawable.lock_on);
            } else {
                button_home_lock.setImageResource(R.drawable.lock_off);
            }
        }else {
            button_home_light.setVisibility(View.GONE);
            button_home_lock.setVisibility(View.GONE);
        }
        ShowErrorcode();

    }

    //启动初始化查询
    public void CheckStatusStart(final Handler handler){

        home_check_flag = true;
        home_check_list = 1;
        home_recheck_count = 0;
        checkstatusprogress.showNow();
        //发送数据
        MainActivity.doorStatus.runmodeSet_Qeury(activity,false,MainActivity.doorStatus.modecurrent);

    }
/*
    //重新查询
    public boolean reCheckStatus(){
        MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
        checkstatusprogress.showNow();
        //Toast.makeText(activity, Integer.toString(home_check_list), Toast.LENGTH_LONG).show();
        switch (home_check_list) {
            case 1:
                MainActivity.doorStatus.runmodeSet_Qeury(activity,false,MainActivity.doorStatus.modecurrent);
                home_recheck_count++;
                break;
            case 2:
                MainActivity.doorStatus.errorQuery(activity,false);
                home_recheck_count++;
                break;
            case 3:
                MainActivity.doorStatus.lockTurnOn_Off(activity,false);
                home_recheck_count++;
                break;
            case 4:
                MainActivity.doorStatus.lightTurnOn_Off(activity,false);
                home_recheck_count++;
                break;
            default:
                break;
        }

        if (home_recheck_count > 10) {
            CleanCheckstatus();
            return true;
        }else{
            return false;
        }
    }*/
    //收到返回时检测
    public void receiveStatus(){
        //checkstatusprogress.showNow();
        switch (home_check_list){
            case 1:
                MainActivity.doorStatus.errornowQuery(activity);
                home_recheck_count = 0;
                home_check_list  = 2;
                break;
            case 2:
                if(MainActivity.doorStatus.revolvingdoortype) {
                    MainActivity.doorStatus.lockTurnOn_Off(activity, false);
                    home_recheck_count = 0;
                    home_check_list = 3;
                }else{
                    CleanCheckstatus();
                }
                break;
            case 3:
                MainActivity.doorStatus.lightTurnOn_Off(activity,false);
                home_recheck_count = 0;
                home_check_list = 4;
                break;
            case 4:
                CleanCheckstatus();
                break;
            default:
                break;
        }
    }
    //清除查询状态
    public void CleanCheckstatus(){

        home_check_flag = false;
        home_recheck_count = 0;
        home_check_list = 0;
        if(MainActivity.doorStatus.plc_communication_flag != DoorStatus.PLC_COMMUNICATION_FREE){
            MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
        }
        if(MainActivity.bluetoothComm.sendwaittime_flag){
            MainActivity.bluetoothComm.sendwaittime.cancel();
            MainActivity.bluetoothComm.sendwaittime_flag = false;
        }

        checkstatusprogress.hideNow();
    }

    //报警代码显示
    public void ShowErrorcode(){
        if(MainActivity.doorStatus.revolvingdoortype) {
            if (MainActivity.doorStatus.error_code.equals("01")) {
                text_home_error_name.setText("急停按扭被按下");
                text_home_error_number.setText("——01——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("02")) {
                text_home_error_name.setText("危险区1夹人");
                text_home_error_number.setText("——02——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("03")) {
                text_home_error_name.setText("危险区2夹人");
                text_home_error_number.setText("——03——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("04")) {
                text_home_error_name.setText("门翼1撞人");
                text_home_error_number.setText("——04——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("05")) {
                text_home_error_name.setText("门翼2撞人");
                text_home_error_number.setText("——05——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("06")) {
                text_home_error_name.setText("展箱1撞人");
                text_home_error_number.setText("——06——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("07")) {
                text_home_error_name.setText("展箱2撞人");
                text_home_error_number.setText("——07——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("08")) {
                text_home_error_name.setText("编码器/定位开关故障");
                text_home_error_number.setText("——08——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else {
                text_home_error_name.setText("正常运行");
                text_home_error_number.setText("——00——");
                home_error_image.setImageResource(R.drawable.error_no);
            }
        }else {
            if (MainActivity.doorStatus.error_code.equals("01")) {
                text_home_error_name.setText("自动门自学习");
                text_home_error_number.setText("——01——");
                home_error_image.setImageResource(R.drawable.error_no);
            } else if (MainActivity.doorStatus.error_code.equals("02")) {
                text_home_error_name.setText("自动门自恢复");
                text_home_error_number.setText("——02——");
                home_error_image.setImageResource(R.drawable.error_no);
            } else if (MainActivity.doorStatus.error_code.equals("03")) {
                text_home_error_name.setText("自动门电压过低");
                text_home_error_number.setText("——03——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("04")) {
                text_home_error_name.setText("自动门开门撞人");
                text_home_error_number.setText("——04——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("05")) {
                text_home_error_name.setText("自动门关门撞人");
                text_home_error_number.setText("——05——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("06")) {
                text_home_error_name.setText("自动门温度报警");
                text_home_error_number.setText("——06——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("07")) {
                text_home_error_name.setText("自动门电锁异常");
                text_home_error_number.setText("——07——");
                home_error_image.setImageResource(R.drawable.error_have);
            } else if (MainActivity.doorStatus.error_code.equals("09")) {
                text_home_error_name.setText("自动门电机过热");
                text_home_error_number.setText("——09——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0A")) {
                text_home_error_name.setText("自动门硬件过流");
                text_home_error_number.setText("——0A——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0B")) {
                text_home_error_name.setText("自动门霍尔传感器故障");
                text_home_error_number.setText("——0B——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0C")) {
                text_home_error_name.setText("自动门未找到中心定位信号");
                text_home_error_number.setText("——0C——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0D")) {
                text_home_error_name.setText("自动门无刷电机卡死");
                text_home_error_number.setText("——0D——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0E")) {
                text_home_error_name.setText("自动门CAN通信错误");
                text_home_error_number.setText("——0E——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("0F")) {
                text_home_error_name.setText("自动门电机故障");
                text_home_error_number.setText("——0F——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("10")) {
                text_home_error_name.setText("自动门行程错误");
                text_home_error_number.setText("——10——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("11")) {
                text_home_error_name.setText("自动门试用期结束");
                text_home_error_number.setText("——11——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("12")) {
                text_home_error_name.setText("自动门硬件故障");
                text_home_error_number.setText("——12——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("14")) {
                text_home_error_name.setText("自动门编码器反向复位");
                text_home_error_number.setText("——14——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("15")) {
                text_home_error_name.setText("自动门行程错误复位");
                text_home_error_number.setText("——15——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("16")) {
                text_home_error_name.setText("自动门编码器故障");
                text_home_error_number.setText("——16——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("17")) {
                text_home_error_name.setText("自动门人工复位");
                text_home_error_number.setText("——17——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("18")) {
                text_home_error_name.setText("自动门DIP拨动复位");
                text_home_error_number.setText("——18——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("19")) {
                text_home_error_name.setText("自动门闭锁失败");
                text_home_error_number.setText("——19——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("1A")) {
                text_home_error_name.setText("自动门锁被撬开");
                text_home_error_number.setText("——1A——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else if (MainActivity.doorStatus.error_code.equals("20")) {
                text_home_error_name.setText("自动门系统被禁止");
                text_home_error_number.setText("——20——");
                home_error_image.setImageResource(R.drawable.error_have);
            }else {
                text_home_error_name.setText("正常运行");
                text_home_error_number.setText("——00——");
                home_error_image.setImageResource(R.drawable.error_no);
            }
        }
    }


}
