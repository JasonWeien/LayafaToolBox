package com.lafaya.toolbox;

import android.app.Activity;
import android.widget.Toast;

import java.util.Arrays;

/**
 * Created by JeffYoung on 2016/9/22.
 **/
class CommandFVD {
/*
    //VFD
    private String vfdbasicpr1Register = "01";
    private String vfdbasicpr2Register = "02";
    private String vfdbasicpr3Register = "03";
    private String vfdbasicpr7Register = "07";
    private String vfdbasicpr8Register = "08";;
    private String vfdbasicpr9Register = "09";

    private String vfdspeedpr4Register = "04";
    private String vfdspeedpr5Register = "05";
    private String vfdspeedpr6Register = "06";
    private String vfdspeedpr24Register = "24";
    private String vfdspeedpr25Register = "25";
    private String vfdspeedpr26Register = "26";
    private String vfdspeedpr27Register = "27";

    private String vfdmonitormodeRegister = "7B";
    private String vfdmonitorfreqRegister = "6F";
    private String vfdmonitorcurrentRegister = "70";
    private String vfdmonitorvoltageRegister = "71";
    private String vfdmonitorpr25Register = "19";
    private String vfdmonitorpr26Register = "1A";
    private String vfdmonitorpr27Register = "1B";
    */

    private String setvfdspeedupRegister = "87" + "1";
    private String readvfdspeedupRegister = "07" + "1";
    private String setvfdspeeddownRegister = "88" + "1";
    private String readvfdspeeddownRegister = "08" + "1";
    private String setvfddctimeRegister = "8B" + "1";
    private String readvfddctimeRegister = "0B" + "1";
    private String setvfdpwmRegister = "C8" + "1";
    private String readvfdpwmRegister = "48" + "1";
    private String setvfdspeedhighRegister = "84" + "1";
    private String readvfdspeedhighRegister = "04" + "1";
    private String setvfdspeedmidRegister = "85" + "1";
    private String readvfdspeedmidRegister = "05" + "1";
    private String setvfdspeedlowRegister = "86" + "1";
    private String readvfdspeedlowRegister = "06" + "1";

    private String setvfdspeed4Register = "98" + "1";
    private String readvfdspeed4Register = "18" + "1";
    private String setvfdspeed5Register = "99" + "1";
    private String readvfdspeed5Register = "19" + "1";
    private String setvfdspeed6Register = "9A" + "1";
    private String readvfdspeed6Register = "1A" + "1";
    private String setvfdspeed7Register = "9B" + "1";
    private String readvfdspeed7Register = "1B" + "1";

    private String readvfderrorcodeRegister = "74" + "1";

//    public static String vfdcommError = "正常";
    public static boolean vfdEQN_flag = false;
    public static String vfdEQN_text = "";
//    public static boolean vfdEQN_set = false;

    // 控制字: 文本接收开始字0x02，文本接收结束字0x03，查询字0x05，确认应答字0x06，否定应答字0x15
    private char vfdSTX = 0x02;
//    private char vfdETX = 0x03;
    private char vfdENQ = 0x05;
    private char vfdASK = 0x06;
//    private char vfdNAK = 0x15;

    //VFD 站号：0x00
    private String plcID = "00";


    //生成校验码
    private String vfdreatcheckcode(char[] msg){
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
    //发送到VFD
    private String SendtovfdCommand(String register){
        vfdEQN_flag = true;
        vfdEQN_text = vfdENQ + plcID + register;
        return vfdEQN_text;
    }

    //发送查询/设置加速时间
    String SendVFDspeedupTime(boolean flag,String time){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeedupRegister) + time).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeedupRegister).toCharArray());
        }
    }

    //发送查询/设置减速时间
    String SendVFDspeeddownTime(boolean flag,String time){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeeddownRegister) + time).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeeddownRegister).toCharArray());
        }
    }

    //发送查询/设置直流动作时间
    String SendVFDdcTime(boolean flag,String time){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfddctimeRegister) + time).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfddctimeRegister).toCharArray());
        }
    }

    //发送查询/设置PWM选择位
    String SendVFDpwmSet(boolean flag,String time){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdpwmRegister) + time).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdpwmRegister).toCharArray());
        }
    }

    //发送查询/设置多段速度高速
    String SendVFDspeedhigh(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeedhighRegister) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeedhighRegister).toCharArray());
        }
    }

    //发送查询/设置多段速度中速
    String SendVFDspeedmid(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeedmidRegister) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeedmidRegister).toCharArray());
        }
    }

    //发送查询/设置多段速度低速
    String SendVFDspeedlow(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeedlowRegister) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeedlowRegister).toCharArray());
        }
    }

    //发送查询/设置多段速度4速
    String SendVFDspeed4(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeed4Register) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeed4Register).toCharArray());
        }
    }

    //发送查询/设置多段速度5速
    String SendVFDspeed5(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeed5Register) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeed5Register).toCharArray());
        }
    }

    //发送查询/设置多段速度6速
    String SendVFDspeed6(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeed6Register) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeed6Register).toCharArray());
        }
    }

    //发送查询/设置多段速度4速
    String SendVFDspeed7(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeed7Register) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeed7Register).toCharArray());
        }
    }

    //发送查询/设置多段速度4速
    String SendVFDcurrent(boolean flag,String speed){
        if(flag){
            return vfdreatcheckcode((SendtovfdCommand(setvfdspeed7Register) + speed).toCharArray());
        }else {
            return vfdreatcheckcode(SendtovfdCommand(readvfdspeed7Register).toCharArray());
        }
    }

    //查询当前异常代码
//    public String SendVFDerrorcode(){
 //       return vfdreatcheckcode((SendtovfdCommand(readvfderrorcodeRegister)).toCharArray());
 //   }



    //接收到VFD信信息
    //进行数据检验
    private boolean vfddatacheck(char[] msg){
        /*生成检验码*/
        int sum = 0;
        for(int i = 1; i < msg.length -3; i++){
            sum = sum + msg[i];
        }
        //校验和转换为16进行字符，区分大小写。
        String strtemp = Integer.toHexString(sum & 0x00FF).toUpperCase();
        //字符串比较，区分大小写。。。
        return (Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(msg.length - 2,msg.length).equals(strtemp));

    }

    void ReceiveVFDProcess(char[] msg,Activity activity){
        //进行数据校验
            if(msg[0] == vfdASK){//接收 到应答
                MainActivity.bluetoothComm.cleanWaitreceive();
                //变频器指令
                ReceiveVFDASK();
            }else if(msg[0] == vfdSTX){//接收 到文本
                if (vfddatacheck(msg)) {
                    MainActivity.bluetoothComm.cleanWaitreceive();
                    ReceiveVFDText(msg);
                }
            }
    }
    //接收到应答
    private void ReceiveVFDASK(){
        MainActivity.pageParameterlayout.parameterSavefinished(5);
    }

    //接收到文本
    private void ReceiveVFDText(char[] msg){
        MainActivity.pageParameterlayout.parameterVFD.ReceiveParameters(Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", ""));
    }



}
