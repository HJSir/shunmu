package com.example.jian.shunmu;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.jian.shunmu.CourseModeFragment.CourseTime;
import static com.example.jian.shunmu.CourseModeFragment.choose;

public class AlarmTimeService extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();

        toggleNotificationListenerService(); //service被杀死后重新绑定
    }

    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, com.example.jian.shunmu.AlarmTimeService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this,com.example.jian.shunmu.AlarmTimeService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, AlarmTimeService.class);  //销毁时重新启动Service
        this.startService(localIntent);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {





                        final Calendar mCalendar = Calendar.getInstance();
                        int sysHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                        int sysMinute = mCalendar.get(Calendar.MINUTE);
                        for (int i = 0; i < 4; i++) {
                            //如果被选择了
 Log.i("Tag"," "+i+" "+choose[i]);
                            if (choose[i] == true)
                                if (sysHour < CourseTime.get(i).endTimeHour || (sysHour == CourseTime.get(i).endTimeHour && sysMinute < CourseTime
                                        .get(i).endTimeMinute)) {  //满足属于这个定时区间
                                    Intent intent2 = new Intent(getApplication(), AlarmTimeReceiver.class);
                                    PendingIntent sender = PendingIntent.getBroadcast(getApplication(),
                                            i, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
                                    // Schedule the alarm!
                                    AlarmManager am = (AlarmManager) getApplication()
                                            .getSystemService(Context.ALARM_SERVICE);
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.HOUR_OF_DAY, CourseTime.get(i).startTimeHour);
                                    calendar.set(Calendar.MINUTE, CourseTime.get(i).startTimeMinute);
                                    calendar.set(Calendar.SECOND, 10);
                                    calendar.set(Calendar.MILLISECOND, 0);
                                    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                                }
                        }






        return START_STICKY;
    }


}
