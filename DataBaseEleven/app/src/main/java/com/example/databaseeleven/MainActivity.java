package com.example.databaseeleven;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.databaseeleven.Fragments.fifthQuery;
import com.example.databaseeleven.Fragments.firstQuery;
import com.example.databaseeleven.Fragments.fourthQuery;
import com.example.databaseeleven.Fragments.secondQuery;
import com.example.databaseeleven.Fragments.thirdQuery;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper helper=new DataBaseHelper(this);
        SQLiteDatabase db=helper.getWritableDatabase();
        BottomNavigationView btm=findViewById(R.id.BottomMenu);
        Uri uri= Uri.parse("content://com.demo.provider/GROUPS");
        Cursor cursor=getContentResolver().query(uri, null, null, null, null);
        while(cursor.moveToNext()){
            Log.d("ExceptionLog", "onCreate: " + "asd");
        }
        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.FirstQueryMenu:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new firstQuery()).commit();
                        return true;
                    }
                    case R.id.SecondQueryMenu:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new secondQuery()).commit();
                        return true;
                    }
                    case R.id.ThirdQueryMenu:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new thirdQuery()).commit();
                        return true;
                    }
                    case R.id.FourthQueryMenu:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new fourthQuery()).commit();
                        return true;
                    }
                    case R.id.FifthQueryMenu:{
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new fifthQuery()).commit();
                        return true;
                    }
                }
                return true;
            }
        });
        btm.setSelectedItemId(R.id.FirstQuery);
    }
}