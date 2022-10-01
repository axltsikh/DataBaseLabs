package com.example.databasesix;

import static androidx.core.content.FileProvider.getUriForFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import java.sql.Date;
import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String fName="Lab.json";
    ArrayList<Item> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        FileChecker();
        Deserialize();
    }

    public void FileChecker(){
        File ff=new File(super.getFilesDir(),fName);
        File f=new File(Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DOCUMENTS),fName);
        if(f.exists() && ff.exists()){

        }
        else {
            try{
                ff.createNewFile();
                f.createNewFile();
                Toast toast= Toast.makeText(this,"Файлы успешно созданы",Toast.LENGTH_LONG);
                toast.show();
            }
            catch(Exception e){
                Log.d("ExceptionLog",e.getMessage());
            }
        }
    }

    public void allowButtonClick(View view){
        EditText name=findViewById(R.id.FirstName);
        EditText lname=findViewById(R.id.LastName);
        EditText phone=findViewById(R.id.Phone);
        if(name.getText().toString().equals("") || phone.getText().toString().equals("") || lname.getText().toString().equals("")){
            Toast toast=Toast.makeText(this,"Заполните поля",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        Item item=new Item();
        item.FirstName=name.getText().toString();
        item.LastName=lname.getText().toString();
        item.Phone=phone.getText().toString();
        DatePicker datePicker=findViewById(R.id.Bday);
        item.bday=new Date(datePicker.getYear()-1900,datePicker.getMonth(),datePicker.getDayOfMonth());
        items.add(item);
        Clear();
        Serialize();
    }


    public void Serialize(){
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        try {
            String str=gson.toJson(items);
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fName)));
            bw.write(str);
            bw.close();
            bw=new BufferedWriter(new FileWriter(new File(super.getFilesDir(),fName)));
            bw.write(str);
            bw.close();
            Log.d("ExceptionLog",str);

        }
        catch(Exception e){
            Log.d("ExceptionLog",e.getMessage());
        }
    }

    public void Deserialize(){
        GsonBuilder builder=new GsonBuilder();
        Gson gson=builder.create();
        String str;
        try{
            File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fName);
            if(f.length()!=0) {
                BufferedReader bw = new BufferedReader(new FileReader(f));
                str = bw.readLine();
                items = gson.fromJson(str, new TypeToken<ArrayList<Item>>(){}.getType());
            }
        }
        catch(Exception e){
            Log.d("ExceptionLog", e.getMessage());
        }
    }

    public void FindButtonOnClick(View view){
        Intent intent=new Intent(this,FindActivity.class);
        intent.putExtra("items",items);
        startActivity(intent);
    }

    public void Clear(){
        EditText editText=findViewById(R.id.FirstName);
        editText.setText("");
        editText=findViewById(R.id.LastName);
        editText.setText("");
        editText=findViewById(R.id.Phone);
        editText.setText("");
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