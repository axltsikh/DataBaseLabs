package com.example.databaseten;

import java.util.ArrayList;

public class Student {
    public int IDGroup;
    public int IDStudent;
    public String Name;
    public static ArrayList<Student> students=new ArrayList<>();
    Student(int id,int studid,String name){
        IDGroup=id;
        IDStudent=studid;
        Name=name;
    }
    public Student(){}
    @Override
    public String toString(){
        return Name;
    }
}
