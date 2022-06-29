package com.example.manager.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.listeners.Listener_for_BackFragment;

public class Fragment_Information extends Fragment {
    private Button btn_specifi_Inf_Frag, btn_HTSU_Inf_Frag,
            btn_AboutUs_Inf_Frag, btn_TermofU_Inf_Frag, btn_LinkServer;
    private Listener_for_BackFragment listener_for_backFragment = new Listener_for_BackFragment() {
        @Override
        public void BackFragment() {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        SetUp(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void SetUp(View view){
        btn_specifi_Inf_Frag = (Button) view.findViewById(R.id.btn_specifi_Inf_Frag);
        btn_HTSU_Inf_Frag = (Button) view.findViewById(R.id.btn_HTSU_Inf_Frag);
        btn_AboutUs_Inf_Frag = (Button) view.findViewById(R.id.btn_AboutUs_Inf_Frag);
        btn_TermofU_Inf_Frag = (Button) view.findViewById(R.id.btn_TermofU_Inf_Frag);
        btn_LinkServer = (Button) view.findViewById(R.id.btn_LinkServer);

        btn_AboutUs_Inf_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentWebview fragmentWebview = new FragmentWebview(listener_for_backFragment, false);
                back_to_AccountsFragment(fragmentWebview);
            }
        });

        btn_TermofU_Inf_Frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentWebview fragmentWebview = new FragmentWebview(listener_for_backFragment, true);
                back_to_AccountsFragment(fragmentWebview);
            }
        });

        btn_LinkServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.fragment_dialog_linkserver);
                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                dialog.setCancelable(true);
                EditText txt_thread =dialog.findViewById(R.id.txt_thread);
                Button btn_cancel_dialog = dialog.findViewById(R.id.btn_cancel_dialog);
                txt_thread.setText(Constant_Values.SERVER_URL);
                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Button btn_change_dialog = dialog.findViewById(R.id.btn_change_dialog);
                btn_change_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Constant_Values.SERVER_URL = txt_thread.getText().toString().trim();
                        Constant_Values.ENB = Constant_Values.SERVER_URL;
                        Constant_Values.TAKE_PIC = Constant_Values.SERVER_URL +"/takepic";
                        Constant_Values.IMAGE_URL = Constant_Values.SERVER_URL +"/static/img/img2.jpg";
                        Constant_Values.IMAGE_URL2 = Constant_Values.SERVER_URL +"/static/img/img.jpg";
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void back_to_AccountsFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.addToBackStack("Account");
        transaction.commit();
    }
}
