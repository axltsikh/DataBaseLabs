package com.example.databasenine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    static Context globalcontext;

    public DataBaseHelper(Context context) {
        super(context, "lab.db", null, 1);
        globalcontext=context;
    }
    public void Insert(SQLiteDatabase db, ContentValues cv){
        long result=db.insert("keyvalue",null,cv);
        if(result==-1)
            ToastCreator("Строка уже существует");
        else
            ToastCreator("Строка успешно добавлена");
    }
    public Cursor Select(SQLiteDatabase db, String id){
        return db.query("keyvalue",null,"_id=?",new String[]{id},null,null,null);
//        return db.query("select * from keyvalue where _id=?",new String[]{String.valueOf(id)});
    }
    public Cursor SelectRaw(SQLiteDatabase db, String id){
        return db.rawQuery("select * from keyvalue where _id=?",new String[]{String.valueOf(id)});
    }
    public void Update(SQLiteDatabase db, ContentValues cv,String id){
        int result=db.update("keyvalue",cv,"_id=" + id,null);
        if(result==0)
            ToastCreator("Строка не существует");
        else
            ToastCreator("Строка успешно обновлена");
    }
    public void Delete(SQLiteDatabase db,String id){
        int result=db.delete("keyvalue","_id=?",new String[]{id});
        if(result==0)
            ToastCreator("Строка не существует");
        else
            ToastCreator("Строка успешно удалена");
    }
    public static void ToastCreator(String message){
        Toast toast= Toast.makeText(globalcontext,message,Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE keyvalue(_id INTEGER PRIMARY KEY AUTOINCREMENT,F REAL,T TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
