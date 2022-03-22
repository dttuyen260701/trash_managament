package com.example.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;

import java.util.ArrayList;

public class Fragment_Status extends Fragment {
    private Button btnReload_Status_Frag, btnTakePhoto_Status_Frag;
    private ImageView img_Descrip_Status_Frag;

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

            }
        });

        btnTakePhoto_Status_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> link_img = new ArrayList<>();
                link_img.add("https://scr.vn/wp-content/uploads/2020/07/%E1%BA%A2nh-n%E1%BB%81n-thi%C3%AAn-nhi%C3%AAn-ch%E1%BA%A5t-l%C6%B0%E1%BB%A3ng-cao-scaled.jpg");
                Fragment_Dialog_Image fragment_dialog_image = new Fragment_Dialog_Image(link_img);
                fragment_dialog_image.show(getActivity().getSupportFragmentManager(), "fragment");
            }
        });
    }
}
