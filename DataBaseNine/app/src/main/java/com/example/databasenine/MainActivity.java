package com.example.databasenine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ExceptionLog";
    EditText ID;
    EditText F;
    EditText T;
    DataBaseHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new DataBaseHelper(this);
        db=helper.getWritableDatabase();
        ID=findViewById(R.id.IDEditText);
        F=findViewById(R.id.FEditText);
        T=findViewById(R.id.TEditText);
    }
    public void InsertButtonClick(View view){
        ContentValues cv=new ContentValues();
        cv.put("_id",ID.getText().toString());
        cv.put("F",F.getText().toString());
        cv.put("T",T.getText().toString());
        helper.Insert(db,cv);
        Cleaner();
    }
    public void SelectButtonClick(View view){
        try {
            Cursor cursor = helper.Select(db, ID.getText().toString());
            cursor.moveToFirst();
            T.setText(cursor.getString(2));
            F.setText(cursor.getString(1));
        }
        catch(Exception e){
            DataBaseHelper.ToastCreator("Строка не существует");
        }
    }
    public void SelectRawButtonClick(View view){
        try {
            Cursor cursor = helper.SelectRaw(db, ID.getText().toString());
            cursor.moveToFirst();
            T.setText(cursor.getString(2));
            F.setText(cursor.getString(1));
        }
        catch(Exception e){
            DataBaseHelper.ToastCreator("Строка не существует");
        }
    }
    public void UpdateButtonClick(View view){
        ContentValues cv=new ContentValues();
        cv.put("F",F.getText().toString());
        cv.put("T",T.getText().toString());
        helper.Update(db,cv,ID.getText().toString());
    }
    public void DeleteButtonClick(View view){
        helper.Delete(db,ID.getText().toString());
        Cleaner();
    }
    public void Cleaner(){
        T.setText("");
        F.setText("");
        ID.setText("");
    }
}