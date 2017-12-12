package com.lafaya.toolbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ioani on 2016/11/25.
 **/

public class ParameterPLC {

    //PLC position set
    // private Button button_parameter_position_reset;
    private TextView text_parameter_position_summer,text_parameter_position_winter,text_parameter_position_middle;
    private TextView text_parameter_position_risk1_start,text_parameter_position_risk1_end,text_parameter_position_risk2_start,text_parameter_position_risk2_end;
    private CheckBox checkbox_position_summer,checkbox_position_winter,checkbox_position_middle;
    private CheckBox checkbox_position_risk1start,checkbox_position_risk1end,checkbox_position_risk2start,checkbox_position_risk2end;

    private LinearLayout layout_paramter_position;

    //revolving door
    private AutoCountListView list_parameter_revolvingdoor;
    public boolean reovolingdoor_position_flag = false;
    private int speed_change_flag = 0;
    private int speed_value_set = 0;

    public boolean revolvingdoor_check_flag = false;


    final static int POSITIONNOTHING_SELECTED = 0,POSITIONSUMMER_SELECTED = 1,POSITIONWINTER_SELECTED = 2,POSITIONSLIDING_SELECTED = 3,
            POSITIONRISK1START_SELECTED = 4,POSITIONRISK1END_SELECTED = 5,POSITIONRISK2START_SELECTED = 6,POSITIONRISK2END_SELECTED = 7;
    private int positionset_selected = POSITIONNOTHING_SELECTED;
    private boolean positionset_flag = false;
    private boolean positionread_flag = false;
    private boolean checkposition_flag = false;
    private int checkposition = POSITIONNOTHING_SELECTED;
//    private int currentposition = 0;


    Activity activity;
    Handler handler;
    public void initializeParameterPLC(Activity inactivity,Handler inhandler){
        activity = inactivity;
        handler = inhandler;

        layout_paramter_position = (LinearLayout)activity.findViewById(R.id.layout_paramter_position);
        //revolving door
        list_parameter_revolvingdoor = (AutoCountListView)activity.findViewById(R.id.list_parameter_revolvingdoor);

        //position set
        checkbox_position_summer = (CheckBox)activity.findViewById(R.id.checkbox_position_summer);
        checkbox_position_winter = (CheckBox)activity.findViewById(R.id.checkbox_position_winter);
        checkbox_position_middle = (CheckBox)activity.findViewById(R.id.checkbox_position_middle);
        checkbox_position_risk1start = (CheckBox)activity.findViewById(R.id.checkbox_position_risk1start);
        checkbox_position_risk1end = (CheckBox)activity.findViewById(R.id.checkbox_position_risk1end);
        checkbox_position_risk2start = (CheckBox)activity.findViewById(R.id.checkbox_position_risk2start);
        checkbox_position_risk2end = (CheckBox)activity.findViewById(R.id.checkbox_position_risk2end);
        //Text view
        text_parameter_position_summer = (TextView)activity.findViewById(R.id.text_parameter_position_summer);
        text_parameter_position_winter = (TextView)activity.findViewById(R.id.text_parameter_position_winter);
        text_parameter_position_middle = (TextView)activity.findViewById(R.id.text_parameter_position_middle);
        text_parameter_position_risk1_start = (TextView)activity.findViewById(R.id.text_parameter_position_risk1_start);
        text_parameter_position_risk1_end = (TextView)activity.findViewById(R.id.text_parameter_position_risk1_end);
        text_parameter_position_risk2_start = (TextView)activity.findViewById(R.id.text_parameter_position_risk2_start);
        text_parameter_position_risk2_end = (TextView)activity.findViewById(R.id.text_parameter_position_risk2_end);

        RevolvingdoorparametersShow();
        // revolving door parameters selected listener
        list_parameter_revolvingdoor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Revolvingdoorparametersselect(pos);
            }
        });
        checkbox_listener();
    }

    //press saving button
    public void parameterPLC_saving(String msg){
        //positions set
        if(positionset_selected != POSITIONNOTHING_SELECTED){
            if(positionset_flag){
                setpositionSave();
                //wait to finish
            }else{
                // send read position now
                MainActivity.doorStatus.setPositionstart(activity,true);
            }
        }else if(speed_change_flag == 1){
            speed_value_set = Integer.parseInt(msg);
            msg = Integer.toHexString(speed_value_set * 5).toUpperCase();
            if(msg.length() < 1){
                return;
            }else if(msg.length() < 2){
                msg = "000" + msg;
            }else if(msg.length() < 3){
                msg = "00" + msg;
            }else if(msg.length() < 4){
                msg = "0" + msg;
            }else{
                msg = msg.substring(msg.length()-4,msg.length());
            }
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 5;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedhigh(true, msg),activity);
        }else if(speed_change_flag == 2){
            speed_value_set = Integer.parseInt(msg);
            msg = Integer.toHexString(speed_value_set * 5).toUpperCase();
            if(msg.length() < 1){
                return;
            }else if(msg.length() < 2){
                msg = "000" + msg;
            }else if(msg.length() < 3){
                msg = "00" + msg;
            }else if(msg.length() < 4){
                msg = "0" + msg;
            }else{
                msg = msg.substring(msg.length()-4,msg.length());
            }
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 6;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedmid(true, msg),activity);
        }
    }

    //press Cancle button
    public void parameterPLC_cancle(){
        if(positionset_selected != POSITIONNOTHING_SELECTED)
        {
            //显示 原来数据
            positionvalueShow(oldposition(positionset_selected),positionset_selected);
            positionset_selected = POSITIONNOTHING_SELECTED;
            if(positionset_flag ) {
                positionset_flag = false;
                MainActivity.doorStatus.setPositionstart(activity, false);
            }
            setpositionselectshow();
        }else if(speed_change_flag == 1){

        }
    }

    // speed set finished
    public void Receivespeedfinished(){
        if(speed_change_flag == 1){
            speed_change_flag = 0;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 0;
            MainActivity.doorStatus.speedhigh_revolving = speed_value_set;
            RevolvingdoorparametersShow();
        }else if(speed_change_flag == 2){
            speed_change_flag = 0;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 0;
            MainActivity.doorStatus.speedlow_revolving = speed_value_set;
            RevolvingdoorparametersShow();
        }
    }
    //hid position set layout
    public void postionsethide(){
        reovolingdoor_position_flag = false;
        layout_paramter_position.setVisibility(View.GONE);
        speed_change_flag = 0;
        revolvingdoor_check_flag = false;
    }

    //checkbox listener
    private void checkbox_listener(){
        //when revolving door positions set, check which one to be selected.
        checkbox_position_summer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONSUMMER_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_winter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONWINTER_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_middle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONSLIDING_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_risk1start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONRISK1START_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_risk1end.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONRISK1END_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_risk2start.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONRISK2START_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });

        checkbox_position_risk2end.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked){
                    positionset_selected = POSITIONRISK2END_SELECTED;
                }else {
                    positionset_selected = POSITIONNOTHING_SELECTED;
                }
                setpositionselectshow();
            }
        });
    }
    //revolving door
    public void RevolvingdoorparametersShow(){
        List<HashMap<String, Object>> list = new ArrayList<>();

        list.add(PageParameterlayout.getparameterlistViewData("常转速度",Integer.toString(MainActivity.doorStatus.speedhigh_revolving)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("缓行速度",Integer.toString(MainActivity.doorStatus.speedlow_revolving)+" mm/s"));
        list.add(PageParameterlayout.getparameterlistViewData("当前运行次数清零"," "));
        list.add(PageParameterlayout.getparameterlistViewData("位置参数恢复出厂设置"," "));
        list.add(PageParameterlayout.getparameterlistViewData("位置参数设置"," "));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_revolvingdoor.setAdapter(saImageItems);


        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_revolvingdoor);
        }
    }
    // parameter query
    public void RevolvingdoorQuery(){
        if(!revolvingdoor_check_flag){
            revolvingdoor_check_flag = true;
            speed_change_flag = 1;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_flag = true;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 4;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedhigh(false, ""),activity);
        }
    }

    public void Receivespeedvalue(int value){
        if(speed_change_flag == 1){
            speed_change_flag = 2;
            MainActivity.doorStatus.speedhigh_revolving = value / 5;
            RevolvingdoorparametersShow();
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_flag = true;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 5;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedmid(false, ""),activity);
        }else{
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_flag = false;
            MainActivity.pageParameterlayout.parameterVFD.vfd_check_status = 0;
            speed_change_flag = 0;
            revolvingdoor_check_flag = false;
            MainActivity.doorStatus.speedlow_revolving = value / 5;
            RevolvingdoorparametersShow();
        }
    }
    // parameter set
    private void Revolvingdoorparametersselect(int pos){

        Dialog dialog;
        switch (pos){
            case 0://normal speed
                speed_change_flag = 1;
                MainActivity.pageParameterlayout.parameter_dialog(true,"旋转自动门常转速度",MainActivity.doorStatus.speedhigh_revolving_max,MainActivity.doorStatus.speedhigh_revolving_min,MainActivity.doorStatus.speedhigh_revolving,5);
                break;
            case 1:// low speed
                speed_change_flag = 2;
                MainActivity.pageParameterlayout.parameter_dialog(true,"旋转自动门缓行速度",MainActivity.doorStatus.speedlow_revolving_max,MainActivity.doorStatus.speedlow_revolving_min,MainActivity.doorStatus.speedlow_revolving,3);
                break;
            case 2:// clear runtime
                dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.icon_about).setTitle("旋转自动门运行次数").setMessage("清零旋转自动门当前运行次数？").
                        setPositiveButton("清零", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.doorStatus.cleanRuntime(activity);
                                if(reovolingdoor_position_flag){
                                    reovolingdoor_position_flag = false;
                                    layout_paramter_position.setVisibility(View.GONE);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
                break;
            case 3://position reset
                dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.icon_about).setTitle("旋转门位置参数").setMessage("旋转门位置参数将恢复出厂设置，真的要恢复吗？").
                        setPositiveButton("恢复出厂设置", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.doorStatus.setResetposition(activity);
                                if(reovolingdoor_position_flag){
                                    reovolingdoor_position_flag = false;
                                    layout_paramter_position.setVisibility(View.GONE);
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();

                break;
            case 4:// position set
                if(reovolingdoor_position_flag){
                    reovolingdoor_position_flag = false;
                    layout_paramter_position.setVisibility(View.GONE);
                }else{
                    reovolingdoor_position_flag = true;
                    layout_paramter_position.setVisibility(View.VISIBLE);
                    positionset_selected = POSITIONNOTHING_SELECTED;
                    setpositionselectshow();
                    if(!checkposition_flag){
                        checkposition = POSITIONSUMMER_SELECTED;
                        MainActivity.doorStatus.Readposition(activity,checkposition);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void ReadPositionvalue(){
        //发送需要读取的位置
        MainActivity.doorStatus.Readposition(activity,0);
    }
    //position set
    private void setpositionselectshow(){
        boolean[] array_enable = {false,false,false,false,false,false,false};
        if(positionset_selected == 0){
            checkbox_position_summer.setEnabled(true);
            checkbox_position_summer.setChecked(false);
            checkbox_position_winter.setEnabled(true);
            checkbox_position_winter.setChecked(false);
            checkbox_position_middle.setEnabled(true);
            checkbox_position_middle.setChecked(false);
            checkbox_position_risk1start.setEnabled(true);
            checkbox_position_risk1start.setChecked(false);
            checkbox_position_risk1end.setEnabled(true);
            checkbox_position_risk1end.setChecked(false);
            checkbox_position_risk2start.setEnabled(true);
            checkbox_position_risk2start.setChecked(false);
            checkbox_position_risk2end.setEnabled(true);
            checkbox_position_risk2end.setChecked(false);
            list_parameter_revolvingdoor.setEnabled(true);
        }else{
            array_enable[positionset_selected - 1] = true;
            checkbox_position_summer.setEnabled(array_enable[0]);
            checkbox_position_winter.setEnabled(array_enable[1]);
            checkbox_position_middle.setEnabled(array_enable[2]);
            checkbox_position_risk1start.setEnabled(array_enable[3]);
            checkbox_position_risk1end.setEnabled(array_enable[4]);
            checkbox_position_risk2start.setEnabled(array_enable[5]);
            checkbox_position_risk2end.setEnabled(array_enable[6]);
            list_parameter_revolvingdoor.setEnabled(false);
        }

        if(positionset_selected == 0){
            MainActivity.pageParameterlayout.parametersetShow(false);
            MainActivity.pageParameterlayout.setbutton_text("保存");
            checkposition_flag = false;
        }else{
            MainActivity.pageParameterlayout.parametersetShow(true);
            MainActivity.pageParameterlayout.setbutton_text("开始");
        }
    }

    public void ReceivePositionset(){
        //位置设置启动、保存、取消的接收到应答
        //按下save键，启动的应答
        if(positionset_selected != POSITIONNOTHING_SELECTED){
            if(checkposition_flag) {
                // seting
                positionset_flag = true;
                //开始读取位置数据
                MainActivity.pageParameterlayout.setbutton_text("保存");
                ReadPositionvalue();
                positionread_flag = true;
            }
        }
        //按下cancle键，取消的应答
        else if((!positionset_flag) && (positionset_selected == POSITIONNOTHING_SELECTED)){
            //还原按键显示内容
            MainActivity.pageParameterlayout.setbutton_text("开始");
            positionread_flag = false;
        }

    }


    public void operationPLC_control(boolean flag){
        list_parameter_revolvingdoor.setEnabled(flag);
    }

    public void ReadpositionVlaue(String textstring){
        //正在读取已设置的位置信息
        if(!checkposition_flag){
            textstring = Integer.toString(MainActivity.pageParameterlayout.dataStringtoInteger(textstring.substring(textstring.length()-3-4,textstring.length()-3)));
            setoldposition(checkposition,Integer.parseInt(textstring));
            positionvalueShow(textstring,checkposition);
            checkposition++;
            if(checkposition > POSITIONRISK2END_SELECTED){
                checkposition_flag = true;
            }else{
                MainActivity.doorStatus.Readposition(activity,checkposition);
            }
        }else{//读取当前位置
            textstring = Long.toString(MainActivity.pageParameterlayout.dataStringtoLong(textstring.substring(textstring.length()-3-8,textstring.length()-3)));
            positionvalueShow(textstring,positionset_selected);
            if(positionread_flag) {
                Handler rehandler = new Handler();
                rehandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = MainActivity.POSITION_READ;
                        handler.sendMessage(msg);
                    }
                }, 500);
            }
        }
    }

    //显示位置数值
    public void positionvalueShow(String textstring, int pos){
        switch (pos){
            case 1:
                text_parameter_position_summer.setText(textstring);
                break;
            case 2:
                text_parameter_position_winter.setText(textstring);
                break;
            case 3:
                text_parameter_position_middle.setText(textstring);
                break;
            case 4:
                text_parameter_position_risk1_start.setText(textstring);
                break;
            case 5:
                text_parameter_position_risk1_end.setText(textstring);
                break;
            case 6:
                text_parameter_position_risk2_start.setText(textstring);
                break;
            case 7:
                text_parameter_position_risk2_end.setText(textstring);
                break;
            default:
                break;
        }
    }
    //保存原有位置
    private void setoldposition(int pos,int value){
        switch (pos){
            case 1:
                MainActivity.doorStatus.stopposition_summer = value;
                break;
            case 2:
                MainActivity.doorStatus.stopposition_winter = value;
                break;
            case 3:
                MainActivity.doorStatus.stopposition_middle = value;
                break;
            case 4:
                MainActivity.doorStatus.risk1position_start = value;
                break;
            case 5:
                MainActivity.doorStatus.risk1position_end = value;
                break;
            case 6:
                MainActivity.doorStatus.risk2position_start = value;
                break;
            case 7:
                MainActivity.doorStatus.risk2position_end = value;
                break;
            default:
                break;
        }
    }
    //恢复原有位置
    private String oldposition(int pos){
        int value = 0;
        switch (pos){
            case 1:
                value =  MainActivity.doorStatus.stopposition_summer;
                break;
            case 2:
                value = MainActivity.doorStatus.stopposition_winter;
                break;
            case 3:
                value = MainActivity.doorStatus.stopposition_middle;
                break;
            case 4:
                value = MainActivity.doorStatus.risk1position_start;
                break;
            case 5:
                value = MainActivity.doorStatus.risk1position_end;
                break;
            case 6:
                value = MainActivity.doorStatus.risk2position_start;
                break;
            case 7:
                value = MainActivity.doorStatus.risk2position_end;
                break;
            default:
                break;
        }
        return Integer.toString(value);
    }

    //位置设置完成，保存成功
    public void finishSetpostion(){
        // 显示设置后的数据
        positionread_flag = false;
        positionset_flag = false;
        positionset_selected = 0;
        setpositionselectshow();
    }
    //Save position
    private void setpositionSave(){
        switch (positionset_selected){
            case 1:
                MainActivity.doorStatus.setStopposition(activity,0);
                break;
            case 2:
                MainActivity.doorStatus.setStopposition(activity,1);
                break;
            case 3:
                MainActivity.doorStatus.setStopposition(activity,2);
                break;
            case 4:
                MainActivity.doorStatus.setRiskposition(activity,0);
                break;
            case 5:
                MainActivity.doorStatus.setRiskposition(activity,1);
                break;
            case 6:
                MainActivity.doorStatus.setRiskposition(activity,2);
                break;
            case 7:
                MainActivity.doorStatus.setRiskposition(activity,3);
                break;
            default:
                break;
        }
    }
}
