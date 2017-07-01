package omrecorder;

import android.media.AudioRecord;

/**
 * An {@code PullableSource} represents {@link Source} which is Pullable
 *
 * @author Kailash Dabhi
 * @date 01-07-2017
 */
public interface PullableSource extends Source {
  /**
   * @return number of bytes to be read from @{@link Source}
   */
  int pullSizeInBytes();

  void isEnableToBePulled(boolean enabledToBePulled);

  boolean isEnableToBePulled();

  AudioRecord preparedToBePulled();

  class Default extends Source.Default implements PullableSource {
    private final int pullSizeInBytes;
    private volatile boolean pull;

    public Default(AudioRecordConfig config, int pullSizeInBytes) {
      super(config);
      this.pullSizeInBytes = pullSizeInBytes;
    }

    public Default(AudioRecordConfig config) {
      super(config);
      this.pullSizeInBytes = minimumBufferSize();
    }

    @Override
    public int pullSizeInBytes() {
      return pullSizeInBytes;
    }

    @Override
    public void isEnableToBePulled(boolean enabledToBePulled) {
      this.pull = enabledToBePulled;
    }

    @Override
    public boolean isEnableToBePulled() {
      return pull;
    }

    @Override
    public AudioRecord preparedToBePulled() {
      final AudioRecord audioRecord = audioRecord();
      audioRecord.startRecording();
      isEnableToBePulled(true);
      return audioRecord;
    }
  }


}