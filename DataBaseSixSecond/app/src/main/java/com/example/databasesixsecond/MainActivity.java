package com.example.databasesixsecond;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final String fName="Lab.json";
    ArrayList<Item> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
        FileGetter();
        TextView text=findViewById(R.id.Output);
        TextView textt=findViewById(R.id.textView);
        Item item=items.get(2);
        for(Item a: items) {
            textt.append("День объекта: " + String.valueOf(a.bday.getDay()) + "\r\n" +
                    "Месяц объекта: " + String.valueOf(a.bday.getMonth()) + "\r\n" +
                    "Год объекта: " + String.valueOf(a.bday.getYear()) + "\r\n");
        }
        DatePicker date=findViewById(R.id.datePicker);
        date.init(date.getYear(), date.getMonth(), date.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                TextView text=findViewById(R.id.Output);
                text.setText("");
                for(Item a: items){
                    if(a.bday.equals(new Date(i-1900,i1,i2))){
                        text.append("Имя: " + a.FirstName + "\r\n" +
                                "Фамилия: " + a.LastName + "\r\n" +
                                "Номер: " + a.Phone + "\r\n" +
                                "Дата рождения: " + a.bday + "\r\n");
                    }
                }
            }
        });
    }
    public void FileGetter(){
        try {
            GsonBuilder builder=new GsonBuilder();
            Gson gson=builder.create();
            File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fName);
            if(f.length()!=0) {
                BufferedReader bw = new BufferedReader(new FileReader(f));
                String str = bw.readLine();
                items = gson.fromJson(str, new TypeToken<ArrayList<Item>>(){}.getType());
            }
        }
        catch(IOException e){
            Log.d("ExceptionLog",e.getMessage());
        }
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}