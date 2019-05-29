package com.example.voicerecording.Controller;

import com.example.voicerecording.Model.SoundModel;
import com.example.voicerecording.View.IRecorderView;

public class RecorderPresenter implements IRecorderPresenter {

    IRecorderView iRecorderView;
    SoundModel soundModel;
    String path;
    public RecorderPresenter(IRecorderView iRecorderView) {
        this.iRecorderView = iRecorderView;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void startRecordingClick() {
        iRecorderView.startRecording();
    }

    @Override
    public void stopRecordingClick() {
        iRecorderView.stopRecording();
        soundModel=new SoundModel(path);
    }

    @Override
    public void playRecordingClick() {
        iRecorderView.playRecord();
    }

}
