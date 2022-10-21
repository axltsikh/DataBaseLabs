package com.example.databaseten.ui.student;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.databaseten.DataBaseHelper;
import com.example.databaseten.Group;
import com.example.databaseten.R;
import com.example.databaseten.Student;
import com.example.databaseten.databinding.FragmentAddstudentBinding;


public class AddStudentFragment extends Fragment {

    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;
    Student student;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentAddstudentBinding binding= FragmentAddstudentBinding.inflate(getActivity().getLayoutInflater());
        rootView=binding.getRoot();
        student=new Student();
        binding.setStudent(student);
        Button button=rootView.findViewById(R.id.addStudentButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudentClick();
            }
        });
        Spinner spinner=rootView.findViewById(R.id.GroupSpinnerStudent);
        ArrayAdapter<Group> adapter=new ArrayAdapter<Group>(getContext(), android.R.layout.simple_spinner_dropdown_item,Group.groups);
        spinner.setAdapter(adapter);
        return rootView;
    }
    public void addStudentClick(){
        Spinner spinner=rootView.findViewById(R.id.GroupSpinnerStudent);
        if(student.Name==null || spinner.getSelectedItemPosition()==-1){
            Toast.makeText(getContext(),"Заполните поля!",Toast.LENGTH_SHORT).show();
            return;
        }
        student.IDGroup=Group.groups.get(spinner.getSelectedItemPosition()).IDGroup;
        helper=new DataBaseHelper(getContext());
        db=helper.getWritableDatabase();
        helper.InsertStudent(db,student);
        Toast.makeText(getContext(),"Студен добавлен!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}