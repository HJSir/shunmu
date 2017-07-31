package com.example.jian.shunmu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.jian.shunmu.MainActivity;
import com.example.jian.shunmu.BootBroadcastReceiver;

import java.util.Calendar;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public final static String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

    private int time_hour;
    final Calendar mCalendar=Calendar.getInstance();
    private int time_min; //用户到达指定分钟
    private int time_system_hour;//系统小时
    private int time_system_min; //系统分钟
    private int time_day; // 跨越天数
    private int time;  //计算得出的 还需要锁屏的秒数
  private int time_system_year;
    private int time_system_month;
    private int time_system_day;

    public BootBroadcastReceiver() {
        super();
    }

    // 重写onReceive方法
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)){
            time_system_year=mCalendar.get(Calendar.YEAR);
            time_system_month=mCalendar.get(Calendar.MONTH);
            time_system_day=mCalendar.get(Calendar.DATE);
            SharedPreferences sp = context.getSharedPreferences("SystemTime", Context.MODE_PRIVATE);
           if((sp.getInt("tag", 0)==1)&&(sp.getInt("year", 0)==time_system_year)&&(sp.getInt("month",0)==time_system_month)){//锁屏没完成的情况下关机了 且下次开机时间为当年 当月
               time_system_hour=mCalendar.get(Calendar.HOUR_OF_DAY);
               time_system_min=mCalendar.get(Calendar.MINUTE);
               time_hour=sp.getInt("hour",0);
               time_min=sp.getInt("minute",0);
               time_day=sp.getInt("day",0);

               time=(time_hour-(time_day-time_system_day)*24-time_system_hour)*3600+(time_min-time_system_min)*60;

           if(time>=0) { //说明时间还没到 应该继续锁屏

               Intent serIntent = new Intent(context, ControlService.class);
               //用Bundle携带锁屏时间数据
               Bundle bundle=new Bundle();
               //将时间换算成秒
               bundle.putInt("time",time);
               serIntent.putExtras(bundle);
               serIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startService(serIntent);
           }

           }
        }


    }

}
