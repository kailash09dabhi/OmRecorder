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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An AudioChunk is a audio data wrapper.
 *
 * @author Kailash Dabhi
 * @date 20-07-2016
 */
public interface AudioChunk {
  double maxAmplitude();

  byte[] toBytes();

  short[] toShorts();

  int readCount();

  void readCount(int numberOfUnitThatWereRead);

  final class Bytes implements AudioChunk {
    private static final double REFERENCE = 0.6;
    private final byte[] bytes;
    private int numberOfBytesRead;

    Bytes(byte[] bytes) {
      this.bytes = bytes;
    }

    @Override public double maxAmplitude() {
      short[] shorts = toShorts();
      int nMaxAmp = 0;
      int arrLen = shorts.length;
      int peakIndex;
      for (peakIndex = 0; peakIndex < arrLen; peakIndex++) {
        if (shorts[peakIndex] >= nMaxAmp) {
          nMaxAmp = shorts[peakIndex];
        }
      }
      return (int) (20 * Math.log10(nMaxAmp / REFERENCE));
    }

    @Override public byte[] toBytes() {
      return bytes;
    }

    @Override public short[] toShorts() {
      short[] shorts = new short[bytes.length / 2];
      ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
      return shorts;
    }

    @Override public int readCount() {
      return numberOfBytesRead;
    }

    @Override public void readCount(int numberOfUnitThatWereRead) {
      this.numberOfBytesRead = numberOfUnitThatWereRead;
    }
  }

  final class Shorts implements AudioChunk {
    //number denotes the bytes read in @code buffer
    private static final short SILENCE_THRESHOLD = 2700;
    private static final double REFERENCE = 0.6;
    private final short[] shorts;
    private int numberOfShortsRead;

    Shorts(short[] bytes) {
      this.shorts = bytes;
    }

    int peakIndex() {
      int peakIndex;
      int arrLen = shorts.length;
      for (peakIndex = 0; peakIndex < arrLen; peakIndex++) {
        if ((shorts[peakIndex] >= SILENCE_THRESHOLD) || (shorts[peakIndex] <= -SILENCE_THRESHOLD)) {
          return peakIndex;
        }
      }
      return -1;
    }

    @Override public double maxAmplitude() {
      int nMaxAmp = 0;
      int arrLen = shorts.length;
      int peakIndex;
      for (peakIndex = 0; peakIndex < arrLen; peakIndex++) {
        if (shorts[peakIndex] >= nMaxAmp) {
          nMaxAmp = shorts[peakIndex];
        }
      }
      return (int) (20 * Math.log10(nMaxAmp / REFERENCE));
    }

    @Override public byte[] toBytes() {
      int shortIndex, byteIndex;
      byte[] buffer = new byte[numberOfShortsRead * 2];
      shortIndex = byteIndex = 0;
      for (; shortIndex != numberOfShortsRead; ) {
        buffer[byteIndex] = (byte) (shorts[shortIndex] & 0x00FF);
        buffer[byteIndex + 1] = (byte) ((shorts[shortIndex] & 0xFF00) >> 8);
        ++shortIndex;
        byteIndex += 2;
      }
      return buffer;
    }

    @Override public short[] toShorts() {
      return shorts;
    }

    @Override public int readCount() {
      return numberOfShortsRead;
    }

    @Override public void readCount(int numberOfUnitThatWereRead) {
      this.numberOfShortsRead = numberOfUnitThatWereRead;
    }
  }
}
