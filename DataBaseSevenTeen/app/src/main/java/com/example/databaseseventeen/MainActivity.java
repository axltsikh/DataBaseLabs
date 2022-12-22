package com.example.databaseseventeen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.databaseseventeen.Fragments.AddFragment;
import com.example.databaseseventeen.Fragments.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView btm=findViewById(R.id.bottommenu);
        btm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.choose:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListFragment()).commit();
                        return true;
                    case R.id.add:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddFragment()).commit();
                        return true;
                }
                return false;
            }
        });
        btm.setSelectedItemId(R.id.choose);
    }
}