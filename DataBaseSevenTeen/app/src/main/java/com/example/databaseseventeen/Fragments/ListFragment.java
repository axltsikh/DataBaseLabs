package com.example.databaseseventeen.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.databaseseventeen.DataBase;
import com.example.databaseseventeen.DetailsFragment;
import com.example.databaseseventeen.Person;
import com.example.databaseseventeen.R;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    public static ArrayList<Person> persons=new ArrayList<>();
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_list, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        DataBase db=new DataBase(rootView.getContext());
        Button button=rootView.findViewById(R.id.SimpleChoose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView list=rootView.findViewById(R.id.chooseList);
                persons=db.getAll();
                ArrayAdapter<Person> adapter=new ArrayAdapter<Person>(rootView.getContext(), android.R.layout.simple_list_item_1,persons);
                list.setAdapter(adapter);
            }
        });
        button=rootView.findViewById(R.id.PreparedChoose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text=rootView.findViewById(R.id.MinAgeEditText);
                ListView list=rootView.findViewById(R.id.chooseList);
                persons=db.getAllPreparedStatement(Integer.valueOf(text.getText().toString()));
                ArrayAdapter<Person> adapter=new ArrayAdapter<Person>(rootView.getContext(), android.R.layout.simple_list_item_1,persons);
                list.setAdapter(adapter);
            }
        });
        ListView list=rootView.findViewById(R.id.chooseList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment=new DetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("Person",persons.get(i));
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            }
        });
    }
}