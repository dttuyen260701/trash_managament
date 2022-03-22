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

import com.example.manager.Asyntask.Load_Image_Asynctask;
import com.example.manager.R;
import com.example.manager.Utils.Methods;
import com.example.manager.listeners.Load_Img_Listener;

import java.util.ArrayList;

public class Fragment_Dialog_Image extends AppCompatDialogFragment {
    private ImageView img_Dialog_Frag;
    private ArrayList<String> link_img;
    private ArrayList<Bitmap> list_image;
    private ProgressBar progressBar_Dialog_Image_frag;

    public Fragment_Dialog_Image(ArrayList<String> link_img) {
        this.link_img = link_img;
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

        loadImage();

    }

    private void loadImage(){
        list_image.clear();
        Load_Img_Listener listener = new Load_Img_Listener() {
            @Override
            public void onPre() {
                if (!Methods.getInstance(getContext()).isNetworkConnected()) {
                    Toast.makeText(getContext(), "Vui lòng kết nối internet", Toast.LENGTH_SHORT).show();
                }
                progressBar_Dialog_Image_frag.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(boolean isSuccess, ArrayList<Bitmap> list_result) {
                progressBar_Dialog_Image_frag.setVisibility(View.GONE);
                if(isSuccess){
                    img_Dialog_Frag.setImageBitmap(list_result.get(0));
                }
                else
                    Toast.makeText(getContext(), "Lỗi Server", Toast.LENGTH_SHORT).show();
            }
        };

        Load_Image_Asynctask asynctask = new Load_Image_Asynctask(link_img, listener);
        asynctask.execute();
    }
}
