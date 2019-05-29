package com.example.voicerecording.View;

import android.net.Uri;

public interface IRecorderView {
    boolean checkpermission();
    void requestpermission();
    void startRecording();
    void stopRecording();
    void playRecord();
    void savefile();
}
