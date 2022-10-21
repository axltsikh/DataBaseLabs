package com.example.databaseten.ui.allgroups;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.databaseten.DataBaseHelper;
import com.example.databaseten.Group;
import com.example.databaseten.GroupAdapter;
import com.example.databaseten.R;


public class AllGroupsFragment extends Fragment {

    final String TAG="ExceptionLog";
    DataBaseHelper helper;
    SQLiteDatabase db;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_all_groups, container, false);
        Filler();
        return rootView;
    }
    private void Filler(){
        ListView list=rootView.findViewById(R.id.AllGroupsItem);
        GroupAdapter adapter=new GroupAdapter(getContext(),R.layout.list_item, Group.groups);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Group buffer=Group.groups.get(i);
                DataBaseHelper helper=new DataBaseHelper(getContext());
                SQLiteDatabase db=helper.getWritableDatabase();
                db.execSQL("PRAGMA foreign_keys=ON");
                Log.d(TAG, "onItemClick: " + buffer.IDGroup);
                helper.DeleteGroup(db,buffer.IDGroup);
                helper.SelectGroups(db);
                Filler();
            }
        });
    }
}