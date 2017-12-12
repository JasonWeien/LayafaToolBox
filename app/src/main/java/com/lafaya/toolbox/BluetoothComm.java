package com.lafaya.toolbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

import me.aflak.bluetooth.Bluetooth;

/**
 * Created by JeffYoung on 2016/11/4.
 **/
class BluetoothComm {

    CommandPLC commandPLC = new CommandPLC();
    CommandFVD commandFVD = new CommandFVD();
    CommandLafaya commandLafaya = new CommandLafaya();

//    public static boolean commedwait_flag = false;

    /*Bluetooth*/
    Bluetooth bluetooth;
    private int devicepos = 0;

    private static boolean bluetooth_connect_flag = false;
    private boolean registered=false;
    private Activity activity;

    Timer sendwaittime;//更新定时器
    boolean sendwaittime_flag = false;
    int resend_count = 0;

    private Handler handler;
    private String msgwaitsend = null;


    //Bluetooth初始化
    void bluetoothInitialize(Activity inactivity, Handler inhandler){
        activity = inactivity;
        handler = inhandler;
        bluetooth = new Bluetooth(activity);
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        activity.registerReceiver(mReceiver, filter);
        if(bluetooth.isConnected()){
            bluetooth.removeCommunicationCallback();
            bluetooth.disconnect();
        }
        //弹出连接设备窗口，要求连接设备
        activity.startActivityForResult(new Intent(activity, PageDevicelayout.class), 0);
    }
    //bluetooth 连接函数
    void bluetoothconnect(Intent data, final Handler handler, final Activity activity){
        /*弹出小窗口信息*/

//        Toast.makeText(activity, "连接设备中...", Toast.LENGTH_SHORT).show();
        devicepos = data.getExtras().getInt("pos");
        registered = true;
        bluetooth = new Bluetooth(activity);

        /*判断是否有已连接设备*/
        if(bluetooth.isConnected())
        {
            if(bluetooth.getPairedDevices().get(devicepos).getName().equals( bluetooth.getDevice().getName())) {
                bluetooth_connect_flag  = true;
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                handler.sendMessage(msg);
            }
            else{
                /*关闭连接*/
                bluetooth.disconnect();
                bluetooth_connect_flag = false;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bluetooth.connectToDevice(bluetooth.getPairedDevices().get(devicepos));
                            }
                        }, 2000);
                    }
                });
            }
        }
        else {
            bluetooth.connectToDevice(bluetooth.getPairedDevices().get(devicepos));
        }

        bluetooth.setCommunicationCallback(new Bluetooth.CommunicationCallback() {
            //设备连接成功
            @Override
            public void onConnect(BluetoothDevice device) {
                Message msg = new Message();
                msg.what = MainActivity.BT_CONNECTED;
                handler.sendMessage(msg);
            }

            //设备连接失败
            @Override
            public void onDisconnect(final BluetoothDevice device, String message) {
                Message msg = new Message();
                msg.what = MainActivity.BT_CONNECTFLASE;
                handler.sendMessage(msg);
            }
            //收到信息
            @Override
            public void onMessage(String message) {
                Message msg = new Message();
                msg.what = MainActivity.BT_RECEIVE;
                Bundle bundle = new Bundle();
                bundle.putString("msg",message);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            //错误
            @Override
            public void onError(String message) {
                Message msg = new Message();
                msg.what = MainActivity.BT_ERROR;
                handler.sendMessage(msg);
            }
            //连接错误
            @Override
            public void onConnectError(final BluetoothDevice device, String message) {
                Message msg = new Message();
                msg.what = MainActivity.BT_ERROR2;
                handler.sendMessage(msg);
            }
        });
    }

    //设备连接与断开
    void connectDevice(Activity activity,boolean flag,final Handler handler){
        if(flag){
            activity.startActivityForResult(new Intent(activity, PageDevicelayout.class), 0);
        }else{
            Dialog dialog = new AlertDialog.Builder(activity).setTitle("断开自动门连接").setMessage("确定断开自动门连接吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bluetooth.removeCommunicationCallback();
                    bluetooth.disconnect();
                    Message msg = new Message();
                    msg.what = MainActivity.BT_DISCONNECT;
                    handler.sendMessage(msg);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).create();
            dialog.show();
        }
    }

    //
    /*Send */
    //信息发送。。
    void SendMessage(String msg, final Activity activity){
        if(!MainActivity.doorStatus.door_connect){

            if(sendwaittime_flag){
                sendwaittime.cancel();
                sendwaittime_flag = false;
            }
            MainActivity.pageInfolayout.CleanInfolayout();
            MainActivity.pageHomelayout.CleanCheckstatus();

            //清PLC通讯标志
            if( MainActivity.doorStatus.plc_communication_flag != DoorStatus.PLC_COMMUNICATION_FREE){
                MainActivity.doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
            }
            //
            if(MainActivity.doorStatus.lafaya_communication_flag){
                MainActivity.doorStatus.lafaya_communication_flag = false;
            }

            Dialog dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.error_have).setTitle("自动门未连接").setMessage("请先连接自动门！").
                    setPositiveButton("确认", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //MainActivity.bluetoothComm.connectDevice(activity, true, handler);
                        }
                    }).create();
            dialog.show();
        }else if(!sendwaittime_flag){
            msgwaitsend = msg;
            resend_count = 0;
            sendwaittime_flag = true;
            sendwaittime = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = MainActivity.SEND_WAITE;
                    handler.sendMessage(msg);
                }
            };
            //查询间隔，每隔1000ms查询一次
            sendwaittime.schedule(task,1000,1000);
            MainActivity.bluetoothComm.bluetooth.send(msg);
            //Toast.makeText(activity, msg,Toast.LENGTH_LONG).show();
        }
    }

    //信息重新发送。。
    void reSendMessage(){
        MainActivity.bluetoothComm.bluetooth.send(msgwaitsend);
    }


    /*Receive*/
    //信息接收

    void ReceiveMessage(char[] msg) {

        //Toast.makeText(activity, "VFD指令", Toast.LENGTH_LONG).show();
        //Toast.makeText(activity, Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", ""), Toast.LENGTH_LONG).show();

        if(msg[0] == 0x7E){
            //7E lafaya door
            //Toast.makeText(activity, Arrays.toString(msg).replaceAll("[\\[\\]\\s,]", ""), Toast.LENGTH_LONG).show();
            if(msg.length >= 4) {
                commandLafaya.ReceiveLafayaCheck(msg);
            }

        }else if((msg[1] == 0x30) && (msg[2] == 0x31)) {
            //PLC指令
            commandPLC.ReceivePLCProcess(msg, activity);
        }else if((msg[1] == 0x30) && (msg[2] == 0x30)){
            //变频器指令
//            Toast.makeText(activity, "VFD指令", Toast.LENGTH_LONG).show();
            commandFVD.ReceiveVFDProcess(msg,activity);
        }

    }


    //蓝牙设备状态
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        if(registered) {
                            activity.unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        Toast.makeText(activity, "蓝牙设备已关闭", Toast.LENGTH_LONG).show();
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if(registered) {
                            activity.unregisterReceiver(mReceiver);
                            registered=false;
                        }
                        Toast.makeText(activity, "蓝牙设备已打开", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };

    void communicating(){
        Message msg = new Message();
        msg.what = MainActivity.COMMUNICATING;
        handler.sendMessage(msg);
    }

    void cleanWaitreceive(){
        //通讯。。等待
        if(sendwaittime_flag){
            resend_count = 0;
            sendwaittime.cancel();
            sendwaittime_flag = false;
        }
    }


}
