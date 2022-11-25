package com.example.databasefifteen.Fragments;

import android.content.ContentValues;
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

public class SingleGroupFragment extends Fragment {
    private static final String TAG = "ExceptionLog";
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_single_group, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button getSingleGroupButton=rootView.findViewById(R.id.getSingleGroupButton);
        Button deleteSingleGroupButton=rootView.findViewById(R.id.deleteSingleGroupButton);
        EditText groupID=rootView.findViewById(R.id.singleGroupNumber);
        TextView singleGroupOutput=rootView.findViewById(R.id.singleGroupTextView);
        //Обновление группы
        EditText newGroupID=rootView.findViewById(R.id.changeGroupIDNew);
        EditText newGroupHead=rootView.findViewById(R.id.changeGroupHead);
        EditText oldGroupID=rootView.findViewById(R.id.changeGroupIDOld);
        Button changeSingleGroupButton=rootView.findViewById(R.id.changeGroupButton);
        //Добавление группы
        EditText addGroupFaculty=rootView.findViewById(R.id.addGroupFaculty);
        EditText addGroupCourse=rootView.findViewById(R.id.addGroupCourse);
        EditText addGroupName=rootView.findViewById(R.id.addGroupName);
        EditText addGroupHead=rootView.findViewById(R.id.addGroupHead);
        Button addGroupButton=rootView.findViewById(R.id.addGroupButton);

        getSingleGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleGroupOutput.setText("");
                Log.d(TAG, "onClick: ");
                Uri groupsUri=Uri.parse("content://com.demo.provider/GROUPS/" + groupID.getText().toString());
                Log.d(TAG, "onClick: " + groupsUri);
                Cursor cursor=getContext().getContentResolver().query(groupsUri,null,null,null,null);
                while(cursor.moveToNext()){
                    singleGroupOutput.append("Группа: " + cursor.getString(0) + "\nКоличество студентов: " + cursor.getString(1)+ "\n");
                }
            }
        });
        deleteSingleGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri groupsUri=Uri.parse("content://com.demo.provider/GROUPS/" + groupID.getText().toString());
                int result=getContext().getContentResolver().delete(groupsUri,null);
                String resultString="Группа успешно удалена";
                if(result==0){
                    resultString="Нечего удалять";
                }
                Toast.makeText(getContext(),resultString, Toast.LENGTH_SHORT).show();
                singleGroupOutput.setText("");
            }
        });
        changeSingleGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                if(!newGroupID.getText().toString().equals("")) {
                    cv.put("IDGROUP", newGroupID.getText().toString());
                }
                if(!newGroupHead.getText().toString().equals("")) {
                    cv.put("HEAD", newGroupHead.getText().toString());
                }
                Uri studentUri=Uri.parse("content://com.demo.provider/GROUPS/" + oldGroupID.getText().toString());
                int result=getContext().getContentResolver().update(studentUri,cv,null);
                String resultString="Группа успешно обновлена";
                if(result==0){
                    resultString="Произошла ошибка";
                }
            }
        });
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put("FACULTY",addGroupFaculty.getText().toString());
                cv.put("COURSE",addGroupCourse.getText().toString());
                cv.put("NAME",addGroupName.getText().toString());
                cv.put("HEAD",addGroupHead.getText().toString());
                Uri groupUri=Uri.parse("content://com.demo.provider/GROUPS");
                getContext().getContentResolver().insert(groupUri,cv,null);
            }
        });
    }
}