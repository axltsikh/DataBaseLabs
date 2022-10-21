package com.example.databaseeight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Date;
import java.util.ArrayList;
import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class MainActivity extends AppCompatActivity {

    static final String fname = "Lab.xml";
    private static final String TAG = "ExceptionLog";
    BottomNavigationView bottommenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottommenu=findViewById(R.id.bottomNavigation);
        bottommenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.All:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new MainFragment()).commit();
                        return true;
                    case R.id.Category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new Category()).commit();
                        return true;
                    case R.id.thisDateTasks:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new TasksFragment()).commit();
                        return true;
                    case R.id.Xpath:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new Xpath()).commit();
                        return true;
                    case R.id.Xslt:
                        getSupportFragmentManager().beginTransaction().replace(R.id.Frame,new XsltFragment()).commit();
                        return true;
                }
                return false;
            }
        });
        bottommenu.setSelectedItemId(R.id.All);
    }

    public void AddButtonClick(View view){
        int counter=0;
        if(Task.tasks.size()>19){
            Toast toast= Toast.makeText(this,"Максимальное количество задач - 20!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        for(Task a:Task.tasks){
            if(a.NoteDate.equals(Task.calendarDate.toString())){
                counter++;
            }
        }
        if(counter>4){
            Toast toast= Toast.makeText(this,"Максимальное количество задач на одну дату - 5!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        EditText text=Task.rootView.findViewById(R.id.TaskText);
        if(text.getText().toString().equals("")){
            Toast.makeText(this,"Введите название категории!",Toast.LENGTH_SHORT).show();
            return;
        }
        Spinner spinner=findViewById(R.id.categorySpinner);
        Task task=new Task(text.getText().toString(),spinner.getSelectedItem().toString(),Task.calendarDate);
        Task.tasks.add(task);
        Task.Serialize();
        text.setText("");
    }
    public void SaveButtonClickOnClick(View view){
        if(Task.categorys.size()>4){
            Toast.makeText(this,"Максимальное количество категорий - 5!",Toast.LENGTH_SHORT).show();
            return;
        }
        EditText text=findViewById(R.id.CategoryText);
        if(Task.categoryBuffer==null){
            Task.categorys.add(new CategoryClass(text.getText().toString()));
            Task.JsonSerialzie();
            Category.Filler();
            Task.JsonDeserialize();
            return;
        }
        for(CategoryClass a:Task.categorys){
            if(a.Id==Task.categoryBuffer.Id) {
                Task.categorys.remove(a);
                Task.categorys.add(new CategoryClass(text.getText().toString()));
                Task.JsonSerialzie();
                Category.Filler();
                Task.JsonDeserialize();
                return;
            }
        }
        if(Task.categorys.size()>4){
            Toast toast=Toast.makeText(this,"Максимальное количество категорий - 5!",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Task.categorys.add(new CategoryClass(text.getText().toString()));
        Task.JsonSerialzie();
        Task.JsonDeserialize();
        Category.Filler();
    }
    public void DeleteButtonOnClick(View view){
        EditText text=findViewById(R.id.CategoryText);
        try {
            Task.categorys.remove(Task.categoryBuffer);
            Task.JsonSerialzie();
            Category.Filler();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "DeleteButtonOnClick: " + e.getMessage());
        }
        text.setText("");
    }
    public void SaveTaskOnCLick(View view){
//        Task.tasks.remove(Task.taskBuffer);
        EditText text=findViewById(R.id.OneTaskText);
        String category=Task.taskBuffer.Category;
        String date=Task.taskBuffer.NoteDate;
        Task.tasks.remove(Task.taskBuffer);
        Task buffer=new Task();
        buffer.NoteText=text.getText().toString();
        buffer.Category=category;
        buffer.NoteDate=date;
        Task.tasks.add(buffer);
        Task.Serialize();
        Task.Deserialize();
        TasksFragment.OutputTasks();
    }
    public void DeleteTaskOnClick(View view){
        Task.tasks.remove(Task.taskBuffer);
        EditText text=findViewById(R.id.OneTaskText);
        text.setText("");
        Task.Serialize();
        Task.Deserialize();
        TasksFragment.OutputTasks();
    }
}