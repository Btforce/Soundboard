package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;


public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button a;
    private Button b;
    private Button b_flat;
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;
    private int aNote;
    private int bNote;
    private int bFlatNote;
    private Map<Integer, Integer> noteMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);


        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        wireWidgets();
        loadSounds();
        setListeners();

    }

    private void loadSounds() {
        aNote = soundPool.load(this, R.raw.scalea, 1);
        bNote = soundPool.load(this, R.raw.scaleb, 1);
        bFlatNote = soundPool.load(this, R.raw.scalebb, 1);

        noteMap = new HashMap<>();
        noteMap.put(a.getId(), aNote);
        noteMap.put(b_flat.getId(), bFlatNote);
        noteMap.put(b.getId(), bNote);
    }

    private void setListeners() {
//        a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
//                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                float maxVolume = (float) audioManager
//                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                float volume = actualVolume / maxVolume;
//                // Is the sound loaded already?
//                if (loaded) {
//                    soundPool.play(aNote, volume, volume, 1, 0, 1f);
//                    Log.e("Test", "Played sound");
//                }
//            }
//        });
//
//        b_flat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
//                float actualVolume = (float) audioManager
//                        .getStreamVolume(AudioManager.STREAM_MUSIC);
//                float maxVolume = (float) audioManager
//                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                float volume = actualVolume / maxVolume;
//                // Is the sound loaded already?
//                if (loaded) {
//                    soundPool.play(bFlatNote, volume, volume, 1, 0, 1f);
//                    Log.e("Test", "Played sound");
//                }
//            }
//        });
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
//                float actualVolume = (float) audioManager
//                        .getStreamVolume(AudioManager.STREAM_MUSIC);
//                float maxVolume = (float) audioManager
//                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                float volume = actualVolume / maxVolume;
//                // Is the sound loaded already?
//                if (loaded) {
//                    soundPool.play(bNote, volume, volume, 1, 0, 1f);
//                    Log.e("Test", "Played sound");
//                }
//            }
//
//        });
        KeyboardListener keyboardListener = new KeyboardListener();
        a.setOnClickListener(keyboardListener);
        b.setOnClickListener(keyboardListener);
        b_flat.setOnClickListener(keyboardListener);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        b_flat.setOnClickListener(this);
    }

    private void delay(int millisDelay){
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wireWidgets() {
        a = findViewById(R.id.button_main_A);
        b_flat = findViewById(R.id.button_main_Bflat);
        b = findViewById(R.id.button_main_B);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_main_A : {
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                // Is the sound loaded already?
                if (loaded) {
                    soundPool.play(aNote, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
                break;
            }

            case R.id.button_main_Bflat : {
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                // Is the sound loaded already?
                if (loaded) {
                    soundPool.play(bFlatNote, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
                break;
            }

            case R.id.button_main_B : {
                AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                // Is the sound loaded already?
                if (loaded) {
                    soundPool.play(bNote, volume, volume, 1, 0, 1f);
                    Log.e("Test", "Played sound");
                }
                break;
            }
        }
    }

    private class KeyboardListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            // read from my map -- looking up which button was pressed and play the associated note
            int songId = noteMap.get(view.getId());
            if(songId != 0){
                soundPool.play(songId, 1, 1, 1, 0, 1);
            }
        }
    }
}
