/**
 * Copyright 2017 Kailash Dabhi (Kingbull Technology)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author Kailash Dabhi
 * @date 18-07-2016.
 * Copyright (c) 2017 Kingbull Technology. All rights reserved.
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
