package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JeffYoung on 2016/11/1.
 **/
public class PageModesetlayout {
    private GridView grid_modeset;
    private Button button_modeset_cancle,button_modeset_enter;
    private TextView text_modeset_name,text_modeset_info;
    private LinearLayout layout_open_scale;

    private ImageButton button_opensacle_minus,button_opensacle_plus;
    private TextView text_open_scal;
    private int openscal = 100;

    private int button_scale_flag = 0;



    public void activityModesetlayoutt(final Activity activity, final Handler handler){
        button_modeset_cancle = (Button)activity.findViewById(R.id.button_modeset_cancle);
        button_modeset_enter = (Button)activity.findViewById(R.id.button_modeset_enter);
        button_modeset_enter.setVisibility(View.GONE);
        button_modeset_cancle.setVisibility(View.GONE);
        grid_modeset = (GridView)activity.findViewById(R.id.grid_modeset);


        text_modeset_name = (TextView)activity.findViewById(R.id.text_modeset_name);
        text_modeset_info = (TextView)activity.findViewById(R.id.text_modeset_info);

        // open scale
        layout_open_scale = (LinearLayout)activity.findViewById(R.id.layout_open_scale);
        button_opensacle_minus = (ImageButton)activity.findViewById(R.id.button_opensacle_minus);
        button_opensacle_plus = (ImageButton)activity.findViewById(R.id.button_opensacle_plus);
        text_open_scal = (TextView)activity.findViewById(R.id.text_open_scal);

        // revolving door
        if(MainActivity.doorStatus.revolvingdoortype){
            grid_modeset.setNumColumns(3);
            layout_open_scale.setVisibility(View.GONE);
        }else {        // other automatic door
            grid_modeset.setNumColumns(2);
            grid_modeset.setColumnWidth(150);
            layout_open_scale.setVisibility(View.VISIBLE);
            text_open_scal.setText(Integer.toString(openscal) + "  %");
        }

        updateGride(activity,MainActivity.doorStatus.modeselected);

        grid_modeset.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.doorStatus.modeselected = i;
                updateGride(activity,MainActivity.doorStatus.modeselected);
                activity.invalidateOptionsMenu();
                if(MainActivity.doorStatus.modeselected != MainActivity.doorStatus.modecurrent)
                {
                    button_scale_flag = 1;
                    button_control(button_scale_flag);
                    button_modeset_enter.setVisibility(View.VISIBLE);
                    button_modeset_cancle.setVisibility(View.VISIBLE);
                }
            }
        });
        button_opensacle_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openscal > 30){
                    button_scale_flag = 2;
                    button_control(button_scale_flag);
                    button_modeset_enter.setVisibility(View.VISIBLE);
                    button_modeset_cancle.setVisibility(View.VISIBLE);
                    openscal -= 5;
                    text_open_scal.setText(Integer.toString(openscal) + "  %");
                }
            }
        });
        button_opensacle_minus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_minus_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_minus));
                }
                return false;
            }
        });

        button_opensacle_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openscal < 100){
                    button_scale_flag = 2;
                    button_control(button_scale_flag);
                    button_modeset_enter.setVisibility(View.VISIBLE);
                    button_modeset_cancle.setVisibility(View.VISIBLE);
                    openscal += 5;
                    text_open_scal.setText(Integer.toString(openscal) + "  %");
                }
            }
        });
        button_opensacle_plus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_plus_on));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_plus));
                }
                return false;
            }
        });


        button_modeset_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGride(activity,MainActivity.doorStatus.modecurrent);
                button_scale_flag = 0;
                button_control(button_scale_flag);
                button_modeset_enter.setVisibility(View.GONE);
                button_modeset_cancle.setVisibility(View.GONE);
            }
        });

        // send set the door running mode
        button_modeset_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(button_scale_flag == 1){
                    MainActivity.doorStatus.runmodeSet_Qeury(activity,true,MainActivity.doorStatus.modeselected);
                }else{
                    if(!MainActivity.doorStatus.lafaya_communication_flag){
                        MainActivity.doorStatus.lafaya_communication_flag = true;
                        MainActivity.doorStatus.lafayaOpenrate_setquery(activity, true, 0, openscal);
                    }else{
                        MainActivity.bluetoothComm.communicating();
                    }
                }

                button_scale_flag = 0;
                button_control(button_scale_flag);
                button_modeset_enter.setVisibility(View.GONE);
                button_modeset_cancle.setVisibility(View.GONE);
                if(MainActivity.doorStatus.door_connect) {
                    Message msg = new Message();
                    msg.what = MainActivity.LAYOUTSHOW;
                    Bundle bundle = new Bundle();
                    bundle.putInt("layoutnumber", 9);
                    bundle.putInt("time", 20);
                    bundle.putString("tesxshow", "设置保存");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }else {
                    updateGride(activity,MainActivity.doorStatus.modecurrent);
                }
            }
        });
    }

    private void button_control(int flag){
        if(flag == 0){
            grid_modeset.setEnabled(true);
            button_opensacle_minus.setEnabled(true);
            button_opensacle_plus.setEnabled(true);
        }else if(flag == 1){
            grid_modeset.setEnabled(true);
            button_opensacle_minus.setEnabled(false);
            button_opensacle_plus.setEnabled(false);

        }else if(flag == 2){
            grid_modeset.setEnabled(false);
            button_opensacle_minus.setEnabled(true);
            button_opensacle_plus.setEnabled(true);

        }
    }

    public void updateGride(Activity activity,int selected){
        List<HashMap<String, Object>> list = new ArrayList<>();
        if(MainActivity.doorStatus.revolvingdoortype) {
            int[] drawablelist = {R.drawable.mode_normal_off, R.drawable.mode_summer_off, R.drawable.mode_winter_off,
                    R.drawable.mode_auto_off, R.drawable.mode_exit_off, R.drawable.mode_open_off, R.drawable.mode_manual_off, R.drawable.mode_check_off};
            int[] drawablelist_on = {R.drawable.mode_normal_on, R.drawable.mode_summer_on, R.drawable.mode_winter_on,
                    R.drawable.mode_auto_on, R.drawable.mode_exit_on, R.drawable.mode_open_on, R.drawable.mode_manual_on, R.drawable.mode_check_on};

            drawablelist[selected] = drawablelist_on[selected];
            for (int itmp = 0; itmp < 8; itmp++) {
                list.add(getGridViewData(drawablelist[itmp]));
            }
        }else {
            int[] drawablelist = {R.drawable.mode_auto_off, R.drawable.mode_open_off, R.drawable.mode_close_off,R.drawable.mode_exit_off};
            int[] drawablelist_on = {R.drawable.mode_auto_on, R.drawable.mode_open_on, R.drawable.mode_close_on,R.drawable.mode_exit_on};

            drawablelist[selected] = drawablelist_on[selected];
            for (int itmp = 0; itmp < 4; itmp++) {
                list.add(getGridViewData(drawablelist[itmp]));
            }
        }

        SimpleAdapter saImageItems = new SimpleAdapter(activity,
                list,
                R.layout.modeset_gridlist,
                new String[] { "modeset_grid_image"},
                new int[] { R.id.modeset_grid_image});
        // 设置GridView的adapter。GridView继承于AbsListView。
        grid_modeset.setAdapter(saImageItems);
        UpdateModesetinfo(selected);
    }


    /**
     * 获取GridView的数据
     */
    private HashMap<String, Object> getGridViewData(int modeimage) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("modeset_grid_image", modeimage);
        return (map);
    }

    private void UpdateModesetinfo(int selected)
    {
        if(MainActivity.doorStatus.revolvingdoortype) {
            switch (selected) {
                case 0:
                    text_modeset_name.setText("常转模式");
                    text_modeset_info.setText(R.string.mode_normal_info);
                    break;
                case 1:
                    text_modeset_name.setText("夏季模式");
                    text_modeset_info.setText(R.string.mode_summer_info);
                    break;
                case 2:
                    text_modeset_name.setText("冬季模式");
                    text_modeset_info.setText(R.string.mode_winter_info);
                    break;
                case 3:
                    text_modeset_name.setText("自动模式");
                    text_modeset_info.setText(R.string.mode_auto_info);
                    break;
                case 4:
                    text_modeset_name.setText("单向模式");
                    text_modeset_info.setText(R.string.mode_exit_info);
                    break;
                case 5:
                    text_modeset_name.setText("常开模式");
                    text_modeset_info.setText(R.string.mode_open_info);
                    break;
                case 6:
                    text_modeset_name.setText("手动模式");
                    text_modeset_info.setText(R.string.mode_manual_info);
                    break;
                case 7:
                    text_modeset_name.setText("自测模式");
                    text_modeset_info.setText(R.string.mode_check_info);
                    break;
                default:
                    break;
            }
        }else{
            switch (selected) {
                case 0:
                    text_modeset_name.setText("自动模式");
                    text_modeset_info.setText(R.string.mode_auto_info);
                    break;
                case 1:
                    text_modeset_name.setText("常开模式");
                    text_modeset_info.setText(R.string.mode_open_info);
                    break;
                case 2:
                    text_modeset_name.setText("常闭模式");
                    text_modeset_info.setText(R.string.mode_close_info);
                    break;
                case 3:
                    text_modeset_name.setText("单向模式");
                    text_modeset_info.setText(R.string.mode_exit_info);
                    break;
                default:
                    break;
            }

            openscal = MainActivity.doorStatus.openrate_sliding;
            text_open_scal.setText(Integer.toString(openscal) + "  %");
        }
    }
}
