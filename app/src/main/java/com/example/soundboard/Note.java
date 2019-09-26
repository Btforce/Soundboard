package com.example.soundboard;

public class Note {
    private int soundID;
    private int delay;

    public Note(int soundID, int delay){
        this.soundID = soundID;
        this.delay = delay;
    }



    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getSoundID() {
        return soundID;
    }

    public void setSoundID(int soundID) {
        this.soundID = soundID;
    }
}
