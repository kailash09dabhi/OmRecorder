package omrecorder;

import android.media.AudioRecord;

/**
 * @author Kailash Dabhi
 * @date 01 July, 2017 12:34 PM
 */
final class MinimumBufferSize {
  private final int minimumBufferSize;

  MinimumBufferSize(AudioRecordConfig audioRecordConfig) {
    this.minimumBufferSize = AudioRecord.getMinBufferSize(audioRecordConfig.frequency(),
        audioRecordConfig.channelPositionMask(), audioRecordConfig.audioEncoding());
  }

  int asInt() {
    return minimumBufferSize;
  }
}
