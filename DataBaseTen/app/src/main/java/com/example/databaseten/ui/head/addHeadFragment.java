package com.example.databaseten.ui.head;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.databaseten.DataBaseHelper;
import com.example.databaseten.Group;
import com.example.databaseten.R;
import com.example.databaseten.Student;


public class addHeadFragment extends Fragment {

    final String TAG="ExceptionLog";
    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_add_head, container, false);
        Filler();
        return rootView;
    }
    public void Filler(){
        helper=new DataBaseHelper(getContext());
        db=helper.getWritableDatabase();
        Spinner spinner=rootView.findViewById(R.id.HeadGroupSpinner);
        ArrayAdapter<Group> adapter=new ArrayAdapter<Group>(getContext(), android.R.layout.simple_spinner_dropdown_item,Group.groups);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                StudentFiller(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button changebutton=rootView.findViewById(R.id.HeadSaveButton);
        changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClick();
            }
        });
    }
    private void StudentFiller(int i){
        Group groupBuffer=Group.groups.get(i);
        helper.SelectStudents(db,groupBuffer.IDGroup);
        Spinner spinner=rootView.findViewById(R.id.HeadStudentSpinner);
        ArrayAdapter<Student> studentArrayAdapter=new ArrayAdapter<Student>(getContext(), android.R.layout.simple_spinner_dropdown_item,Student.students);
        spinner.setAdapter(studentArrayAdapter);
        if(groupBuffer.HeadID!=0){
            int t=0;
            for(Student a:Student.students){
                t++;
                if(a.IDStudent==groupBuffer.HeadID){
                    spinner.setSelection(t-1);
                }
            }
        }
    }
    private void addClick(){
        Spinner studentspinner=rootView.findViewById(R.id.HeadStudentSpinner);
        Student buffer=Student.students.get(studentspinner.getSelectedItemPosition());
        studentspinner=rootView.findViewById(R.id.HeadGroupSpinner);
        Group group=Group.groups.get(studentspinner.getSelectedItemPosition());
        Log.d(TAG, "addClick: " + buffer.IDStudent);
        Log.d(TAG, "addClick: " + group.IDGroup);
        helper.UpdateGroup(db,buffer.IDStudent,group.IDGroup);

    }

}