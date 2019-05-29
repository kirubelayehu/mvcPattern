package com.example.voicerecording.Model;

public class SoundModel {

    private String soundpath;

    public SoundModel(String soundpath) {
        this.soundpath = soundpath;
    }

    public String getSoundpath() {
        return soundpath;
    }

    public void setSoundpath(String soundpath) {
        this.soundpath = soundpath;
    }
}
