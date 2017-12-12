package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by JeffYoung on 2016/11/3.
 **/
public class PageAboutlayout {
    private LinearLayout activity_about_version,activity_about_update,activity_about_lafaya;
    private ImageButton button_about_lafaya,button_about_update,button_about_version;

    public void activityAboutlayout(final Activity activity, final Handler handler){
        button_about_version = (ImageButton)activity.findViewById(R.id.button_about_version);
        button_about_update = (ImageButton)activity.findViewById(R.id.button_about_update);
        button_about_lafaya = (ImageButton)activity.findViewById(R.id.button_about_lafaya);
        activity_about_version = (LinearLayout)activity.findViewById(R.id.activity_about_version);
        activity_about_update = (LinearLayout)activity.findViewById(R.id.activity_about_update);
        activity_about_lafaya = (LinearLayout)activity.findViewById(R.id.activity_about_lafaya);

        activity_about_version.setVisibility(View.GONE);
        activity_about_update.setVisibility(View.GONE);
        activity_about_lafaya.setVisibility(View.GONE);

        button_about_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",10);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        button_about_version.setOnTouchListener(new View.OnTouchListener() {
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

        button_about_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",11);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }
        });
        button_about_update.setOnTouchListener(new View.OnTouchListener() {
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

        button_about_lafaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                Bundle bundle = new Bundle();
                bundle.putInt("layoutnumber",12);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }
        });
        button_about_lafaya.setOnTouchListener(new View.OnTouchListener() {
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

    public void UpdataAboutlayout(int pagenumber){
        if(pagenumber == 8){
            activity_about_version.setVisibility(View.GONE);
            activity_about_update.setVisibility(View.GONE);
            activity_about_lafaya.setVisibility(View.GONE);
        }else if(pagenumber == 10){
            activity_about_version.setVisibility(View.VISIBLE);
            activity_about_update.setVisibility(View.GONE);
            activity_about_lafaya.setVisibility(View.GONE);
        }else if(pagenumber == 11){
            activity_about_version.setVisibility(View.GONE);
            activity_about_update.setVisibility(View.VISIBLE);
            activity_about_lafaya.setVisibility(View.GONE);
        }else if(pagenumber == 12){
            activity_about_version.setVisibility(View.GONE);
            activity_about_update.setVisibility(View.GONE);
            activity_about_lafaya.setVisibility(View.VISIBLE);
        }
    }
}

