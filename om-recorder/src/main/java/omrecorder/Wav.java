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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * {@code Wav} is recorder for recording audio in wav format.
 *
 * @author Kailash Dabhi (kailash09dabhi@gmail.com)
 * @date 31-07-2016
 * @skype kailash.09
 */
final class Wav extends AbstractRecorder {
  private final RandomAccessFile wavFile;

  public Wav(PullTransport pullTransport, File file) {
    super(pullTransport,file);
    this.wavFile = randomAccessFile(file);
  }

  private RandomAccessFile randomAccessFile(File file) {
    RandomAccessFile randomAccessFile;
    try {
      randomAccessFile = new RandomAccessFile(file, "rw");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return randomAccessFile;
  }

  @Override public void stopRecording() {
    super.stopRecording();
    try {
      writeWavHeader();
    } catch (IOException e) {
    }
  }

  private void writeWavHeader() throws IOException {
    long totalAudioLen = new FileInputStream(file).getChannel().size();
    try {
      wavFile.seek(0); // to the beginning
      wavFile.write(new WavHeader(pullTransport.source(), totalAudioLen).toBytes());
      wavFile.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}