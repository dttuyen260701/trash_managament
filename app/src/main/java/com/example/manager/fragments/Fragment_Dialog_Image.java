package com.example.manager.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.manager.Asyntask.Load_task_AsyncTask;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.Methods;
import com.example.manager.listeners.Load_Task_Listener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_Dialog_Image extends AppCompatDialogFragment {
    private ImageView img_Dialog_Frag;
    private ArrayList<Bitmap> list_image;
    private ProgressBar progressBar_Dialog_Image_frag;

    public Fragment_Dialog_Image() {
        list_image = new ArrayList<>();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.fragment_dialog_image, null);
        SetUp(view);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(view);
        dialog.setTitle("In the Chamber");
        dialog.setIcon(R.drawable.ic_baseline_photo_camera_24);
        Dialog dialog_description = dialog.create();
        return dialog_description;
    }

    private void SetUp(View view){
        img_Dialog_Frag = (ImageView) view.findViewById(R.id.img_Dialog_Frag);
        progressBar_Dialog_Image_frag = (ProgressBar) view.findViewById(R.id.progressBar_Dialog_Image_frag);

        if(Constant_Values.garbage_can.isProcessing()){
            Picasso.get().load(Constant_Values.IMAGE_URL2).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(img_Dialog_Frag, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            if(getContext() != null)
                                Toast.makeText(getContext(), "Hệ thống bận", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            loadImage();
        }

    }

    private void loadImage(){
        Load_Task_Listener listener = new Load_Task_Listener() {
            @Override
            public void onPre() {
                if (!Methods.getInstance(getContext()).isNetworkConnected()) {
                    Toast.makeText(getContext(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_Dialog_Image_frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean done, boolean is_pro) {
                if(done){
                    if(is_pro){
                        progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                        if(getContext() != null)
                            Toast.makeText(getContext(), "Hệ thống đang xử lý!", Toast.LENGTH_SHORT).show();
                    } else {
                        Picasso.get().load(Constant_Values.IMAGE_URL).networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(img_Dialog_Frag, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                                        if(getContext() != null)
                                        Toast.makeText(getContext(), "Hệ thống bận", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                else{
                    progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                    if(getContext() != null)
                        Toast.makeText(getContext(), "Lỗi Server", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Load_task_AsyncTask asyncTask = new Load_task_AsyncTask(listener);
        asyncTask.execute(Constant_Values.TAKE_PIC);
    }
}
