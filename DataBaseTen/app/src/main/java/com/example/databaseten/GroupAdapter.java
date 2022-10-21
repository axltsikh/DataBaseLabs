package com.example.databaseten;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {
    private LayoutInflater inflater;
    private int layout;
    private List<Group> groups;

    public GroupAdapter(Context context, int resource, List<Group> states) {
        super(context, resource, states);
        this.groups = states;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView Faculty = view.findViewById(R.id.FacultyItem);
        TextView Course = view.findViewById(R.id.CourseItem);
        TextView Name = view.findViewById(R.id.NameItem);
        TextView Head=view.findViewById(R.id.HeadItem);
        Group group = groups.get(position);
        DataBaseHelper helper=new DataBaseHelper(getContext());
        SQLiteDatabase db=helper.getWritableDatabase();
        helper.SelectStudents(db,group.IDGroup);
        Log.d("ExceptionLog", "getView: " + group.HeadID);
        for(Student a:Student.students){
            if(a.IDStudent==group.HeadID){
                Log.d("ExceptionLog", "getView: " + a.IDStudent + " " + a.Name);
                Head.setText("Староста: " + a.Name);
                break;
            }
        }
        Faculty.setText("Факультет: " + group.Faculty);
        Course.setText("Курс: " + group.Course);
        Name.setText("Название группы: " + group.GroupName);

        return view;
    }
}
