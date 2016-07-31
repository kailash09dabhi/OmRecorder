package com.kingbull.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.kailashdabhi.audiorecord.AudioChunk;
import com.kailashdabhi.audiorecord.AudioSource;
import com.kailashdabhi.audiorecord.OmRecorder;
import com.kailashdabhi.audiorecord.PullTransport;
import com.kailashdabhi.audiorecord.Recorder;
import com.kailashdabhi.audiorecord.WriteAction;
import java.io.File;

/**
 * Created by Kailash Dabhi on 18-07-2016.
 * You can contact us at kailash09dabhi@gmail.com OR on skype(kailash.09)
 * Copyright (c) 2016 Kingbull Technology. All rights reserved.
 */
public class WavRecorderActivity extends AppCompatActivity {
  Recorder recorder;
  ImageView recordButton;
  CheckBox skipSilence;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recorder);
    getSupportActionBar().setTitle("Wav Recorder");
    setupRecorder();
    skipSilence = (CheckBox) findViewById(R.id.skipSilence);
    skipSilence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
          setupNoiseRecorder();
        } else {
          setupRecorder();
        }
      }
    });

    recordButton = (ImageView) findViewById(R.id.recordButton);
    recordButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        recorder.startRecording();
        skipSilence.setEnabled(false);
      }
    });
    findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        recorder.stopRecording();
        skipSilence.setEnabled(true);
        recordButton.post(new Runnable() {
          @Override public void run() {
            animateVoice(0);
          }
        });
      }
    });
  }

  private void setupNoiseRecorder() {

    recorder = OmRecorder.wav(
        new PullTransport.Noise(mic(), new PullTransport.OnAudioChunkPulledListener() {
          @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
            animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
          }
        }, new WriteAction.Default(), new Recorder.OnSilenceListener() {
          @Override public void onSilence(long silenceTime) {
            Log.e("silenceTime", String.valueOf(silenceTime));
            Toast.makeText(WavRecorderActivity.this, "silence of " + silenceTime + " detected",
                Toast.LENGTH_SHORT).show();
          }
        }, 200), file());
  }

  private void setupRecorder() {
    recorder = OmRecorder.wav(
        new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
          @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
            animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
          }
        }), file());
  }

  private void animateVoice(final float maxPeak) {
    recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
  }

  private AudioSource mic() {
    return new AudioSource.Smart(MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
        AudioFormat.CHANNEL_IN_MONO, 44100);
  }

  @NonNull private File file() {
    return new File(Environment.getExternalStorageDirectory(), "kailashdabhi.wav");
  }
}
