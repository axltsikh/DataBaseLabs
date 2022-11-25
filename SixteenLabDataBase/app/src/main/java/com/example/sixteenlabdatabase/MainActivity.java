package com.example.sixteenlabdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;

import com.example.sixteenlabdatabase.Fragments.AddFragment;
import com.example.sixteenlabdatabase.Fragments.ContactDetailsFragment;
import com.example.sixteenlabdatabase.Fragments.NameSurnameFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_READ_CONTACTS=1;
    private static boolean READ_CONTACTS_GRANTED =false;
    private static final String TAG = "ExceptionLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int hasReadContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        // если устройство до API 23, устанавливаем разрешение
        if(hasReadContactPermission == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true;
        }
        else{
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
        }
        BottomNavigationView btm=findViewById(R.id.btmMenu);
        btm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.NameSurnameMenuItem:{
                        changeFragment(new NameSurnameFragment());
                        return true;
                    }
                    case R.id.AddMenuItem:{
                        changeFragment(new AddFragment());
                        return true;
                    }
                }
                return false;
            }
        });
        btm.setSelectedItemId(R.id.NameSurnameMenuItem);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true;
            }
        }
//        if(READ_CONTACTS_GRANTED){
//            loadContacts();
//        }
//        else{
//            Toast.makeText(this, "Требуется установить разрешения", Toast.LENGTH_LONG).show();
//        }
    }
    private void changeFragment(Fragment frame){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,frame).commit();
    }
}