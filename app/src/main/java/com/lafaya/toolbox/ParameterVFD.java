package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ioani on 2016/11/28.
 **/

/**
 * r=143mm, v = 20.05 * f
 * 速度1：高速/常转   15~50
 * 速度2：中速/残障    10~25
 * 速度3：低速         5~15
 * 速度4：             5~15
 * 速度5：             5~10
 * 速度6：             5~10
 * 速度7：未用
 **/

public class ParameterVFD {

    //vfd parameter
    private AutoCountListView list_parameter_fvd;

    private int vfd_adtime = 2,vfd_rdtime = 2,vfd_dctime = 1,vfd_pwmselected = 15;
    private int vfd_speed1 = 35,vfd_speed2 = 20,vfd_speed3 = 15,vfd_speed4 = 15, vfd_speed5 = 10, vfd_speed6 = 10, vfd_speed7 = 10;
//    private int vfd_current = 7000;
    private String vfd_errocode = "null";

    //define max min
    private int vfd_adtime_max = 360,vfd_adtime_min = 0;
    private int vfd_rdtime_max = 360,vfd_rdtime_min = 0;
    private int vfd_dctime_max = 100,vfd_dctime_min = 0;
    private int vfd_pwmselected_max = 15,vfd_pwmselected_min = 0;
    private int vfd_speed1_max = 10000,vfd_speed1_min = 1500;
    private int vfd_speed2_max = 5000,vfd_speed2_min = 1000;
    private int vfd_speed3_max = 3000,vfd_speed3_min = 500;
    private int vfd_speed4_max = 3000,vfd_speed4_min = 500;
    private int vfd_speed5_max = 2000,vfd_speed5_min = 500;
    private int vfd_speed6_max = 2000,vfd_speed6_min = 500;
    private int vfd_speed7_max = 2000,vfd_speed7_min = 500;
    private int vfd_current_max = 15,vfd_current_min = 500;
    //step
    private int vfd_adtime_step = 1,vfd_rdtime_step = 1,vfd_dctime_step = 1,vfd_pwmselected_step = 1;
    private int vfd_speed1_step = 1,vfd_speed2_step = 1,vfd_speed3_step = 1,vfd_speed4_step = 1, vfd_speed5_step = 1, vfd_speed6_step = 1, vfd_speed7_step = 1;
//    private int vfd_current_step = 1;
    //
    private int vfd_cureent_value = 0;

    public int vfd_check_status = 0;
    public boolean vfd_check_flag = false;

    private Activity activity;


    public void initializeParameterVFD(Activity inactivity, Handler handler){
        activity = inactivity;
        //fvd
        list_parameter_fvd = (AutoCountListView)activity.findViewById(R.id.list_parameter_fvd);
        list_parameter_fvd.setVisibility(View.VISIBLE);
        initializeData();
        VFDparametersShow();
        // vfd parameters selected listener
        list_parameter_fvd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                vfd_check_status = pos + 1;
                vfdparameterset(vfd_check_status);
            }
        });
    }

    //initialize parameter
    public void initializeData(){
        vfd_adtime = 2;
        vfd_rdtime = 2;
        vfd_dctime = 1;
        vfd_pwmselected = 15;
        vfd_speed1 = 35;
        vfd_speed2 = 20;
        vfd_speed3 = 15;
        vfd_speed4 = 15;
        vfd_speed5 = 10;
        vfd_speed6 = 10;
        vfd_speed7 = 10;
//        vfd_current = 7000;
        vfd_errocode = "null";
        //define max min
        vfd_adtime_max = 360;
        vfd_adtime_min = 0;
        vfd_rdtime_max = 360;
        vfd_rdtime_min = 0;
        vfd_dctime_max = 100;
        vfd_dctime_min = 0;
        vfd_pwmselected_max = 15;
        vfd_pwmselected_min = 0;
        vfd_speed1_max = 10000;
        vfd_speed1_min = 1500;
        vfd_speed2_max = 5000;
        vfd_speed2_min = 1000;
        vfd_speed3_max = 3000;
        vfd_speed3_min = 500;
        vfd_speed4_max = 3000;
        vfd_speed4_min = 500;
        vfd_speed5_max = 2000;
        vfd_speed5_min = 500;
        vfd_speed6_max = 2000;
        vfd_speed6_min = 500;
        vfd_speed7_max = 2000;
        vfd_speed7_min = 500;
        vfd_current_max = 15;
        vfd_current_min = 500;
        //step
        vfd_adtime_step = 1;
        vfd_rdtime_step = 1;
        vfd_dctime_step = 1;
        vfd_pwmselected_step = 1;
        vfd_speed1_step = 1;
        vfd_speed2_step = 1;
        vfd_speed3_step = 1;
        vfd_speed4_step = 1;
        vfd_speed5_step = 1;
        vfd_speed6_step = 1;
        vfd_speed7_step = 1;
    //    vfd_current_step = 1;
        vfd_cureent_value = 0;
        vfd_check_status = 0;
        vfd_check_flag = false;
    }
    //
    public void operationVFD_control(boolean flag){
        list_parameter_fvd.setEnabled(flag);
    }
    public void parameterclear(){
        vfd_check_flag = false;
        vfd_check_status = 0;
        //显示默认值
    }

    //VFD parameter change saving
    public void parameterVFD_saving(String msg){
        vfd_cureent_value = Integer.parseInt(msg);
        msg = Integer.toHexString(vfd_cureent_value).toUpperCase();
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

        //发送数据
        switch (vfd_check_status){
            case 1:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedupTime(true, msg),activity);
                break;
            case 2:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeeddownTime(true, msg),activity);
                break;
            case 3:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDdcTime(true, msg),activity);
                break;
            case 4:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDpwmSet(true, msg),activity);
                break;
            case 5:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedhigh(true, msg),activity);
                break;
            case 6:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedmid(true, msg),activity);
                break;
            case 7:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedlow(true, msg),activity);
                break;
            case 8:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed4(true, msg),activity);
                break;
            case 9:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed5(true, msg),activity);
                break;
            case 10:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed6(true, msg),activity);
                break;
            case 11:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed7(true, msg),activity);
                break;
            case 12:
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDcurrent(true, msg),activity);
                break;
            default:
                break;
        }
    }
    //VFD parameter change cancle
    public void parameterVFD_cancle(){

    }

    //VFD send check order
    public void VFDParameterQuery(){
        if(!vfd_check_flag){
            vfd_check_flag = true;
            vfd_check_status = 0;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedupTime(false, ""),activity);
        }
    }
    //VFD receive data
    public void ReceiveParameters(String msg){
        int tmp = MainActivity.pageParameterlayout.dataStringtoInteger(msg.substring(msg.length()-3-4,msg.length()-3));

        if(MainActivity.pageParameterlayout.parameterPLC.revolvingdoor_check_flag) {
            MainActivity.pageParameterlayout.parameterPLC.Receivespeedvalue(tmp);
        }else if(vfd_check_flag){

            switch (vfd_check_status){
                case 0:
                    vfd_adtime = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeeddownTime(false, ""),activity);
                    vfd_check_status = 1;
                    break;
                case 1:
                    vfd_rdtime = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDdcTime(false, ""),activity);
                    vfd_check_status = 2;
                    break;
                case 2:
                    vfd_dctime = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDpwmSet(false, ""),activity);
                    vfd_check_status = 3;
                    break;
                case 3:
                    vfd_pwmselected = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedhigh(false, ""),activity);
                    vfd_check_status = 4;
                    break;
                case 4:
                    vfd_speed1 = tmp;
                    vfd_check_status = 5;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedmid(false, ""), activity);
                    break;
                case 5:
                    vfd_speed2 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeedlow(false, ""), activity);
                    vfd_check_status = 6;
                    break;
                case 6:
                    vfd_speed3 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed4(false, ""),activity);
                    vfd_check_status = 7;
                    break;
                case 7:
                    vfd_speed4 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed5(false, ""),activity);
                    vfd_check_status = 8;
                    break;
                case 8:
                    vfd_speed5 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed6(false, ""),activity);
                    vfd_check_status = 9;
                    break;
                case 9:
                    vfd_speed6 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDspeed7(false, ""),activity);
                    vfd_check_status = 10;
                    break;
                case 10:
                    vfd_speed7 = tmp;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDcurrent(false, ""),activity);
                    vfd_check_status = 11;
                    break;
                case 11:
//                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandFVD.SendVFDerrorcode(),activity);
//                    vfd_current = tmp;
//                    vfd_check_status = 12;
//                    break;
//                case 12:
                    vfd_errocode = msg.substring(msg.length()-3-2,msg.length()-3);
                    vfd_check_flag = false;
                    vfd_check_status = 0;
                    break;
                default:
                    break;
            }
        }
        //显示数据
        VFDparametersShow();
    }
    //VFD receive data
    //VFD receive data

    public void Receivesetfinished(){
        switch (vfd_check_status){
            case 1:
                vfd_adtime = vfd_cureent_value;
                break;
            case 2:
                vfd_rdtime = vfd_cureent_value;
                break;
            case 3:
                vfd_dctime = vfd_cureent_value;
                break;
            case 4:
                vfd_pwmselected = vfd_cureent_value;
                break;
            case 5:
                vfd_speed1 = vfd_cureent_value;
                MainActivity.pageParameterlayout.parameterPLC.Receivespeedfinished();
                break;
            case 6:
                vfd_speed2 = vfd_cureent_value;
                MainActivity.pageParameterlayout.parameterPLC.Receivespeedfinished();
                break;
            case 7:
                vfd_speed3 = vfd_cureent_value;
                break;
            case 8:
                vfd_speed4 = vfd_cureent_value;
                break;
            case 9:
                vfd_speed5 = vfd_cureent_value;
                break;
            case 10:
                vfd_speed6 = vfd_cureent_value;
                break;
            case 11:
                vfd_speed7 = vfd_cureent_value;
                break;
//            case 12:
//                vfd_current = vfd_cureent_value;
//                break;
            default:
                break;
        }
        vfd_check_flag = false;
        vfd_check_status = 0;
        //显示数据
        VFDparametersShow();
    }

    //VFD
    public void VFDparametersShow(){
        List<HashMap<String, Object>> list = new ArrayList<>();
        if(vfd_adtime > 36000){
            list.add(PageParameterlayout.getparameterlistViewData("加速时间", "9999.9" + " s"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("加速时间", Float.toString((((float) vfd_adtime) / 10.0f)) + " s"));
        }
        if(vfd_rdtime > 36000){
            list.add(PageParameterlayout.getparameterlistViewData("减速时间", "9999.9" + " s"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("减速时间", Float.toString(((float) vfd_rdtime) / 10.0f) + " s"));
        }
        if(vfd_dctime > 100){
            list.add(PageParameterlayout.getparameterlistViewData("直流动作时间", "99.9" + " s"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("直流动作时间", Float.toString(((float) vfd_dctime) / 10.0f) + " s"));
        }
        if(vfd_pwmselected > 15){
            list.add(PageParameterlayout.getparameterlistViewData("PWM频率", "9999"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("PWM频率", Integer.toString(vfd_pwmselected)));
        }
        if(vfd_speed1 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度1速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度1速", Float.toString(((float) vfd_speed1) / 100.00f) + " Hz"));
        }
        if(vfd_speed2 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度2速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度2速", Float.toString(((float) vfd_speed2) / 100.00f) + " Hz"));
        }
        if(vfd_speed3 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度3速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度3速", Float.toString(((float) vfd_speed3) / 100.00f) + " Hz"));
        }
        if(vfd_speed4 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度4速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度4速", Float.toString(((float) vfd_speed4) / 100.00f) + " Hz"));
        }
        if(vfd_speed5 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度5速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度5速", Float.toString(((float) vfd_speed5) / 100.00f) + " Hz"));
        }
        if(vfd_speed6 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度6速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度6速", Float.toString(((float) vfd_speed6) / 100.00f) + " Hz"));
        }
        if(vfd_speed7 > 40000){
            list.add(PageParameterlayout.getparameterlistViewData("多段速度7速", "9999.99" + " Hz"));
        }else {
            list.add(PageParameterlayout.getparameterlistViewData("多段速度7速", Float.toString(((float) vfd_speed7) / 100.00f) + " Hz"));
        }


//        list.add(PageParameterlayout.getparameterlistViewData("额定电流",Integer.toString(vfd_current)+" Hz"));
        list.add(PageParameterlayout.getparameterlistViewData("异常代码",vfd_errocode +" "));
        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list, R.layout.parameter_list,
                new String[] { "parameter_name","parameter_value"},
                new int[] { R.id.parameter_name,R.id.parameter_value});
        // 设置GridView的adapter。GridView继承于AbsListView。
        list_parameter_fvd.setAdapter(saImageItems);
        // 设置显示高度
        if(saImageItems.getCount() > 0) {
            AutoCountListView.setListViewHeightBasedOnChildren(list_parameter_fvd);
        }
    }

    //FVD参数设置对话框
    private void vfdparameterset(int flag){
        switch (flag){
            case 1:
                MainActivity.pageParameterlayout.parameter_dialog(true,"加速时间设置",vfd_adtime_max,vfd_adtime_min,vfd_adtime,vfd_adtime_step);
                break;
            case 2:
                MainActivity.pageParameterlayout.parameter_dialog(true,"减速时间设置",vfd_rdtime_max,vfd_rdtime_min,vfd_rdtime,vfd_rdtime_step);
                break;
            case 3:
                MainActivity.pageParameterlayout.parameter_dialog(true,"直流动作时间设置",vfd_dctime_max,vfd_dctime_min,vfd_dctime,vfd_dctime_step);
                break;
            case 4:
                MainActivity.pageParameterlayout.parameter_dialog(true,"PWM频率设置",vfd_pwmselected_max,vfd_pwmselected_min,vfd_pwmselected,vfd_pwmselected_step);
                break;
            case 5:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度1速设置",vfd_speed1_max,vfd_speed1_min,vfd_speed1,vfd_speed1_step);
                break;
            case 6:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度2速设置",vfd_speed2_max,vfd_speed2_min,vfd_speed2,vfd_speed2_step);
                break;
            case 7:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度3速设置",vfd_speed3_max,vfd_speed3_min,vfd_speed3,vfd_speed3_step);
                break;
            case 8:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度4速设置",vfd_speed4_max,vfd_speed4_min,vfd_speed4,vfd_speed4_step);
                break;
            case 9:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度5速设置",vfd_speed5_max,vfd_speed5_min,vfd_speed5,vfd_speed5_step);
                break;
            case 10:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度6速设置",vfd_speed6_max,vfd_speed6_min,vfd_speed6,vfd_speed6_step);
                break;
            case 11:
                MainActivity.pageParameterlayout.parameter_dialog(true,"多段速度7速设置",vfd_speed7_max,vfd_speed7_min,vfd_speed7,vfd_speed7_step);
                break;
//            case 12:
//                MainActivity.pageParameterlayout.parameter_dialog(true,"额定电流",vfd_current_max,vfd_current_min,vfd_current,vfd_current_step);
//                break;
            default:
                MainActivity.pageParameterlayout.parameter_dialog(false,"--",0,0,0,1);
                break;
        }

    }




}
