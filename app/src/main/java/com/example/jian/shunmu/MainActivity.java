package com.example.jian.shunmu;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private TextView tv_course;
    private TextView tv_appoint;
    private TextView tv_free;
    private TextView tv_forum;
    private CourseModeFragment mCourseModeFragment;
    private FreedomModeFragment mFreedomModeFragment;
    private AppointModeFragment mAppointModeFragment;
    private static final int REQUECT_CODE = 2;
    private String[] Permissions={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        //如果系统是6.0以上的 必须动态授权
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! Settings.canDrawOverlays(MainActivity.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,10);
            }
        }

        MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE, Permissions);
        initFragment();

    }
    /////////////////////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (!Settings.canDrawOverlays(this)) {
                // SYSTEM_ALERT_WINDOW permission not granted...
                Toast.makeText(MainActivity.this,"您没有打开权限 本应用将无法正常使用",Toast.LENGTH_SHORT);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("tag","grantResults:"+grantResults + "requestCod:"+requestCode);
    }


    @PermissionGrant(REQUECT_CODE)
    public void requestSdcardSuccess()
    {
        Toast.makeText(this, "成功授权！", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE)
    public void requestSdcardFailed()
    {
        Toast.makeText(this, "授权失败！部分功能将无法使用！", Toast.LENGTH_SHORT).show();
    }
    /////////////////////////
    private void initFragment() {
        mCourseModeFragment = new CourseModeFragment();
        mAppointModeFragment = new AppointModeFragment();
        mFreedomModeFragment = new FreedomModeFragment();
        tv_appoint= (TextView) findViewById(R.id.main_appoint);
        tv_forum= (TextView) findViewById(R.id.main_forum);
        tv_free= (TextView) findViewById(R.id.main_free);
        tv_course= (TextView) findViewById(R.id.main_course);
        tv_appoint.setOnClickListener(this);
        tv_forum.setOnClickListener(this);
        tv_free.setOnClickListener(this);
        tv_course.setOnClickListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,mCourseModeFragment).commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.main_free:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mFreedomModeFragment).commit();
                break;
            case R.id.main_appoint:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mAppointModeFragment).commit();
                break;
            case R.id.main_forum:
                break;
            case R.id.main_course:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,mCourseModeFragment).commit();
                break;
            
        }

    }
}



