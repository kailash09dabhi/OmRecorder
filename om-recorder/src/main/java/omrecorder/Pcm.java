/*
 * Copyright (C) 2016 Kailash Dabhi (Kingbull Technology)
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
package omrecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * {@code Pcm} is recorder for recording audio in wav format.
 * @author Kailash Dabhi (kailash09dabhi@gmail.com)
 * @date 31-07-2016
 * @skype kailash.09
 */
final class Pcm implements Recorder {
  private final PullTransport pullTransport;
  private final File file;

  public Pcm(PullTransport pullTransport, File file) {
    this.pullTransport = pullTransport;
    this.file = file;
  }

  @Override public void startRecording() {
    new Thread(new Runnable() {
      @Override public void run() {
        try {
          pullTransport.start(new FileOutputStream(file));
        } catch (IOException e) {
          new RuntimeException(e);
        }
      }
    }).start();
  }

  @Override public void stopRecording() {
    pullTransport.stop();
  }
}