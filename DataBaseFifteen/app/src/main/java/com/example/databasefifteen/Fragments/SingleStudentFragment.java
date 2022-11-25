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

import java.util.List;

public class SingleStudentFragment extends Fragment {
    private static final String TAG = "ExceptionLog";
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_single_student, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        Button deleteSingleStudentButton=rootView.findViewById(R.id.deleteSingleStudentButton);
        Button getSingleStudentButton=rootView.findViewById(R.id.getSingleStudentButton);
        EditText groupID=rootView.findViewById(R.id.getSingleStudentGroupID);
        EditText studentID=rootView.findViewById(R.id.getSingleStudentStudentID);
        TextView singleGroupOutput=rootView.findViewById(R.id.getSingleStudentOutput);
        //Изменение ФИО
        EditText newName=rootView.findViewById(R.id.changeStudentNameEditText);
        EditText studentIDChange=rootView.findViewById(R.id.IDToChangeStudentName);
        Button changeStudentName=rootView.findViewById(R.id.changeStudentNameButton);

        //Добавление студента
        EditText newStudentName=rootView.findViewById(R.id.studentName);
        EditText newStudentIDGroup=rootView.findViewById(R.id.studentGroupID);
        EditText newStudentBirthDay=rootView.findViewById(R.id.studentBirthday);
        EditText newStudentAddress=rootView.findViewById(R.id.studentAdress);
        Button addNewStudent=rootView.findViewById(R.id.addStudentButton);

        getSingleStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleGroupOutput.setText("");
                Uri studentUri=Uri.parse("content://com.demo.provider/STUDENT/" + groupID.getText().toString() + "/" + studentID.getText().toString());
                List<String> a=studentUri.getPathSegments();
                Log.d(TAG, "onClick: " + a.get(1));
                Log.d(TAG, "onClick: " + a.get(2));
                Cursor cursor=getContext().getContentResolver().query(studentUri,null,null,null,null);
                while(cursor.moveToNext()){
                    singleGroupOutput.append("ID: " + cursor.getString(0) + " Имя: " + cursor.getString(1)+ "\n" + "Средний балл: " + cursor.getString(2));
                }
            }
        });
        deleteSingleStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri studentUri=Uri.parse("content://com.demo.provider/STUDENT/" + groupID.getText().toString() + "/" + studentID.getText().toString());
                int result=getContext().getContentResolver().delete(studentUri,null);
                String resultString="Студент успешно удалён";
                if(result==0){
                    resultString="Нечего удалять";
                }
                groupID.setText("");
                studentID.setText("");
                singleGroupOutput.setText("");
                Toast.makeText(getContext(),resultString, Toast.LENGTH_SHORT).show();
            }
        });
        changeStudentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put("NAME",newName.getText().toString());
                Uri studentUri=Uri.parse("content://com.demo.provider/STUDENT/" + studentIDChange.getText().toString());
                Log.d(TAG, "onClick: " + studentUri);
                int result=getContext().getContentResolver().update(studentUri,cv,null);
                String resultString="ФИО успешно изменено";
                if(result==0){
                    resultString="Произошла ошибка";
                }
                Toast.makeText(getContext(),resultString, Toast.LENGTH_SHORT).show();
            }
        });
        addNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put("NAME",newStudentName.getText().toString());
                cv.put("IDGROUP",newStudentIDGroup.getText().toString());
                cv.put("BIRTHDATE",newStudentBirthDay.getText().toString());
                cv.put("ADDRESS",newStudentAddress.getText().toString());
                Uri studentUri=Uri.parse("content://com.demo.provider/GROUPS/" + newStudentIDGroup.getText().toString());
                getContext().getContentResolver().insert(studentUri,cv,null);
            }
        });
    }
}