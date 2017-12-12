package com.lafaya.toolbox;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JeffYoung on 2016/11/2.
 **/
public class PageParameterlayout {

    private Activity activity;
    private Handler handler;
    //layout
    private ImageButton button_parameterrevolving_flod,button_parametercenterdoor_flod,button_parameterleftwing_flod,
            button_parameterrightwing_flod,button_parameterfvd_flod;
    private LinearLayout relayout_parameter_sliding;
    private LinearLayout activity_parameter_revolvingdoor,activity_parameter_slidingdoor,activity_parameter_wingleft,activity_parameter_wingright,activity_parameter_fvd;
    //button_parameterposition_flod;
    private LinearLayout layout_paramter_revolving,layout_paramter_fvd;
    private LinearLayout layout_parameter_dialog,layout_parameter_all,layout_paramter_sliding,layout_paramter_wingleft,layout_paramter_wingright;

    final static int LAYOUT_RELEASE = 0, REVOLVINGDOOR_SHOW = 1, SLIDINGDOOR_SHOW = 2, WINGLEFT_SHOW = 3, WINGRIGHT_SHOW = 4, VFD_SHOW = 5;
    private int layout_layer = LAYOUT_RELEASE;

    //parameter dialog
    private RelativeLayout relativelayout_parameter_set;
    private TextView text_parameter_settitle,text_parameter_setold;
    private EditText edittext_parameter_setnew;
    private SeekBar seekbar_parameter_set;
  //  private String dialog_titlename = "--";
    private int dialog_maxvalue = 1;
    private int dialog_minvalue = 0;
    //private int dialog_nowvalue = 0;
    private float dialog_step = 1.0f;

    //参数保存或取消
    private Button button_parameter_save,button_parameter_cancle;

    private boolean modify_flag = false;

    ParameterPLC parameterPLC = new ParameterPLC();
    ParameterVFD parameterVFD = new ParameterVFD();
    ParameterLafaya parameterLafaya = new ParameterLafaya();


    public void activityParameterlayout(Activity inactivity, Handler inhandler){
        activity = inactivity;
        handler = inhandler;

        // initialisation
        //activity
        activity_parameter_revolvingdoor = (LinearLayout)activity.findViewById(R.id.activity_parameter_revolvingdoor);
        activity_parameter_wingleft = (LinearLayout)activity.findViewById(R.id.activity_parameter_wingleft);
        activity_parameter_wingright = (LinearLayout)activity.findViewById(R.id.activity_parameter_wingright);
        activity_parameter_fvd = (LinearLayout)activity.findViewById(R.id.activity_parameter_fvd);

        //parameter layout : revolving door, leftwing, right wing, vfd, center door, plc position
        layout_paramter_revolving  = (LinearLayout)activity.findViewById(R.id.layout_paramter_revolving);
        layout_paramter_sliding = (LinearLayout)activity.findViewById(R.id.layout_paramter_sliding);
        layout_paramter_wingleft = (LinearLayout)activity.findViewById(R.id.layout_paramter_wingleft);
        layout_paramter_wingright = (LinearLayout)activity.findViewById(R.id.layout_paramter_wingright);
        layout_paramter_fvd = (LinearLayout)activity.findViewById(R.id.layout_paramter_fvd);

        relayout_parameter_sliding = (LinearLayout)activity.findViewById(R.id.relayout_parameter_sliding);

        button_parameterrevolving_flod = (ImageButton)activity.findViewById(R.id.button_parameterrevolving_flod);
        button_parametercenterdoor_flod = (ImageButton)activity.findViewById(R.id.button_parametercenterdoor_flod);
        button_parameterleftwing_flod = (ImageButton)activity.findViewById(R.id.button_parameterleftwing_flod);
        button_parameterrightwing_flod = (ImageButton)activity.findViewById(R.id.button_parameterrightwing_flod);;
        button_parameterfvd_flod  = (ImageButton)activity.findViewById(R.id.button_parameterfvd_flod);

        //save & cancle button
        relativelayout_parameter_set = (RelativeLayout)activity.findViewById(R.id.relativelayout_parameter_set);
        button_parameter_save = (Button)activity.findViewById(R.id.button_parameter_save);
        button_parameter_cancle = (Button)activity.findViewById(R.id.button_parameter_cancle);

        //parameter set input
        layout_parameter_all = (LinearLayout)activity.findViewById(R.id.layout_parameter_all);

        //parameter set dialog
        layout_parameter_dialog = (LinearLayout)activity.findViewById(R.id.layout_parameter_dialog);
        layout_parameter_dialog.setVisibility(View.GONE);
        text_parameter_settitle = (TextView)activity.findViewById(R.id.text_parameter_settitle);
        edittext_parameter_setnew = (EditText)activity.findViewById(R.id.edittext_parameter_setnew);
        text_parameter_setold = (TextView)activity.findViewById(R.id.text_parameter_setold);
        seekbar_parameter_set = (SeekBar)activity.findViewById(R.id.seekbar_parameter_set);


        //initialize revolving door parameters
        parameterPLC.initializeParameterPLC(activity,handler);
        //initialize VFD parameters
        parameterVFD.initializeParameterVFD(activity,handler);
        //initialize Lafaya parameters
        parameterLafaya.initializeParameterLafaya(activity);


        if(MainActivity.doorStatus.revolvingdoortype) {
            //Listener
            //button listener
            button_listener();
            updateLayout();
        }else {
            layout_paramter_sliding.setVisibility(View.VISIBLE);
            relayout_parameter_sliding.setVisibility(View.GONE);
            activity_parameter_revolvingdoor.setVisibility(View.GONE);
            activity_parameter_fvd.setVisibility(View.GONE);
            activity_parameter_wingleft.setVisibility(View.GONE);
            activity_parameter_wingright.setVisibility(View.GONE);
            relativelayout_parameter_set.setVisibility(View.GONE);
        }

        // parameter changed listener
        parameter_changed_listener();
        //parameter list listener

        //cancle parameter setting
        button_parameter_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paramter_cancle();
            }
        });
        // save parameter
        button_parameter_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameter_saving();
            }
        });
    }

    //button listener
    private void button_listener(){
        //button Lister
        //revolving door parameters show or hide
        button_parameterrevolving_flod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_layer == REVOLVINGDOOR_SHOW){
                    layout_layer = LAYOUT_RELEASE;
                }else{
                    layout_layer = REVOLVINGDOOR_SHOW;
                    parameterPLC.RevolvingdoorQuery();
                }
                updateLayout();
            }
        });
        button_parameterrevolving_flod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layout_layer == REVOLVINGDOOR_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(layout_layer == REVOLVINGDOOR_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }
                }
                return false;
            }
        });

        //sliding door parameters show or hide
        button_parametercenterdoor_flod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_layer == SLIDINGDOOR_SHOW){
                    layout_layer = LAYOUT_RELEASE;
                }else{
                    layout_layer = SLIDINGDOOR_SHOW;
                    parameterLafaya.SlidingdoorQuery();
                    //parameterLafaya.SlidingParametersShow();
                }
                updateLayout();
            }
        });

        button_parametercenterdoor_flod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layout_layer == SLIDINGDOOR_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(layout_layer == SLIDINGDOOR_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }
                }
                return false;
            }
        });
        //left wing parameters of revolving door  show or hide
        button_parameterleftwing_flod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_layer == WINGLEFT_SHOW){
                    layout_layer = LAYOUT_RELEASE;
                }else{
                    layout_layer = WINGLEFT_SHOW;
                    parameterLafaya.WingleftParametersShow();
                }
                updateLayout();
            }
        });

        button_parameterleftwing_flod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layout_layer == WINGLEFT_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(layout_layer == WINGLEFT_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }
                }
                return false;
            }
        });
        //right wing parameters of revolving door show or hide
        button_parameterrightwing_flod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_layer == WINGRIGHT_SHOW){
                    layout_layer = LAYOUT_RELEASE;
                }else{
                    layout_layer = WINGRIGHT_SHOW;
                    parameterLafaya.WingrightParametersShow();
                }
                updateLayout();
            }
        });

        button_parameterrightwing_flod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layout_layer == WINGRIGHT_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(layout_layer == WINGRIGHT_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }
                }
                return false;
            }
        });
        //vfd parameters show or hide
        button_parameterfvd_flod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_layer == VFD_SHOW){
                    layout_layer = LAYOUT_RELEASE;
                }else{
                    layout_layer = VFD_SHOW;
                    //发送信息。查询原来的参数。
                   parameterVFD.VFDParameterQuery();
                }
                updateLayout();
            }
        });

        button_parameterfvd_flod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(layout_layer == VFD_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(layout_layer == VFD_SHOW){
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_unfold));
                    }else {
                        ((ImageButton)view).setImageDrawable(activity.getDrawable(R.drawable.icon_fold));
                    }
                }
                return false;
            }
        });
    }

    // parameter changed listener
    private void parameter_changed_listener(){
        //seekbar listener
        seekbar_parameter_set.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                edittext_parameter_setnew.setText(Integer.toString(((int)(seekbar_parameter_set.getProgress() * dialog_step)) + dialog_minvalue));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //edittext listen
        edittext_parameter_setnew.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if(count > 0) {
                    //updata seekbar
                    int tmp = Integer.parseInt(edittext_parameter_setnew.getText().toString());
                    if ((tmp >= dialog_minvalue) && (tmp <= dialog_maxvalue)) {
                        seekbar_parameter_set.setProgress((int)((tmp - dialog_minvalue) / dialog_step));
                    } else if (tmp > dialog_maxvalue) {
                        edittext_parameter_setnew.setText(Integer.toString(dialog_maxvalue));
                    }
                    Editable b = edittext_parameter_setnew.getText();
                    edittext_parameter_setnew.setSelection(b.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void UpdateParameterlayout(int pagenumber){
        if(pagenumber != 4){
            layout_layer = LAYOUT_RELEASE;
            modify_flag = false;
            button_parameter_save.setEnabled(true);
            button_parameter_cancle.setEnabled(true);
            parameter_dialog(false,"--",0,0,0,1);
            operation_control(true);
        }else {
            updateLayout();
        }
        if(MainActivity.doorStatus.revolvingdoortype) {
            updateLayout();
        }
    }

    //parameter cancle
    private void paramter_cancle(){
        if(layout_layer == REVOLVINGDOOR_SHOW){
            parameterPLC.parameterPLC_cancle();
        }else if(layout_layer == VFD_SHOW){
            parameterVFD.parameterVFD_cancle();
        }

        parameter_dialog(false," ",0,0,0,0);
        modify_flag = false;
        operation_control(true);
        updateLayout();
    }
    //parameter saving
    private void parameter_saving(){
        switch(layout_layer){
            case REVOLVINGDOOR_SHOW://revolving door parameters saving
                parameterPLC.parameterPLC_saving(edittext_parameter_setnew.getText().toString());
                button_parameter_save.setEnabled(false);
                button_parameter_cancle.setEnabled(false);
                break;
            case SLIDINGDOOR_SHOW://sliding door parameters saving
                parameterLafaya.parameterLafaya_Saving(edittext_parameter_setnew.getText().toString());
                button_parameter_save.setEnabled(false);
                button_parameter_cancle.setEnabled(false);
                break;
            case WINGLEFT_SHOW://
                break;
            case WINGRIGHT_SHOW:
                break;
            case VFD_SHOW:
                parameterVFD.parameterVFD_saving(edittext_parameter_setnew.getText().toString());
                button_parameter_save.setEnabled(false);
                button_parameter_cancle.setEnabled(false);
                break;
            default:
                break;
        }
    }

    public void setbutton_text(String text){
        button_parameter_save.setText(text);
    }

    public void parametersetShow(boolean flag){
        if(flag){
            relativelayout_parameter_set.setVisibility(View.VISIBLE);
        }else {
            relativelayout_parameter_set.setVisibility(View.GONE);
        }
    }

    public void parameterCommunicationfailure(){
        parameterVFD.parameterclear();
        parameterLafaya.parameterclear();
        button_parameter_save.setEnabled(true);
        button_parameter_cancle.setEnabled(true);
        modify_flag = false;
        parameter_dialog(false,"--",0,0,0,1);
        operation_control(true);
        updateLayout();
    }

    public void parameterSavefinished(int flag){
        if(flag == VFD_SHOW){
            parameterVFD.Receivesetfinished();
        }else if(flag == SLIDINGDOOR_SHOW){
            parameterLafaya.Receivesetfinished();
        }
        parameter_dialog(false,"--",0,0,0,1);
        modify_flag = false;
        operation_control(true);
        updateLayout();
    }

    private void updateLayout(){
        if(MainActivity.doorStatus.revolvingdoortype) {

            int[] array_visiblity = new int[]{View.GONE, View.GONE, View.GONE, View.GONE, View.GONE};
            int[] array_icon = new int[]{R.drawable.icon_fold, R.drawable.icon_fold, R.drawable.icon_fold, R.drawable.icon_fold, R.drawable.icon_fold, R.drawable.icon_fold};

            relayout_parameter_sliding.setVisibility(View.VISIBLE);
            activity_parameter_revolvingdoor.setVisibility(View.VISIBLE);
            activity_parameter_fvd.setVisibility(View.VISIBLE);
            activity_parameter_wingleft.setVisibility(View.VISIBLE);
            activity_parameter_wingright.setVisibility(View.VISIBLE);

            if (layout_layer != LAYOUT_RELEASE) {
                array_visiblity[layout_layer - 1] = View.VISIBLE;
                array_icon[layout_layer - 1] = R.drawable.icon_unfold;
            }

            if (layout_layer != REVOLVINGDOOR_SHOW) {
                parameterPLC.postionsethide();
            } else if (layout_layer != VFD_SHOW) {
                parameterVFD.parameterclear();
            }

            layout_paramter_revolving.setVisibility(array_visiblity[0]);
            button_parameterrevolving_flod.setImageResource(array_icon[0]);

            layout_paramter_sliding.setVisibility(array_visiblity[1]);
            button_parametercenterdoor_flod.setImageResource(array_icon[1]);

            layout_paramter_wingleft.setVisibility(array_visiblity[2]);
            button_parameterleftwing_flod.setImageResource(array_icon[2]);

            layout_paramter_wingright.setVisibility(array_visiblity[3]);
            button_parameterrightwing_flod.setImageResource(array_icon[3]);

            layout_paramter_fvd.setVisibility(array_visiblity[4]);
            button_parameterfvd_flod.setImageResource(array_icon[4]);
        }else {
            layout_paramter_sliding.setVisibility(View.VISIBLE);
            relayout_parameter_sliding.setVisibility(View.GONE);
            activity_parameter_revolvingdoor.setVisibility(View.GONE);
            activity_parameter_fvd.setVisibility(View.GONE);
            activity_parameter_wingleft.setVisibility(View.GONE);
            activity_parameter_wingright.setVisibility(View.GONE);

            if(layout_layer != SLIDINGDOOR_SHOW){
                layout_layer = SLIDINGDOOR_SHOW;
                parameterLafaya.SlidingdoorQuery();
            }
        }
        //有参数需要发送
        if(modify_flag){
            relativelayout_parameter_set.setVisibility(View.VISIBLE);
        }else{
            relativelayout_parameter_set.setVisibility(View.GONE);
        }
    }

    //enable or disable operation
    private void operation_control(boolean flag){

        button_parameterrevolving_flod.setEnabled(flag);
        button_parametercenterdoor_flod.setEnabled(flag);
        button_parameterrightwing_flod.setEnabled(flag);
        button_parameterleftwing_flod.setEnabled(flag);
        button_parameterfvd_flod.setEnabled(flag);

        parameterPLC.operationPLC_control(flag);
        parameterVFD.operationVFD_control(flag);
        parameterLafaya.operationLafaya_control(flag);

        layout_paramter_revolving.setEnabled(flag);
        layout_paramter_sliding.setEnabled(flag);
        layout_paramter_wingleft.setEnabled(flag);
        layout_paramter_wingright.setEnabled(flag);
        layout_paramter_fvd.setEnabled(flag);
    }
    //参数设置对话框
    public void parameter_dialog(boolean flag,String name,int maxv, int minv, int nowv, float step){
//        dialog_titlename = name;
        dialog_maxvalue = maxv;
        dialog_minvalue = minv;
//        dialog_nowvalue = nowv;
        dialog_step = step;

        if(flag){
            modify_flag = true;
            relativelayout_parameter_set.setVisibility(View.VISIBLE);
            layout_parameter_all.setEnabled(false);
            operation_control(false);
            text_parameter_settitle.setText(name);
            text_parameter_setold.setText(Integer.toString(nowv));
            edittext_parameter_setnew.setText(Integer.toString(nowv));
            seekbar_parameter_set.setMax((int)((dialog_maxvalue - dialog_minvalue)/dialog_step));
            seekbar_parameter_set.setProgress((int)((nowv - dialog_minvalue)/dialog_step));
            layout_parameter_dialog.setVisibility(View.VISIBLE);
        }else {
            layout_parameter_dialog.setVisibility(View.GONE);
        }

        button_parameter_save.setEnabled(true);
        button_parameter_cancle.setEnabled(true);
    }


    // 获取ListView的数据
    public static HashMap<String, Object> getparameterlistViewData( String textname,String value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("parameter_name", textname);
        map.put("parameter_value", value);
        return (map);
    }

    //String to long
    public long dataStringtoLong(String textstring){
        char[] data = MainActivity.bluetoothComm.commandLafaya.AsciiToHex(textstring.toCharArray());
        int datatemp;
        long runtime;
        datatemp = data[2];
        datatemp = (datatemp << 8) + data[3];
        runtime = ((long)datatemp << 16);
        datatemp = data[0];
        datatemp = (datatemp << 8) + data[1];
        runtime += ((long)datatemp);
        return  runtime;
    }
    //String to int
    public int dataStringtoInteger(String textstring){
        char[] data = MainActivity.bluetoothComm.commandLafaya.AsciiToHex(textstring.toCharArray());
        int datatemp;
        datatemp = data[0];
        datatemp = (datatemp << 8) + data[1];
        return  datatemp;
    }

    public void resetParameterlayout(){

    }



}
