[ ![Download](https://api.bintray.com/packages/kailash09dabhi/maven/om-recorder/images/download.svg) ](https://bintray.com/kailash09dabhi/maven/om-recorder/_latestVersion) <a href="http://www.methodscount.com/?lib=com.kailashdabhi%3Aom-recorder%3A1.1.0"><img src="https://img.shields.io/badge/Methods and size-126 | 18 KB-e91e63.svg"/></a>[![Android Arsenal] (https://img.shields.io/badge/Android%20Arsenal-OmRecorder-orange.svg?style=flat)](http://android-arsenal.com/details/1/4028)
Om Recorder
============

![Logo](website/static/om.png)

A Simple Pcm / Wav audio recorder with nice api. 

 * Record Pcm audio
 * Record Wav audio
 * Configure audio source to have desired output
 * Record with pause / resume feature

```java

  recorder = OmRecorder.wav(
      new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
        @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
          animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
        }
      }), file());
```   
__For Skip Silence__
```java
  // FOR SKIP SILENCE     
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
      }, 200/**silence threshold**/), file());
      
 @NonNull private File file() {
    return new File(Environment.getExternalStorageDirectory(), "demo.wav");
  }
  
```
__Configure Audio Source__
```java
  private AudioSource mic() {
    return new AudioSource.Smart(MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
        AudioFormat.CHANNEL_IN_MONO, 44100);
  }

```
__Start & Stop Recording__
```java
    recorder.startRecording();
    recorder.stopRecording();
```
__Pause & Resume Recording__
```java
    recorder.pauseRecording();
    recorder.resumeRecording();
```

For documentation and additional information see [the website][1].

__OmRecorder__

Download
--------
    compile 'com.kailashdabhi:om-recorder:1.1.0'
License
-------

    Copyright 2016 Kailash Dabhi (Kingbull Technology)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: https://kailash09dabhi.github.io/OmRecorder/



