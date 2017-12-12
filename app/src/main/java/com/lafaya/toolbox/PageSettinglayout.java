package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by JeffYoung on 2016/11/1.
 */
public class PageSettinglayout {
    private Button button_setting_switch;
    private ImageView image_setting;
    private TextView text_setting_name;
    private TextView text_setting_info;
    private ImageView image_setting_label;
    private Button button_setting_backforward;
    private ImageButton button_setting_forward;
    private Button button_setting_restart;
    private LinearLayout layout_setting_restart;
    private Button button_setting_enter,button_setting_cancle;
    private int view_number = 0;//当前显示的设置界面，0模式设置，1参数设置，2系统维护

    public void activitySettinglayout(final Activity activity, final Handler handler){

        button_setting_switch = (Button)activity.findViewById(R.id.button_setting_switch);
        image_setting = (ImageView)activity.findViewById(R.id.image_setting);
        image_setting_label = (ImageView)activity.findViewById(R.id.image_setting_label);
        text_setting_name = (TextView)activity.findViewById(R.id.text_setting_name);
        text_setting_info = (TextView)activity.findViewById(R.id.text_setting_info);
        button_setting_backforward = (Button)activity.findViewById(R.id.button_setting_backforward);
        button_setting_restart = (Button)activity.findViewById(R.id.button_setting_restart);
        button_setting_forward = (ImageButton)activity.findViewById(R.id.button_setting_forward);
        layout_setting_restart = (LinearLayout)activity.findViewById(R.id.layout_setting_restart);
        layout_setting_restart.setVisibility(View.GONE);
        button_setting_enter = (Button)activity.findViewById(R.id.button_setting_enter);
        button_setting_cancle = (Button)activity.findViewById(R.id.button_setting_cancle);


        //切换设置状态
        image_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                switch (view_number)
                {
                    case 0:
                        bundle.putInt("layoutnumber",3);//进入模式设置
                        break;
                    case 1:
                        bundle.putInt("layoutnumber",4);//进入参数设置
                        break;
                    case 2:
                        bundle.putInt("layoutnumber",5);//进入系统维护
                        break;
                    default:
                        break;
                }
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        // when press, change iamge.
        image_setting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    switch (view_number){
                        case 0:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_modeset_on));
                            break;
                        case 1:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_paraset_on));
                            break;
                        case 2:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_maintenanceset_on));
                            break;
                        default:
                            break;
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    switch (view_number){
                        case 0:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_modeset));
                            break;
                        case 1:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_paraset));
                            break;
                        case 2:
                            ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_maintenanceset));
                            break;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        button_setting_forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_enter_forward_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.enter_forward));
                }
                return false;
            }
        });

        //返回首页
        button_setting_backforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",1);
                msg.setData(bundle);
                handler.sendMessage(msg);
                layout_setting_restart.setVisibility(View.GONE);
            }
        });
        //选择按键
        button_setting_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_setting_restart.setVisibility(View.GONE);
                view_number += 1;
                if(view_number > 2)
                {
                    view_number = 0;
                }
                view_switch();

            }
        });
        button_setting_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_setting_restart.setVisibility(View.GONE);
                view_number += 1;
                if(view_number > 2)
                {
                    view_number = 0;
                }
                view_switch();

            }
        });
        //按下重置按键
        button_setting_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_setting_restart.setVisibility(View.VISIBLE);
            }
        });
        button_setting_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.pageMaintenancelayout.resetStart(2);
                layout_setting_restart.setVisibility(View.GONE);
            }
        });
        button_setting_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_setting_restart.setVisibility(View.GONE);
            }
        });

    }
    private void view_switch(){
        switch (view_number){
            case 0://运行模式设置
                image_setting.setImageResource(R.drawable.icon_modeset);
                image_setting_label.setImageResource(R.drawable.label_modeset);
                text_setting_info.setText(R.string.setting_info_modeset);
                text_setting_name.setText("运行模式设置");
                break;
            case 1://运行参数设置
                image_setting.setImageResource(R.drawable.icon_paraset);
                image_setting_label.setImageResource(R.drawable.label_paraset);
                text_setting_info.setText(R.string.setting_info_paraset);
                text_setting_name.setText("自动门参数设置");
                break;
            case 2://系统维护
                image_setting.setImageResource(R.drawable.icon_maintenanceset);
                image_setting_label.setImageResource(R.drawable.label_maintenanceset);
                text_setting_info.setText(R.string.setting_info_maintenanceset);
                text_setting_name.setText("系统维护");
                break;
            default:
                break;
        }
    }
    public void UpdateSettinglayout(){
        view_number = 0;
        view_switch();
        
    }

}
