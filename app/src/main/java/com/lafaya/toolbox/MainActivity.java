package com.lafaya.toolbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.aflak.bluetooth.Bluetooth;

/**
 * Created by JeffYoung on 2016/10/27.
 **/
public class MainActivity extends Activity{
    private ImageButton button_backward;
    private ImageButton button_forward;
    private ImageButton button_forward_drawer;
    private ImageButton button_menu;
    private TextView text_title;
    private TextView text_title_drawer;
    private ImageView image_menu;
    private TextView view_exit_app;

    // layout
    public static int layoutnumber;
    public int layoutnumber_old;
    private RelativeLayout activity_drawerlayout,activity_homelayout,activity_settinglayout,activity_modesetlayout;
    private RelativeLayout activity_maintenancelayout,activity_parameterlayout,layout_titlebar;
    private RelativeLayout activity_aboutlayout,activity_infolayout,activity_helplayout,activity_progresslayout;

    public static PageHomelayout pageHomelayout = new PageHomelayout();
    public static PageDrawerlayout pageDrawerlayout = new PageDrawerlayout();
    public static PageSettinglayout pageSettinglayout = new PageSettinglayout();
    public static PageModesetlayout pageModesetlayout = new PageModesetlayout();
    public static PageMaintenancelayout pageMaintenancelayout = new PageMaintenancelayout();
    public static PageParameterlayout pageParameterlayout = new PageParameterlayout();
    public static PageAboutlayout pageAboutlayout = new PageAboutlayout();
    public static PageHelplayout pageHelplayout = new PageHelplayout();
    public static PageInfolayout pageInfolayout = new PageInfolayout();
    public static PageProgressLayout pageProgressLayout = new PageProgressLayout();

    public static DoorStatus doorStatus = new DoorStatus();

    //bluetooth
    static final private int GET_DEVICE = 0;
    public static  BluetoothComm bluetoothComm = new BluetoothComm();


    //Layout layer


    //Handle case
    public final static int LAYOUTSHOW = 1;
    public final static int BT_CONNECTED = 2;
    public final static int BT_CONNECTFLASE = 3;
    public final static int BT_RECEIVE = 4;
    public final static int BT_ERROR = 5;
    public final static int BT_ERROR2 = 6;
    public final static int BT_DISCONNECT = 7;
    public final static int HOME_UPDATE = 8;
    public final static int PLC_RESET = 9;
    public final static int COMMUNICATING = 10;

    public final static int POSITION_READ = 66;
    public final static int INFO_CHECK = 77;
    public final static int SEND_WAITE = 88;
    public final static int POPUP_HIDE = 99;
    public final static int EXIT_SHOW = 100;


//    public static DBManager dbManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);
/*
        dbManager = new DBManager(this);
        ArrayList<AutomaticDoor> autodoors = new ArrayList<AutomaticDoor>();
        AutomaticDoor autodoor1 = new AutomaticDoor("Revolving Door","A1", "平安");
        AutomaticDoor autodoor2 = new AutomaticDoor("Sliding Door", "B2", "招商");
        AutomaticDoor autodoor3 = new AutomaticDoor("Swing Door", "C3", "华润");
        autodoors.add(autodoor1);
        autodoors.add(autodoor2);
        autodoors.add(autodoor3);
        dbManager.add(autodoors);*/

        //title setting
        button_backward = (ImageButton)findViewById(R.id.button_backward);
        button_forward = (ImageButton)findViewById(R.id.button_forward);
        button_forward_drawer = (ImageButton)findViewById(R.id.button_forward_drawer);
        button_menu = (ImageButton)findViewById(R.id.button_menu);
        text_title = (TextView)findViewById(R.id.text_title);
        text_title_drawer = (TextView)findViewById(R.id.text_title_drawer);
        image_menu = (ImageView)findViewById(R.id.image_menu);
        view_exit_app = (TextView)findViewById(R.id.view_exit_app);
        view_exit_app.setVisibility(View.GONE);


        //layout
        layout_titlebar = (RelativeLayout)findViewById(R.id.layout_titlebar);
        activity_drawerlayout = (RelativeLayout)findViewById(R.id.activity_drawerlayout);
        activity_homelayout = (RelativeLayout)findViewById(R.id.activity_homelayout);
        activity_settinglayout = (RelativeLayout)findViewById(R.id.activity_settinglayout);
        activity_modesetlayout = (RelativeLayout)findViewById(R.id.activity_modesetlayout);
        activity_maintenancelayout = (RelativeLayout)findViewById(R.id.activity_maintenancelayout);
        activity_parameterlayout = (RelativeLayout)findViewById(R.id.activity_parameterlayout);
        activity_aboutlayout = (RelativeLayout)findViewById(R.id.activity_aboutlayout);
        activity_infolayout = (RelativeLayout)findViewById(R.id.activity_infolayout);
        activity_helplayout = (RelativeLayout)findViewById(R.id.activity_helplayout);

        activity_progresslayout = (RelativeLayout)findViewById(R.id.activity_progresslayout);

        //初始化各函数
        //初始化界面
        layoutnumber = 1;
        initLayout();

        //初始化蓝牙
        bluetoothComm.bluetoothInitialize(this,handler);

        //菜单键按下
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutnumber = 0;
                layoutUpdate(layoutnumber);
            }
        });
        button_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.ic_menu_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.menu));
                }
                return false;
            }
        });

        //在菜单界面下按下前进键
        button_forward_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutnumber = 1;
                layoutUpdate(layoutnumber);
            }
        });
        button_forward_drawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.ic_forward_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.forward));
                }
                return false;
            }
        });
        //按下设置键
        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutnumber = 2;
                layoutUpdate(layoutnumber);
            }
        });
        button_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.ic_setting_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.setting));
                }
                return false;
            }
        });
        //按下返回键
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //设置下的子菜单，返回设置
                layoutBackword();
            }
        });

        button_backward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.ic_backward_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(getDrawable(R.drawable.backward));
                }
                return false;
            }
        });

    }

    //initialize layout
    private void initLayout(){
        pageDrawerlayout.activityDrawerlayout(this,handler);
        pageHomelayout.activityHomelayout(this,handler);
        pageSettinglayout.activitySettinglayout(this,handler);
        pageModesetlayout.activityModesetlayoutt(this,handler);
        pageMaintenancelayout.activityMaintenancelayout(this,handler);
        pageParameterlayout.activityParameterlayout(this,handler);
        pageInfolayout.activityInfolayout(this,handler);
        pageHelplayout.activityHelplayout(this,handler);
        pageAboutlayout.activityAboutlayout(this,handler);
        pageProgressLayout.activityProgresslayout(this,handler);

        layoutUpdate(layoutnumber);
    }
    private void layoutBackword(){
        //设置下的子菜单，返回设置
        if((layoutnumber >= 3) && (layoutnumber <= 5)){
            layoutnumber = 2;
        }else if((layoutnumber >= 6) && (layoutnumber <= 8)){
            layoutnumber = 0;
        }else if((layoutnumber >= 10) && (layoutnumber <= 12)){
            layoutnumber = 8;
        }else if((layoutnumber >= 13) && (layoutnumber <= 14)){
            layoutnumber = 7;
        }else{
            layoutnumber = 1;
        }
        layoutUpdate(layoutnumber);
    }

    // update layout show
    private void layoutUpdate(int pagenumber){
        int[] array_visiblity = new int[]{View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,//
        View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,View.GONE,View.GONE};

        array_visiblity[pagenumber] = View.VISIBLE;

        switch (pagenumber){
            case 0:
                break;
            case 1:
                text_title.setText(R.string.title_activity_main);
                break;
            case 2:
                text_title.setText("设置");
                break;
            case 3:
                text_title.setText("运行模式设置");
                break;
            case 4:
                text_title.setText("运行参数设置");
                break;
            case 5:
                text_title.setText("系统维护");
                break;
            case 6:
                text_title.setText("自动门信息");
                break;
            case 7:
                text_title.setText("帮助");
                break;
            case 8:
                text_title.setText("关于");
                break;
            case 9:
                break;
            case 10:
                text_title.setText("软件版本");
                array_visiblity[8] = View.VISIBLE;
                break;
            case 11:
                text_title.setText("软件更新");
                array_visiblity[8] = View.VISIBLE;
                break;
            case 12:
                text_title.setText("关于门老爷");
                array_visiblity[8] = View.VISIBLE;
                break;
            case 13:
                text_title.setText("运行模式说明");
                array_visiblity[7] = View.VISIBLE;
                break;
            case 14:
                text_title.setText("常见故障");
                array_visiblity[7] = View.VISIBLE;
                break;
            default:
                break;
        }

        activity_drawerlayout.setVisibility(array_visiblity[0]);
        activity_homelayout.setVisibility(array_visiblity[1]);
        activity_settinglayout.setVisibility(array_visiblity[2]);
        activity_modesetlayout.setVisibility(array_visiblity[3]);
        activity_parameterlayout.setVisibility(array_visiblity[4]);
        activity_maintenancelayout.setVisibility(array_visiblity[5]);
        activity_infolayout.setVisibility(array_visiblity[6]);
        activity_helplayout.setVisibility(array_visiblity[7]);
        activity_aboutlayout.setVisibility(array_visiblity[8]);
        activity_progresslayout.setVisibility(array_visiblity[9]);

        updateTitlebar(pagenumber);
        pageDrawerlayout.UpdateDrawerlayout();
        pageHomelayout.UpdateHomelayout();
        pageAboutlayout.UpdataAboutlayout(pagenumber);
        pageSettinglayout.UpdateSettinglayout();
        pageHelplayout.UpdataHelplayout(pagenumber);
        pageParameterlayout.UpdateParameterlayout(pagenumber);

        pageModesetlayout.updateGride(this,MainActivity.doorStatus.modecurrent);
        if(pagenumber == 6){
            pageInfolayout.UpdataInfolayout(false);
        }else {
            pageInfolayout.UpdataInfolayout(true);
        }

    }

    //update title bar
    private void updateTitlebar(int pagenumber){
        if(pagenumber == 0){
            button_forward.setVisibility(View.GONE);
            button_menu.setVisibility(View.GONE);
            button_backward.setVisibility(View.GONE);
            text_title_drawer.setVisibility(View.VISIBLE);
            text_title.setVisibility(View.GONE);
            image_menu.setVisibility(View.VISIBLE);
            button_forward_drawer.setVisibility(View.VISIBLE);
        }else if(pagenumber == 1){
            button_forward.setVisibility(View.VISIBLE);
            button_menu.setVisibility(View.VISIBLE);
            button_backward.setVisibility(View.GONE);
            text_title.setVisibility(View.VISIBLE);
            text_title_drawer.setVisibility(View.GONE);
            image_menu.setVisibility(View.GONE);
            button_forward_drawer.setVisibility(View.GONE);
        }else{
            button_forward.setVisibility(View.GONE);
            button_menu.setVisibility(View.GONE);
            button_backward.setVisibility(View.VISIBLE);
            text_title.setVisibility(View.VISIBLE);
            text_title_drawer.setVisibility(View.GONE);
            image_menu.setVisibility(View.GONE);
            button_forward_drawer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //应用的最后一个Activity关闭时应释放DB
        //dbManager.closeDB();
        //应用的最后一个Activity关闭时应释放bluetooth
        bluetoothComm.bluetooth.removeCommunicationCallback();
        bluetoothComm.bluetooth.disconnect();
    }


    //返回键监听
    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(layoutnumber == 1){
                exitBy2Click();        //调用双击退出函数
            }else{
                //设置下的子菜单，返回设置
                layoutBackword();
            }

        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;
    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            view_exit_app.setText("再按一次退出程序");
            view_exit_app.setVisibility(View.VISIBLE);
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                    Message msg = new Message();
                    msg.what = EXIT_SHOW;
                    handler.sendMessage(msg);

                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            bluetoothComm.bluetooth.removeCommunicationCallback();
            bluetoothComm.bluetooth.disconnect();
            finish();
            System.exit(0);
        }
    }
    //弹出窗口显示
    public  void popupView(String textstring,int time) {
        view_exit_app.setText(textstring);
        view_exit_app.setVisibility(View.VISIBLE);
        Timer tExit = new Timer();
        tExit.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = POPUP_HIDE;
                handler.sendMessage(msg);

            }
        }, time); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    }

    @Override
    //收到其它窗口结束时的返回值
    protected  void onActivityResult(int requestCode, int resultCode,Intent data){
        if(requestCode == GET_DEVICE){
            //蓝牙连接
            if(resultCode == RESULT_OK){
                //蓝牙连接
                popupView("正在连接自动门......",1000);
                bluetoothComm.bluetoothconnect(data,handler,MainActivity.this);
            }
        }
    }

    //设置自定义listview显示 高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int itemp = 0; itemp < listAdapter.getCount(); itemp++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(itemp, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();
            switch (msg.what){
                case LAYOUTSHOW://界面切换
                    if(layoutnumber == 9){
                        layoutnumber = layoutnumber_old;
                        layout_titlebar.setVisibility(View.VISIBLE);
                    }else {
                        layoutnumber_old = layoutnumber;
                        layoutnumber = bd.getInt("layoutnumber");
                        if (layoutnumber == 9) {
                            layout_titlebar.setVisibility(View.GONE);
                            pageProgressLayout.Progress_show(handler,bd.getString("tesxshow"),bd.getInt("time"));
                        }
                    }
                    layoutUpdate(layoutnumber);
                   break;
                case BT_CONNECTED://bluetooth 连接成功
                    if((bluetoothComm.bluetooth.isConnected()) && (!doorStatus.door_connect)){
                        doorStatus.door_connect = true;
                    }
                    popupView("自动门已连接，正在加载自动门数据。",2000);
                    layoutUpdate(layoutnumber);

                    //查询自动门状态
                    pageHomelayout.CheckStatusStart(handler);
                    break;
                case BT_CONNECTFLASE://bluetooth 连接失败
                    popupView("自动门连接失败，请重新连接。",2000);
                    doorStatus.door_connect = false;
                    layoutUpdate(layoutnumber);
                    break;
                case BT_RECEIVE://bluetooth 收到信息

                    //popupView(msg.getData().getString("msg").substring(1,3),1000);

                    if(msg.getData().getString("msg").length() >= 2) {
                        bluetoothComm.ReceiveMessage(msg.getData().getString("msg").toCharArray());
                        layoutUpdate(layoutnumber);
                    }
                    break;
                case BT_ERROR://bluetooth 错误
                    popupView("自动门连接错误",2000);
                    break;
                case BT_ERROR2://bluetooth 连接错误
                    popupView("自动门连接错误",2000);
                    break;
                case BT_DISCONNECT://bluetooth断开成功

                    popupView("自动门已断开",2000);
                    doorStatus.door_connect = false;
                    if(bluetoothComm.sendwaittime_flag){
                        bluetoothComm.sendwaittime_flag = false;
                        bluetoothComm.sendwaittime.cancel();
                        bluetoothComm.resend_count = 0;
                    }
                    pageHomelayout.CleanCheckstatus();
                    pageInfolayout.CleanInfolayout();
                    layoutUpdate(layoutnumber);

                    break;
                case SEND_WAITE://状态接收处理
                    bluetoothComm.resend_count++;
                    if(bluetoothComm.resend_count > 5){
                        bluetoothComm.commandPLC.CleanPLCflag();
                        doorStatus.plc_communication_flag = DoorStatus.PLC_COMMUNICATION_FREE;
                        doorStatus.lafaya_communication_flag = false;
                        bluetoothComm.resend_count = 0;
                        bluetoothComm.sendwaittime.cancel();
                        bluetoothComm.sendwaittime_flag = false;
                        if(layoutnumber == 9) {
                            pageProgressLayout.Progress_hide(handler);
                        }
                        if(pageHomelayout.home_check_flag){
                            pageHomelayout.CleanCheckstatus();
                            popupView("自动门状态加载失败，请重新加载！",2000);
                        }else {
                            pageInfolayout.infoCommunicationfailure();
                            pageParameterlayout.parameterCommunicationfailure();
                            popupView("通讯失败！",2000);
                        }

                    }else{
                        bluetoothComm.reSendMessage();
                    }
                    layoutUpdate(layoutnumber);
                    break;
                case HOME_UPDATE:

                    break;
                case COMMUNICATING:
                    popupView("通讯正忙，请稍后再试！",2000);
                    break;
                case POPUP_HIDE://弹出窗口显示
                    view_exit_app.setVisibility(View.GONE);
                    break;
                case POSITION_READ:
                    pageParameterlayout.parameterPLC.ReadPositionvalue();
                    break;
                case INFO_CHECK:
                    pageInfolayout.infoCheck();
                    break;
                case EXIT_SHOW:
                    view_exit_app.setVisibility(View.GONE);
                    break;
                case PLC_RESET:
                    doorStatus.doorReset(MainActivity.this,bd.getInt("reset_flag"),"01");
                    break;
                default:
                    break;
            }
        }
    };





}
