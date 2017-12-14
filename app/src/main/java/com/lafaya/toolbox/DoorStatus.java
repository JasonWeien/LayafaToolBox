package com.lafaya.toolbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by ioani on 2016/11/10.
 **/

public class DoorStatus {

    public boolean revolvingdoortype = false;  //旋转门为true,平滑门为false

    //自动门状态：灯光状态、锁闭状态、连接状态、当前动行模式、故障代码
    public boolean light_status = false;
    public boolean lock_status = false;
    public boolean door_connect = false;
    public int modeselected = 0;
    public int modecurrent = 0;
    public String error_code = "00";//报警代码

    //PLC Communication
    public boolean activate_flag = false;
    public int plc_communication_flag = PLC_COMMUNICATION_FREE;
    public final static int PLC_COMMUNICATION_FREE = 0;
    public final static int PLC_MODE_SEND = 1;//运行模式查询设置
    public final static int PLC_LOCK_SEND = 2;//锁定查询设置
    public final static int PLC_LIGHT_SEND = 3;//灯光查询设置
    public final static int PLC_ERROR_SEND = 4;//报警查询
    public final static int PLC_VERSION_SEND = 5;//版本查询
    public final static int PLC_RUNTIMECURRENT_SEND = 6;//当前次数查询
    public final static int PLC_RUNTIMEHISTORY_SEND = 7;//历史次数查询
    public final static int PLC_SPEEDENCODER_SEND = 8;//编码器速度查询
    public final static int PLC_RESTART_SEND = 9;//重置
    public final static int PLC_CLEANRUNTIME_SEND = 10;//清除当前运行次数
    public final static int PLC_RISKPOSITION_SEND = 11;//危险区域设置
    public final static int PLC_STOPPOSITION_SEND = 12;//停机位置设置
    public final static int PLC_SENSORFIXED_SEND = 13;//传感器查询
    public final static int PLC_SENSORMOBILE_SEND = 14;//传感器查询
    public final static int PLC_ERRORALL_SEND = 15;//历史报警代码
    public final static int PLC_RESETPOSITION_SEND = 16;//恢复出厂设置位置
    public final static int PLC_SETPOSITION_SEND = 17;//开始位置设置
    public final static int PLC_READPOSITION_SEND = 18;//读取当前位置



    public final static int diameter_revolving = 4200;
    //位置信息：
    public int stopposition_summer = 0;
    public int stopposition_winter = 0;
    public int stopposition_middle = 0;
    public int risk1position_start = 0;
    public int risk1position_end = 0;
    public int risk2position_start = 0;
    public int risk2position_end = 0;

    //speed
    //public int speednow_revolving = 0;
    public int speedlow_revolving = 300;
    public int speedhigh_revolving = 500;
    public int speedlow_revolving_min = 100;
    public int speedlow_revolving_max = 500;
    public int speedhigh_revolving_min = 200;
    public int speedhigh_revolving_max = 700;

    //sliding door
    public int speedopen_sliding = 400;
    public int speedopen_max = 500;
    public int speedopen_min = 100;
    public int speedclose_sliding = 300;
    public int speedclose_max = 400;
    public int speedclose_min = 100;
    public int keepopentime_sliding = 3;
    public int keepopentime_max = 60;
    public int keepopentime_min = 0;

    public int openrate_sliding = 100;
    public int openrate_max = 100;
    public int openrate_min = 30;

    //

    public boolean lafaya_communication_flag = false;
    public boolean lafaya_modeset_flag = false;



    public int speedopen_leftwing = 0;
    public int speedclose_leftwing = 0;
    public int speedopen_rightwing = 0;
    public int speedclose_rightwing = 0;

    //VFD 通信
    public int vfd_communication_flag = 0;



    //打开/关闭灯光
    public void lightTurnOn_Off(Activity activity,boolean flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_LIGHT_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCLight(flag,(!light_status)),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }
    //打开/关闭锁
    public void lockTurnOn_Off(Activity activity,boolean flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_LOCK_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCLock(flag,(!lock_status)),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }
    //查询故障代码
    public void errornowQuery(Activity activity){
        if(revolvingdoortype){
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_ERROR_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCError(false),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }else {
            if(!lafaya_communication_flag){
                lafaya_communication_flag = true;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaErrorcode(CommandLafaya.sendrightwingID),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }

    }
    //查询历史报警代码
    public void errorhistoryQuery(Activity activity){
        if(MainActivity.doorStatus.revolvingdoortype){
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_ERRORALL_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCError(true),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }else {
            if(!lafaya_communication_flag){
                lafaya_communication_flag = true;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaTotalErrorcode(CommandLafaya.sendrightwingID),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }

    }

    //运行模式查询设置
    public void runmodeSet_Qeury(Activity activity,boolean flag,int mode_now){
        if(revolvingdoortype){
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_MODE_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCMode(flag, mode_now),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }else {
            if(!lafaya_communication_flag){
                lafaya_communication_flag = true;
                lafaya_modeset_flag = flag;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaRummode(CommandLafaya.sendrightwingID,mode_now,flag),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }

    }
    //open the door
    public void opendoor(Activity activity){
        if(revolvingdoortype){

        }else {
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaOpendoor(CommandLafaya.sendrightwingID),activity);
        }

    }


    //=========复位/
    public void doorReset(Activity activity,int flag,String address){
        if(revolvingdoortype) {
            if (address.equals("01")) {//读主旋转门
                if (plc_communication_flag == PLC_COMMUNICATION_FREE) {
                    plc_communication_flag = PLC_RESTART_SEND;
                    MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCrestart(flag), activity);
                } else {
                    MainActivity.bluetoothComm.communicating();
                }
            } else if (address.equals("05")) {//读中间平滑门

            } else if (address.equals("03")) {//左门翼

            } else if (address.equals("04")) {//右门翼

            }
        }else {
            if(flag == 2){
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaRestartDoor(CommandLafaya.sendslidingID),activity);
            }else {
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaResettDoor(CommandLafaya.sendslidingID),activity);
            }

        }

    }


    //==========信息读取
    //读取软件版本
    public void readVersion(Activity activity,String address){

        if(address.equals("01")){//读主旋转门
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_VERSION_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCVersion(),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }

        }else if(address.equals("05")){//读中间平滑门
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoVersion(CommandLafaya.sendslidingID),activity);
        }else if(address.equals("03")){//左门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoVersion(CommandLafaya.sendleftwingID),activity);
        }else if(address.equals("04")){//右门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoVersion(CommandLafaya.sendrightwingID),activity);
        }
    }

    //读取当前运行次数
    public void readRuntimeCurrent(Activity activity,String address){
        if(address.equals("01")){//读主旋转门
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_RUNTIMECURRENT_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCruntimeCurrent(),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }
        }else if(address.equals("05")){//读中间平滑门
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentRuntime(CommandLafaya.sendslidingID),activity);
        }else if(address.equals("03")){//左门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentRuntime(CommandLafaya.sendleftwingID),activity);
        }else if(address.equals("04")){//右门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoCurrentRuntime(CommandLafaya.sendrightwingID),activity);
        }
    }

    //读取历史运行次数
    public void readRuntimeHistory(Activity activity,String address){
        if(address.equals("01")){//读主旋转门
            if(plc_communication_flag == PLC_COMMUNICATION_FREE){
                plc_communication_flag = PLC_RUNTIMEHISTORY_SEND;
                MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCruntimeHistory(),activity);
            }else{
                MainActivity.bluetoothComm.communicating();
            }

        }else if(address.equals("05")){//读中间平滑门
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTotalRuntime(CommandLafaya.sendslidingID),activity);
        }else if(address.equals("03")){//左门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTotalRuntime(CommandLafaya.sendleftwingID),activity);

        }else if(address.equals("04")){//右门翼
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoTotalRuntime(CommandLafaya.sendrightwingID),activity);
        }
    }

    //读取PLC编码器速度次数
    public void readPLCencoderSpeed(Activity activity){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_SPEEDENCODER_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCencoderSpeed(),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }

    }

    //清除当前运行次数
    public void cleanRuntime(Activity activity){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_CLEANRUNTIME_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCcleanRuntime(),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }
    //位置设置启动
    public void setPositionstart(Activity activity,boolean flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_SETPOSITION_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.sendPLCSetpositionstart(flag),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }

    //危险区域设置
    public void setRiskposition(Activity activity, int flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_RISKPOSITION_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCRiskposition(flag),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }
    //停机位置设置
    public void setStopposition(Activity activity, int flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_STOPPOSITION_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCStopposition(flag),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }
    //恢复出厂位置设置
    public void setResetposition(Activity activity){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_RESETPOSITION_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCResetposition(),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }

    //读取当前位置
    public void Readposition(Activity activity,int flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){
            plc_communication_flag = PLC_READPOSITION_SEND;
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCReadposition(flag),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }

    //传感器状态监控
    public void readSensorStatus(Activity activity,boolean flag){
        if(plc_communication_flag == PLC_COMMUNICATION_FREE){

            if(flag){
                plc_communication_flag = PLC_SENSORFIXED_SEND;//固定部分
            }else {
                plc_communication_flag = PLC_SENSORMOBILE_SEND;//移动部分
            }
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandPLC.SendPLCSensorstatus(flag),activity);
        }else{
            MainActivity.bluetoothComm.communicating();
        }
    }

    // Receive data
    public void ReceiveRunmode(String value){
        if(lafaya_modeset_flag){
            lafaya_modeset_flag = false;
            modecurrent = modeselected;
        }else{
            switch (Integer.parseInt(value)){
                case 1:
                    modecurrent = 3;
                    break;
                case 2:
                    modecurrent = 0;
                    break;
                case 4:
                    modecurrent = 2;
                    break;
                case 8:
                    modecurrent = 1;
                    break;
                default:
                    break;
            }
            modeselected = modecurrent;
        }
        if(lafaya_communication_flag){
            lafaya_communication_flag = false;
        }
        //若在主页面状态查询，则执行下一条指令
        if (MainActivity.pageHomelayout.home_check_list == 1) {
            MainActivity.pageHomelayout.receiveStatus();
        }
    }

    public void ReceiveOpenrate(String value){

        openrate_sliding = Integer.parseInt(value);
        if(openrate_sliding > 100){
            openrate_sliding = 100;
        }
        if(lafaya_communication_flag){
            lafaya_communication_flag = false;
        }
    }

    public void ReceiveErrocode(String value){

        MainActivity.doorStatus.error_code = value;
        if(lafaya_communication_flag){
            lafaya_communication_flag = false;
        }
        //若在主页面状态查询，则执行下一条指令
        if (MainActivity.pageHomelayout.home_check_list == 2) {
            MainActivity.pageHomelayout.receiveStatus();
        }
    }


    //VFD 参数查询与设置
//    public void VFD_


    //lafaya 参数查询与设置
    //开门速度
    public void lafayaSpeedopen_setquery(Activity activity,boolean flag,int addr,int data){

        if(addr == 0){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaOpenSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }else if(addr == 1){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaOpenSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }else{
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaOpenSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }
    }
    //关门速度
    public void lafayaSpeedclose_setquery(Activity activity,boolean flag,int addr,int data){
        if(addr == 0){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaCloseSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }else if(addr == 1){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaCloseSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }else{
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaCloseSpeed(CommandLafaya.sendrightwingID,data,flag),activity);
        }
    }

    //保持时间
    public void lafayaKeeptime_setquery(Activity activity,boolean flag,int addr,int data){
        if(addr == 0){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaKeepopenTime(CommandLafaya.sendrightwingID,data,flag),activity);
        }else if(addr == 1){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaKeepopenTime(CommandLafaya.sendrightwingID,data,flag),activity);
        }else{
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaKeepopenTime(CommandLafaya.sendrightwingID,data,flag),activity);
        }
    }

    //开门开度
    public void lafayaOpenrate_setquery(Activity activity,boolean flag,int addr,int data){
        if(addr == 0){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoOpenScale(CommandLafaya.sendrightwingID,data,flag),activity);
        }else if(addr == 1){
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoOpenScale(CommandLafaya.sendrightwingID,data,flag),activity);
        }else{
            MainActivity.bluetoothComm.SendMessage(MainActivity.bluetoothComm.commandLafaya.SendLafayaInfoOpenScale(CommandLafaya.sendrightwingID,data,flag),activity);
        }
    }






}
