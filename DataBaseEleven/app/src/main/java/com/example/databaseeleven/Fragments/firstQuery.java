package com.example.databaseeleven.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.databaseeleven.DataBaseHelper;
import com.example.databaseeleven.R;


public class firstQuery extends Fragment {

    SQLiteDatabase db;
    DataBaseHelper helper;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_first_query, container, false);
        helper=new DataBaseHelper(rootView.getContext());
        Filler();
        return rootView;
    }
    public void Filler(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.subjects, android.R.layout.simple_spinner_item);
        Spinner spinner=rootView.findViewById(R.id.FirstQuerySpinner);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> secondadapter=ArrayAdapter.createFromResource(rootView.getContext(),
                R.array.GroupID, android.R.layout.simple_spinner_item);
        Spinner secondspinner=rootView.findViewById(R.id.GroupIDSpinner);
        secondspinner.setAdapter(secondadapter);
        Button button=rootView.findViewById(R.id.FirstQuery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstQuery();
            }
        });
    }
    public void firstQuery(){
        TextView text=rootView.findViewById(R.id.FirstResultTextView);
        TextView begin=rootView.findViewById(R.id.BeginFirstEditText);
        TextView end=rootView.findViewById(R.id.EndFirstEditText);
        Spinner spinner=rootView.findViewById(R.id.FirstQuerySpinner);
        Spinner secondspinner=rootView.findViewById(R.id.GroupIDSpinner);
        db=helper.getWritableDatabase();
        Cursor cursor=helper.firstQuery(db,spinner.getSelectedItem().toString(),begin.getText().toString(),
                end.getText().toString(),secondspinner.getSelectedItem().toString());
        text.setText("");
        String groupmark=null;
        while (cursor.moveToNext()) {
            groupmark=cursor.getString(3);
            text.append(cursor.getString(0) + "\nСредняя оценка: " + cursor.getString(1) + "\nПо предмету: " +
                    cursor.getString(2) + "\n\n");
        }
        if(groupmark!=null) {
            text.append("Средняя оценка группы: " + groupmark);
        }
    }
}