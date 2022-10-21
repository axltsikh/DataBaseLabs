package com.example.databaseten.ui.group;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingComponent;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.databaseten.DataBaseHelper;
import com.example.databaseten.Group;
import com.example.databaseten.R;
import com.example.databaseten.databinding.FragmentAddgroupBinding;

public class AddGroupFragment extends Fragment {

    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;
    Group group=new Group();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentAddgroupBinding binding= FragmentAddgroupBinding.inflate(getActivity().getLayoutInflater());
        rootView=binding.getRoot();
        binding.setGroup(group);
        Button button=rootView.findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddButtonClick();
            }
        });
        return rootView;
    }

    public void AddButtonClick(){
        EditText text=rootView.findViewById(R.id.GroupCourse);
        if(text.getText().equals("") || group.GroupName==null || group.Faculty==null){
            Toast.makeText(getContext(),"Заполните все поля!",Toast.LENGTH_SHORT).show();
            return;
        }
        group.Course=Integer.parseInt(text.getText().toString());
        helper=new DataBaseHelper(getContext());
        db=helper.getWritableDatabase();
        helper.InsertGroup(db,group);
        Toast.makeText(getContext(),"Группа добавлен!",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}