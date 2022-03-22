package com.example.manager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.manager.Models.Garbage_Can;
import com.example.manager.R;
import com.example.manager.fragments.Fragment_Control;
import com.example.manager.fragments.Fragment_Information;
import com.example.manager.fragments.Fragment_Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottom_Navigation;
    private ConstraintLayout fragment_layout;
    private Fragment fragment_Status, fragment_Control, fragment_Information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        setUp();
    }

    private void AnhXa(){
        bottom_Navigation = (BottomNavigationView) findViewById(R.id.bottom_Navigation);
        fragment_layout = (ConstraintLayout) findViewById(R.id.fragment_layout);
        bottom_Navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void setUp(){
        fragment_Status = new Fragment_Status();
        fragment_Control = new Fragment_Control(new Garbage_Can("", 0f));
        fragment_Information = new Fragment_Information();
        chang_Menu(fragment_Status);
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