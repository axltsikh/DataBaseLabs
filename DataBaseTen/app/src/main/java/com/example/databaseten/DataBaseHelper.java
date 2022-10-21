package com.example.databaseten;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.opengl.EGLObjectHandle;
import android.util.Log;

import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper {
    static Context context;
    final String TAG="ExceptionLog";
    public DataBaseHelper(Context context1){
        super(context1,"STUDENTSDB.db",null,1);
        context=context1;
    }

    public void SelectStudents(SQLiteDatabase db,int IDgroup){
        Cursor cursor=db.rawQuery("SELECT * FROM Students WHERE IDGROUP=" + IDgroup,null);
        Student.students.clear();
        while(cursor.moveToNext()){
            Student.students.add(new Student(cursor.getInt(0),cursor.getInt(1),cursor.getString(2)));
        }
    }
    public void SelectGroups(SQLiteDatabase db){
        Cursor cursor=db.rawQuery("SELECT * FROM Groups",null);
        Group.groups.clear();
        while(cursor.moveToNext()){
            Group.groups.add(new Group(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getInt(4)));
        }
    }
    public void InsertGroup(SQLiteDatabase db,Group group){
        ContentValues cv=new ContentValues();
        cv.put("FACULTY",group.Faculty);
        cv.put("COURSE",group.Course);
        cv.put("NAME",group.GroupName);
        db.insert("Groups",null,cv);
        SelectGroups(db);
    }
    public void InsertStudent(SQLiteDatabase db,Student student){
        ContentValues cv=new ContentValues();
        cv.put("IDGROUP",student.IDGroup);
        cv.put("NAME",student.Name);
        db.insert("Students",null,cv);
    }
    public void UpdateGroup(SQLiteDatabase db,int IdStudent,int IDGroup){
        ContentValues cv=new ContentValues();
        cv.put("HEAD",IdStudent);
        db.update("Groups",cv,"IDGROUP = " + IDGroup,null);
        SelectGroups(db);
    }
    public void DeleteGroup(SQLiteDatabase db,int id){
        db.delete("Groups","IDGROUP=?", new String[]{String.valueOf(id)});

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Groups(IDGROUP INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FACULTY TEXT, COURSE INTEGER,NAME TEXT, HEAD INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE Students(IDGROUP INTEGER, IDSTUDENT INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT,FOREIGN KEY(IDGROUP) REFERENCES Groups(IDGROUP) ON UPDATE CASCADE ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
