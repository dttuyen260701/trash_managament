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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;

public class Fragment_Control extends Fragment {
    private TextView txtPower_Control_Frag;
    private ImageButton btn_Compartment, btn_Inside_door,
            btnPower_Ctrl_Frag;
    private ImageView img_Descrip_Control_Frag, img_first_door,
            image_tool_bar_Control_frag;
    private Garbage_Can garbage_can;
    private long mLastClick_Power = 0, mLastClick_Close = 0,
            mLastClick_LRDoor = 0;

    public Fragment_Control(Garbage_Can garbage_can) {

        this.garbage_can = garbage_can;
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
        btn_Compartment = (ImageButton) view.findViewById(R.id.btn_Compartment);
        btn_Inside_door = (ImageButton) view.findViewById(R.id.btn_Inside_door);
        btnPower_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnPower_Ctrl_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);
        img_first_door = (ImageView) view.findViewById(R.id.img_first_door);
        image_tool_bar_Control_frag = (ImageView) view.findViewById(R.id.image_tool_bar_Control_frag);
        txtPower_Control_Frag = (TextView) view.findViewById(R.id.txtPower_Control_Frag);

        updateView();

        if(garbage_can.isPower()){
            txtPower_Control_Frag.setText("Power: On");
            isPowerOn();
        } else {
            txtPower_Control_Frag.setText("Power: Off");
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

        btn_Compartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Close < 4000){
                    return;
                }
                mLastClick_Close = SystemClock.elapsedRealtime();
                int img_door = 0;
                if(garbage_can.isDoor()){
                    garbage_can.setDoor(false);
                    img_door = R.drawable.first_door_nrc;
                } else{
                    garbage_can.setDoor(true);
                    img_door = R.drawable.first_door_rc;
                }
                Animation anim_rotate_center = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_center );
                int finalImg_door = img_door;
                anim_rotate_center.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        img_first_door.setImageResource(finalImg_door);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                img_first_door.startAnimation(anim_rotate_center);
            }
        });

        btn_Inside_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_LRDoor < 4000){
                    return;
                }
                mLastClick_LRDoor = SystemClock.elapsedRealtime();
                Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_down);
                image_tool_bar_Control_frag.startAnimation(rotate);

            }
        });

        btnPower_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Power < 4000){
                    return;
                }
                mLastClick_Power = SystemClock.elapsedRealtime();
                if(garbage_can.isPower()){
                    txtPower_Control_Frag.setText("Power: Off");
                    garbage_can.setPower(false);
                    btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
                    isPowerOff();
                } else {
                    txtPower_Control_Frag.setText("Power: On");
                    garbage_can.setPower(true);
                    btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
                    isPowerOn();
                }
            }
        });
    }

    private void isPowerOff(){
        //dong cua k nhan rac
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
        btn_Compartment.setEnabled(false);
        btn_Inside_door.setEnabled(false);
    }

    private void isPowerOn(){
        //mo cua thung rac
        garbage_can.setDoor(true);
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
        btn_Compartment.setEnabled(true);
        btn_Inside_door.setEnabled(true);
    }


    public void updateView(){
        if(garbage_can.isDoor()){
            img_first_door.setImageResource(R.drawable.first_door_rc);
        } else {
            img_first_door.setImageResource(R.drawable.first_door_nrc);
        }

        if(garbage_can.isPower()){
            btn_Compartment.setEnabled(true);
            btn_Inside_door.setEnabled(true);
        } else {
            btn_Compartment.setEnabled(false);
            btn_Inside_door.setEnabled(false);
        }
    }
}
