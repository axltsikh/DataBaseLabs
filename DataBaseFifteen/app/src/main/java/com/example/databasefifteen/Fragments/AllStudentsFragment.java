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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasefifteen.R;


public class AllStudentsFragment extends Fragment {
    private static final String TAG = "ExceptionLog";
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_students, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button getAllGroupStudentsButton=rootView.findViewById(R.id.getSingleGroupStudentsButton);
        Button deleteAllGroupStudentsButton=rootView.findViewById(R.id.deleteSingleGroupStudentsButton);
        EditText groupID=rootView.findViewById(R.id.singleGroupStudentsNumber);
        TextView singleGroupOutput=rootView.findViewById(R.id.singleGroupStudentsTextView);
        getAllGroupStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleGroupOutput.setText("");
                Log.d(TAG, "onClick: ");
                Uri groupsUri=Uri.parse("content://com.demo.provider/STUDENT/" + groupID.getText().toString());
                Log.d(TAG, "onClick: " + groupsUri);
                Cursor cursor=getContext().getContentResolver().query(groupsUri,null,null,null,null);
                while(cursor.moveToNext()){
                    singleGroupOutput.append(cursor.getString(0) + " " + cursor.getString(1)+ "\n");
                }
            }
        });
        deleteAllGroupStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri groupsUri=Uri.parse("content://com.demo.provider/STUDENT/" + groupID.getText().toString());
                int result=getContext().getContentResolver().delete(groupsUri,null);
                String resultString="Все студенты групппы успешно удалены";
                if(result==0){
                    resultString="Нечего удалять";
                }
                Toast.makeText(getContext(),resultString, Toast.LENGTH_SHORT).show();
                singleGroupOutput.setText("");
            }
        });
    }
}