package com.example.databaseseven;

import java.sql.Array;
import java.sql.Date;

public class Note {
    String NoteText;
    Date NoteDate;
    public Note(String noteText,Date noteDate){
        NoteText=noteText;
        NoteDate=noteDate;
    }
    public Note(){};
}
