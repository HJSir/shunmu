package com.example.jian.shunmu;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import static com.example.jian.shunmu.ControlService.hidden;
import static com.example.jian.shunmu.ControlService.show;

public class PhoneStatReceiver extends BroadcastReceiver{

    private static final String TAG = "PhoneStatReceiver";


    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        //如果是拨打电话

        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){


        }else{
            //如果是来电
            TelephonyManager tm =
                    (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

            switch (tm.getCallState()) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    hidden();
                    incomingFlag = true;//标识当前是来电
                    incoming_number = intent.getStringExtra("incoming_number");
                    Log.i(TAG, "RINGING :"+ incoming_number);
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if(incomingFlag){
                        Log.i(TAG, "incoming ACCEPT :"+ incoming_number);
                    }
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if(incomingFlag){
                        show();
                        Log.i(TAG, "incoming IDLE");
                    }
                    break;
            }
        }
    }
}
