package com.example.databasefive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    String sample="     :          :  ;\n";
    String fname="File.txt";
    EditText KeyInput, ValueInput, KeyGet;
    TextView ValueOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyInput=findViewById(R.id.KeyEditText);
        ValueInput=findViewById(R.id.ValueEditText);
        KeyGet=findViewById(R.id.KeyFindEditText);
        ValueOutput=findViewById(R.id.ValueGetEditText);
        FileChecker();
    }
    //Запись значения
    public void SaveButtonOnClick(View view){
        if(KeyInput.getText().toString().equals("") || ValueInput.getText().toString().equals("")){
            Toast toast=Toast.makeText(this,"Заполните поля!",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        String value=ValueInput.getText().toString();
        String key=KeyInput.getText().toString();
        int Hash=Hash(key);
        Log.d("ExceptionLog", "Key: " + key);
        Log.d("ExceptionLog", "Hash: " + String.valueOf(Hash));
        StringReader(Hash,key,value);
    }
    public void StringReader(int Hash,String key,String value){
        String buffer;
        String keyBuffer;
        try{
            File f=new File(super.getFilesDir(),fname);
            RandomAccessFile raf=new RandomAccessFile(f,"rw");
            raf.seek(21*Hash);
            buffer=raf.readLine();
            keyBuffer=buffer.substring(0,5);
            if(key.trim().equals(keyBuffer.trim()) || keyBuffer.trim().equals("")){
                Writer(Hash,key,value);
            }
            else{
                newHashSolver(Hash,key,value);
            }
        }
        catch(Exception e){
            Log.d("ExceptionLog", "StringReader: " + e.getMessage());
        }
    }
    public void newHashSolver(int Hash,String key,String value){
        int newHash=Hash+10;
        String buffer;
        String newHashbuffer;
        String keyBuffer;
        try{
            Log.d("ExceptionLog", "newHashSolver: " + "Hash: " + Hash
            + "NewHash: " + newHash);
            File f=new File(super.getFilesDir(),fname);
            RandomAccessFile raf=new RandomAccessFile(f,"rw");
            raf.seek(21*Hash);
            buffer=raf.readLine();
            Log.d("ExceptionLog",buffer);
            keyBuffer=buffer.substring(0,5);
            newHashbuffer=buffer.substring(17,19);
            Log.d("ExceptionLog", "newHashSolver: {" + newHashbuffer + "}");
            if(newHashbuffer.trim().equals("") && !keyBuffer.trim().equals(key)){
                raf.seek(21*Hash+17);
                raf.write(String.valueOf(newHash+1).getBytes());
                sampleAdder(newHash);
                Writer(newHash,key,value);
            }
            else if(keyBuffer.trim().equals(key)){
                Writer(Hash,key,value);
            }
            else{
                newHashSolver(newHash,key,value);
            }
        }
        catch(Exception e){
            Log.d("ExceptionLog", "newHashSolver: " + e.getMessage());
        }
    }
    public void sampleAdder(int newHash){
        File f=new File(super.getFilesDir(),fname);
        long iterations=newHash - f.length()%20;
        Log.d("ExceptionLog", "sampleAdder: " + iterations);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            for (int i = 0; i < iterations+1; i++) {
                bw.write(sample);
            }
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "sampleAdder: " + e.getMessage());
        }
    }
    public int Hash(String keyValue){
        return keyValue.hashCode()%9;
    }
    public void Writer(int HashPosition,String key,String value){
        try{
            File f=new File(super.getFilesDir(),fname);
            RandomAccessFile raf=new RandomAccessFile(f,"rw");
            raf.seek(21*HashPosition);
            raf.writeBytes(key);
            raf.seek(HashPosition*21+6);
            raf.writeBytes(value);
        }
        catch(Exception e){
            Log.d("ExceptionLog", "Writer: " + e.getMessage());
        }
    }
    //Получение значения
    public void GetButtonOnClick(View view){
        String keyFind=KeyGet.getText().toString().trim();
        int Hash=Hash(keyFind);
        HashFinder(Hash,keyFind);
    }
    public void HashFinder(int Hash,String keyFind){
        String buffer;
        String keyBuffer;
        String nextHashBuffer;
        try{
            File f=new File(super.getFilesDir(),fname);
            RandomAccessFile raf=new RandomAccessFile(f,"rw");
            raf.seek(21*Hash);
            buffer=raf.readLine();
            nextHashBuffer=buffer.substring(17,19);
            keyBuffer=buffer.substring(0,5);
            Log.d("ExceptionLog", "HashFinder: " + nextHashBuffer);
            if(keyFind.equals(keyBuffer.trim())){
                ValueOutput.setText(buffer.substring(6,16));
            }
            else if(keyBuffer.trim().equals("") && nextHashBuffer.trim().equals("")){
                Toast toast=Toast.makeText(this,"Ключ не существует",Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                int nextHashBufferInteger=Integer.valueOf(nextHashBuffer)-1;
                Log.d("ExceptionLog", "HashFinder: " + nextHashBufferInteger);
                HashFinder(nextHashBufferInteger,keyFind);
            }
        }
        catch(Exception e){
            Log.d("ExceptionLog", "GetButtonOnClick: " + e.getMessage());
        }
    }
    //Предварительные действия
    public void FileChecker(){
        File f=new File(super.getFilesDir(),fname);
        if(f.exists()){

        }
        else{
            try {
                f.createNewFile();
                SampleFiller(10);
            }
            catch(Exception e){
                Log.d("ExceptionLog",e.getMessage());
            }
        }
    }
    public void SampleFiller(int count){
        File f=new File(super.getFilesDir(),fname);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            for (int i = 0; i < count; i++) {
                bw.write(sample);
            }
            bw.close();
        }
        catch(Exception e){
            Log.d("ExceptionLog", "SampleFiller: " + e.getMessage());
        }
    }
}