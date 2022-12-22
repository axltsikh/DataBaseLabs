package com.example.databaseseventeen.Fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.databaseseventeen.DataBase;
import com.example.databaseseventeen.Person;
import com.example.databaseseventeen.R;
import com.example.databaseseventeen.databinding.FragmentAddBinding;
import com.example.databaseseventeen.databinding.FragmentDetailsBinding;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    ArrayList<Person> persons=new ArrayList<>();
    Person person=new Person();
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAddBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add,container,false);

        rootView =  binding.getRoot();
        Filler();
        return rootView;
    }
    public void Filler(){
        DataBase db=new DataBase(rootView.getContext());
        Button button=rootView.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText age=rootView.findViewById(R.id.ageText);
                EditText first=rootView.findViewById(R.id.FirstNameText);
                EditText second=rootView.findViewById(R.id.SecondNameText);
                person.age= Integer.valueOf(age.getText().toString());
                person.FirstName=first.getText().toString();
                person.SecondName=second.getText().toString();
                persons.add(person);
                person=new Person();
            }
        });
        button=rootView.findViewById(R.id.addAllButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText age=rootView.findViewById(R.id.ageText);
                db.BatchInsert(persons);
            }
        });
        button=rootView.findViewById(R.id.functionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView age=rootView.findViewById(R.id.functionResultText);
                int res = db.callFunction();
                age.setText("Количество человек: " + res);
            }
        });
    }
}