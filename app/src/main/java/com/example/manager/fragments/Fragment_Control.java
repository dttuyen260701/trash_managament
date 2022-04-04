package com.example.manager.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;

public class Fragment_Control extends Fragment {
    private TextView txtPower_Control_Frag;
    private ImageButton btnClose_Ctrl_Frag, btnInSideDoor_Ctrl_Frag,
            btnPower_Ctrl_Frag;
    private ImageView img_Descrip_Control_Frag, img_Door_Control_frag,
            image_tool_bar_Control_frag, img_board_Control_frag;
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
        btnClose_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnClose_Ctrl_Frag);
        btnInSideDoor_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnInSideDoor_Ctrl_Frag);
        btnPower_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnPower_Ctrl_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);
        img_Door_Control_frag = (ImageView) view.findViewById(R.id.img_Door_Control_frag);
        img_board_Control_frag = (ImageView) view.findViewById(R.id.img_board_Control_frag);
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

        btnClose_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Close < 2000){
                    return;
                }
                mLastClick_Close = SystemClock.elapsedRealtime();
                int img_door = 0;
                if(garbage_can.isDoor()){
                    garbage_can.setDoor(false);
                    btnClose_Ctrl_Frag.setImageResource(R.drawable.open);
                    img_door = R.drawable.door;
                } else{
                    garbage_can.setDoor(true);
                    btnClose_Ctrl_Frag.setImageResource(R.drawable.close);
                    img_door = R.drawable.garbage;
                }
                Animation anim_alpha = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
                int finalImg_door = img_door;
                anim_alpha.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        img_Door_Control_frag.setImageResource(finalImg_door);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                img_Door_Control_frag.startAnimation(anim_alpha);
            }
        });

        btnInSideDoor_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
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
                if (SystemClock.elapsedRealtime() - mLastClick_Power < 2000){
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
        if(garbage_can.isDoor()){
            garbage_can.setDoor(false);
            Animation anim_alpha = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
            anim_alpha.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    img_Door_Control_frag.setImageResource(R.drawable.door);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            img_Door_Control_frag.startAnimation(anim_alpha);
        }
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
        btnClose_Ctrl_Frag.setImageResource(R.drawable.open);
        btnClose_Ctrl_Frag.setEnabled(false);
        btnInSideDoor_Ctrl_Frag.setEnabled(false);
    }

    private void isPowerOn(){
        //mo cua thung rac
        garbage_can.setDoor(true);
        btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
        btnClose_Ctrl_Frag.setImageResource(R.drawable.close);
        btnClose_Ctrl_Frag.setEnabled(true);
        btnInSideDoor_Ctrl_Frag.setEnabled(true);
        Animation anim_alpha = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
        anim_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_Door_Control_frag.setImageResource(R.drawable.garbage);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img_Door_Control_frag.startAnimation(anim_alpha);
    }


    public void updateView(){
        if(garbage_can.isDoor()){
            btnClose_Ctrl_Frag.setImageResource(R.drawable.close);
            img_Door_Control_frag.setImageResource(R.drawable.garbage);
        } else {
            btnClose_Ctrl_Frag.setImageResource(R.drawable.open);
            img_Door_Control_frag.setImageResource(R.drawable.door);
        }

        if(garbage_can.isPower()){
            btnClose_Ctrl_Frag.setEnabled(true);
            btnInSideDoor_Ctrl_Frag.setEnabled(true);
        } else {
            btnClose_Ctrl_Frag.setEnabled(false);
            btnInSideDoor_Ctrl_Frag.setEnabled(false);
        }
    }
}
