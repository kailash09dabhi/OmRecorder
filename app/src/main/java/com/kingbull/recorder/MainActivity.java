package com.kingbull.recorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kailash Dabhi on 18-07-2016.
 * You can contact us at kailash09dabhi@gmail.com OR on skype(kailash.09)
 * Copyright (c) 2016 Kingbull Technology. All rights reserved.
 */
public class MainActivity extends AppCompatActivity {
  private final static String DEMO_PCM = "Pcm Recorder";
  private final static String DEMO_WAV = "Wav Recorder";
  ListView listView;
  String[] demoArray = new String[] { "Pcm Recorder", "Wav Recorder" };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listView = (ListView) findViewById(android.R.id.list);
    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, demoArray));
    listView.setOnItemClickListener(new OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (demoArray[i]) {
          case DEMO_PCM:
            startActivity(new Intent(MainActivity.this, PcmRecorderActivity.class));
            break;
          case DEMO_WAV:
            startActivity(new Intent(MainActivity.this, WavRecorderActivity.class));
            break;
        }
      }
    });
  }
}
