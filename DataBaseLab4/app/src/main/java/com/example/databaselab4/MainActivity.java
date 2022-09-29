package com.example.databaselab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ExistBase("Base_Lab.txt")){

        }
        else{
            CreateFile("Base_Lab.txt");
        }
        FileReader();
    }
    private boolean ExistBase(String Fname){
        boolean rc=false;
        File f=new File(super.getFilesDir(),Fname);
        if(f.exists()){
            Log.d("Log_02","Файл "+Fname+" существует");
        }
        else{
            Log.d("Log_02","Файл "+ Fname + " не найден");
        }
        return rc;
    }
    private void CreateFile(String Fname){
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setTitle("Создается файл "+Fname).setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                            Log.d("Log_02","Создание файла " + Fname);
                    }
                });
        AlertDialog ad=b.create();
        ad.show();
        File baseFile=new File(super.getFilesDir(),Fname);
        try{
            baseFile.createNewFile();
            Log.d("Log_02","Файл" + Fname + "создан");
        }
        catch(IOException e){
            Log.d("Log_02","Файл" + Fname + "Не создан");
        }
    }
    public void InputButtonOnClick(View view){
        EditText SurnameView=findViewById(R.id.SurnameInput);
        EditText NameView = findViewById(R.id.NameInput);
        String SurnameString=SurnameView.getText().toString();
        String NameString=NameView.getText().toString();
        FileOpen(NameString,SurnameString,"Base_Lab.txt");
    }
    private void FileOpen(String Name,String Surname,String Fname){

        try{
            File f=new File(super.getFilesDir(),Fname);
            FileWriter fw=new FileWriter(f,true);
            BufferedWriter bw=new BufferedWriter(fw);
            String FullString = Surname + " " + Name + " " + "\r\n";
            FileWrite(FullString,bw);
        }
        catch(IOException e){
            Log.d("Log_02","Файл " + Fname + "не открыт " + e.getMessage());
        }
    }
    private void FileWrite(String str,BufferedWriter bw){
        try{
            bw.write(str);
            Log.d("Log_02","Данные записаны");
            bw.close();
        }
        catch(IOException e){
            Log.d("Log_02",e.getMessage());
        }
    }
    private void FileReader(){
        TextView viewGetter=findViewById(R.id.FileContentText);
        viewGetter.setText("");
        File f=new File(super.getFilesDir(),"Base_Lab.txt");
        try(BufferedReader bw=new BufferedReader(new FileReader(f))){
            String s;
            while((s=bw.readLine())!=null){
                viewGetter.setText(viewGetter.getText().toString()+s + "\r\n");
            }
        }
        catch(IOException e){
            Log.d("Log_02",e.getMessage());
        }
    }
}