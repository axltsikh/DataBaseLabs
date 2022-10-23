package com.example.databaseseven;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
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
import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Note note;
    ArrayList<Note> notes=new ArrayList<>();
    static final String fileName="File.json";
    Date calendarDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileChecker();
        CalendarView calendar=findViewById(R.id.Calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Log.d("ExceptionLog", "onSelectedDayChange: " + String.valueOf(year) + "\n"
                        + String.valueOf(month) + "\n" + String.valueOf(day) + "\n");
                calendarDate=new Date(year,month,day);
                Log.d("ExceptionLog", "onSelectedDayChange: " + calendarDate);
                Finder();
            }
        });
        calendar.setDate(calendar.getDate());
    }

    public void Finder(){
        EditText text=findViewById(R.id.NoteText);
        Button save=findViewById(R.id.AddButton);
        Button deleteButton=findViewById(R.id.DeleteButton);
        Log.d("ExceptionLog", "onSelectedDayChange: " + calendarDate);
        for(Note i: notes){
            if(i.NoteDate.equals(calendarDate)){
                Log.d("ExceptionLog", "onSelectedDayChange: " + i.NoteText);
                text.setText(i.NoteText);
                save.setText(R.string.save);
                note=i;
                deleteButton.setVisibility(View.VISIBLE);
                break;
            }
            else{
                note=null;
                save.setText(R.string.add);
                deleteButton.setVisibility(View.INVISIBLE);
                text.setText("");
            }
        }
    }

    public void addButtonOnClick(View view){
        Note noteBuffer=new Note();
        EditText text=findViewById(R.id.NoteText);
        if(text.getText().toString().equals("")) {
            Toast toast= Toast.makeText(this,"Заметка не может быть пустой!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if(note!=null){
            notes.remove(note);
            noteBuffer.NoteDate=calendarDate;
            noteBuffer.NoteText=text.getText().toString();
            notes.add(noteBuffer);
            Serialize();
        }
        else{
            if(notes.size()>9){
                Toast toast= Toast.makeText(this,"Максимальное количество замеотк - 10",Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            note=new Note(text.getText().toString(),calendarDate);
            notes.add(note);
            Serialize();
        }
        Finder();
    }
    public void DeleteButtonOnClick(View view){
        notes.remove(note);
        Serialize();
        EditText text=findViewById(R.id.NoteText);
        Button save=findViewById(R.id.AddButton);
        Button deleteButton=findViewById(R.id.DeleteButton);
        save.setText(R.string.add);
        deleteButton.setVisibility(View.INVISIBLE);
        text.setText("");
        Finder();
    }
    public void FileChecker(){
        File f=new File(super.getFilesDir(),fileName);
        if(f.exists()){
            if(f.length()!=0){
                try {
                    BufferedReader bw = new BufferedReader(new FileReader(f));
                    Gson gson = new GsonBuilder().create();
                    notes = gson.fromJson(bw.readLine(), new TypeToken<ArrayList<Note>>() {
                    }.getType());
                }
                catch(Exception e){
                    Log.d("ExceptionLog", "FileChecker: " + e.getMessage());
                }
            }
        }
        else{
            try{
                f.createNewFile();
            }
            catch(Exception e){
                Log.d("ExceptionLog", "FileChecker: " + e.getMessage());
            }
        }
    }
    public void Serialize(){
        Gson gson=new GsonBuilder().create();
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(new File(super.getFilesDir(),fileName)));
            bw.write(gson.toJson(notes));
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "addButtonOnClick: " + e.getMessage());
        }
    }
}