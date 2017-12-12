package com.lafaya.toolbox;


import android.app.Activity;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by JeffYoung on 2016/9/22.
 **/
public class CommandPLC {

    //PLC
    interface runmode{
        String SUMMER = "10000000";
        String WINTER = "01000000";
        String EXIT = "00100000";
        String AUTO = "00010000";
        String OPEN = "00001000";
        String MANUAL = "00000100";
        String NORMAL = "00000010";
        String CHECK = "00000001";
    }

    private String[] runingmode = new String[]{
            runmode.NORMAL,runmode.SUMMER,runmode.WINTER,
            runmode.AUTO,runmode.EXIT,runmode.OPEN,
            runmode.MANUAL,runmode.CHECK
    };

    public static String plccommError = "正常";
    public static boolean plcEQN_flag = false;
    public static String plcEQN_text = "";
    public static boolean plcEQN_set = false;

    //PLC 控制字: PLC文本接收开始字0x02，PLC文本接收结束字0x03，PLC查询字0x05，PLC确认应答字0x06，PLC否定应答字0x15
    private char plcSTX = 0x02;
    private char plcETX = 0x03;
    private char plcENQ = 0x05;
    private char plcASK = 0x06;
    private char plcNAK = 0x15;


    //PLC 站号：0x01, PC号:0xFF
    private String plcID = "01FF";
    private String plcIDerror = "01FE";

    private String startRegister = "M0400"+"08";
    private String startstateRegister = "X0021"+"01";

    private String modesetRegister = "M0400"+"08";
    private String lightsetRegister = "M0006"+"01";
    private String lightqueryRegister = "Y0003"+"01";//BR
    private String locksetRegister = "M0408" + "01";

    //故障代码位置未确认。。。。。
    private String errorcodeRegister = "D0035"+"01";
    private String errorcodeallRegister = "D0300"+"0A";

    private String plchardwareRegister = "D8001" + "01";//WR
    private String plcversionRegister = "D0220" + "01";//WR

    //    private String plcruntimeRegister = "D0103"  + "02";//WR
    private String plcruntimenowRegister = "D0130"  + "02";//WR
    private String plcruntimehistoryRegister = "D0132"  + "02";//WR

    private String speedencoderRegister = "D0028" + "01";//WR
    private String fixsensorRegister = "M0013" + "08";//BR
    private String mobilesensorRegister = "M0050" + "0C";//BR
    private String wingbackRegister = "X0022" + "02";//BR

    // Sensor
    private String sensorfixedRegister = "M0013" + "08";//BR
    private String sensormobileRegister = "M0050" + "0C";//BR

    //position set
    private String positionsetstartRegister = "M0007"+"01";//BW

    private String positionsetwinterRegister = "M0009"+"01"+"1";//BW
    private String positionsetsummerRegister = "M0010"+"01"+"1";//BW
    private String positionsetmiddleRegister = "M0008"+"01"+"1";//BW
    private String positionsetrisk1startRegister = "M0011"+"01"+"1";//BW
    private String positionsetrisk1endRegister = "M0012"+"01"+"1";//BW
    private String positionsetrisk2startRegister = "M0005"+"01"+"1";//BW
    private String positionsetrisk2endRegister = "M0078"+"01"+"1";//BW
    private String positionresetRegister = "M0022"+"01"+"1";//BW

    private String resetRegister = "M0086"+"01"+"1";//BW

    //当前位置信息
    private String positionslidingRegister = "D0213"+"01";//WR
    private String positionwinterRegister = "D0313"+"01";//WR
    private String positionsummerRegister = "D0413"+"01";//WR
    private String positionrisk1startRegister = "D0510"+"01";//WR
    private String positionrisk1endRegister = "D0515"+"01";//WR
    private String positionrisk2startRegister = "D0610"+"01";//WR
    private String positionrisk2endRegister = "D0615"+"01";//WR
    private String positioncurrentRegister = "D0208"+"02";//WR

    //reset runtime
    private String runtimeresetRegister = "M0004"+"01" + "1";//WR

    //生成带校验码数据
    private String plccreatcheckcode(char[] msg){
        int sum = 0;
        for(int i = 1; i < msg.length; i++){
            sum = sum + msg[i];
        }
        if((sum & 0x00FF) <= 0x000F){
            //注：字母为大写形式。。。
            return (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "") + "0"+ Integer.toHexString(sum & 0x00FF).toUpperCase()) + "\r\n";
        }else {
            //注：字母为大写形式。。。
            return (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "") + Integer.toHexString(sum & 0x00FF).toUpperCase()) + "\r\n";
        }
    }
    //发送到PLC
    private String SendtoPLCCommand(String register,String flag){
        plcEQN_flag = true;
        plcEQN_text = plcENQ + plcID + flag + "1" + register;

        if((flag.equals("BW")) || (flag.equals("RS")) || (flag.equals("RR"))){
            plcEQN_set = true;
        }
        return plcEQN_text;
    }

    //PLC 灯光设置
    public String SendPLCLight(boolean flag,boolean light){
        if(flag){
            return plccreatcheckcode((SendtoPLCCommand(lightsetRegister,"BW") + ((light)? 1:0)).toCharArray());
        }else {
            return plccreatcheckcode(SendtoPLCCommand(lightqueryRegister,"BR").toCharArray());
        }
    }
    //PLC 系统锁定设置
    public String SendPLCLock(boolean flag,boolean lock){
        if(flag){
            return plccreatcheckcode((SendtoPLCCommand(locksetRegister,"BW") + ((lock)? 1:0)).toCharArray());
        }else {
            return plccreatcheckcode(SendtoPLCCommand(locksetRegister,"BR").toCharArray());
        }
    }
    //PLC 故障查询
    public String SendPLCError(boolean flag){
        if(flag){//历史报警
            return plccreatcheckcode((SendtoPLCCommand(errorcodeallRegister, "WR")).toCharArray());
        }else {//当前报警
            return plccreatcheckcode((SendtoPLCCommand(errorcodeRegister, "WR")).toCharArray());
        }
    }

    //PLC 运行模式查询与修改。当flag = false时查询，flag = true时修改。
    public String SendPLCMode(boolean flag,int mode) {
        if(flag) {
            return plccreatcheckcode((SendtoPLCCommand(modesetRegister,"BW") + runingmode[mode]).toCharArray());
        }else{
            return plccreatcheckcode(SendtoPLCCommand(modesetRegister,"BR").toCharArray());
        }
    }

    //信息查询
    //PLC 软件版本号
    public String SendPLCVersion(){
        return plccreatcheckcode(SendtoPLCCommand(plcversionRegister,"WR").toCharArray());
    }
    //===========
    //PLC 硬件版本号
    public String SendPLChardwareVersion(){
        return plccreatcheckcode(SendtoPLCCommand(plchardwareRegister,"WR").toCharArray());
    }
    //=============
    //PLC 运行次数
    public String SendPLCruntimeCurrent(){
        return plccreatcheckcode(SendtoPLCCommand(plcruntimenowRegister,"WR").toCharArray());
    }
    //PLC 运行次数
    public String SendPLCruntimeHistory(){
        return plccreatcheckcode(SendtoPLCCommand(plcruntimehistoryRegister,"WR").toCharArray());
    }
    //PLC 编码器查询
    public String SendPLCencoderSpeed(){
        return plccreatcheckcode(SendtoPLCCommand(speedencoderRegister,"WR").toCharArray());
    }

    //PLC 清除当前运行次数
    public String SendPLCcleanRuntime(){
        return plccreatcheckcode(SendtoPLCCommand(runtimeresetRegister,"BW").toCharArray());
    }
    //PLC 位置设置启动
    public String sendPLCSetpositionstart(boolean flag){
        if(flag) {
            return plccreatcheckcode(SendtoPLCCommand(positionsetstartRegister + "1", "BW").toCharArray());
        }else{
            return plccreatcheckcode(SendtoPLCCommand(positionsetstartRegister + "0", "BW").toCharArray());
        }
    }
    //PLC 危险区域设置
    public String SendPLCRiskposition(int flag){
        if(flag == 0){
            return plccreatcheckcode(SendtoPLCCommand(positionsetrisk1startRegister,"BW").toCharArray());
        }else if(flag == 1){
            return plccreatcheckcode(SendtoPLCCommand(positionsetrisk1endRegister,"BW").toCharArray());
        }else if(flag == 2){
            return plccreatcheckcode(SendtoPLCCommand(positionsetrisk2startRegister,"BW").toCharArray());
        }else{
            return plccreatcheckcode(SendtoPLCCommand(positionsetrisk2endRegister,"BW").toCharArray());
        }
    }
    //PLC 停机位置设置
    public String SendPLCStopposition(int flag){
        if(flag == 0){//夏季
            return plccreatcheckcode(SendtoPLCCommand(positionsetsummerRegister,"BW").toCharArray());
        }else if(flag == 1){//冬季
            return plccreatcheckcode(SendtoPLCCommand(positionsetwinterRegister,"BW").toCharArray());
        }else {//中间门
            return plccreatcheckcode(SendtoPLCCommand(positionsetmiddleRegister,"BW").toCharArray());
        }
    }
    //PLC 恢复出厂默认位置设置
    public String SendPLCResetposition(){
        return plccreatcheckcode(SendtoPLCCommand(positionresetRegister,"BW").toCharArray());
    }
    


    //PLC 读取位置信息
    public String SendPLCReadposition(int flag){
        switch (flag){
            case 0:
                return plccreatcheckcode(SendtoPLCCommand(positioncurrentRegister,"WR").toCharArray());
            case 1:
                return plccreatcheckcode(SendtoPLCCommand(positionsummerRegister,"WR").toCharArray());
            case 2:
                return plccreatcheckcode(SendtoPLCCommand(positionwinterRegister,"WR").toCharArray());
            case 3:
                return plccreatcheckcode(SendtoPLCCommand(positionslidingRegister,"WR").toCharArray());
            case 4:
                return plccreatcheckcode(SendtoPLCCommand(positionrisk1startRegister,"WR").toCharArray());
            case 5:
                return plccreatcheckcode(SendtoPLCCommand(positionrisk1endRegister,"WR").toCharArray());
            case 6:
                return plccreatcheckcode(SendtoPLCCommand(positionrisk2startRegister,"WR").toCharArray());
            case 7:
                return plccreatcheckcode(SendtoPLCCommand(positionrisk2endRegister,"WR").toCharArray());
            default:
                return plccreatcheckcode(SendtoPLCCommand(positioncurrentRegister,"WR").toCharArray());
        }
    }


    //PLC 传感器状态监控
    public String SendPLCSensorstatus(boolean flag){
        if(flag){//固定部分
            return plccreatcheckcode((SendtoPLCCommand(sensorfixedRegister,"BR")).toCharArray());
        }else{//活动部分
            return plccreatcheckcode((SendtoPLCCommand(sensormobileRegister,"BR")).toCharArray());
        }

    }
    public String SendPLCrestart(int flag){
        if(flag == 1){
            return plccreatcheckcode(SendtoPLCCommand(resetRegister,"BW").toCharArray());
        }else if(flag == 2){
            return  SendPLCStop();
        }else{
            return  SendPLCRun();
        }
    }





    //PLC 远程运行
    private String SendPLCRun(){
        return plccreatcheckcode(SendtoPLCCommand("","RR").toCharArray());
    }
    //PLC 远程停止
    private String SendPLCStop(){
        return plccreatcheckcode(SendtoPLCCommand("","RS").toCharArray());
    }

    //PLC 固定传感器
    public String SendPLCFixsensors(){
        return plccreatcheckcode(SendtoPLCCommand(fixsensorRegister,"BR").toCharArray());
    }
    //PLC 移动传感器
    public String SendPLCMobilesensors(){
        return plccreatcheckcode(SendtoPLCCommand(mobilesensorRegister,"BR").toCharArray());
    }
    //PLC 门翼后退查询
    public String SendPLCWingback(){
        return plccreatcheckcode(SendtoPLCCommand(wingbackRegister,"BR").toCharArray());
    }


    //PLC 接收处理
    //进行数据检验
    private boolean plcdatacheck(char[] msg){
        /*生成检验码*/
        int sum = 0;
        for(int i = 1; i < msg.length -2; i++){
            sum = sum + msg[i];
        }
        //校验和转换为16进行字符，区分大小写。
        String strtemp = Integer.toHexString(sum & 0x00FF).toUpperCase();
        //字符串比较，区分大小写。。。
        if(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2,msg.length).equals(strtemp)){
            return true;
        } else{
            return false;
        }
    }
    public void CleanPLCflag(){
        plccommError = "正常";
        plcEQN_flag = false;
        plcEQN_text = "";
        plcEQN_set = false;
    }

    public void ReceivePLCProcess(char[] msg,Activity activity){
        //校验通过，进行校验后处理

        //Toast.makeText(activity, Integer.toHexString(msg[0]).toUpperCase(),Toast.LENGTH_LONG).show();
        if(plcEQN_set){
            if(msg[0] == plcASK){
                MainActivity.bluetoothComm.cleanWaitreceive();
                plcEQN_flag = ReceivePLCASK();
                plccommError = "正常";
                plcEQN_text = "";
            }
            plcEQN_set = false;
        }else if(msg[0] == plcNAK){
            //MainActivity.bluetoothComm.cleanWaitreceive();
            plccommError = ReceivePLCNAK(msg);
            if(plcEQN_flag){
                //通讯错误
                String error = ReceivePLCNAK(msg);
                plcEQN_flag = false;
            }
        }else if(msg[0] == plcSTX){
            if (plcdatacheck(msg)) {
                if(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(3, 5).equals("FE")){//接收到系统主动发出的信息，报警代码或自测模式下的状态
                    Toast.makeText(activity, Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length-3-8,msg.length-3),Toast.LENGTH_LONG).show();
                    MainActivity.doorStatus.error_code = ReceiveErrorcode(msg);
                    MainActivity.pageHomelayout.ShowErrorcode();
                }else {
                    MainActivity.bluetoothComm.cleanWaitreceive();
                    ReceivePLCText(msg);
                    plccommError = "正常";
                    plcEQN_flag = false;
                    plcEQN_text = "";
                }
            }
        }
    }
    //PLC ASK应答命令
    private boolean ReceivePLCASK(){
        switch (MainActivity.doorStatus.plc_communication_flag){
            case DoorStatus.PLC_MODE_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.doorStatus.modecurrent = MainActivity.doorStatus.modeselected;
                break;
            case DoorStatus.PLC_LIGHT_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.doorStatus.light_status = !MainActivity.doorStatus.light_status;
                break;
            case DoorStatus.PLC_LOCK_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.doorStatus.lock_status = !MainActivity.doorStatus.lock_status;
                break;
            case DoorStatus.PLC_SETPOSITION_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.pageParameterlayout.parameterPLC.ReceivePositionset();
                break;
            case DoorStatus.PLC_RISKPOSITION_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.pageParameterlayout.parameterPLC.finishSetpostion();
                break;
            case DoorStatus.PLC_STOPPOSITION_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.pageParameterlayout.parameterPLC.finishSetpostion();
                break;
            case DoorStatus.PLC_RESETPOSITION_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                break;
            case DoorStatus.PLC_CLEANRUNTIME_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                break;
            case DoorStatus.PLC_RESTART_SEND:
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                MainActivity.pageMaintenancelayout.resetPorgress();
                break;
            default:
                break;
        }
        return false;
    }

    //PLC NAK错误指令
    private String ReceivePLCNAK(char[] msg){
        switch (msg[msg.length-1]){
            case 0x02:
                return "和校验出错";
            case 0x03:
                return "协议出错";
            case 0x06:
                return "字符区出错";
            case 0x07:
                return "字符出错";
            case 0x0A:
                return "PC号出错";
            case 0x10:
                return "PC号出错";
            case 0x18:
                return "远程出错";
            default:
                return "未知出错";
        }
    }

    //接收到PLC文本
    public void ReceivePLCText(char[] msg){

//        if(MainActivity.doorStatus.plc_communication_flag == DoorStatus.PLC_COMMUNICATION_FREE){//接收到系统主动发出的信息
//
//        }else {
            switch (MainActivity.doorStatus.plc_communication_flag) {
                case DoorStatus.PLC_MODE_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    //读取当前运行模式
                    if (MainActivity.bluetoothComm.commandPLC.ReceivePLCMode(msg)) {
                        //若在主页面状态查询，则执行下一条指令
                        if (MainActivity.pageHomelayout.home_check_list == 1) {
                            MainActivity.pageHomelayout.receiveStatus();
                        }
                    }
                    break;
                case DoorStatus.PLC_LIGHT_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    if (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2 - 1, msg.length - 2).equals("1")) {
                        MainActivity.doorStatus.light_status = true;
                    } else if (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2 - 1, msg.length - 2).equals("0")) {
                        MainActivity.doorStatus.light_status = false;
                    }
                    //若在主页面状态查询，则执行下一条指令
                    if (MainActivity.pageHomelayout.home_check_list == 4) {
                        MainActivity.pageHomelayout.receiveStatus();
                    }
                    break;
                case DoorStatus.PLC_LOCK_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    if (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2 - 1, msg.length - 2).equals("1")) {
                        MainActivity.doorStatus.light_status = true;
                    } else if (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2 - 1, msg.length - 2).equals("0")) {
                        MainActivity.doorStatus.light_status = false;
                    }
                    //若在主页面状态查询，则执行下一条指令
                    if (MainActivity.pageHomelayout.home_check_list == 3) {
                        MainActivity.pageHomelayout.receiveStatus();
                    }
                    break;
                case DoorStatus.PLC_ERROR_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.doorStatus.error_code = ReceiveErrorcode(msg);
                    //若在主页面状态查询，则执行下一条指令
                    MainActivity.doorStatus.error_code = Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 2, msg.length - 3);
                    if (MainActivity.pageHomelayout.home_check_list == 2) {
                        MainActivity.pageHomelayout.receiveStatus();
                    }
                    break;
                case DoorStatus.PLC_VERSION_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoVersionReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 4, msg.length - 3));
                    break;
                case DoorStatus.PLC_RUNTIMECURRENT_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoRuntimeReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 8, msg.length - 3));
                    break;
                case DoorStatus.PLC_RUNTIMEHISTORY_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoRuntimeReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 8, msg.length - 3));
                    break;
                case DoorStatus.PLC_SPEEDENCODER_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    break;
                case DoorStatus.PLC_SENSORFIXED_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoSensorReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 8, msg.length - 3));
                    break;
                case DoorStatus.PLC_SENSORMOBILE_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoSensorReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 12, msg.length - 3));
                    break;
                case DoorStatus.PLC_ERRORALL_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageInfolayout.infoErrorcodeReceive(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 40, msg.length - 3));
                    break;
                case DoorStatus.PLC_READPOSITION_SEND:
                    MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                    MainActivity.pageParameterlayout.parameterPLC.ReadpositionVlaue(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", ""));
//                    MainActivity.pageParameterlayout.parameterPLC.ReadpositionVlaue(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 3 - 8, msg.length - 3));
                    break;
                default:
                    break;
            }
    }

    //PLC 运行模式接收处理
    private boolean ReceivePLCMode(char[] msg){
        //提取运行模式的位置
        String strtmp = Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length-3-8,msg.length-3);
        switch (strtmp){
            case runmode.NORMAL:
                MainActivity.doorStatus.modecurrent = 0;
                MainActivity.doorStatus.modeselected = 0;
                break;
            case runmode.SUMMER:
                MainActivity.doorStatus.modecurrent = 1;
                MainActivity.doorStatus.modeselected = 1;
                break;
            case runmode.WINTER:
                MainActivity.doorStatus.modecurrent = 2;
                MainActivity.doorStatus.modeselected = 2;
                break;
            case runmode.AUTO:
                MainActivity.doorStatus.modecurrent = 3;
                MainActivity.doorStatus.modeselected = 3;
                break;
            case runmode.EXIT:
                MainActivity.doorStatus.modecurrent = 4;
                MainActivity.doorStatus.modeselected = 4;
                break;
            case runmode.OPEN:
                MainActivity.doorStatus.modecurrent = 5;
                MainActivity.doorStatus.modeselected = 5;
                break;
            case runmode.MANUAL:
                MainActivity.doorStatus.modecurrent = 6;
                MainActivity.doorStatus.modeselected = 6;
                break;
            case runmode.CHECK:
                MainActivity.doorStatus.modecurrent = 7;
                MainActivity.doorStatus.modeselected = 7;
                break;
            default:
                return false;
        }
        return true;
    }

    //PLC 错误代码接收处理
    private String ReceiveErrorcode(char[] msg){
        //提取运行模式的位置
        String strtmp = Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length-3-2,msg.length-3);
        return strtmp;
    }






}
