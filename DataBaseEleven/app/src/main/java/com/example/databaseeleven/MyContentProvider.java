package com.example.databaseeleven;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "ExceptionLog";
    static final String AUTHORITY="com.demo.provider";
    static final String Groups="GROUPS";
    static final String Students="STUDENT";
    static final int URI_ALLGROUPS=0;
    static final int URI_SINGLEGROUP=1;
    static final int URI_ALLSTUDENTS=2;
    static final int URI_SINGLESTUDENT=3;
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,Groups,URI_ALLGROUPS);
        uriMatcher.addURI(AUTHORITY,Groups+"/#",URI_SINGLEGROUP);
        uriMatcher.addURI(AUTHORITY,Students+"/#",URI_ALLSTUDENTS);
        uriMatcher.addURI(AUTHORITY,Students+"/#/#",URI_SINGLESTUDENT);
    }
    DataBaseHelper helper;
    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        helper=new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        db=helper.getWritableDatabase();
        Log.d(TAG, "query: " + uri);
        Cursor cursor=db.rawQuery("SELECT * FROM GROUPS",null);
        switch(uriMatcher.match(uri)){
            case URI_ALLGROUPS:{
                cursor=db.rawQuery("SELECT IDGROUP,NAME,(SELECT COUNT(*) FROM STUDENT WHERE STUDENT.IDGROUP=G.IDGROUP) FROM GROUPS G GROUP BY IDGROUP",null);
                return cursor;
            }
            case URI_SINGLEGROUP:{
                String groupID = uri.getLastPathSegment();
                cursor=db.rawQuery("SELECT NAME,(SELECT COUNT(*) FROM STUDENT WHERE STUDENT.IDGROUP=G.IDGROUP) FROM GROUPS G WHERE G.IDGROUP=?",new String[]{groupID});
                return cursor;
            }
            case URI_ALLSTUDENTS:{
                String groupID = uri.getLastPathSegment();
                cursor=db.rawQuery("SELECT IDSTUDENT,NAME FROM STUDENT WHERE IDGROUP=?",new String[]{groupID});
                return cursor;
            }
            case URI_SINGLESTUDENT:{
                List<String> idList=uri.getPathSegments();
                cursor=db.rawQuery("SELECT S.IDSTUDENT,S.NAME,(SELECT AVG(P.MARK) FROM PROGRESS P WHERE P.IDSTUDENT=S.IDSTUDENT) FROM STUDENT S WHERE S.IDGROUP=? AND S.IDSTUDENT=?",new String[]{idList.get(1),idList.get(2)});
                return cursor;
            }
        }
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        db=helper.getWritableDatabase();
        Uri resultUri=null;
        switch(uriMatcher.match(uri)){
            case URI_ALLGROUPS:{
                String groupID = uri.getLastPathSegment();
                long res=db.insert(Groups,null,contentValues);
//                resultUri = ContentUris.withAppendedId("content", res);
//                return resultUri;
            }
            case URI_SINGLEGROUP:{
                List<String> idList=uri.getPathSegments();
                long res=db.insert(Students,null,contentValues);
//                resultUri = ContentUris.withAppendedId("content://com.demo.provider/STUDENT", res);
//                return resultUri;
            }
        }
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        db=helper.getWritableDatabase();
        Log.d(TAG, "query: " + uri);
        db.execSQL("PRAGMA foreign_keys=ON");
        switch(uriMatcher.match(uri)){
            case URI_ALLGROUPS:{
                int result=db.delete("GROUPS",null,null);
                return result;
            }
            case URI_SINGLEGROUP:{
                String groupID = uri.getLastPathSegment();
                int result=db.delete("GROUPS","IDGROUP="+groupID,null);
                return result;
            }
            case URI_ALLSTUDENTS:{
                String groupID = uri.getLastPathSegment();
                int result=db.delete("STUDENT","IDGROUP="+groupID,null);
                return result;
            }
            case URI_SINGLESTUDENT:{
                List<String> idList=uri.getPathSegments();

                int result=db.delete("STUDENT","IDGROUP="+idList.get(1) + " AND IDSTUDENT=" + idList.get(2),null);
                return result;
            }
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        db=helper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON");
        int updateResult=0;
        Log.d(TAG, "query: " + uri);
        switch(uriMatcher.match(uri)){
            case URI_SINGLEGROUP:{
                String groupID = uri.getLastPathSegment();
                updateResult=db.update(Groups,contentValues,"IDGROUP=" + groupID,null);
                return updateResult;
            }
            case URI_ALLSTUDENTS:{
                String studentID=uri.getLastPathSegment();
                updateResult=db.update(Students,contentValues,"IDSTUDENT=" + studentID,null);
                return updateResult;
            }
        }
        return updateResult;
    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
