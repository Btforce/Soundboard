package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class SoundBoardActivity extends AppCompatActivity {
    private Button a;
    private Button b;
    private Button b_flat;
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;
    private int aNote;
    private int bNote;
    private int bFlatNote;

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
    }

    private void setListeners() {
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        b_flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }

    private void wireWidgets() {
        a = findViewById(R.id.button_main_A);
        b_flat = findViewById(R.id.button_main_Bflat);
        b = findViewById(R.id.button_main_B);
    }
}
