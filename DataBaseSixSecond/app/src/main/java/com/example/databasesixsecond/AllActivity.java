package com.example.databasesixsecond;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        TextView text=findViewById(R.id.List);
        ArrayList<Item> items=(ArrayList<Item>) getIntent().getExtras().get("all");
        for(Item a: items){
            text.append("Имя: " + a.FirstName + "\r\n" +
                    "Фамилия: " + a.LastName + "\r\n" +
                    "Номер: " + a.Phone + "\r\n" +
                    "Дата рождения: " + a.bday + "\r\n");
        }
    }

    public void ReturnOnClick(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}