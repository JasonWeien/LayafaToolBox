package com.lafaya.toolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JeffYoung on 2016/10/27.
 */
public class SplashActivity extends Activity{
    //ImageView splash_image;
    private RelativeLayout splash_image1,splash_image2;
    private Button splash_jump;
    private boolean skip_flag = false;
    private Timer updatetimer = new Timer();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
//        splash_image = (ImageView)findViewById(R.id.splash_image);
        splash_jump = (Button)findViewById(R.id.splash_jump);
        splash_image1 = (RelativeLayout)findViewById(R.id.splash_image1);
        splash_image2 = (RelativeLayout)findViewById(R.id.splash_image2);
        splash_image1.setVisibility(View.VISIBLE);
        splash_image2.setVisibility(View.GONE);

//        splash_image.setImageResource(R.drawable.start2);


/*
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash_image.setImageResource(R.drawable.start3);
                skip_flag = true;
            }
        },1200);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },2400);*/

        splash_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!skip_flag){
                    splash_image1.setVisibility(View.GONE);
                    splash_image2.setVisibility(View.VISIBLE);
//                    splash_image.setImageResource(R.drawable.start3);
                    skip_flag = true;

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }else{
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    updatetimer.cancel();
                }
            }
        });

        updatetimer.schedule(task, 1200,1200);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();
            switch (msg.what) {
                case 0:
                    if(!skip_flag){
                        splash_image1.setVisibility(View.GONE);
                        splash_image2.setVisibility(View.VISIBLE);
                        //splash_image.setImageResource(R.drawable.start3);
                        skip_flag = true;
                    }else{
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                        updatetimer.cancel();
                    }
                    break;
                case 1:

                    break;
                default:
                    break;
            }
        }
    };
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 0;
            handler.sendMessage(msg);
        }
    };
}
