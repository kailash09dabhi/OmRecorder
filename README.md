[![Build Status](https://travis-ci.org/kailash09dabhi/OmRecorder.svg?branch=master)](https://travis-ci.org/kailash09dabhi/OmRecorder) [ ![Download](https://api.bintray.com/packages/kailash09dabhi/maven/om-recorder/images/download.svg) ](https://bintray.com/kailash09dabhi/maven/om-recorder/_latestVersion) <a href="http://www.methodscount.com/?lib=com.kailashdabhi%3Aom-recorder%3A1.1.3"><img src="https://img.shields.io/badge/Methods count-132-e91e63.svg"/></a> 
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-OmRecorder-orange.svg?style=flat)](https://android-arsenal.com/details/1/4028)

Om Recorder
============

![Logo](website/static/om.png)

A Simple Pcm / Wav audio recorder with nice api. 

 * Record Pcm audio
 * Record Wav audio
 * Configure audio source to have desired output
 * Record with pause / resume feature
 
<a href='https://play.google.com/store/apps/details?id=com.kingbull.omrecorder&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play'  height="80" src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>

Add these permissions into your `AndroidManifest.xml` and [request for them in Android 6.0+](https://developer.android.com/training/permissions/requesting.html)
```xml
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
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
        new PullTransport.Noise(mic(),
            new PullTransport.OnAudioChunkPulledListener() {
              @Override public void onAudioChunkPulled(AudioChunk audioChunk) {
                animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
              }
            },
            new WriteAction.Default(),
            new Recorder.OnSilenceListener() {
              @Override public void onSilence(long silenceTime) {
                Log.e("silenceTime", String.valueOf(silenceTime));
                Toast.makeText(WavRecorderActivity.this, "silence of " + silenceTime + " detected",
                    Toast.LENGTH_SHORT).show();
              }
            }, 200
        ), file()
    );
      
 @NonNull private File file() {
    return new File(Environment.getExternalStorageDirectory(), "demo.wav");
  }
  
```
__Configure Audio Source__
```java
  return new PullableSource.Default(
         new AudioRecordConfig.Default(
             MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
             AudioFormat.CHANNEL_IN_MONO, 44100
         )
     );
     
   To Enable NoiseSuppresor (android.media.audiofx.NoiseSuppressor)
   
   return new PullableSource.NoiseSuppressor(
          new PullableSource.Default(
              new AudioRecordConfig.Default(
                  MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                  AudioFormat.CHANNEL_IN_MONO, 44100
              )
          )
      );   
      
   To Enable AutomaticGainControl (android.media.audiofx.AutomaticGainControl)
   
   return new PullableSource.AutomaticGainControl(
            new PullableSource.Default(
                new AudioRecordConfig.Default(
                    MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                    AudioFormat.CHANNEL_IN_MONO, 44100
                )
            )
        );    
        
   and if you want both NoiseSuppressor & AutomaticGainControl :-
   
   return new PullableSource.AutomaticGainControl(
           new PullableSource.NoiseSuppressor(
               new PullableSource.Default(
                   new AudioRecordConfig.Default(
                       MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                       AudioFormat.CHANNEL_IN_MONO, 44100
                   )
               )
           )
       );
       
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

Download
--------
    compile 'com.kailashdabhi:om-recorder:1.1.5'
    

Donations
---------

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $7]**: Thank's for creating this project, here's a coffee (or some beer) for you!
* **[Donate $10]**: Wow, I am stunned. Let me take you to the movies!
* **[Donate $16]**: I really appreciate your work, let's grab some lunch!
* **[Donate $25]**: That's some awesome stuff you did right there, dinner is on me!
* **[Donate $52]**: I really really want to support this project, great job!
* **[Donate $97]**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!
* **[Donate $2500]**: Go buddy, buy Macbook Pro for yourself!
Of course, you can also choose what you want to donate, all donations are awesome!

## Contributing Code

If you would like to help provide the other format to be recorded then please implement the  WriteAction interface to provide mp3, amr,etc and then create pull request.

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
    
License
-------

    Copyright 2017 Kailash Dabhi (Kingbull Technology)

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
 [Donate $7]: 		https://www.paypal.me/MrKailashDabhi/7
 [Donate $10]:  		https://www.paypal.me/MrKailashDabhi/10
 [Donate $16]:  		https://www.paypal.me/MrKailashDabhi/16
 [Donate $25]:  		https://www.paypal.me/MrKailashDabhi/25
 [Donate $52]: 		https://www.paypal.me/MrKailashDabhi/52
 [Donate $97]: 		https://www.paypal.me/MrKailashDabhi/97
 [Donate $2500]: 	https://www.paypal.me/MrKailashDabhi/2500
