package com.example.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;

public class Fragment_Control extends Fragment {

    private ImageButton btnClose_Ctrl_Frag, btnLeftDoor_Ctrl_Frag,
            btnRightDoor_Ctrl_Frag, btnPower_Ctrl_Frag;
    private ImageView img_Descrip_Control_Frag;
    private Garbage_Can garbage_can;

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
        btnLeftDoor_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnLeftDoor_Ctrl_Frag);
        btnRightDoor_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnRightDoor_Ctrl_Frag);
        btnPower_Ctrl_Frag = (ImageButton) view.findViewById(R.id.btnPower_Ctrl_Frag);
        img_Descrip_Control_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Control_Frag);

        if(garbage_can.isPower()){
            isPowerOn();
        } else {
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
                if(garbage_can.isDoor()){
                    garbage_can.setDoor(false);
                    btnClose_Ctrl_Frag.setImageResource(R.drawable.open);
                } else{
                    garbage_can.setDoor(true);
                    btnClose_Ctrl_Frag.setImageResource(R.drawable.close);
                }
            }
        });

        btnLeftDoor_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(garbage_can.isLeftDoor()){
                    garbage_can.setLeftDoor(false);
                    btnLeftDoor_Ctrl_Frag.setImageResource(R.drawable.up);
                } else {
                    garbage_can.setLeftDoor(true);
                    btnLeftDoor_Ctrl_Frag.setImageResource(R.drawable.down);
                }
            }
        });

        btnRightDoor_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(garbage_can.isRightDoor()){
                    garbage_can.setRightDoor(false);
                    btnRightDoor_Ctrl_Frag.setImageResource(R.drawable.up);
                } else {
                    garbage_can.setRightDoor(true);
                    btnRightDoor_Ctrl_Frag.setImageResource(R.drawable.down);
                }
            }
        });

        btnPower_Ctrl_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(garbage_can.isPower()){
                    garbage_can.setPower(false);
                    btnPower_Ctrl_Frag.setImageResource(R.drawable.power_on);
                    isPowerOff();
                } else {
                    garbage_can.setPower(true);
                    btnPower_Ctrl_Frag.setImageResource(R.drawable.power_off);
                    isPowerOn();
                }
            }
        });
    }

    private void isPowerOff(){
        //dong cua k nhan rac
        garbage_can.setDoor(false);
        btnClose_Ctrl_Frag.setImageResource(R.drawable.open);
        btnClose_Ctrl_Frag.setEnabled(false);
        btnLeftDoor_Ctrl_Frag.setEnabled(false);
        btnRightDoor_Ctrl_Frag.setEnabled(false);
    }

    private void isPowerOn(){
        //mo cua thung rac
        garbage_can.setDoor(true);
        btnClose_Ctrl_Frag.setImageResource(R.drawable.close);
        btnClose_Ctrl_Frag.setEnabled(true);
        btnLeftDoor_Ctrl_Frag.setEnabled(true);
        btnRightDoor_Ctrl_Frag.setEnabled(true);
    }
}
