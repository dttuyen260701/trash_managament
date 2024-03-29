package com.example.manager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.manager.Asyntask.Load_Information_Asynctask;
import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.Utils.Methods;
import com.example.manager.fragments.Fragment_Control;
import com.example.manager.fragments.Fragment_Information;
import com.example.manager.fragments.Fragment_Status;
import com.example.manager.listeners.Load_Data_Listener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Notification";
    private static final int NOTIFICATION_ID = 121;
    private static BottomNavigationView bottom_Navigation;
    private Fragment fragment_Status, fragment_Control, fragment_Information;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Constant_Values.garbage_can == null)
            Constant_Values.garbage_can = new Garbage_Can(false, true, 0f, 0f);
        AnhXa();
        setUp();
    }

    private void AnhXa(){
        bottom_Navigation = (BottomNavigationView) findViewById(R.id.bottom_Navigation);
        bottom_Navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void setUp(){
        createNotification();
        fragment_Status = new Fragment_Status();
        fragment_Control = new Fragment_Control();
        fragment_Information = new Fragment_Information();
        chang_Menu(fragment_Status);
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(60*60*60,
                Constant_Values.TIME_TO_UPDATE_GARBAGE*1000) {
            @Override
            public void onTick(long l) {
                Load_Data_Listener listener = new Load_Data_Listener() {
                    @Override
                    public void onPre() {
                        if(!Methods.getInstance(MainActivity.this).isNetworkConnected()){
                            Toast.makeText(MainActivity.this, "Vui lòng kết nối Internet", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onEnd(boolean isSuccess, Garbage_Can garbage_can2) {
                        if(isSuccess){
                            Constant_Values.garbage_can = garbage_can2;
                            switch (bottom_Navigation.getSelectedItemId()){
                                case R.id.bottom_Status:
                                    ((Fragment_Status) fragment_Status).updateView();
                                    break;
                                case R.id.bottom_Control:
                                    ((Fragment_Control) fragment_Control).updateView();
                                    break;
                                default:
                                    break;
                            }
                            createNotification();
                        } else {
//                            Toast.makeText(MainActivity.this, "Server is busy, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Load_Information_Asynctask asynctask = new Load_Information_Asynctask(listener);
                asynctask.execute();
            }
            @Override
            public void onFinish() {
                this.start();
            }
        };
        countDownTimer.start();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.bottom_Status:
                            chang_Menu(fragment_Status);
                            return true;
                        case R.id.bottom_Control:
                            chang_Menu(fragment_Control);
                            return true;
                        case R.id.bottom_Information:
                            chang_Menu(fragment_Information);
                            return true;
                    }
                    return false;
                }
    };

    private void chang_Menu(Fragment fragment){
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            for(int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i){
                getSupportFragmentManager().popBackStack();
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_layout, fragment);
        transaction.commit();
    }

    private void createNotification(){
        check_SDK_Notification();

        float per_Recycle = (float) Math.abs(Constant_Values.garbage_can.getVolume_recycle())/Constant_Values.Volume_Machine,
                per_NonRecycle = (float) Math.abs(Constant_Values.garbage_can.getVolume_nonRecycle())/Constant_Values.Volume_Machine;


        RemoteViews notification_layout =
                new RemoteViews(getPackageName(), R.layout.notification_view);

        notification_layout.setImageViewResource(R.id.img_NonRecycle_noti_view, R.drawable.clip_image);
        notification_layout.setInt(R.id.img_NonRecycle_noti_view, "setImageLevel", (int) (10000*per_NonRecycle));
        notification_layout.setImageViewResource(R.id.img_Recycle_noti_view, R.drawable.clip_image2);
        notification_layout.setInt(R.id.img_Recycle_noti_view, "setImageLevel", (int) (10000*per_Recycle));

        per_NonRecycle = ((float) Math.round(per_NonRecycle*10000)/100);
        per_Recycle = ((float) Math.round(per_Recycle*10000)/100);
        notification_layout.setTextViewText(R.id.txtPer_NonRecycle_noti_view, per_NonRecycle + "%");
        notification_layout.setTextViewText(R.id.txtPer_Recycle_noti_view, per_Recycle + "%");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        //set quay lại màn hình chính dùng set Action + add  Category
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // Create the PendingIntent, FLAG_UPDATE_CURRENT: mở được nheièu lần, FLAG_ONE_SHOT: mở được 1 lần
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.iconstrash80);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setCustomBigContentView(notification_layout).
                setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setContentIntent(pendingIntent);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void check_SDK_Notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //channel cho phiên bản mới
            CharSequence name = "Notification";
            String des = "This is my Personal Notification";
            int important = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel( CHANNEL_ID, name, important);
            channel.setDescription(des);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}