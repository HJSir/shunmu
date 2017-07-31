package com.example.jian.shunmu;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreedomModeFragment extends Fragment implements View.OnClickListener {
View currentView;
    NumberPicker hourPicker;
    NumberPicker minutePicker;
  Context mContext;
    private int time_hour;
    final Calendar mCalendar=Calendar.getInstance();
    private int time_min;
    private int time_system_hour;
    private int time_system_min;
    private Button bt_ok;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        currentView=inflater.inflate(R.layout.fragment_freedom_mode, container, false);
        init();
        return currentView;
    }
    private void init() {
        mContext=getActivity();
        hourPicker=(NumberPicker)currentView.findViewById(R.id.hourpicker);
        minutePicker=(NumberPicker)currentView.findViewById(R.id.minuteicker);
        bt_ok= (Button) currentView.findViewById(R.id.bt_freedom_ok);

        bt_ok.setOnClickListener(this);

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time_hour=newVal;

            }
        });
        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);
        hourPicker.setValue(0);
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                time_min=newVal;

            }
        });

        minutePicker.setMaxValue(60);
        minutePicker.setMinValue(0);
        minutePicker.setValue(0);}

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_freedom_ok){

            //获取系统时间 以备关机后 再开机读取时间 可正常启动锁屏
            time_system_hour=mCalendar.get(Calendar.HOUR_OF_DAY);
            time_system_min=mCalendar.get(Calendar.MINUTE);
            Log.i("tag","系统时间："+time_system_hour+"时"+time_system_min+"分");
            //该锁屏到几点（计算）。
            int temp_hour=0;
            int temp_min=0;
            int temp_day=0; //跨天
            int temp_year=0; //确保在这一年内
            int temp_month=0; //确保在这一个月内
            temp_min = (time_min+time_system_min)%60;
            temp_hour=time_system_hour+time_hour+(time_min+time_system_min)/60; //此时间可能超过24小时
            //temp_day=(time_system_hour+time_hour+(time_min+time_system_min)/60)/24;
            temp_day=mCalendar.get(Calendar.DATE);
            temp_year=mCalendar.get(Calendar.YEAR);
            temp_month=mCalendar.get(Calendar.MONTH);
         Log.i("tag","dat"+temp_day);
            //存入文件中。
            SharedPreferences sp = getActivity().getSharedPreferences("SystemTime", Context.MODE_PRIVATE);
            sp.edit().putInt("hour",temp_hour).putInt("minute", temp_min).putInt("day",temp_day).putInt("tag",1).putInt("year",temp_year).putInt("month",temp_month).commit();
            Toast.makeText(mContext,time_hour+"时 "+time_min+"分",Toast.LENGTH_SHORT).show();
            //跳转锁屏界面
            Intent intent = new Intent();
            intent.setClass(mContext,ControlService.class);
            //用Bundle携带锁屏时间数据
            Bundle bundle=new Bundle();
           //将时间换算成秒
            bundle.putInt("time", time_hour*3600+time_min*60);
            intent.putExtras(bundle);
            getActivity().startService(intent);

        }


    }
}
