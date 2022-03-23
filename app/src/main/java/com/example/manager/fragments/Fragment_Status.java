package com.example.manager.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;

import java.util.ArrayList;

public class Fragment_Status extends Fragment {
    private Button btnReload_Status_Frag, btnTakePhoto_Status_Frag;
    private ImageView img_Descrip_Status_Frag, img_NonRecycle_Status_frag,
            img_Recycle_Status_frag;
    private TextView txtPer_NonRecycle_Status_Frag, txtPer_Recycle_Status_Frag;
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
                        Constant_Values.HTU_Status
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
            }
        });

        btnTakePhoto_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClick_TakePhoto < 2000){
                    return;
                }
                mLastClick_TakePhoto = SystemClock.elapsedRealtime();
                ArrayList<String> link_img = new ArrayList<>();
                link_img.add("https://scr.vn/wp-content/uploads/2020/07/%E1%BA%A2nh-n%E1%BB%81n-thi%C3%AAn-nhi%C3%AAn-ch%E1%BA%A5t-l%C6%B0%E1%BB%A3ng-cao-scaled.jpg");
                Fragment_Dialog_Image fragment_dialog_image = new Fragment_Dialog_Image(link_img);
                fragment_dialog_image.show(getActivity().getSupportFragmentManager(), "fragment");
            }
        });
    }

    public void updateView(){
        float per_Recycle = garbage_can.getVolume_recycle()/Constant_Values.Volume_Machine,
                per_NonRecycle = garbage_can.getVolume_nonRecycle()/Constant_Values.Volume_Machine;
        img_NonRecycle_Status_frag.setImageLevel((int) (10000*per_NonRecycle));
        img_Recycle_Status_frag.setImageLevel((int) (10000*per_Recycle));

        per_NonRecycle = ((float) Math.round(per_NonRecycle*10000)/100);
        per_Recycle = ((float) Math.round(per_Recycle*10000)/100);

        txtPer_NonRecycle_Status_Frag.setText(per_NonRecycle + "%");
        txtPer_Recycle_Status_Frag.setText(per_Recycle + "%");
    }
}
