package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by JeffYoung on 2016/11/3.
 */
public class PageHelplayout {
    private LinearLayout activity_help_mode,activity_help_fault;
    private ImageButton button_help_mode,button_help_error;

    public void activityHelplayout(final Activity activity, final Handler handler){

        button_help_mode = (ImageButton)activity.findViewById(R.id.button_help_mode);
        button_help_error = (ImageButton)activity.findViewById(R.id.button_help_error);
        activity_help_mode = (LinearLayout)activity.findViewById(R.id.activity_help_mode);
        activity_help_fault = (LinearLayout)activity.findViewById(R.id.activity_help_fault);
        activity_help_mode.setVisibility(View.GONE);
        activity_help_fault.setVisibility(View.GONE);

        button_help_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",13);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        button_help_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",14);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });

        button_help_mode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });
        button_help_error.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.ic_forward_red));
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.forward_gray));
                }
                return false;
            }
        });
    }

    public void UpdataHelplayout(int pagenumber){
        if(pagenumber == 7){
            activity_help_mode.setVisibility(View.GONE);
            activity_help_fault.setVisibility(View.GONE);
        }else if(pagenumber == 13){
            activity_help_mode.setVisibility(View.VISIBLE);
            activity_help_fault.setVisibility(View.GONE);
        }else if(pagenumber == 14){
            activity_help_mode.setVisibility(View.GONE);
            activity_help_fault.setVisibility(View.VISIBLE);
        }
    }
}
