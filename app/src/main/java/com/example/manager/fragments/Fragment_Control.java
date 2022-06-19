package com.example.manager.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.manager.Activity.MainActivity;
import com.example.manager.Asyntask.Load_task_AsyncTask;
import com.example.manager.Asyntask.Set_ENB_Asynctask;
import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.Methods;
import com.example.manager.listeners.Load_Task_Listener;

public class Fragment_Control extends Fragment {
    private TextView txt_EnB;
    private ImageButton btnPower_Ctrl_Frag;
    private ImageView img_Descrip_Control_Frag, img_block;
    private ProgressBar pro_Control;
    private long mLastClick_Power = 0, mLastClick_Close = 0,
            mLastClick_LRDoor = 0;

    public Fragment_Control() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        txt_EnB = (TextView) view.findViewById(R.id.txt_EnB);
        btnPower_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnPower_Ctrl_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);
        img_block = (ImageView) view.findViewById(R.id.img_block);
        pro_Control = (ProgressBar) view.findViewById(R.id.pro_Control);
        updateView();

        if(Constant_Values.garbage_can.isEnb()){
            txt_EnB.setText("Enable");
            isPowerOn();
        } else {
            txt_EnB.setText("Disable");
            isPowerOff();
        }

        img_Descrip_Control_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_HTU_Dialog fragment_htu_dialog = new Fragment_HTU_Dialog(
                        Constant_Values.HTU_Control
                );
                fragment_htu_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        btnPower_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Power < 4000){
                    return;
                }
                mLastClick_Power = SystemClock.elapsedRealtime();
                Load_Task_Listener listener = new Load_Task_Listener() {
                    @Override
                    public void onPre() {
                        if(getContext() != null){

                            if(!Methods.getInstance(getContext()).isNetworkConnected()){
                                Toast.makeText(getContext(), "Vui lòng kết nối Internet", Toast.LENGTH_SHORT).show();
                            }
                            pro_Control.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onEnd(boolean done, boolean is_pro) {
                        pro_Control.setVisibility(View.GONE);
                        if(done){
                            Constant_Values.garbage_can.setEnb(is_pro);
                            if(is_pro){
                                isPowerOn();
                            } else {
                                isPowerOff();
                            }
                        } else {
                            if(getContext() != null) {
                                Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                };
                Set_ENB_Asynctask task =  new Set_ENB_Asynctask(listener);
                String enb_set = (Constant_Values.garbage_can.isEnb() == true) ? "0" : "1";
                String URL = Constant_Values.ENB + enb_set;
                task.execute(URL);
            }
        });
    }

    private void isPowerOff(){
        //dong cua k nhan rac
        txt_EnB.setText("Garbage can lid: Close");
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
        img_block.setVisibility(View.GONE);

    }

    private void isPowerOn(){
        //mo cua thung rac
        txt_EnB.setText("Garbage can lid: Open");
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
        img_block.setVisibility(View.VISIBLE);
    }


    public void updateView(){
        if(Constant_Values.garbage_can.isEnb()){
            isPowerOn();
        } else {
            isPowerOff();
        }
    }
}
