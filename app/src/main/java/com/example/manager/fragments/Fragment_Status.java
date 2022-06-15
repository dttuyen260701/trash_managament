package com.example.manager.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.manager.Activity.MainActivity;
import com.example.manager.Asyntask.Load_Information_Asynctask;
import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.Methods;
import com.example.manager.listeners.Load_Data_Listener;

public class Fragment_Status extends Fragment {
    private Button btnReload_Status_Frag, btnTakePhoto_Status_Frag;
    private ImageView img_Descrip_Status_Frag, img_NonRecycle_Status_frag,
            img_Recycle_Status_frag;
    private TextView txtPer_NonRecycle_Status_Frag, txtPer_Recycle_Status_Frag,
            txt_pro_status, txt_enb_status;
    private Garbage_Can garbage_can;
    private long mLastClick_Reload = 0, mLastClick_TakePhoto = 0;

    public Fragment_Status(Garbage_Can garbage_can) {
        this.garbage_can = garbage_can;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        txt_pro_status = (TextView) view.findViewById(R.id.txt_pro_status);
        txt_enb_status = (TextView) view.findViewById(R.id.txt_enb_status);
        btnReload_Status_Frag = (Button) view.findViewById(R.id.btnReload_Status_Frag);
        btnTakePhoto_Status_Frag = (Button) view.findViewById(R.id.btnTakePhoto_Status_Frag);
        img_Descrip_Status_Frag = (ImageView) view.findViewById(R.id.img_Descrip_Status_Frag);
        img_NonRecycle_Status_frag = (ImageView) view.findViewById(R.id.img_NonRecycle_Status_frag);
        img_Recycle_Status_frag = (ImageView) view.findViewById(R.id.img_Recycle_Status_frag);
        txtPer_NonRecycle_Status_Frag = (TextView) view.findViewById(R.id.txtPer_NonRecycle_Status_Frag);
        txtPer_Recycle_Status_Frag = (TextView) view.findViewById(R.id.txtPer_Recycle_Status_Frag);

        updateView();

        img_Descrip_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_HTU_Dialog fragment_htu_dialog = new Fragment_HTU_Dialog(
                        Constant_Values.HTU_STATUS
                );
                fragment_htu_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        btnReload_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_Reload < 2000){
                    return;
                }
                mLastClick_Reload = SystemClock.elapsedRealtime();
                Load_Data_Listener listener = new Load_Data_Listener() {
                    @Override
                    public void onPre() {
                        if(!Methods.getInstance(getActivity()).isNetworkConnected()){
                            Toast.makeText(getActivity(), "Vui lòng kết nối Internet", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onEnd(boolean isSuccess, Garbage_Can garbage_can2) {
                        Constant_Values.garbage_can = garbage_can2;
                        updateView();
                    }
                };
                Load_Information_Asynctask asynctask = new Load_Information_Asynctask(listener);
                asynctask.execute();
            }
        });

        btnTakePhoto_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_TakePhoto < 2000){
                    return;
                }
                mLastClick_TakePhoto = SystemClock.elapsedRealtime();
                Fragment_Dialog_Image fragment_dialog_image = new Fragment_Dialog_Image();
                fragment_dialog_image.show(getActivity().getSupportFragmentManager(), "fragment");
            }
        });
    }

    public void updateView(){
        float per_Recycle = garbage_can.getVolume_recycle()/Constant_Values.Volume_Machine,
                per_NonRecycle = garbage_can.getVolume_nonRecycle()/Constant_Values.Volume_Machine;
        img_NonRecycle_Status_frag.setImageLevel((int) (10000*per_NonRecycle));
        img_Recycle_Status_frag.setImageLevel((int) (10000*per_Recycle));
        String enb = (garbage_can.isEnb()) ? "Garbage can: is Enable" : "Garbage can: is Disable";
        txt_enb_status.setText(enb);
        String pro = (garbage_can.isEnb()) ? "Processor: is Processing" : "Processor: is not Processing";
        txt_pro_status.setText(pro);
        per_NonRecycle = ((float) Math.round(per_NonRecycle*10000)/100);
        per_Recycle = ((float) Math.round(per_Recycle*10000)/100);

        txtPer_NonRecycle_Status_Frag.setText(per_NonRecycle + "%");
        txtPer_Recycle_Status_Frag.setText(per_Recycle + "%");
    }
}
