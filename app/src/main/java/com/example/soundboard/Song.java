package com.example.soundboard;

import java.util.ArrayList;

public class Song {
    private ArrayList<Note> notes;
    public Song(){
        notes = new ArrayList<Note>();
    }



    public void addNote(Note note){
        notes.add(notes.size(), note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }
}
