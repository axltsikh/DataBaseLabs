package com.example.databaseten;

import java.util.ArrayList;

public class Group {
    public static ArrayList<Group> groups=new ArrayList<>();
    public int IDGroup;
    public String Faculty;
    public int Course;
    public String GroupName;
    public int HeadID;
    Group(int ID,String fac,int course,String name,int head){
        IDGroup=ID;
        Faculty=fac;
        Course=course;
        GroupName=name;
        HeadID=head;
    }
    public Group(){}
    @Override
    public String toString(){
        return GroupName;
    }

}
