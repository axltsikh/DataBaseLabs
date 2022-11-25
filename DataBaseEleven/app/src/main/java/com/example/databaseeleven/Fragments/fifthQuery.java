package com.example.databaseeleven.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.databaseeleven.DataBaseHelper;
import com.example.databaseeleven.R;


public class fifthQuery extends Fragment {
    SQLiteDatabase db;
    DataBaseHelper helper;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fifth_query, container, false);
        helper=new DataBaseHelper(rootView.getContext());
        Filler();
        return rootView;
    }
    public void Filler(){
        Button button=rootView.findViewById(R.id.FifthQuery);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fifthQuery();
            }
        });
    }
    public void fifthQuery(){
        TextView text=rootView.findViewById(R.id.FifthResultTextView);
        TextView begin=rootView.findViewById(R.id.BeginFifthEditText);
        TextView end=rootView.findViewById(R.id.EndFifthEditText);
        db=helper.getWritableDatabase();
        Cursor cursor=helper.fifthQuery(db, begin.getText().toString(),end.getText().toString());
        text.setText("");
        while (cursor.moveToNext()) {
            text.append(cursor.getString(0) + " Средняя оценка: " + cursor.getString(1) + "\n");
        }
    }

}