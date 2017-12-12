package com.lafaya.toolbox;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ioani on 2016/11/29.
 **/

public class ParameterLafaya {

    //sliding door
    private AutoCountListView list_parameter_slidingdoor;
    private AutoCountListView list_parameter_slidingdoor_control;
    private AutoCountListView list_parameter_slidingdoor_current;
    private AutoCountListView list_parameter_slidingdoor_distance;
    private AutoCountListView list_parameter_slidingdoor_pwm;
    private AutoCountListView list_parameter_slidingdoor_peri;
    private AutoCountListView list_parameter_wingleft;
    private AutoCountListView list_parameter_wingright;

    private boolean slidingdoor_check_flag = false;
    private int slidingdoor_check_status = 0;
    private int valuetemp = 0;

    // communication flag, when communicating - - - true.
    private boolean communication_query_flag = false;
    private int parameters_control_statues = 0;
    //control parameters
    private int value_speed_crawl = 0;
    private int value_smoothness = 0;
    private int value_keepmodeopen_time = 0;
    private int value_speed_min = 0;
    private int value_drop_rate = 0;
    private int value_barrier_interval = 0;
    private int value_barrier_times = 0;
    private int value_temperature_upper = 0;
    private int value_temperature_lower = 0;
    private int value_speed_lock = 0;

    //peripheral parameters
    private int parameters_peri_statues = 0;
    private int value_lock_delay = 0;
    private int value_lock_retry = 0;
    private int value_lock_retrytime = 0;
    private int value_testlock_frequery = 0;
    private int value_battery_enable = 0;

    //current parameters
    private int parameters_current_statues = 0;
    private int value_current_open = 0;
    private int value_current_close = 0;
    private int value_current_max = 0;
    private int value_current_one = 0;
    private int value_current_two = 0;
    private int value_current_three = 0;
    private int value_current_four = 0;

    //distance parameters
    private int parameters_distance_statues = 0;
    private int value_distance_opencrawl = 0;
    private int value_distance_closecrawl = 0;
    private int value_distance_openend = 0;
    private int value_distance_closeend = 0;
    private int value_distance_holdupper = 0;
    private int value_distance_holdlower = 0;
    private int value_distance_openmovelow = 0;


    private int parameters_pwm_statues = 0;
    private int value_pwm_pid_period = 0;
    private int value_pwm_pid_drive = 0;
    private int value_pwm_pid_recall = 0;
    private int value_pwm_pid_brake = 0;
    private int value_pwm_pid_reverse = 0;
    private int value_pwm_max = 0;
    private int value_pwm_min = 0;
    private int value_pwm_uniform_rate = 0;
    private int value_pwm_brake_rate = 0;
    private int value_pwm_crawllow_rate = 0;
    private int value_pwm_reverseflag = 0;

    private Button button_parameter_lafayacontrol,button_parameter_lafayaperi,button_parameter_lafayacurrent,
            button_parameter_lafayadistance,button_parameter_lafayapwm;

    private int parameters_lafaya_button_flag = 0;

    Activity activity;
    public void initializeParameterLafaya(Activity inactivity){
        activity = inactivity;
        //sliding door
        list_parameter_slidingdoor = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor);
        list_parameter_slidingdoor_control = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor_control);
        list_parameter_slidingdoor_peri = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor_peri);
        list_parameter_slidingdoor_current = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor_current);
        list_parameter_slidingdoor_distance = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor_distance);
        list_parameter_slidingdoor_pwm = (AutoCountListView)activity.findViewById(R.id.list_parameter_slidingdoor_pwm);

        button_parameter_lafayacontrol = (Button)activity.findViewById(R.id.button_parameter_lafayacontrol);
        button_parameter_lafayaperi = (Button)activity.findViewById(R.id.button_parameter_lafayaperi);
        button_parameter_lafayacurrent = (Button)activity.findViewById(R.id.button_parameter_lafayacurrent);
        button_parameter_lafayadistance = (Button)activity.findViewById(R.id.button_parameter_lafayadistance);
        button_parameter_lafayapwm = (Button)activity.findViewById(R.id.button_parameter_lafayapwm);


        list_parameter_wingleft = (AutoCountListView)activity.findViewById(R.id.list_parameter_wingleft);
        list_parameter_wingright = (AutoCountListView)activity.findViewById(R.id.list_parameter_wingright);
        SlidingParametersShow();

        //sliding door parameters selected listener
        list_parameter_slidingdoor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Slidingparametersselect(pos);
            }
        });
        button_listener();
        list_listener();

        //left wing parameters selected listener
        list_parameter_wingleft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Wingleftparametersselect(pos);
            }
        });

        //right wing parameters selected listener
        list_parameter_wingright.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Wingrightparametersselect(pos);
            }
        });

    }
    // button listener
    private void button_listener(){
        // control parameters button, when pressed,update control parameters
        button_parameter_lafayacontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameters_lafaya_button_flag == 1){
                    parameters_lafaya_button_flag = 0;
                }else if(!communication_query_flag){
                    parameters_lafaya_button_flag = 1;
                    QueryParameterscontrol();
                }
                SlidingParametersbuttonShow();
            }
        });

        button_parameter_lafayaperi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameters_lafaya_button_flag == 2){
                    parameters_lafaya_button_flag = 0;
                }else if(!communication_query_flag){
                    parameters_lafaya_button_flag = 2;
                    QueryParametersperi();
                }
                SlidingParametersbuttonShow();
            }
        });

        button_parameter_lafayacurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameters_lafaya_button_flag == 3){
                    parameters_lafaya_button_flag = 0;
                }else if(!communication_query_flag){
                    parameters_lafaya_button_flag = 3;
                    QueryParameterscurrent();
                }
                SlidingParametersbuttonShow();
            }
        });

        button_parameter_lafayadistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameters_lafaya_button_flag == 4){
                    parameters_lafaya_button_flag = 0;
                }else if(!communication_query_flag){
                    parameters_lafaya_button_flag = 4;
                    QueryParametersdistance();
                }
                SlidingParametersbuttonShow();

            }
        });

        button_parameter_lafayapwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parameters_lafaya_button_flag == 5){
                    parameters_lafaya_button_flag = 0;
                }else if(!communication_query_flag){
                    parameters_lafaya_button_flag = 5;
                    QueryParameterspwm();
                }
                SlidingParametersbuttonShow();
            }
        });
    }

    // list listener
    private void list_listener(){
        //control parameters selected listener
        list_parameter_slidingdoor_control.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(!communication_query_flag) {
                    SetParameterscontrol(pos);
                }
            }
        });
        //peri parameters selected listener
        list_parameter_slidingdoor_peri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(!communication_query_flag) {
                    SetParametersperi(pos);
                }
            }
        });
        //current parameters selected listener
        list_parameter_slidingdoor_current.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(!communication_query_flag) {
                    SetParameterscurrent(pos);
                }
            }
        });
        //distance parameters selected listener
        list_parameter_slidingdoor_distance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(!communication_query_flag) {
                    SetParametersdistance(pos);
                }
            }
        });
        //pwm parameters selected listener
        list_parameter_slidingdoor_pwm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(!communication_query_flag) {
                    SetParameterspwm(pos);
                }
            }
        });
    }

    //
    public void operationLafaya_control(boolean flag){
        list_parameter_slidingdoor.setEnabled(flag);
        list_parameter_wingleft.setEnabled(flag);
        list_parameter_wingright.setEnabled(flag);

        list_parameter_slidingdoor_control.setEnabled(flag);
        list_parameter_slidingdoor_peri.setEnabled(flag);
        list_parameter_slidingdoor_current.setEnabled(flag);
        list_parameter_slidingdoor_distance.setEnabled(flag);
        list_parameter_slidingdoor_pwm.setEnabled(flag);

        button_parameter_lafayacontrol.setEnabled(flag);
        button_parameter_lafayaperi.setEnabled(flag);
        button_parameter_lafayacurrent.setEnabled(flag);
        button_parameter_lafayadistance.setEnabled(flag);
        button_parameter_lafayapwm.setEnabled(flag);


        if(flag) {
            communication_query_flag = false;
            parameters_control_statues = 0;
            parameters_peri_statues = 0;
            parameters_current_statues = 0;
            parameters_distance_statues = 0;
            parameters_pwm_statues = 0;
            SlidingParametersbuttonShow();
        }
    }

    // parameter query
    public void SlidingdoorQuery(){
        //
        if(!slidingdoor_check_flag){
            slidingdoor_check_flag = true;
            slidingdoor_check_status = 1;
            //Send message
            MainActivity.doorStatus.lafayaSpeedopen_setquery(activity,false,0,MainActivity.doorStatus.speedopen_sliding);
        }
    }

    public void Receivesetfinished(){
        switch (slidingdoor_check_status){
            case 1:
                MainActivity.doorStatus.speedopen_sliding = valuetemp;
                slidingdoor_check_status = 0;
                break;
            case 2:
                MainActivity.doorStatus.speedclose_sliding = valuetemp;
                slidingdoor_check_status = 0;
                break;
            case 3:
                MainActivity.doorStatus.keepopentime_sliding = valuetemp;
                slidingdoor_check_status = 0;
                break;
            case 4:
                MainActivity.doorStatus.openrate_sliding = valuetemp;
                slidingdoor_check_status = 0;
                break;
            default:
                break;
        }
        SlidingParametersShow();

        if(parameters_control_statues != 0){
            ReceiveParameterscontrol(Integer.toString(valuetemp));
        }else if(parameters_peri_statues != 0){
            ReceiveParametersperi(Integer.toString(valuetemp));
        }else if(parameters_current_statues != 0){
            ReceiveParameterscurrent(Integer.toString(valuetemp));
        }else if(parameters_distance_statues != 0){
            ReceiveParametersdistance(Integer.toString(valuetemp));
        }else if(parameters_pwm_statues != 0){
            ReceiveParameterspwm(Integer.toString(valuetemp));
        }
    }
    //saving
    public void parameterLafaya_Saving(String msg){
        valuetemp = Integer.parseInt(msg);
        if(slidingdoor_check_status == 1){
            MainActivity.doorStatus.lafayaSpeedopen_setquery(activity,true,0,valuetemp);
        }else if(slidingdoor_check_status == 2){
            MainActivity.doorStatus.lafayaSpeedclose_setquery(activity, true, 0, valuetemp);
        }else if(slidingdoor_check_status == 3){
            MainActivity.doorStatus.lafayaKeeptime_setquery(activity, true, 0, valuetemp);
        }else if(slidingdoor_check_status == 4){
            MainActivity.doorStatus.lafayaOpenrate_setquery(activity, true, 0, valuetemp);
        }else if(parameters_control_statues != 0){
            parameters_control_saving(valuetemp);
        }else if(parameters_peri_statues != 0){
            parameters_peri_saving(valuetemp);
        }else if(parameters_current_statues != 0){
            parameters_current_saving(valuetemp);
        }else if(parameters_distance_statues != 0){
            parameters_distance_saving(valuetemp);
        }else if(parameters_pwm_statues != 0){
            parameters_pwm_saving(valuetemp);
        }

    }
    //clear
    public void parameterclear(){
        slidingdoor_check_status = 0;
        slidingdoor_check_flag = false;
        parameters_lafaya_button_flag = 0;

        communication_query_flag = false;
        parameters_control_statues = 0;
        parameters_peri_statues = 0;
        parameters_current_statues = 0;
        parameters_distance_statues = 0;
        parameters_pwm_statues = 0;

        SlidingParametersbuttonShow();
    }

    //receive Query
    public void ReceiveQuery(String value){
        switch (slidingdoor_check_status){
            case 1:
                MainActivity.doorStatus.speedopen_sliding = Integer.parseInt(value);
                if(slidingdoor_check_flag) {
                    slidingdoor_check_status = 2;
                    MainActivity.doorStatus.lafayaSpeedclose_setquery(activity, false, 0, MainActivity.doorStatus.speedclose_sliding);
                }else{
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                    slidingdoor_check_status = 0;
                }
                break;
            case 2:
                MainActivity.doorStatus.speedclose_sliding = Integer.parseInt(value);
                if(slidingdoor_check_flag) {
                    slidingdoor_check_status = 3;
                    MainActivity.doorStatus.lafayaKeeptime_setquery(activity, false, 0, MainActivity.doorStatus.keepopentime_sliding);
                }else{
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                    slidingdoor_check_status = 0;
                }
                break;
            case 3:
                MainActivity.doorStatus.keepopentime_sliding = Integer.parseInt(value);
                if(slidingdoor_check_flag) {
                    slidingdoor_check_status = 4;
                    MainActivity.doorStatus.lafayaOpenrate_setquery(activity, false, 0, MainActivity.doorStatus.openrate_sliding);
                }else {
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                    slidingdoor_check_status = 0;
                }
                break;
            case 4:
                MainActivity.doorStatus.openrate_sliding = Integer.parseInt(value);
                if(slidingdoor_check_flag) {
                    slidingdoor_check_flag = false;
                    slidingdoor_check_status = 0;
                }else {
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                    slidingdoor_check_status = 0;
                }
                break;
            default:
                break;
        }
        SlidingParametersShow();
    }

    //Sliding door setting
    private void Slidingparametersselect(int pos){
        switch (pos){
            case 0://open speed
                if(slidingdoor_check_status == 0){
                    slidingdoor_check_status = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门速度",MainActivity.doorStatus.speedopen_max,MainActivity.doorStatus.speedopen_min,MainActivity.doorStatus.speedopen_sliding,20);
                }
                break;
            case 1://close speed
                if(slidingdoor_check_status == 0){
                    slidingdoor_check_status = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门速度",MainActivity.doorStatus.speedclose_max,MainActivity.doorStatus.speedclose_min,MainActivity.doorStatus.speedclose_sliding,25);
                }
                break;
            case 2://keep open time
                if(slidingdoor_check_status == 0){
                    slidingdoor_check_status = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门保持时间",MainActivity.doorStatus.keepopentime_max,MainActivity.doorStatus.keepopentime_min,MainActivity.doorStatus.keepopentime_sliding,1);
                }
                break;
            case 3://open rate
                if(slidingdoor_check_status == 0){
                    slidingdoor_check_status = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门开度",MainActivity.doorStatus.openrate_max,MainActivity.doorStatus.openrate_min,MainActivity.doorStatus.openrate_sliding,1);
                }
                break;
            default:
                break;
        }
    }

    // communication======================
    //control paramters query
    private void QueryParameterscontrol(){
        if(!communication_query_flag){
            communication_query_flag = true;
            // query crawl speed,
            parameters_control_statues = 1;
            //Send message, query crawl speed....
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCrawlSpeed(CommandLafaya.sendrightwingID,0,false),activity);
        }

    }
    //control parameters receive
    public void ReceiveParameterscontrol(String value){
        switch (parameters_control_statues){
            case 1://crawl speed
                value_speed_crawl = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 2;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSmooth(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 2://run smoothness
                value_smoothness = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 3;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoOpenmodeKeep(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 3://keep mode-open time
                value_keepmodeopen_time = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 4;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedThreshold(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 4://speed minimum
                value_speed_min = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedlowRate(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 5://speed drop rate
                value_drop_rate = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 6;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoObstacleReinterval(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 6://barrier interval time
                value_barrier_interval = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 7;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoObstacleRetime(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 7://barrier times
                value_barrier_times = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 8;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTemperatureUpper(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 8://temperature upper
                value_temperature_upper = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 9;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTemperatureLower(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 9://temperature lower
                value_temperature_lower = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_control_statues = 10;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedLock(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 10:
                value_speed_lock = Integer.parseInt(value);
                if(communication_query_flag){
                    communication_query_flag = false;
                    parameters_control_statues = 0;
                }else{
                    parameters_control_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            default:
                break;
        }

        SlidingParametersbuttonShow();
        if(parameters_control_statues == 0){
            list_parameter_slidingdoor_control.setEnabled(true);
        }
    }
    //
    private void parameters_control_saving(int datavalue){
        switch (parameters_control_statues){
            case 1:// speed crawl
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCrawlSpeed(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSmooth(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoOpenmodeKeep(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedThreshold(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedlowRate(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 6:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoObstacleReinterval(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 7:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoObstacleRetime(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 8:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTemperatureUpper(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 9:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTemperatureLower(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 10:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoSpeedLock(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            default:
                break;
        }
    }
    //control paramters set
    private void SetParameterscontrol(int pos){
        switch (pos){
            case 0://
                if(parameters_control_statues == 0){
                    parameters_control_statues = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"爬行速度",60,10,value_speed_crawl,0.5f);
                }
                break;
            case 1:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"平稳度",10,1,value_smoothness,1);
                }
                break;
            case 2:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"常开模式保持时间",100,0,value_keepmodeopen_time,1);
                }
                break;
            case 3:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"门体最小速度",10,1,value_speed_min,1);
                }
                break;
            case 4:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 5;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"速度跌落比例",90,30,value_drop_rate,1);
                }
                break;
            case 5:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 6;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"遇阻重试间隔",10,1,value_barrier_interval,1);
                }
                break;
            case 6:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 7;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"遇阻重试次数",10,1,value_barrier_times,1);
                }
                break;
            case 7:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 8;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"超温报警上限",100,value_temperature_lower,value_temperature_upper,1);
                }
                break;
            case 8:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 9;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"超温报警下限",value_temperature_upper,40,value_temperature_lower,1);
                }
                break;
            case 9:
                if(parameters_control_statues == 0){
                    parameters_control_statues = 10;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"速度锁定",1,0,value_speed_lock,1);
                }
                break;
            default:
                break;
        }
        if(parameters_control_statues != 0){
            list_parameter_slidingdoor_control.setEnabled(false);
        }
    }
    //==
    //peri paramters query
    private void QueryParametersperi(){
        if(!communication_query_flag){
            communication_query_flag = true;
            // lock move keep time
            parameters_peri_statues = 1;
            //Send message, query crawl speed....
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockDelay(CommandLafaya.sendrightwingID,0,false),activity);
        }
    }
    //peri parameters receive
    public void ReceiveParametersperi(String value){
        switch (parameters_peri_statues){
            case 1://lock move keep time
                value_lock_delay = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_peri_statues = 2;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockRetry(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_peri_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 2://lock retry times
                value_lock_retry = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_peri_statues = 3;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockRetime(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_peri_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 3://lock retry interval
                value_lock_retrytime = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_peri_statues = 4;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTestmodeLockrate(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_peri_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 4://when testing, lcok motion frequery
                value_testlock_frequery = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_peri_statues = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoStandbypowerEnable(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_peri_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 5://battery statue
                value_battery_enable = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_peri_statues = 0;
                    communication_query_flag = false;
                }else{
                    parameters_peri_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            default:
                break;
        }

        SlidingParametersbuttonShow();
        if(parameters_peri_statues == 0){
            list_parameter_slidingdoor_peri.setEnabled(true);
        }
    }
    private void parameters_peri_saving(int datavalue){
        switch (parameters_peri_statues){
            case 1:// speed crawl
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockDelay(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockRetry(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLockRetime(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTestmodeLockrate(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoStandbypowerEnable(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            default:
                break;
        }
    }
    //peri paramters set
    private void SetParametersperi(int pos){
        switch (pos){
            case 0://
                if(parameters_peri_statues == 0){
                    parameters_peri_statues = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"锁动作延时",10,0,value_lock_delay,1);
                }
                break;
            case 1:
                if(parameters_peri_statues == 0){
                    parameters_peri_statues = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"锁重试次数",10,1,value_lock_retry,1);
                }
                break;
            case 2:
                if(parameters_peri_statues == 0){
                    parameters_peri_statues = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"锁重试间隔",10,1,value_lock_retrytime,1);
                }
                break;
            case 3:
                if(parameters_peri_statues == 0){
                    parameters_peri_statues = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"测试模式落锁频率",100,10,value_testlock_frequery,1);
                }
                break;
            case 4:
                if(parameters_peri_statues == 0){
                    parameters_peri_statues = 5;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"备用电源使能",1,0,value_battery_enable,1);
                }
                break;
            default:
                break;
        }
        if(parameters_peri_statues != 0){
            list_parameter_slidingdoor_peri.setEnabled(false);
        }
    }

    //==
    //current paramters query
    private void QueryParameterscurrent(){
        if(!communication_query_flag){
            communication_query_flag = true;
            // keep open current
            parameters_current_statues = 1;
            //Send message, query keep current....
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsOpen(CommandLafaya.sendrightwingID,0,false),activity);
        }
    }
    //current parameters receive
    public void ReceiveParameterscurrent(String value){
        switch (parameters_current_statues){
            case 1://keep open current
                value_current_open = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 2;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsClose(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 2://keep close current
                value_current_close = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 3;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsMax(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 3://current max
                value_current_max = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 4;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents1(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 4://current one
                value_current_one = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents2(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 5://current two
                value_current_two = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 6;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents3(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 6://current three
                value_current_three = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 7;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents4(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 7://current four
                value_current_four = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_current_statues = 0;
                    communication_query_flag = false;
                }else{
                    parameters_current_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            default:
                break;
        }

        SlidingParametersbuttonShow();
        if(parameters_current_statues == 0){
            list_parameter_slidingdoor_current.setEnabled(true);
        }
    }
    //save current parameters
    private void parameters_current_saving(int datavalue){
        switch (parameters_current_statues){
            case 1:// speed crawl
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsOpen(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsClose(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentsMax(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents1(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents2(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 6:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents3(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 7:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrents4(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            default:
                break;
        }
    }
    //current paramters set
    private void SetParameterscurrent(int pos){
        switch (pos){
            case 0://
                if(parameters_current_statues == 0){
                    parameters_current_statues = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门保持电流",500,100,value_current_open,10);
                }
                break;
            case 1:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门保持电流",500,100,value_current_close,10);
                }
                break;
            case 2:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"最大允许电流",15000,5000,value_current_max,1000);
                }
                break;
            case 3:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"第一档电流限流",10000,1000,value_current_one,100);
                }
                break;
            case 4:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 5;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"第二档电流限流",10000,1000,value_current_two,1);
                }
                break;
            case 5:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 6;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"第三档电流限流",10000,1000,value_current_three,1);
                }
                break;
            case 6:
                if(parameters_current_statues == 0){
                    parameters_current_statues = 7;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"第四档电流限流",10000,1000,value_current_four,1);
                }
                break;
            default:
                break;
        }
        if(parameters_current_statues != 0){
            list_parameter_slidingdoor_current.setEnabled(false);
        }
    }

    //===
    //distance paramters query
    private void QueryParametersdistance(){
        if(!communication_query_flag){
            communication_query_flag = true;

            parameters_distance_statues = 1;
            //Send message, query open crawl distance....
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistancelowOpen(CommandLafaya.sendrightwingID,0,false),activity);
        }
    }
    //distance parameters receive
    public void ReceiveParametersdistance(String value){
        switch (parameters_distance_statues){
            case 1://keep open current
                value_distance_opencrawl = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_distance_statues = 2;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistancelowClose(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 2://keep close current
                value_distance_closecrawl = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_distance_statues = 3;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistanceendOpen(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 3://current max
                value_distance_openend = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_distance_statues = 4;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistanceendClose(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 4://current one
                value_distance_closeend = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_distance_statues = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoKeepcloseUpper(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 5://current two
                value_distance_holdupper = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_distance_statues = 6;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoKeepcloseLower(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 6://current three
                value_distance_holdlower = Integer.parseInt(value);
                if(communication_query_flag){
                    communication_query_flag = false;
                    parameters_distance_statues = 0;
                    //parameters_distance_statues = 7;
                    //MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLowspeedOpen(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 7://current four
                value_distance_openmovelow = Integer.parseInt(value);
                if(communication_query_flag){
                    communication_query_flag = false;
                    parameters_distance_statues = 0;
                }else{
                    parameters_distance_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            default:
                break;
        }

        SlidingParametersbuttonShow();
        if(parameters_distance_statues == 0){
            list_parameter_slidingdoor_distance.setEnabled(true);
        }
    }
    private void parameters_distance_saving(int datavalue){
        switch (parameters_distance_statues){
            case 1:// speed crawl
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistancelowOpen(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistancelowClose(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistanceendOpen(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoDistanceendClose(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoKeepcloseUpper(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 6:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoKeepcloseLower(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 7:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoLowspeedOpen(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            default:
                break;
        }
    }
    //distance paramters set
    private void SetParametersdistance(int pos){
        switch (pos){
            case 0://
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门爬行距离",5000,100,value_distance_opencrawl,10);
                }
                break;
            case 1:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门爬行距离",5000,100,value_distance_closecrawl,10);
                }
                break;
            case 2:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门末段距离",5000,100,value_distance_openend,10);
                }
                break;
            case 3:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门末段距离",5000,100,value_distance_closeend,10);
                }
                break;
            case 4:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 5;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门保持距离上限",5000,value_distance_holdlower,value_distance_holdupper,1);
                }
                break;
            case 5:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 6;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"关门保持距离下限",value_distance_holdupper,100,value_distance_holdlower,1);
                }
                break;
            case 6:
                if(parameters_distance_statues == 0){
                    parameters_distance_statues = 7;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"开门强制缓行距离",10000,100,value_distance_openmovelow,1);
                }
                break;
            default:
                break;
        }
        if(parameters_distance_statues != 0){
            list_parameter_slidingdoor_distance.setEnabled(false);
        }
    }

    //pwm paramters query
    private void QueryParameterspwm(){
        if(!communication_query_flag){
            communication_query_flag = true;

            parameters_pwm_statues = 1;
            //Send message, query pid updater period..
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDFrequency(CommandLafaya.sendrightwingID,0,false),activity);
        }
    }
    //pwm parameters receive
    public void ReceiveParameterspwm(String value){
        switch (parameters_pwm_statues){
            case 1://keep open current
                value_pwm_pid_period = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 2;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDDrive(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 2://keep close current
                value_pwm_pid_drive = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 3;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDRetract(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 3://current max
                value_pwm_pid_recall = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 4;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDBreak(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 4://current one
                value_pwm_pid_brake = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDReverse(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 5://current two
                value_pwm_pid_reverse = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 6;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoMaxPWM(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 6://current three
                value_pwm_max = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 7;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoMinPWM(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 7://current three
                value_pwm_min = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 8;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedUniform(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 8://current three
                value_pwm_uniform_rate = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 9;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedBreak(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 9://current three
                value_pwm_brake_rate = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 10;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedLow(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 10://current three
                value_pwm_crawllow_rate = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 11;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoEnterReverse(CommandLafaya.sendrightwingID,0,false),activity);
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            case 11://current four
                value_pwm_reverseflag = Integer.parseInt(value);
                if(communication_query_flag){
                    parameters_pwm_statues = 0;
                    communication_query_flag = false;
                }else{
                    parameters_pwm_statues = 0;
                    MainActivity.pageParameterlayout.parameterSavefinished(2);
                }
                break;
            default:
                break;
        }

        SlidingParametersbuttonShow();
        if(parameters_pwm_statues == 0){
            list_parameter_slidingdoor_pwm.setEnabled(true);
        }
    }
    // save
    private void parameters_pwm_saving(int datavalue){
        switch (parameters_distance_statues){
            case 1:// speed crawl
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDFrequency(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDDrive(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDRetract(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDBreak(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPIDReverse(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 6:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoMaxPWM(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 7:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoMinPWM(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 8:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedUniform(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 9:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedBreak(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 10:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoPWMSpeedLow(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            case 11:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoEnterReverse(CommandLafaya.sendrightwingID,datavalue,true),activity);
                break;
            default:
                break;
        }
    }
    //pwm paramters set
    private void SetParameterspwm(int pos){
        switch (pos){
            case 0://
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 1;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"PID调节周期",100,1,value_pwm_pid_period,1);
                }
                break;
            case 1:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 2;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"PID驱动系数",30,1,value_pwm_pid_drive,1);
                }
                break;
            case 2:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 3;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"PID撒力系数",100,1,value_pwm_pid_recall,1);
                }
                break;
            case 3:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 4;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"PID刹车系数",100,1,value_pwm_pid_brake,1);
                }
                break;
            case 4:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 5;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"PID反向系数",30,1,value_pwm_pid_reverse,1);
                }
                break;
            case 5:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 6;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"最大PWM",400,50,value_pwm_max,1);
                }
                break;
            case 6:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 7;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"最小PWM",50,0,value_pwm_min,1);
                }
                break;
            case 7:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 8;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"匀超不减PWM比例",255,5,value_pwm_uniform_rate,2.5f);
                }
                break;
            case 8:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 9;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"刹低不增PWM比例",255,5,value_pwm_brake_rate,2.5f);
                }
                break;
            case 9:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 10;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"爬末不减PWM比例",255,5,value_pwm_crawllow_rate,2.5f);
                }
                break;
            case 10:
                if(parameters_pwm_statues == 0){
                    parameters_pwm_statues = 11;
                    MainActivity.pageParameterlayout.parameter_dialog(true,"反向刹车",1,0,value_pwm_reverseflag,1);
                }
                break;
            default:
                break;
        }
        if(parameters_pwm_statues != 0){
            list_parameter_slidingdoor_pwm.setEnabled(false);
        }
    }


    // show
    private void SlidingParametersShow(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("开门速度",Integer.toString(MainActivity.doorStatus.speedopen_sliding)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("关门速度",Integer.toString(MainActivity.doorStatus.speedclose_sliding)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("开门保持时间",Integer.toString(MainActivity.doorStatus.keepopentime_sliding)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("开门开度",Integer.toString(MainActivity.doorStatus.openrate_sliding)+" %"));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor);
        }
    }

    private void SlidingParametersbuttonShow(){
        int[] array_visiblity = new int[]{View.GONE,View.GONE,View.GONE,View.GONE,View.GONE};
        int[] array_color = new int[]{activity.getResources().getColor(R.color.transparent),activity.getResources().getColor(R.color.transparent),activity.getResources().getColor(R.color.transparent),
                activity.getResources().getColor(R.color.transparent),activity.getResources().getColor(R.color.transparent)};
        if(parameters_lafaya_button_flag != 0){
            array_visiblity[parameters_lafaya_button_flag-1] = View.VISIBLE;
            array_color[parameters_lafaya_button_flag-1] = activity.getResources().getColor(R.color.colorBackground);
        }else {
            communication_query_flag = false;
            parameters_control_statues = 0;
            parameters_peri_statues = 0;
            parameters_current_statues = 0;
            parameters_distance_statues = 0;
            parameters_pwm_statues = 0;
        }
        list_parameter_slidingdoor_control.setVisibility(array_visiblity[0]);
        list_parameter_slidingdoor_peri.setVisibility(array_visiblity[1]);
        list_parameter_slidingdoor_current.setVisibility(array_visiblity[2]);
        list_parameter_slidingdoor_distance.setVisibility(array_visiblity[3]);
        list_parameter_slidingdoor_pwm.setVisibility(array_visiblity[4]);
        button_parameter_lafayacontrol.setBackgroundColor(array_color[0]);
        button_parameter_lafayaperi.setBackgroundColor(array_color[1]);
        button_parameter_lafayacurrent.setBackgroundColor(array_color[2]);
        button_parameter_lafayadistance.setBackgroundColor(array_color[3]);
        button_parameter_lafayapwm.setBackgroundColor(array_color[4]);

        SlidingParametersShow_Control();
        SlidingParametersShow_peri();
        SlidingParametersShow_current();
        SlidingParametersShow_distance();
        SlidingParametersShow_pwm();

    }
    //控制参数
    private void SlidingParametersShow_Control(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("爬行速度",Integer.toString(value_speed_crawl)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("平稳度",Integer.toString(value_smoothness)));
        list.add(PageParameterlayout.getparameterlistViewData("常开模式保持时间",Integer.toString(value_keepmodeopen_time)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("门体最小速度",Integer.toString(value_speed_min)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("速度跌落比例",Integer.toString(value_drop_rate)+" %"));
        list.add(PageParameterlayout.getparameterlistViewData("遇阻重试间隔",Integer.toString(value_barrier_interval)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("遇阻重试次数",Integer.toString(value_barrier_times)+" 次"));
        list.add(PageParameterlayout.getparameterlistViewData("超温报警上限",Integer.toString(value_temperature_upper)+" ℃"));
        list.add(PageParameterlayout.getparameterlistViewData("超温报警下限",Integer.toString(value_temperature_lower)+" ℃"));
        list.add(PageParameterlayout.getparameterlistViewData("速度锁定设置",Integer.toString(value_speed_lock)));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor_control.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor_control);
        }
    }

    //外设参数
    private void SlidingParametersShow_peri(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("锁动作延时",Integer.toString(value_lock_delay)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("锁重试次数",Integer.toString(value_lock_retry)+" 次"));
        list.add(PageParameterlayout.getparameterlistViewData("锁重试间隔",Integer.toString(value_lock_retrytime)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("测试模式落锁频率",Integer.toString(value_testlock_frequery)+" %"));
        list.add(PageParameterlayout.getparameterlistViewData("备用电源",Integer.toString(value_battery_enable)));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor_peri.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor_peri);
        }
    }

    //电流参数
    private void SlidingParametersShow_current(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("开门保持电流",Integer.toString(value_current_open)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("关门保持电流",Integer.toString(value_current_close)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("最大允许电流",Integer.toString(value_current_max)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("第一档电流",Integer.toString(value_current_one)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("第二档电流",Integer.toString(value_current_two)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("第三档电流",Integer.toString(value_current_three)+" mA"));
        list.add(PageParameterlayout.getparameterlistViewData("第四档电流",Integer.toString(value_current_four)+" mA"));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor_current.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor_current);
        }
    }

    //距离参数
    private void SlidingParametersShow_distance(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("开门爬行距离",Integer.toString(value_distance_opencrawl)+" 脉冲"));
        list.add(PageParameterlayout.getparameterlistViewData("关门爬行距离",Integer.toString(value_distance_closecrawl)+" 脉冲"));
        list.add(PageParameterlayout.getparameterlistViewData("开门末段距离",Integer.toString(value_distance_openend)+" 脉冲"));
        list.add(PageParameterlayout.getparameterlistViewData("关门末段距离",Integer.toString(value_distance_closeend)+" 脉冲"));
        list.add(PageParameterlayout.getparameterlistViewData("关门保持距离上限",Integer.toString(value_distance_holdupper)+" 脉冲"));
        list.add(PageParameterlayout.getparameterlistViewData("关门保持距离下限",Integer.toString(value_distance_holdlower)+" 脉冲"));
        //list.add(PageParameterlayout.getparameterlistViewData("开门强制缓行距离",Integer.toString(value_distance_openmovelow)+" 脉冲"));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor_distance.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor_distance);
        }
    }

    //PWM参数
    private void SlidingParametersShow_pwm(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("PID调节周期",Integer.toString(value_pwm_pid_period)));
        list.add(PageParameterlayout.getparameterlistViewData("PID驱动系数",Integer.toString(value_pwm_pid_drive)));
        list.add(PageParameterlayout.getparameterlistViewData("PID撤力系数",Integer.toString(value_pwm_pid_recall)));
        list.add(PageParameterlayout.getparameterlistViewData("PID刹车系数",Integer.toString(value_pwm_pid_brake)));
        list.add(PageParameterlayout.getparameterlistViewData("PID反向系数",Integer.toString(value_pwm_pid_reverse)));
        list.add(PageParameterlayout.getparameterlistViewData("最大PWM",Integer.toString(value_pwm_max)));
        list.add(PageParameterlayout.getparameterlistViewData("最小PWM",Integer.toString(value_pwm_min)));
        list.add(PageParameterlayout.getparameterlistViewData("匀超不减PWM比例",Integer.toString(value_pwm_uniform_rate)+" %"));
        list.add(PageParameterlayout.getparameterlistViewData("刹低不增PWM比例",Integer.toString(value_pwm_brake_rate)+" %"));
        list.add(PageParameterlayout.getparameterlistViewData("爬末不减PWM比例",Integer.toString(value_pwm_crawllow_rate)+" %"));
        list.add(PageParameterlayout.getparameterlistViewData("反向刹车",Integer.toString(value_pwm_reverseflag)));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_slidingdoor_pwm.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_slidingdoor_pwm);
        }
    }


    //
    public void WingleftParametersShow(){

        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("加速时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("减速时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("直流动作时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("PWM频率",Integer.toString(0)));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_wingleft.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_wingleft);
        }
    }
    //Sliding door setting
    private void Wingleftparametersselect(int pos){
//        switch (pos){
//        }
        MainActivity.pageParameterlayout.parameter_dialog(true,"加速时间设置",100,0,0,1);
    }
    //
    public void WingrightParametersShow(){

        List<HashMap<String, Object>> list = new ArrayList<>();
        list.add(PageParameterlayout.getparameterlistViewData("加速时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("减速时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("直流动作时间",Integer.toString(0)+" s"));
        list.add(PageParameterlayout.getparameterlistViewData("PWM频率",Integer.toString(0)));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_wingright.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_wingright);
        }
    }
    //Sliding door setting
    private void Wingrightparametersselect(int pos){
//        switch (pos){
//        }
        MainActivity.pageParameterlayout.parameter_dialog(true,"加速时间设置",100,0,0,1);
    }
}
