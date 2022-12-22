package com.example.databaseseventeen;

import java.io.Serializable;

public class Person implements Serializable {
    public int ID;
    public String FirstName;
    public String SecondName;
    public int age;
    public PersonData personData;
    public Person(int id,String first,String second,int ag,PersonData person){
        ID=id;
        FirstName=first;
        SecondName=second;
        age=ag;
        personData=person;
    }
    @Override
    public String toString(){
        return FirstName + " " + SecondName + "\n" + personData.Phone + "\n" + personData.Email;
    }
    public Person(){
        personData=new PersonData();
    }
}
