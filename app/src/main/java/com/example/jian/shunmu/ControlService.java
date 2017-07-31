package com.example.jian.shunmu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.jian.shunmu.R.styleable.View;

public class ControlService extends Service {

    //定义浮动窗口布局
    static LinearLayout mFloatLayout;
    static WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    static WindowManager mWindowManager;
    private TextView tv_timelost;
    private int time;
    private int recLen = 0;
    private static final String TAG = "FxService";

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();


    }
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createFloatView();
        Log.d(TAG,"onStartCommand");
        ////////////////
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);


        mBuilder.setContentTitle("测试标题")//设置通知栏标题
                .setContentText("测试内容")
        .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//  .setNumber(number) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.shunmulog);//设置通知小ICON

        mNotificationManager.notify(1, mBuilder.build());









        ////////////
        tv_timelost= (TextView)mFloatLayout.findViewById(R.id.tv_timelost);
        handler.postDelayed(runnable, 1000);
       Bundle bundle = intent.getExtras();
//        //接收time值
            time = bundle.getInt("time");

        recLen=time;//设置给倒数的
        Log.i("time=",time+"");

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent)
    {

        // TODO Auto-generated method stub
        return null;
    }

    private void createFloatView()
    {
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        Log.i(TAG, "mWindowManager--->" + mWindowManager);
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据 WindowManager.LayoutParams.MATCH_PARENT
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height =WindowManager.LayoutParams.MATCH_PARENT;

         /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局

        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout

        mWindowManager.addView(mFloatLayout, wmParams);



    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(recLen>0){
            recLen--;
            tv_timelost.setText("" + recLen+"秒");
           // Log.i("time","" + recLen+"秒");
            handler.postDelayed(this, 1000);}else if(recLen<=0)
            {
                //移除悬浮窗口
                hidden();
                SharedPreferences sp = getSharedPreferences("SystemTime", Context.MODE_PRIVATE);
                sp.edit().putInt("tag",0).commit();
            }
        }
    };

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        hidden();
    }
    public static void hidden(){

        if(mFloatLayout != null)
        {
            //移除悬浮窗口
            mWindowManager.removeView(mFloatLayout);
        }

    }
    public static void show(){
        mWindowManager.addView(mFloatLayout, wmParams);
    }
}
