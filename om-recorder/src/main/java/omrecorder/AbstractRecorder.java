/**
 * Copyright 2017 Kailash Dabhi (Kingbull Technology)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package omrecorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Kailash Dabhi
 * @date 22-08-2016.
 * Copyright (c) 2017 Kingbull Technology. All rights reserved.
 */
public abstract class AbstractRecorder implements Recorder {
  protected final PullTransport pullTransport;
  protected final File file;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();
  private OutputStream outputStream;
  private final Runnable recordingTask = new Runnable() {
    @Override public void run() {
      try {
        pullTransport.start(outputStream);
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (IllegalStateException e) {
        throw new RuntimeException("AudioRecord state has uninitialized state", e);
      }
    }
  };

  protected AbstractRecorder(PullTransport pullTransport, File file) {
    this.pullTransport = pullTransport;
    this.file = file;
  }

  @Override public void startRecording() {
    outputStream = outputStream(file);
    executorService.submit(recordingTask);
  }

  private OutputStream outputStream(File file) {
    if (file == null) {
      throw new RuntimeException("file is null !");
    }
    OutputStream outputStream;
    try {
      outputStream = new FileOutputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(
          "could not build OutputStream from" + " this file " + file.getName(), e);
    }
    return outputStream;
  }

  @Override public void stopRecording() throws IOException {
    pullTransport.stop();
    outputStream.flush();
    outputStream.close();
  }

  @Override public void pauseRecording() {
    pullTransport.pullableSource().isEnableToBePulled(false);
  }

  @Override public void resumeRecording() {
    pullTransport.pullableSource().isEnableToBePulled(true);
    executorService.submit(recordingTask);
  }
}
