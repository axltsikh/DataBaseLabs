package com.example.databaseeight;

public class CategoryClass {
    String Category;
    int Id;
    public CategoryClass(String cat){
        Category=cat;
        Id=cat.hashCode();
    }
    @Override
    public String toString(){
        return Category + "\n";
    }
}
