package com.example.databaseeight;

import static com.example.databaseeight.Task.calendarDate;
import static com.example.databaseeight.Task.tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    static TextView Date;
    public TasksFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Task.rootView=inflater.inflate(R.layout.fragment_tasks,container,false);
        for(Task a:tasks){
            Log.d("ExceptionLog", "onCreateView: " + a.NoteDate);
        }
        OutputTasks();
        return Task.rootView;
    }
    public static void OutputTasks(){
        ArrayList<Task> buffer=new ArrayList<>();
        for(Task a:tasks){
            if(a.NoteDate.equals(Task.calendarDate.toString())){
                buffer.add(a);
            }
        }
        Date=Task.rootView.findViewById(R.id.Date);
        Date.setText(Task.calendarDate.toString());
        ListView view=Task.rootView.findViewById(R.id.TasksList);
        ArrayAdapter<Task> adapter=new ArrayAdapter(Task.rootView.getContext(), android.R.layout.simple_expandable_list_item_1, buffer);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task.taskBuffer= tasks.get(i);
                EditText text=Task.rootView.findViewById(R.id.OneTaskText);
                text.setText(Task.taskBuffer.NoteText);
            }
        });
    }

}