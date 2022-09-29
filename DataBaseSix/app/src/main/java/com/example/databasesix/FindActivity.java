package com.example.databasesix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FindActivity extends AppCompatActivity {

    ArrayList<Item> items=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find2);
        items=(ArrayList<Item>)getIntent().getExtras().getSerializable("items");
        EditText editText=findViewById(R.id.FirstLastName);
        editText.addTextChangedListener(watcher);
    }
    private final TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Pattern pattern=Pattern.compile(charSequence.toString());
            Matcher matcher;
            TextView textView=findViewById(R.id.output);
            textView.setText("");
            for(Item a: items){
                if(pattern.matcher(a.FirstName).find() || pattern.matcher(a.LastName).find()){
                    textView.append("Имя: " + a.FirstName + "\r\n");
                    textView.append("Фамилия: " + a.LastName + "\r\n");
                    textView.append("Телефон: " + a.Phone + "\r\n");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    public void ReturnButtonOnClick(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}