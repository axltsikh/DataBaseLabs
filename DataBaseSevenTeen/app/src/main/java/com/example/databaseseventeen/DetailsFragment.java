package com.example.databaseseventeen;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.databaseseventeen.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {

    private Person person;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetailsBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false);
        rootView =  binding.getRoot();
        if(getArguments()!=null){
            person=(Person)getArguments().getSerializable("Person");
        }
        binding.setPerson(person);
        Filler();
        return rootView;
    }
    private void Filler(){
        DataBase db=new DataBase(getContext());
        EditText ageText=rootView.findViewById(R.id.AgeEditText);
        ageText.setText(String.valueOf(person.age));
        Button button=rootView.findViewById(R.id.UpdateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.Update(person);
            }
        });
        button=rootView.findViewById(R.id.DeleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.Delete(person);
            }
        });
        button=rootView.findViewById(R.id.UpdateProcedureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.UpdateProcedure(person);
            }
        });

    }
}