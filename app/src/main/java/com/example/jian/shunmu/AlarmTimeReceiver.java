package com.example.jian.shunmu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmTimeReceiver extends BroadcastReceiver {
    public AlarmTimeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Intent serIntent = new Intent(context, ControlService.class);
        //用Bundle携带锁屏时间数据
        Bundle bundle=new Bundle();
        //将时间换算成秒
        bundle.putInt("time",4);
        serIntent.putExtras(bundle);
        serIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(serIntent);

    }
}
