package com.example.jian.shunmu;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;


public class CourseModeFragment extends Fragment {

    private Button btn1;
    ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();
    public static ArrayList<coursetimeBean> CourseTime = new ArrayList<coursetimeBean>();
    private View currentView;
    public static boolean[] choose = new boolean[]{false, false, false, false};

    void Change(String[] nItems) {

        for (int i = 0; i < nItems.length; i++) {
            CourseTime.add(new coursetimeBean(Integer.parseInt(nItems[i].substring(0, 2)), Integer.parseInt(nItems[i].substring(3, 5)), Integer.parseInt(nItems[i].substring(6, 8)), Integer.parseInt(nItems[i].substring(9, 11))));
//Log.i("tagg",nItems[i].substring(0,nItems[i].indexOf(":"))+nItems[i].substring(nItems[i].indexOf(":")+1,nItems[i].indexOf("-")));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_course_mode, container, false);


//        final String [] nItems = {"08:00-09:40","10:10-11:50","14:30-16:10","16:20-18:00"};
        final String[] nItems = {"16:00-17:05", "19:33-19:35", "20:00-21:00", "06:00-07:47"};
//        Log.i("tagg",nItems[0].substring(0,2)+nItems[0].substring(3,5)+nItems[0].substring(6,8));

//        Log.i("Tag",""+nItems[0].substring(nItems[0].indexOf(":")+1,nItems[0].length()-1));+nItems[0].substring(nItems[0].indexOf("-"),nItems[0].indexOf(":"))
        btn1 = (Button) currentView.findViewById(R.id.bt_choosetime);
        btn1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                MultiChoiceID.clear();
                builder.setIcon(R.drawable.shunmulog);
                builder.setTitle("请选择您的时间");
                //  设置多选项
                builder.setMultiChoiceItems(nItems,
                        choose,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                                // TODO Auto-generated method stub

                            }
                        }

                );
                //  设置确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        // TODO Auto-generated method stub
                        Change(nItems);
                        Intent intent = new Intent();

                        intent.putExtra("list", CourseTime);
                        intent.setClass(getActivity(),AlarmTimeService.class);
                        getActivity().startService(intent);

                    }
                });
                //  设置取消按钮
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });

                builder.create().show();
            }
        });



        return currentView;
    }

}
