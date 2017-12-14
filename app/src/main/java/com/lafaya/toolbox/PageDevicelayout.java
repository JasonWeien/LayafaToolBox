package com.lafaya.toolbox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import me.aflak.bluetooth.Bluetooth;

/**
 * Created by JeffYoung on 2016/11/3.
 **/
public class PageDevicelayout extends Activity{
    private Bluetooth bluetooth;
    private BluetoothAdapter bluetoothAdapter;
    public List<BluetoothDevice> allpaireddevice = new ArrayList<>();
    public List<BluetoothDevice> onlinedevice = new ArrayList<>();
    public List<BluetoothDevice> paireddevice = new ArrayList<>();
    public List<BluetoothDevice> unpaireddevice = new ArrayList<>();
    private List<String> pairenames = new ArrayList<String>();
    private List<String> unpairenames = new ArrayList<String>();

    private boolean registered = false;

    private AutoCountListView list_devive_paired,list_devive_unpaired;

    Button button_device_cancle,button_device_scan;
    LinearLayout progress_scandevice;
    private boolean bluetoothscaning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelayout);

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver,filter);
        registered = true;


        bluetooth = new Bluetooth(this);
        bluetooth.enableBluetooth();
        allpaireddevice = bluetooth.getPairedDevices();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        list_devive_paired = (AutoCountListView)findViewById(R.id.list_devive_paired);
        list_devive_unpaired = (AutoCountListView)findViewById(R.id.list_devive_unpaired);
        button_device_cancle = (Button)findViewById(R.id.button_device_cancle);
        button_device_scan = (Button)findViewById(R.id.button_device_scan);
        progress_scandevice = (LinearLayout)findViewById(R.id.progress_scandevice);
//        progress_scandevice.setVisibility(View.GONE);
//        Toast.makeText(PageDevicelayout.this,"正在扫描设备，请销后！",Toast.LENGTH_LONG).show();
        bluetooth.scanDevices();
        progress_scandevice.setVisibility(View.VISIBLE);
        button_device_scan.setEnabled(false);

        //在线已配对设备选择
        list_devive_paired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BluetoothDevice d  = paireddevice.get(position);

                int pos = 0;

                for(BluetoothDevice d1 : allpaireddevice){
                    if(d.equals(d1)){
                        /*返回选择的位置*/
                        setResult(RESULT_OK,(new Intent()).putExtra("pos",pos));
                        break;
                    }
                    pos++;
                }

                if(registered) {
                    unregisterReceiver(mReceiver);
                    registered=false;
                }
                bluetooth.removeDiscoveryCallback();
                finish();
            }
        });
        //在线未配对设备选择
        list_devive_unpaired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*返回选择的位置*/
                /*配对设备*/
                Message msg = new Message();
                msg.what = 6;
                Bundle bundle = new Bundle();
                bundle.putString("pos",Integer.toString(position));
                msg.setData(bundle);
                bthandler.sendMessage(msg);
            }
        });

        //扫描设备
        button_device_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!bluetoothscaning){
                    cleardevice();
                    bluetoothscaning = true;
                }
                Message msg = new Message();
                msg.what = 7;
                bthandler.sendMessage(msg);

            }
        });

        //取消扫描
        button_device_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registered) {
                    unregisterReceiver(mReceiver);
                    registered=false;
                }
                bluetoothAdapter.cancelDiscovery();
                bluetooth.removeDiscoveryCallback();

                finish();
            }
        });


        bluetooth.setDiscoveryCallback(new Bluetooth.DiscoveryCallback(){
            @Override
            public void onFinish() {
                Message msg = new Message();
                msg.what = 1;
                bthandler.sendMessage(msg);
            }

            @Override
            public void onDevice(BluetoothDevice device) {
                onlinedevice.add(device);
                Message msg = new Message();
                msg.what = 2;
                bthandler.sendMessage(msg);

            }

            @Override
            public void onPair(BluetoothDevice device) {
                Message msg = new Message();
                msg.what = 3;
                bthandler.sendMessage(msg);

            }

            @Override
            public void onUnpair(BluetoothDevice device) {
                Message msg = new Message();
                msg.what = 4;
                bthandler.sendMessage(msg);
            }

            @Override
            public void onError(String message) {
                Message msg = new Message();
                msg.what = 5;
                bthandler.sendMessage(msg);
            }
        });
    }

    //
    // @Override
    public void onDestroy() {
        super.onDestroy();
        if(registered) {
            unregisterReceiver(mReceiver);
            registered=false;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }



    /*显示在线设备*/
    private void addDeviceList(){
        Boolean matchlflag = false;
        BluetoothDevice de = onlinedevice.get(onlinedevice.size()-1);

        for (BluetoothDevice de1 : allpaireddevice) {
            if (de.equals(de1)) {
                pairenames.add(de.getName());
                paireddevice.add(de);
                matchlflag = true;
                break;
            }
        }
        if(!matchlflag){
            if(de.getName() != null) {
                unpairenames.add(de.getName());
                unpaireddevice.add(de);
            }
        }

        //update device list
        //updatedevice(onlinedevice.get(onlinedevice.size()-1));
        // show list view
        if(matchlflag) {
            if ((pairenames.size() > 0) && (!pairenames.isEmpty())) {
                int size = pairenames.size();
                String[] pairearray = pairenames.toArray(new String[size]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drawer_list, R.id.text_drawerlist, pairearray);
                list_devive_paired.setAdapter(adapter);
                // 设置显示高度
                if (adapter.getCount() > 0) {
                    AutoCountListView.setListViewHeightBasedOnChildren(list_devive_paired);
                }
            }
        }else {
            if ((unpairenames.size() > 0) && (!unpairenames.isEmpty())) {
                int size = unpairenames.size();
                String[] unpairearrayarray = unpairenames.toArray(new String[size]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drawer_list, R.id.text_drawerlist, unpairearrayarray);
                list_devive_unpaired.setAdapter(adapter);
                // 设置显示高度
                if (adapter.getCount() > 0) {
                    AutoCountListView.setListViewHeightBasedOnChildren(list_devive_unpaired);
                }
            }
        }
    }

    //清除所有状态
    private void cleardevice(){
        pairenames = new ArrayList<String>();
        unpairenames = new ArrayList<String>();
        paireddevice = new ArrayList<BluetoothDevice>();
        unpaireddevice = new ArrayList<BluetoothDevice>();
        onlinedevice = new ArrayList<BluetoothDevice>();
        allpaireddevice = bluetooth.getPairedDevices();
    }
    //更新设备状态
    private void updatedevice(BluetoothDevice de){
        if(de.getName() != null) {
            for (BluetoothDevice de1 : allpaireddevice) {
                if (de.equals(de1)) {
                    pairenames.add(de.getName());
                    paireddevice.add(de);
                    return;
                }
            }
            unpairenames.add(de.getName());
            unpaireddevice.add(de);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler bthandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    Toast.makeText(PageDevicelayout.this, "设备扫描完成", Toast.LENGTH_SHORT).show();
                    progress_scandevice.setVisibility(View.GONE);
                    button_device_scan.setEnabled(true);
                    //text_select.setText("请选择可用设备");
                    msg.what = 0;
                    break;
                case 2:
                    addDeviceList();
                    msg.what = 0;
                    break;
                case 3:  //配对成功
                    pairenames = new ArrayList<>();
                    unpairenames = new ArrayList<>();
                    paireddevice = new ArrayList<>();
                    unpaireddevice = new ArrayList<>();
                    addDeviceList();
                    Toast.makeText(PageDevicelayout.this, "配对成功", Toast.LENGTH_SHORT).show();
                    msg.what = 0;
                    break;
                case 4: //配对失败
                    addDeviceList();
                    Toast.makeText(PageDevicelayout.this, "配对失败", Toast.LENGTH_SHORT).show();
                    msg.what = 0;
                    break;
                case 5: //出错
                    addDeviceList();
                    msg.what = 0;
                    break;
                case 6:

                    Toast.makeText(PageDevicelayout.this, "正在配对设备", Toast.LENGTH_SHORT).show();
                    bluetooth.pair(unpaireddevice.get(Integer.parseInt(msg.getData().getString("pos"))));
                    msg.what = 0;
                    break;
                case 7: //重新扫描
                    bluetooth = new Bluetooth(PageDevicelayout.this);
//                    Toast.makeText(PageDevicelayout.this, "正在扫描设备，请稍后！", Toast.LENGTH_SHORT).show();
                    progress_scandevice.setVisibility(View.VISIBLE);
                    button_device_scan.setEnabled(false);
                    bluetooth.scanDevices();
                    msg.what = 0;
                    break;
                default:
                    break;
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                        Toast.makeText(PageDevicelayout.this, "蓝牙已关闭", Toast.LENGTH_LONG).show();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cleardevice();
                                Message msg = new Message();
                                msg.what = 5;
                                bthandler.sendMessage(msg);
                            }
                        });
                        Toast.makeText(PageDevicelayout.this, "蓝牙已打开", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    };

}
