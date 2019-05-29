package com.example.voicerecording;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.voicerecording.Controller.RecorderPresenter;

import com.example.voicerecording.View.IRecorderView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRecorderView {
    @BindView(R.id.startrecording)Button startrecordingbtn;
    @BindView(R.id.stoprecording)Button stoprecordingbtn;
    @BindView(R.id.playrecord)Button playrecordbtn;
    private RecorderPresenter recorderPresenter;
    String path;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    final int REQUESTPERMISSIONCODE =1;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recorderPresenter=new RecorderPresenter(this);
        ButterKnife.bind(this);
        if (!checkpermission()){
            requestpermission();
        }

            startrecordingbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkpermission()){
                    savefile();
                    recorderPresenter.setPath(path);
                     initializeMediaRecorder();
                   recorderPresenter.startRecordingClick();}
                    else{
                        requestpermission();
                    }
                }
            });
            stoprecordingbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recorderPresenter.stopRecordingClick();
                    Log.e("path",path);
                }
            });
            playrecordbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recorderPresenter.playRecordingClick();
                }
            });

    }

    @Override
    public boolean checkpermission() {
        int writeonstoragecode= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recordpermissioncode=ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO);
        return writeonstoragecode==PackageManager.PERMISSION_GRANTED&&
                recordpermissioncode==PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestpermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
        },REQUESTPERMISSIONCODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTPERMISSIONCODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        }

    @Override
    public void startRecording() {
        try
        {
            mediaRecorder.prepare();
            mediaRecorder.start();
            Log.e("startRecording","startRecording.......................");
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        playrecordbtn.setEnabled(true);
        stoprecordingbtn.setEnabled(true);
        startrecordingbtn.setEnabled(false);
        Toast.makeText(this, "Recording............", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder=null;
        Log.e("stopRecording","stopRecording");
        startrecordingbtn.setEnabled(true);
        playrecordbtn.setEnabled(true);
        stoprecordingbtn.setEnabled(false);
        Toast.makeText(this, "Record Stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void playRecord() {
        mediaPlayer=new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        }
        catch (IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.start();
        Log.e("playRecord","playRecord.................");
        Toast.makeText(this, path+"Playing.........", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void savefile() {
        String localpath= "/storage/emulated/0/Android/data/com.asana.susdriverapp/files";
        path=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString()+ "driverapprecord.3gp")).getPath();
        Log.e("save file",path);
        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
    }
    public void initializeMediaRecorder(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(path);

    }

}



