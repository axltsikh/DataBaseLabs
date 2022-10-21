package com.example.databaseeight;

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

public class Category extends Fragment {

    public Category() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Task.rootView=inflater.inflate(R.layout.fragment_category, container, false);
        Filler();
        return Task.rootView;
    }
    public static void Filler(){
        Task.JsonDeserialize();
        ListView view=Task.rootView.findViewById(R.id.CategoryList);
        ArrayAdapter<CategoryClass> adapter=new ArrayAdapter<>(Task.rootView.getContext(), android.R.layout.simple_expandable_list_item_1, Task.categorys);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task.categoryBuffer=Task.categorys.get(i);
                EditText text=Task.rootView.findViewById(R.id.CategoryText);
                text.setText(Task.categorys.get(i).Category);
            }
        });
    }
}