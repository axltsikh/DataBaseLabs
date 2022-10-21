package com.example.databaseeight;

import static com.example.databaseeight.Task.calendarDate;
import static com.example.databaseeight.Task.tasks;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import java.io.File;
import java.sql.Date;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Task.rootView=inflater.inflate(R.layout.fragment_main, container, false);
        Init();
        // Inflate the layout for this fragment
        return Task.rootView;
    }
    public void Init(){
        Task.JsonDeserialize();
        ArrayAdapter<CategoryClass> adapter=new ArrayAdapter<>(Task.rootView.getContext(), android.R.layout.simple_spinner_dropdown_item,Task.categorys);
        Spinner spinner=Task.rootView.findViewById(R.id.categorySpinner);
        spinner.setAdapter(adapter);
        CalendarView calendar = Task.rootView.findViewById(R.id.Calendar);
        Task.calendarDate=new Date(calendar.getDate());
        if(calendar==null){
            Log.d("ExceptionLog", "Init: " + "null");
        }
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Task.calendarDate = new Date(year-1900, month, day);
            }
        });
        calendar.setDate(calendar.getDate());
        FileChecker();
    }

    public void FileChecker(){
        File f=new File(Task.rootView.getContext().getFilesDir(),"Tasks.xml");
        if(f.exists()){
            Task.Deserialize();
            for(Task a: tasks){
                Log.d("ExceptionLog", "FileChecker: " + a.NoteText);
                Log.d("ExceptionLog", "FileChecker: " + calendarDate);
            }
        }
        else{
            try {
                f.createNewFile();
            }
            catch(Exception e){
                Log.d("ExceptionLog", "FileChecker: " + e.getMessage());
            }
        }
    }
}