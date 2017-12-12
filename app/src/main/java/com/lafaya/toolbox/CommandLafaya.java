package com.lafaya.toolbox;

import java.util.Arrays;

/**
 * Created by JeffYoung on 2016/9/22.
 **/
public class CommandLafaya {

    //lafaya controller
    public final static boolean LAFAYA_COMMUNICATING = false;
    public final static boolean LAFAYA_WAITTO_SEND = false;
    public final static boolean LAFAYA_WAITTO_RECEIVE = false;
    public final static String LAFAYA_RECEIVE_MSG = null;
    public final static String LAFAYA_SEND_MSG = null;

    private boolean lafaya_send_flag = false;

    //握手字
    private char LafayaSTX = 0x7E;
    //
    // 发送地址
    public static char  sendslidingID = 0x21;
    public static char sendleftwingID  =0x21;
    public static char sendrightwingID  =0x21;
//    public static char  sendslidingID = 0x21;
//    public static char sendleftwingID  =0x25;
//    public static char sendrightwingID  =0x26;
    // 接收地址
    public static char  receiveslidingID = 0x12;
    public static char receiveleftwingID  =0x12;
    public static char receiverightwingID  =0x12;
//    public static char  receiveslidingID = 0x12;
//    public static char receiveleftwingID  =0x52;
//    public static char receiverightwingID  =0x62;

    //命令字说明
    private char cmd_queryinit = 0x80;
    private char cmd_queryopenspeed = 0x81;
    private char cmd_queryclosespeed = 0x82;
    private char cmd_querykeeptime = 0x85;
    private char cmd_queryerrorcode = 0x86;
    //公共命令
    private char cmd_setfinished = 0xC0;
    private char cmd_openspeed = 0xCA;
    private char cmd_closespeed = 0xCC;
    private char cmd_errorcode = 0xC4;
    private char cmd_totalerrorcode = 0xC5;
    private char cmd_keepopentime = 0xD2;
    private char cmd_opendoor = 0xC7;
    private char cmd_closedorr = 0x7C;
    private char cmd_restartdoor = 0xC6;
    private char cmd_resetdoor = 0xC9;
    private char cmd_linkagemode = 0xC8;
    private char cmd_runmode = 0xC2;
    private char cmd_powerlow = 0xC3;
    private char cmd_distancecloseendlearn = 0xCF;


    //信息查询修改命令
    private char cmd_info = 0x8A;
    private char cmd_info_version = 0x00;
    private char cmd_info_positionstart = 0x01;
    private char cmd_info_positionend = 0x02;
    private char cmd_info_positionmid = 0x03;
    private char cmd_info_speedthreshold = 0x04;
    private char cmd_info_pidfrequency = 0x05;
    private char cmd_info_speedlock = 0x06;
    private char cmd_info_lockdelay = 0x07;
    private char cmd_info_lockretry = 0x08;
    private char cmd_info_lockretime = 0x09;
    private char cmd_info_pwmspeeduniform = 0x0A;
    private char cmd_info_pwmspeedlow = 0x0B;
    private char cmd_info_canerrorcode = 0x0C;
    private char cmd_info_pwmspeedbreak = 0x0D;
    private char cmd_info_openscale = 0x0E;
    private char cmd_info_lowspeedopen = 0x0F;
    private char cmd_info_crawlspeed = 0x10;
    private char cmd_info_doublesystem = 0x11;
    private char cmd_info_speedlowrate = 0x12;
    private char cmd_info_distancelowopen = 0x13;
    private char cmd_info_distancelowclose = 0x14;
    private char cmd_info_distanceendopen = 0x15;
    private char cmd_info_distanceendclose = 0x16;
    private char cmd_info_keepcloseupper = 0x17;
    private char cmd_info_keepcloselower = 0x18;
    private char cmd_info_distancebreakopen = 0x19;
    private char cmd_info_distancebreakclose = 0x1A;
    private char cmd_info_distanceaccecalc = 0x1B;
    private char cmd_info_distanceacceactual = 0x1C;
    private char cmd_info_distancebreakactual = 0x1D;
    private char cmd_info_minaccetime = 0x1E;
    private char cmd_info_enterreverse = 0x1F;
    private char cmd_info_minpwm = 0x20;
    private char cmd_info_maxpwm = 0x21;
    private char cmd_info_currentsmax = 0x22;
    private char cmd_info_currents1 = 0x23;
    private char cmd_info_currents2 = 0x24;
    private char cmd_info_currents3 = 0x25;
    private char cmd_info_currents4 = 0x26;
    private char cmd_info_currentsopen = 0x27;
    private char cmd_info_currentsclose = 0x28;
    private char cmd_info_piddrive = 0x29;
    private char cmd_info_pidretract = 0x2A;
    private char cmd_info_pidbreak = 0x2B;
    private char cmd_info_pidreverse = 0x2C;
    private char cmd_info_smooth = 0x2D;
    private char cmd_info_openmodekeep = 0x2E;
    private char cmd_info_currentsintegral = 0x2F;
    private char cmd_info_currentruntime = 0x30;
    private char cmd_info_totalruntime = 0x31;
    private char cmd_info_temperatureupper = 0x32;
    private char cmd_info_temperaturelower = 0x33;
    private char cmd_info_canaddress = 0x34;
    private char cmd_info_standbypowerenable = 0x35;
    private char cmd_info_obstacleretime = 0x36;
    private char cmd_info_obstaclereinterval = 0x37;
    private char cmd_info_testmodelockrate = 0x38;
    private char cmd_info_maxcurrentshistory = 0x41;
    private char cmd_info_maxtemperaturehistory = 0x42;
    private char cmd_info_temperaturenow = 0x43;
    private char cmd_info_systemerrorcause = 0x45;
    private char cmd_info_errorinfo = 0x46;
    private char cmd_info_authorization = 0x47;
    //硬件信息查询
    private char cmd_hardware = 0xA0;
    private char cmd_hardware_door = 0x00;
    private char cmd_hardware_motor = 0x01;
    private char cmd_hardware_lock = 0x02;
    private char cmd_hardware_switch = 0x03;
    private char cmd_hardware_currents = 0x04;
    private char cmd_hardware_linkage = 0x05;
    private char cmd_hardware_speedlock = 0x06;
    private char cmd_hardware_canaddress = 0x07;
    //测试、状态监测
    private char cmd_testmode = 0xFF;
    private char cmd_hardwaretest = 0xA1;
    private char cmd_monitor = 0xA2;

    public static String[] sliding_parameter = new String[]{""};

    //生成校验码
    //生成带校验码数据
    //校验码生成：两两参数进行异或（去除握手字和结束字）
    private String lafayacreatcheckcode(char[] msg){
        lafaya_send_flag = true;
        char sum = 0x00;
        String strmsg = "";
        for(int i = 0; i < msg.length; i++){
            sum ^= msg[i];
            if(msg[i] < 0x0F){
                strmsg += '0';
                strmsg += Integer.toHexString(msg[i] & 0x00FF).toUpperCase();
            }else{
                strmsg += Integer.toHexString(msg[i] & 0x00FF).toUpperCase();
            }
        }
        //注：字母为大写形式。。。
        return strmsg + Integer.toHexString(sum & 0x00FF).toUpperCase() + "\r";
    }

    // Lafaya 命令发送。。。。。。
    //初始化查询0x80
    public String SendLafayaQueryInit(char addrs){
        char[] msg = new char[]{addrs,cmd_queryinit};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //开门最高速度查询0x81
    public String SendLafayaQueryOpenSpeed(char adrs){
        char[] msg = new char[]{adrs,cmd_queryopenspeed};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //关门最高速度查询0x82
    public String SendLafayaQueryCloseSpeed(char addrs){
        char[] msg = new char[]{addrs,cmd_queryclosespeed};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //开门保持时间查询0x85
    public String SendLafayaQueryKeepTime(char addrs){
        char[] msg = new char[]{addrs,cmd_querykeeptime};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //历史报警信息查询0x86
    public String SendLafayaQueryErrorcode(char addrs){
        char[] msg = new char[]{addrs,cmd_queryerrorcode};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //软件版本号查询0x8A + 0x00
    public String SendLafayaInfoVersion(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_version};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //行程起点数值查询0x8A + 0x01
    public String SendLafayaInfoPositionStart(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_positionstart};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //行程终点数值查询0x8A + 0x02
    public String SendLafayaInfoPositionEnd(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_positionend};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //行程半开位置数据查询0x8A + 0x03
    public String SendLafayaInfoPositionMid(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_positionmid};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //门体速度过低门槛0x8A + 0x04，无参数或带1个参数
    public String SendLafayaInfoSpeedThreshold(char addrs,int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedthreshold, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedthreshold};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //PID调节T4中断间隔0x8A + 0x05，无参数或带1个参数
    public String SendLafayaInfoPIDFrequency(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidfrequency, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidfrequency};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //速度锁定标志0x8A + 0x06，无参数或带1个参数
    public String SendLafayaInfoSpeedLock(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedlock, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedlock};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //锁动作延迟0x8A + 0x07，无参数或带1个参数
    public String SendLafayaInfoLockDelay(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockdelay, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockdelay};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //锁动作重试次数0x8A + 0x08，无参数或带1个参数
    public String SendLafayaInfoLockRetry(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockretry, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockretry};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //锁动作重试次数间隔0x8A + 0x09，无参数或带1个参数
    public String SendLafayaInfoLockRetime(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockretime, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lockretime};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //匀速段超速不减PWM门槛0x8A + 0x0A，无参数或带1个参数
    public String SendLafayaInfoPWMSpeedUniform(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeeduniform, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeeduniform};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //爬行末段超速不减PWM门槛0x8A + 0x0B，无参数或带1个参数
    public String SendLafayaInfoPWMSpeedLow(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeedlow, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeedlow};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //刹车段不增PWM门槛0x8A + 0x0C，无参数或带1个参数
    public String SendLafayaInfoCanErrorcode(char addrs, char data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_canerrorcode, data};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_canerrorcode};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //刹车段不增PWM门槛0x8A + 0x0D，无参数或带1个参数
    public String SendLafayaInfoPWMSpeedBreak(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeedbreak, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pwmspeedbreak};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //门扇开度0x8A + 0x0E，无参数或带1个参数
    public String SendLafayaInfoOpenScale(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_openscale, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_openscale};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //开门缓行距离0x8A + 0x0F，无参数或带2个参数
    public String SendLafayaInfoLowspeedOpen(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lowspeedopen, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_lowspeedopen};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //爬行速度0x8A + 0x10，无参数或带2个参数
    public String SendLafayaInfoCrawlSpeed(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_crawlspeed, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_crawlspeed};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //双机联机状态0x8A + 0x11，无参数
    public String SendLafayaInfoDoubelSystem(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_doublesystem};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //速度跌落比系数0x8A + 0x12，无参数或带1个参数
    public String SendLafayaInfoSpeedlowRate(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedlowrate, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_speedlowrate};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //开门爬行距离0x8A + 0x13，无参数或带2个参数
    public String SendLafayaInfoDistancelowOpen(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distancelowopen, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distancelowopen};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //关门爬行距离0x8A + 0x14，无参数或带2个参数
    public String SendLafayaInfoDistancelowClose(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distancelowclose, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distancelowclose};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //开门末段距离0x8A + 0x15，无参数或带2个参数
    public String SendLafayaInfoDistanceendOpen(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distanceendopen, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distanceendopen};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //关门末段距离0x8A + 0x16，无参数或带2个参数
    public String SendLafayaInfoDistanceendClose(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distanceendclose, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_distanceendclose};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //关门保持上限0x8A + 0x17，无参数或带2个参数
    public String SendLafayaInfoKeepcloseUpper(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_keepcloseupper, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_keepcloseupper};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //关门保持下限0x8A + 0x18，无参数或带2个参数
    public String SendLafayaInfoKeepcloseLower(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_keepcloselower, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_keepcloselower};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //查询最近一次开门刹车距离计算值0x8A + 0x19
    public String SendLafayaInfoDistanceBreakopen(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_distancebreakopen};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //查询最近一次关门刹车距离计算值0x8A + 0x1A
    public String SendLafayaInfoDistanceBreakclose(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_distancebreakclose};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //查询最近一次加速距离计算值0x8A + 0x1B
    public String SendLafayaInfoDistanceAccecalc(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_distanceaccecalc};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //查询最近一次加速距离实际值0x8A + 0x1C
    public String SendLafayaInfoDistanceAcceactual(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_distanceacceactual};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //查询最近一次刹车距离实际值0x8A + 0x1D
    public String SendLafayaInfoDistanceBreakactual(char addrs){
        char[] msg = new char[]{addrs,cmd_info,cmd_info_distancebreakactual};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //最小加速时间查询设置0x8A + 0x1E，无参数或带2个参数
    public String SendLafayaInfoMinacceTime(char addrs, char[] data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_minaccetime, data[0],data[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_minaccetime};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //是否允许进行反向刹车查询设置0x8A + 0x1F，无参数或带1个参数
    public String SendLafayaInfoEnterReverse(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_enterreverse, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_enterreverse};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //PWM最小值查询设置0x8A + 0x20，无参数或带2个参数
    public String SendLafayaInfoMinPWM(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_minpwm, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_minpwm};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //PWM最大值查询设置0x8A + 0x21，无参数或带2个参数
    public String SendLafayaInfoMaxPWM(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_maxpwm, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_maxpwm};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //系统最大电流查询设置0x8A + 0x22，无参数或带2个参数
    public String SendLafayaInfoCurrentsMax(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsmax, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsmax};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //电流档位1电流查询设置0x8A + 0x23，无参数或带2个参数
    public String SendLafayaInfoCurrents1(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents1, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents1};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //电流档位2电流查询设置0x8A + 0x24，无参数或带2个参数
    public String SendLafayaInfoCurrents2(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents2, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents2};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //电流档位3电流查询设置0x8A + 0x25，无参数或带2个参数
    public String SendLafayaInfoCurrents3(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents3, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents3};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //电流档位4电流查询设置0x8A + 0x26，无参数或带2个参数
    public String SendLafayaInfoCurrents4(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents4, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currents4};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //开门保持电流查询设置0x8A + 0x27，无参数或带2个参数
    public String SendLafayaInfoCurrentsOpen(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsopen, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsopen};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //关门保持电流查询设置0x8A + 0x28，无参数或带2个参数
    public String SendLafayaInfoCurrentsClose(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsclose, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsclose};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //PID正向驱动系数查询设置0x8A + 0x29，无参数或带2个参数
    public String SendLafayaInfoPIDDrive(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_piddrive, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_piddrive};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //PID撤退系数查询设置0x8A + 0x2A，无参数或带2个参数
    public String SendLafayaInfoPIDRetract(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidretract, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidretract};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //PID刹车系数查询设置0x8A + 0x2B，无参数或带2个参数
    public String SendLafayaInfoPIDBreak(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidbreak, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidbreak};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //PID反向系数查询设置0x8A + 0x2C，无参数或带2个参数
    public String SendLafayaInfoPIDReverse(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidreverse, datatemp[0],datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_pidreverse};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //平稳度查询设置0x8A + 0x2D，无参数或带1个参数
    public String SendLafayaInfoSmooth(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_smooth, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_smooth};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //常开模式保持时间查询设置0x8A + 0x2E，无参数或带1个参数
    public String SendLafayaInfoOpenmodeKeep(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_openmodekeep, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_openmodekeep};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //历史电流积分查询0x8A + 0x2F，无参数
    public String SendLafayaInfoCurrentsIntegral(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_currentsintegral};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //本次开门次数查询0x8A + 0x30，无参数
    public String SendLafayaInfoCurrentRuntime(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_currentruntime};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //历史开门次数查询0x8A + 0x31，无参数
    public String SendLafayaInfoTotalRuntime(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_totalruntime};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //超温上限查询设置0x8A + 0x32，无参数或带1个参数
    public String SendLafayaInfoTemperatureUpper(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_temperatureupper, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_temperatureupper};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //超温下限查询设置0x8A + 0x33，无参数或带1个参数
    public String SendLafayaInfoTemperatureLower(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_temperaturelower, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_temperaturelower};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //CAN地址查询设置0x8A + 0x34，无参数或带1个参数
    public String SendLafayaInfoCanAddress(char addrs, char data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_canaddress, data};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_canaddress};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //备用电池使能查询设置0x8A + 0x35，无参数或带1个参数
    public String SendLafayaInfoStandbypowerEnable(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_standbypowerenable, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_standbypowerenable};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //遇阻重试次数查询设置0x8A + 0x36，无参数或带1个参数
    public String SendLafayaInfoObstacleRetime(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_obstacleretime, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_obstacleretime};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //遇阻重试间隔查询设置0x8A + 0x37，无参数或带1个参数
    public String SendLafayaInfoObstacleReinterval(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_obstaclereinterval, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_obstaclereinterval};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //测试模式下锁落锁频率查询设置0x8A + 0x38，无参数或带1个参数
    public String SendLafayaInfoTestmodeLockrate(char addrs, int data,boolean flag){
        char[] datatemp = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_testmodelockrate, datatemp[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_testmodelockrate};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //历史最大电流查询0x8A + 0x41，无参数
    public String SendLafayaInfoMaxcurrentsHistory(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_maxcurrentshistory};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //历史最高温度查询0x8A + 0x42，无参数
    public String SendLafayaInfoMaxtemperatureHistory(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_maxtemperaturehistory};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //当前温度查询0x8A + 0x43，无参数
    public String SendLafayaInfoTemperatureNow(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_temperaturenow};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //最近10次复位原因查询0x8A + 0x45，无参数
    public String SendLafayaInfcoSystemerrorCause(char addrs){
        char[] msg = new char[]{addrs, cmd_info, cmd_info_systemerrorcause};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //严重错误信息查询0x8A + 0x46，无参数或带1个、两个参数
    public String SendLafayaInfoErrorInfo(char addrs, char[] data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_errorinfo, data[0],data[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_errorinfo};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }

    //软件授权查询、授权0x8A + 0x47，无参数或6个参数
    public String SendLafayaInfoAuthorization(char addrs, char[] data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_info, cmd_info_authorization, data[0],data[1],data[2],data[3],data[4],data[5]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_info, cmd_info_authorization};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //硬件信息查询
    //门类型查询 0xA0 + 0x00
    public String SendLafayaHardwareDoor(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_door};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //电机类型查询 0xA0 + 0x01
    public String SendLafayaHardwareMotor(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_motor};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //锁存在查询 0xA0 + 0x02
    public String SendLafayaHardwareLock(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_lock};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //模式开关类型查询 0xA0 + 0x03
    public String SendLafayaHardwareSwitch(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_switch};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //电流选择查询 0xA0 + 0x04
    public String SendLafayaHardwareCurrents(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_currents};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //双机联动查询 0xA0 + 0x05
    public String SendLafayaHardwareLinkage(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_linkage};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //速度锁定查询 0xA0 + 0x06
    public String SendLafayaHardwareSpeedlock(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_speedlock};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //CAN地址查询 0xA0 + 0x07
    public String SendLafayaHardwareCanaddress(char addrs){
        char[] msg = new char[]{addrs, cmd_hardware, cmd_hardware_canaddress};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //公用命令
    //运行模式查询设置0xC2，无参数或带1个参数
    public String SendLafayaRummode(char addrs, int data,boolean flag){
        if(flag) {
            char[] msg;
            switch (data){
                case 0://auto
                    msg = new char[]{addrs,cmd_runmode, 0x02};
                    break;
                case 1://open
                    msg = new char[]{addrs,cmd_runmode, 0x08};
                    break;
                case 2://close
                    msg = new char[]{addrs,cmd_runmode, 0x04};
                    break;
                case 3://exit
                    msg = new char[]{addrs,cmd_runmode, 0x01};
                    break;
                default:
                    msg = new char[]{addrs,cmd_runmode, 0x02};
                    break;
            }

            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs,cmd_runmode};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //系统掉电 后电锁状态查询设置0xC3，无参数或带1个参数
    public String SendLafayaPowerlow(char addrs, char data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs,cmd_powerlow, data};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs,cmd_powerlow};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //报警通知查询  0xC4 ，无参数
    public String SendLafayaErrorcode(char addrs){
        char[] msg = new char[]{addrs, cmd_errorcode};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //历史报警通知查询  0xC5 ，无参数
    public String SendLafayaTotalErrorcode(char addrs){
        char[] msg = new char[]{addrs,cmd_totalerrorcode};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //软复位  0xC6 ，无参数
    public String SendLafayaRestartDoor(char addrs){
        char[] msg = new char[]{addrs,cmd_restartdoor};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //开门命令  0xC7 ，无参数
    public String SendLafayaOpendoor(char addrs){
        char[] msg = new char[]{addrs,cmd_opendoor};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //双机联动模式查询设置0xC8，无参数或带1个参数
    public String SendLafayaLinkageMode(char addrs, char data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs,cmd_linkagemode, data};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs,cmd_linkagemode};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //硬复位  0xC9 ，无参数
    public String SendLafayaResettDoor(char addrs){
        char[] msg = new char[]{addrs, cmd_resetdoor};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }

    //开门最高速度查询修改0xCA，无参数或两个参数
    public String SendLafayaOpenSpeed(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_openspeed, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_openspeed};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //关门最高速度查询修改0xCC，无参数或两个参数
    public String SendLafayaCloseSpeed(char addrs, int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_closespeed, tempdata[0],tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_closespeed};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //关门末段距离学习0xCF，无参数或1个参数
    public String SendLafayaDistancecloseendLearn(char addrs,char[] data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_distancecloseendlearn, data[0],data[1],data[2],data[3]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_distancecloseendlearn};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //开门保持时间查询修改0xD2，无参数或1个参数
    public String SendLafayaKeepopenTime(char addrs,int data,boolean flag){
        char[] tempdata = IntegertoChar(data);
        if(flag) {
            char[] msg = new char[]{addrs, cmd_keepopentime, tempdata[1]};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_keepopentime};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //测试模式设置取消0xFF，无参数或1个参数
    public String SendLafayaTestmode(char addrs,char data,boolean flag){
        if(flag) {
            char[] msg = new char[]{addrs, cmd_testmode, data};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }else{
            char[] msg = new char[]{addrs, cmd_testmode};
            return  LafayaSTX + lafayacreatcheckcode(msg);
        }
    }
    //测试模式设置取消0xA1，无参数或1个参数
    public String SendLafayaHardwareTest(char addrs,char data){
        char[] msg = new char[]{addrs, cmd_hardwaretest, data};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }
    //测试模式设置取消0xA2，无参数或1个参数
    public String SendLafayaMonitor(char addrs,int data){
        char[] datatemp = IntegertoChar(data);
        char[] msg = new char[]{addrs, cmd_monitor, datatemp[1]};
        return  LafayaSTX + lafayacreatcheckcode(msg);
    }


    //==================================================================================================
    //ASCII 码转 HEX码
    public char[] AsciiToHex(char[] asciidata){
        char[] hexdata = new char[asciidata.length/2];

        for (int i = 0; i < asciidata.length; i++){
            if((asciidata[i] >= 0x61) && (asciidata[i] < 0x67)){
                asciidata[i] = (char)((asciidata[i] - 0x57) & 0x0F);
            }else if((asciidata[i] >= 0x41) && (asciidata[i] < 0x47)){
                asciidata[i] = (char)((asciidata[i] - 0x37) & 0x0F);
            }else{
                asciidata[i] = (char)((asciidata[i] - 0x30) & 0x0F);
            }

            if(i % 2 == 1){
                hexdata[(i-1)/2] = (char)(asciidata[i-1] *16 + asciidata[i]);
            }
        }
        return hexdata;
    }
    //接收检验处理
    public void ReceiveLafayaCheck(char[] msg){
        String strtemp = Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", "").substring(1,msg.length);
        char[] data = AsciiToHex(strtemp.toCharArray());
        if(data.length >= 2){
            char sumcode = 0x00;
            for(int itmp = 0;itmp < (data.length-1);itmp++){
                sumcode ^= data[itmp];
            }
            //接收校验，校验成功后，执行命令
            if(sumcode == data[data.length-1]){
                if(lafaya_send_flag){
                    MainActivity.bluetoothComm.cleanWaitreceive();
                    lafaya_send_flag = false;
                }
                ReceiveLafayaProcess(data);
            }
        }
    }
    //接收处理
    private void ReceiveLafayaProcess(char[] data) {
        //判断命令
        if(data[1] == cmd_info){
            RecieveLafayaInfo(data);
        }else if(data[1] == cmd_hardware){//硬件参数
            RecieveLafayaHardware(data);
        }else if(data[1] == cmd_runmode){//运行模式
            if(data.length >= 3){
                MainActivity.doorStatus.ReceiveRunmode(Integer.toString((int)data[2]));
            }

        }else if(data[1] == cmd_powerlow){//电压低

        }else if(data[1] == cmd_errorcode){//错误代码
            if(data.length >= 3) {
                if(((int)data[2]) <= 15){
                    MainActivity.doorStatus.ReceiveErrocode( "0"+ Integer.toHexString((int)data[2]).toUpperCase());
                }else{
                    MainActivity.doorStatus.ReceiveErrocode(Integer.toHexString((int)data[2]).toUpperCase());
                }
            }
        }else if(data[1] == cmd_totalerrorcode){//历史报警代码
            if(data.length > 10){
                String strtmp = "";
                for(int itmp = 0;itmp < 10; itmp++){
                    if((int)(data[itmp+2]) <= 15){
                        strtmp += ("0" + Integer.toHexString((int)data[itmp+2]).toUpperCase());
                    }else{
                        strtmp += Integer.toHexString((int)data[itmp+2]).toUpperCase();
                    }
                }
                MainActivity.pageInfolayout.infoErrorcodeReceive(strtmp);
                if(MainActivity.doorStatus.lafaya_communication_flag){
                    MainActivity.doorStatus.lafaya_communication_flag = false;
                }
            }

        }else if(data[1] == cmd_linkagemode){//链接模式

        }else if(data[1] == cmd_openspeed){//开门速度
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveQuery(Integer.toString(ChartoInteger(data[2],data[3])));
            }
        }else if(data[1] == cmd_closespeed){//关门速度
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveQuery(Integer.toString(ChartoInteger(data[2],data[3])));
            }
        }else if(data[1] == cmd_distancecloseendlearn){//关门距离学习

        }else if(data[1] == cmd_keepopentime){//开门保持时间
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveQuery(Integer.toString((int)data[2]));
            }
        }else if(data[1] == cmd_testmode){//

        }else if(data[1] == cmd_hardwaretest){
            RecieveLafayaHardwaretest(data);
        }else if(data[1] == cmd_monitor){
            RecieveLafayaMonitor(data);
        }else if(data[1] == cmd_setfinished){
            if(MainActivity.doorStatus.lafaya_modeset_flag){
                MainActivity.doorStatus.ReceiveRunmode("");
            }else {
                MainActivity.pageParameterlayout.parameterSavefinished(2);
            }
        }
    }

    //硬件测试
    private void RecieveLafayaHardwaretest(char[] data){

    }

    //软件监控
    private void RecieveLafayaMonitor(char[] data){
        String strtemp = "";
        if(data.length > 25) {
            if (((int) data[20]) <= 15) {
                strtemp += ("0" + Integer.toString((int) data[20]).toUpperCase());
            } else {
                strtemp += Integer.toString((int) data[20]).toUpperCase();
            }
            //
            if (((int) data[21]) <= 15) {
                strtemp += ("0" + Integer.toString((int) data[21]).toUpperCase());
            } else {
                strtemp += Integer.toString((int) data[21]).toUpperCase();
            }
            //
            if (((int) data[22]) <= 15) {
                strtemp += ("0" + Integer.toString((int) data[22]).toUpperCase());
            } else {
                strtemp += Integer.toString((int) data[22]).toUpperCase();
            }
            //
            if (((int) data[23]) <= 15) {
                strtemp += ("0" + Integer.toString((int) data[23]).toUpperCase());
            } else {
                strtemp += Integer.toString((int) data[23]).toUpperCase();
            }

            MainActivity.pageInfolayout.infoSensorReceive(strtemp);

        }

    }


    //接收到系统信息修改查询反馈
    private void RecieveLafayaInfo(char[] data){
        if(data[2] == cmd_info_version){//软件版本
            ReceiveLafayaVersion(data);
        }else if(data[2] == cmd_info_positionstart){//行程起点

        }else if(data[2] == cmd_info_positionend){//行程终点

        }else if(data[2] == cmd_info_positionmid){//行程中间点

        }else if(data[2] == cmd_info_speedthreshold){//速度低速阈值
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_pidfrequency){//PID修改频率
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_speedlock){//速度锁定标志
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_lockdelay){//锁动作延迟
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersperi(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_lockretry){//锁重试次数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersperi(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_lockretime){//锁重试间隔
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersperi(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_pwmspeeduniform){//PWM匀速不减
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_pwmspeedlow){//PWM低速不减
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_canerrorcode){//CAN错误信息

        }else if(data[2] == cmd_info_pwmspeedbreak){//PWM刹车不增
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_openscale){//开度
            if(data.length >= 3){
                if(MainActivity.doorStatus.lafaya_communication_flag){
                    MainActivity.doorStatus.ReceiveOpenrate(Integer.toString((int) data[3]));
                }else {
                    MainActivity.pageParameterlayout.parameterLafaya.ReceiveQuery(Integer.toString((int) data[3]));
                }
            }

        }else if(data[2] == cmd_info_lowspeedopen){//低速开门距离
                if(data.length >= 3){
                    MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
                }

        }else if(data[2] == cmd_info_crawlspeed){//爬行速度
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_doublesystem){//双机联动标志

        }else if(data[2] == cmd_info_speedlowrate){//速度跌落比例
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_distancelowopen){//开门爬行距离
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_distancelowclose){//关门爬行距离
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_distanceendopen){//开门末段距离
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_distanceendclose){//关门末段距离
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_keepcloseupper){//关门保持上限
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_keepcloselower){//关门保持下限
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersdistance(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_distancebreakopen){//本次开门刹车距离计算值

        }else if(data[2] == cmd_info_distancebreakclose){//本次关门刹车距离计算值

        }else if(data[2] == cmd_info_distanceaccecalc){//本次加速距离计算值

        }else if(data[2] == cmd_info_distanceacceactual){//本次加速距离实际值

        }else if(data[2] == cmd_info_distancebreakactual){//本次刹车距离实际值

        }else if(data[2] == cmd_info_minaccetime){//最小加速时间

        }else if(data[2] == cmd_info_enterreverse){//反向刹车标志
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_minpwm){//PWM最小值
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_maxpwm){//PWM最大值
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currentsmax){//最大电流值
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currents1){//电流1档
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currents2){//电流2档
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currents3){//电流3档
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currents4){//电流4档
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currentsopen){//开门保持电流
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_currentsclose){//关门保持电流
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscurrent(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_piddrive){//PID正向驱动系数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_pidretract){//PID撤退系数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_pidbreak){//PID刹车系数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_pidreverse){//PID反向系数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterspwm(Integer.toString(ChartoInteger(data[3],data[4])));
            }

        }else if(data[2] == cmd_info_smooth){//平稳度
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_openmodekeep){//常开模式保持时间
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_currentsintegral){//电流积分值

        }else if(data[2] == cmd_info_currentruntime){//当前运行次数
            if(data.length > 6){
                MainActivity.pageInfolayout.infoRuntimeReceive(Long.toString(IntegertoLong(ChartoInteger(data[3],data[4]),ChartoInteger(data[5],data[6]))));
            }
        }else if(data[2] == cmd_info_totalruntime){//总运行次数
            if(data.length > 6){
                MainActivity.pageInfolayout.infoRuntimeReceive(Long.toString(IntegertoLong(ChartoInteger(data[3],data[4]),ChartoInteger(data[5],data[6]))));
            }
        }else if(data[2] == cmd_info_temperatureupper){//超温上限
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_temperaturelower){//超温下限
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_canaddress){//CAN地址

        }else if(data[2] == cmd_info_standbypowerenable){//备用电源使能
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersperi(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_obstacleretime){//遇阻重试次数
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_obstaclereinterval){//遇阻重试间隔
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParameterscontrol(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_testmodelockrate){//测试模式下锁闭频率
            if(data.length >= 3){
                MainActivity.pageParameterlayout.parameterLafaya.ReceiveParametersperi(Integer.toString((int)data[3]));
            }

        }else if(data[2] == cmd_info_maxcurrentshistory){//历史电流最大值

        }else if(data[2] == cmd_info_maxtemperaturehistory){//历史温度最大值

        }else if(data[2] == cmd_info_temperaturenow){//当前温度

        }else if(data[2] == cmd_info_systemerrorcause){//系统错误原因

        }else if(data[2] == cmd_info_errorinfo){//严重错误信息

        }else if(data[2] == cmd_info_authorization){//授权信息

        }
    }

    //软件版本
    private void ReceiveLafayaVersion(char[] data){
        //来源判断，
        String strtmp = Integer.toHexString(data[3]).toUpperCase() + Integer.toHexString(data[4]).toUpperCase() + Integer.toHexString(data[5]).toUpperCase() + Integer.toHexString(data[6]).toUpperCase();
//        if(data[0] == receiveslidingID){//中间门
            //PageInformation.system_version[1] = strtmp;
            MainActivity.pageInfolayout.infoVersionReceive(strtmp);
 //       }else if(data[0] == receiveleftwingID){//左门翼
           //PageInformation.system_version[2] = strtmp;
           MainActivity.pageInfolayout.infoVersionReceive(strtmp);
  //      }else if(data[0] == receiverightwingID){//右门翼
            //PageInformation.system_version[3] = strtmp;
   //         MainActivity.pageInfolayout.infoVersionReceive(strtmp);
 //       }
    }

    //接收到系统信息修改查询反馈
    private void RecieveLafayaHardware(char[] data){
        if(data[2] == cmd_hardware_door){

        }else if(data[2] == cmd_hardware_motor){

        }else if(data[2] == cmd_hardware_lock){

        }else if(data[2] == cmd_hardware_switch){

        }else if(data[2] == cmd_hardware_speedlock){

        }else if(data[2] == cmd_hardware_currents){

        }else if(data[2] == cmd_hardware_canaddress){

        }else if(data[2] == cmd_hardware_linkage){

        }
    }

    private int ChartoInteger(char data1,char data2){
        return (((int)data1) << 8) + data2;
    }

    private char[] IntegertoChar(int data){
        char[] temp = {0xFF,0xFF};
        temp[1] &= ((char)data);
        temp[0] &= ((char)data >> 8);
        return temp;
    }

    private char[] LongtoChar(long data){
        char[] temp = {0xFF,0xFF,0xFF,0xFF};
        temp[3] &= ((char)data);
        temp[2] &= ((char)data >> 8);
        temp[1] &= ((char)data >> 16);
        temp[0] &= ((char)data >> 24);
        return temp;
    }

    private long IntegertoLong(int data1, int data2){
        return (((long)data1) << 16) + data2;
    }
}
