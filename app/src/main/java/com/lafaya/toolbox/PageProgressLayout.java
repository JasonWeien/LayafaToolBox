package com.lafaya.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.CircularPropagation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ioani on 2016/11/8.
 **/

public class PageProgressLayout {
    private CircularProgressBar progress_bar;
    private TextView text_progress_rate;
    private TextView text_progress_statue;
    private int progess_count = 0;
    private Button button_progress_cancle;
    Timer timer;


    public void activityProgresslayout(final Activity activity, final Handler handler){
        progress_bar = (CircularProgressBar)activity.findViewById(R.id.progress_bar);
        text_progress_rate = (TextView)activity.findViewById(R.id.text_progress_rate);
        text_progress_statue = (TextView)activity.findViewById(R.id.text_progress_statue);
        button_progress_cancle = (Button)activity.findViewById(R.id.button_progress_cancle);

        progress_bar.setProgress(progess_count);
        text_progress_rate.setText(Integer.toString(progess_count) + "%");
//        text_progress_statue.setText("设置保存中... ...");

        button_progress_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                handler.sendMessage(msg);
                timer.cancel();
                progess_count = 0;
            }
        });
    }
    public void Progress_hide(Handler handler){
        Message msg = new Message();
        msg.what = MainActivity.LAYOUTSHOW;
        handler.sendMessage(msg);
        timer.cancel();
        progess_count = 0;
    }

    public void Progress_show(final Handler handler,final String textshow,int time){

        final Handler prohandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        progess_count += 1;
                        if((MainActivity.doorStatus.plc_communication_flag == DoorStatus.PLC_COMMUNICATION_FREE) && (!MainActivity.doorStatus.lafaya_communication_flag)){
                            if(progess_count >= 100){
                                progress_bar.setProgress((float) progess_count);
                                text_progress_rate.setText(Integer.toString(progess_count) + "%");
                                text_progress_statue.setText(textshow+"完成！");
                                progess_count = 100;
                                timer.cancel();
                                Handler dl = new Handler();
                                dl.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progess_count = 0;
                                        Message msg;
                                        msg = new Message();
                                        msg.what = MainActivity.LAYOUTSHOW;
                                        handler.sendMessage(msg);
                                    }
                                },1000);
                            }else{
                                progress_bar.setProgress((float) progess_count);
                                text_progress_rate.setText(Integer.toString(progess_count) + "%");
                                text_progress_statue.setText(textshow+"中... ...");
                            }
                        }else{
                            if(progess_count >= 50){
                                progess_count = 50;
                            }
                            progress_bar.setProgress((float) progess_count);
                            text_progress_rate.setText(Integer.toString(progess_count) + "%");
                            text_progress_statue.setText(textshow+"中... ...");
                        }

                        break;
                    default:
                        break;
                }
            }
        };

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MainActivity.LAYOUTSHOW;
                prohandler.sendMessage(msg);
            }
        };
        timer = new Timer();
        timer.schedule(task,10,time);

    }
}
