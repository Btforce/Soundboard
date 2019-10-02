package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SoundBoardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonA;
    private Button buttonB;
    private Button buttonBFlat;
    private Button buttonC;
    private Button buttonCSharp;
    private Button buttonD;
    private Button buttonDSharp;
    private Button buttonE;
    private Button buttonF;
    private Button buttonFSharp;
    private Button buttonG;
    private Button buttonGSharp;
    private Button buttonScale;
    private Button buttonRecord;
    private Button buttonPlayRecording;
    private Button buttonResetSong;
    private Button buttonRickRolled;
    private Button buttonMegalovania;
    private SoundPool soundPool;
    boolean loaded = false;
    private int aNote;
    private int bNote;
    private int bFlatNote;
    private int cNote;
    private int cSharpNote;
    private int dNote;
    private int dSharpNote;
    private int eNote;
    private int fNote;
    private int fSharpNote;
    private int gNote;
    private int gSharpNote;
    private int rick_rolled;
    private int megalovania;
    private Map<Integer, Integer> noteMap;
    boolean isFirstClick = true;
    long oldTime;
    long newTime;
    int currentClick;
    int oldClick;
    ArrayList<Integer> scale = new ArrayList<Integer>();
    private Song song;
    private boolean isRecording = false;
    private boolean isFirstNote = true;

    public static final String TAG = SoundBoardActivity.class.getSimpleName();

    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_board);


        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        song = new Song();
        wireWidgets();
        loadSounds();
        setListeners();

    }

    private void loadSounds() {
        aNote = soundPool.load(this, R.raw.scalea, 1);
        bNote = soundPool.load(this, R.raw.scaleb, 1);
        bFlatNote = soundPool.load(this, R.raw.scalebb, 1);
        cNote = soundPool.load(this, R.raw.scalec, 1);
        cSharpNote = soundPool.load(this, R.raw.scalecs, 1);
        dNote = soundPool.load(this, R.raw.scaled, 1);
        dSharpNote = soundPool.load(this, R.raw.scaleds, 1);
        eNote = soundPool.load(this, R.raw.scalee, 1);
        fNote = soundPool.load(this, R.raw.scalef, 1);
        fSharpNote = soundPool.load(this, R.raw.scalefs, 1);
        gNote = soundPool.load(this, R.raw.scaleg, 1);
        gSharpNote = soundPool.load(this, R.raw.scalegs, 1);
        rick_rolled = soundPool.load(this, R.raw.rick_rolled, 1);
        megalovania = soundPool.load(this, R.raw.megalovania, 1);


        noteMap = new HashMap<>();
        noteMap.put(buttonA.getId(), aNote);
        noteMap.put(buttonBFlat.getId(), bFlatNote);
        noteMap.put(buttonB.getId(), bNote);
        noteMap.put(buttonC.getId(), cNote);
        noteMap.put(buttonCSharp.getId(), cSharpNote);
        noteMap.put(buttonD.getId(), dNote);
        noteMap.put(buttonDSharp.getId(), dSharpNote);
        noteMap.put(buttonE.getId(), eNote);
        noteMap.put(buttonF.getId(), fNote);
        noteMap.put(buttonFSharp.getId(), fSharpNote);
        noteMap.put(buttonG.getId(), gNote);
        noteMap.put(buttonGSharp.getId(), gSharpNote);
        noteMap.put(buttonRickRolled.getId(), rick_rolled);
        noteMap.put(buttonMegalovania.getId(), megalovania);


        scale.add(aNote);
        scale.add(bFlatNote);
        scale.add(cNote);
        scale.add(dNote);
        scale.add(dSharpNote);
        scale.add(fNote);
        scale.add(gNote);
    }

    private void setListeners() {

        KeyboardListener keyboardListener = new KeyboardListener();
        buttonA.setOnClickListener(keyboardListener);
        buttonB.setOnClickListener(keyboardListener);
        buttonBFlat.setOnClickListener(keyboardListener);
        buttonC.setOnClickListener(keyboardListener);
        buttonCSharp.setOnClickListener(keyboardListener);
        buttonD.setOnClickListener(keyboardListener);
        buttonDSharp.setOnClickListener(keyboardListener);
        buttonE.setOnClickListener(keyboardListener);
        buttonF.setOnClickListener(keyboardListener);
        buttonFSharp.setOnClickListener(keyboardListener);
        buttonG.setOnClickListener(keyboardListener);
        buttonGSharp.setOnClickListener(keyboardListener);
        buttonRickRolled.setOnClickListener(keyboardListener);
        buttonMegalovania.setOnClickListener(keyboardListener);

        buttonScale.setOnClickListener(this);
        buttonRecord.setOnClickListener(this);
        buttonPlayRecording.setOnClickListener(this);
        buttonResetSong.setOnClickListener(this);
    }

    private void delay(int millisDelay) {
        try {
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void wireWidgets() {
        buttonA = findViewById(R.id.button_main_A);
        buttonBFlat = findViewById(R.id.button_main_Bflat);
        buttonB = findViewById(R.id.button_main_B);
        buttonC = findViewById(R.id.button_main_C);
        buttonCSharp = findViewById(R.id.button_main_Csharp);
        buttonD = findViewById(R.id.button_main_D);
        buttonDSharp = findViewById(R.id.button_main_Dsharp);
        buttonE = findViewById(R.id.button_main_E);
        buttonF = findViewById(R.id.button_main_F);
        buttonFSharp = findViewById(R.id.button_main_Fsharp);
        buttonG = findViewById(R.id.button_main_G);
        buttonGSharp = findViewById(R.id.button_main_Gsharp);
        buttonScale = findViewById(R.id.button_main_scale);
        buttonPlayRecording = findViewById(R.id.button_main_playRecording);
        buttonRecord = findViewById(R.id.button_main_startStop);
        buttonResetSong = findViewById(R.id.button_main_resetSong);
        buttonRickRolled = findViewById(R.id.button_main_rickRolled);
        buttonMegalovania = findViewById(R.id.button_main_megalovania);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_main_A: {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                if (loaded) {
                    soundPool.play(aNote, volume, volume, 1, 0, 1f);
                }
                break;
            }

            case R.id.button_main_Bflat: {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                if (loaded) {
                    soundPool.play(bFlatNote, volume, volume, 1, 0, 1f);
                }
                break;
            }

            case R.id.button_main_B: {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                // Is the sound loaded already?
                if (loaded) {
                    soundPool.play(bNote, volume, volume, 1, 0, 1f);
                }
                break;
            }

            case R.id.button_main_scale: {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;
                for(int i = 0; i < scale.size(); i ++){
                    soundPool.play(scale.get(i), volume, volume, 1, 0, 1f);
                    delay(500);
                }
                break;
            }

            case R.id.button_main_playRecording: {
                AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                float actualVolume = (float) audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                float maxVolume = (float) audioManager
                        .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                float volume = actualVolume / maxVolume;

                if(loaded){
                    for(Note note : song.getNotes()){
                        if(!isFirstNote){
                            delay(note.getDelay());
                        }
                        soundPool.play(note.getSoundID(), volume, volume, 1, 0, 1f);
                        isFirstNote = false;
                    }
                }

                break;
            }

            case R.id.button_main_resetSong: {
                song.clearSong();

                break;
            }

            case R.id.button_main_startStop: {
                if(isRecording){
                    isRecording = false;
                    buttonRecord.setText(getString(R.string.Start));
                }
                else{
                    isRecording = true;
                    buttonRecord.setText(getString(R.string.Stop));
                    oldTime = SystemClock.elapsedRealtime();
                    isFirstNote = true;
                }
                break;
            }
        }
    }

    private class KeyboardListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int songId = noteMap.get(view.getId());
            if (songId != 0) {
                Note note = new Note(0,0);
                note.setSoundID(songId);
                newTime = SystemClock.elapsedRealtime();
                if (isFirstClick) {
                    isFirstClick = false;
                    oldTime = SystemClock.elapsedRealtime();
                    currentClick = 0;
                    oldClick = 0;
                }
                if(currentClick - 1 == oldClick){
                    newTime = SystemClock.elapsedRealtime();

                    oldClick++;
                }
                note.setDelay((int) (newTime - oldTime));
                oldTime = newTime;
                soundPool.play(songId, 1, 1, 1, 0, 1);
                currentClick++;
                Log.d(TAG, "onClick: " + note.getDelay());
                if(isRecording){
                    song.addNote(note);
                }


            }



        }
    }
}
