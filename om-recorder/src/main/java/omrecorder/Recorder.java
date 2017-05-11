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

import java.io.IOException;

/**
 * A Recorder who can start and stop recording with startRecording() and stopRecording() method
 * respectively.
 *
 * @author Kailash Dabhi
 * @date 06-07-2016
 */
public interface Recorder {
  void startRecording();

  void stopRecording() throws IOException;

  void pauseRecording();

  void resumeRecording();

  /**
   * Interface definition for a callback to be invoked when a silence is measured.
   */
  interface OnSilenceListener {
    /**
     * Called when a silence measured
     *
     * @param silenceTime The silence measured
     */
    void onSilence(long silenceTime);
  }
}
