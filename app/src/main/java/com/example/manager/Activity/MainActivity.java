package com.example.manager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.Utils.Constant_Values;
import com.example.manager.fragments.Fragment_Control;
import com.example.manager.fragments.Fragment_Information;
import com.example.manager.fragments.Fragment_Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottom_Navigation;
    private ConstraintLayout fragment_layout;
    private Fragment fragment_Status, fragment_Control, fragment_Information;
    private Garbage_Can garbage_can;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        garbage_can = new Garbage_Can("", 100f, 100f);
        AnhXa();
        setUp();
    }

    private void AnhXa(){
        bottom_Navigation = (BottomNavigationView) findViewById(R.id.bottom_Navigation);
        fragment_layout = (ConstraintLayout) findViewById(R.id.fragment_layout);
        bottom_Navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void setUp(){
        fragment_Status = new Fragment_Status(garbage_can);
        fragment_Control = new Fragment_Control(garbage_can);
        fragment_Information = new Fragment_Information();
        chang_Menu(fragment_Status);
        CountDownTimer countDownTimer = new CountDownTimer(60*60*60,
                Constant_Values.TIME_TO_UPDATE_GARBAGE*60*1000) {
            @Override
            public void onTick(long l) {
                if(garbage_can.getVolume_nonRecycle() >= 100)
                    garbage_can.setVolume_nonRecycle(0);
                if(garbage_can.getVolume_recycle() >= 100)
                    garbage_can.setVolume_recycle(0);
                garbage_can.setVolume_nonRecycle(garbage_can.getVolume_nonRecycle() + 10);
                garbage_can.setVolume_recycle(garbage_can.getVolume_recycle() + 10);
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
}