package com.example.sixteenlabdatabase;

import java.io.Serializable;

public class Contact implements Serializable {
    public String ID;
    public String Name;
    public String Phone;
    @Override
    public String toString(){
        return Name + "\n" + Phone;
    }
}
