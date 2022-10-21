package com.example.databaseten.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.databaseten.DataBaseHelper;
import com.example.databaseten.Group;
import com.example.databaseten.R;
import com.example.databaseten.Student;
import com.example.databaseten.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    final String TAG="ExceptionLog";
    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_home, container, false);
        Filler();
        return rootView;
    }
    public void Filler(){
        helper=new DataBaseHelper(getContext());
        db=helper.getWritableDatabase();
        helper.SelectGroups(db);
        Spinner spinner=rootView.findViewById(R.id.groupSpinnerMain);
        ArrayAdapter<Group> adapter=new ArrayAdapter<Group>(getContext(), android.R.layout.simple_spinner_dropdown_item,Group.groups);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                helper.SelectStudents(db,Group.groups.get(i).IDGroup);
                ListView list=rootView.findViewById(R.id.ListViewMain);
                ArrayAdapter<Student> studentArrayAdapter=new ArrayAdapter<Student>(getContext(), android.R.layout.simple_list_item_1,Student.students);
                list.setAdapter(studentArrayAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}