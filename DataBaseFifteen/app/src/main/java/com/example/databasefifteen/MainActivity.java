package com.example.databasefifteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.databasefifteen.Fragments.AllGroupsFragment;
import com.example.databasefifteen.Fragments.AllStudentsFragment;
import com.example.databasefifteen.Fragments.SingleGroupFragment;
import com.example.databasefifteen.Fragments.SingleStudentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ExceptionLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView btm=findViewById(R.id.btmMenu);
        btm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.allGroupsMenu:{
                        changeFragment(new AllGroupsFragment());
                        return true;
                    }
                    case R.id.currentGroups:{
                        changeFragment(new SingleGroupFragment());
                        return true;
                    }
                    case R.id.allStudents:{
                        changeFragment(new AllStudentsFragment());
                        return true;
                    }
                    case R.id.currentStudents:{
                        changeFragment(new SingleStudentFragment());
                        return true;
                    }
                }
                return false;
            }
        });
        btm.setSelectedItemId(R.id.allGroupsMenu);
    }

    private void changeFragment(Fragment frame){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,frame).commit();
    }
}