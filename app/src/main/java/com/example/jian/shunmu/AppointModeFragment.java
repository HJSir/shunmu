package com.example.jian.shunmu;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AppointModeFragment extends Fragment implements View.OnClickListener {

    private Button setroom;
    private Button joinroom;
    private EditText room_id;
    private View currentView;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(currentView==null)
        {
            currentView = inflater.inflate(R.layout.fragment_appoint_mode, container, false);

        }
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            parent.removeView(currentView);
        }
        currentView = inflater.inflate(R.layout.fragment_appoint_mode, container, false);
         view= inflater.inflate(R.layout.edit_roomid, container, false);
        setroom = (Button) currentView.findViewById(R.id.bt_setroom);
        joinroom = (Button) currentView.findViewById(R.id.bt_joinroom);
        room_id = (EditText) currentView.findViewById(R.id.et_roomid);

        setroom.setOnClickListener(this);
        joinroom.setOnClickListener(this);


        return currentView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_setroom) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());

            builder2.setIcon(R.drawable.shunmulog);
            builder2.setTitle("确认创建？");

            builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //处理创建逻辑 随机一个数字作为房间ID 给创建者房主权限
                    //处理加入房间逻辑


                }
            });
            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {



                }
            });
            builder2.create().show();

        }


        if (v.getId() == R.id.bt_joinroom) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setIcon(R.drawable.shunmulog);
            builder.setTitle("请输入您的房间号");
            ViewGroup parent2 = (ViewGroup) view.getParent();
            if (parent2 != null) {
                parent2.removeView(view);
            }
            builder.setView(view);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // /处理加入逻辑

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();


        }


    }
}
