package com.example.databasefifteen.Fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasefifteen.R;

public class AllGroupsFragment extends Fragment {
    private static final String TAG = "ExceptionLog";
    private Uri groupsUri=Uri.parse("content://com.demo.provider/GROUPS");
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_groups, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button getAllGroupsButton=rootView.findViewById(R.id.getAllGroupsButton);
        Button deleteAllGroupsButton=rootView.findViewById(R.id.deleteAllGroupsButton);
        TextView allGroupsOutputTextView=rootView.findViewById(R.id.allGroupsOutputTextView);
        getAllGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allGroupsOutputTextView.setText("");
                Log.d(TAG, "onClick: ");
                Cursor cursor=getContext().getContentResolver().query(groupsUri,null,null,null,null);
                while(cursor.moveToNext()){
                    allGroupsOutputTextView.append("Имя: " + cursor.getString(0) + " ID: " + cursor.getString(1) + "\nКоличество студентов: " + cursor.getString(2) + "\n");
                }
            }
        });
        deleteAllGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result=getContext().getContentResolver().delete(groupsUri,null);
                String resultString="Все группы успешно удалены";
                if(result==0){
                    resultString="Нечего удалять";
                }
                Toast.makeText(getContext(),resultString, Toast.LENGTH_SHORT).show();
                allGroupsOutputTextView.setText("");
            }
        });
    }
}