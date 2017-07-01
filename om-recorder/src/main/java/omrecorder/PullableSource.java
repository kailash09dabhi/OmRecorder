package omrecorder;

import android.media.AudioRecord;
import android.os.Build;
import android.util.Log;

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

  class Base implements PullableSource {
    private final PullableSource pullableSource;

    Base(PullableSource pullableSource) {
      this.pullableSource = pullableSource;
    }

    @Override
    public AudioRecord audioRecord() {
      return pullableSource.audioRecord();
    }

    @Override
    public AudioRecordConfig config() {
      return pullableSource.config();
    }

    @Override
    public int minimumBufferSize() {
      return pullableSource.minimumBufferSize();
    }

    @Override
    public int pullSizeInBytes() {
      return pullableSource.pullSizeInBytes();
    }

    @Override
    public void isEnableToBePulled(boolean enabledToBePulled) {
      pullableSource.isEnableToBePulled(enabledToBePulled);
    }

    @Override
    public boolean isEnableToBePulled() {
      return pullableSource.isEnableToBePulled();
    }

    @Override
    public AudioRecord preparedToBePulled() {
      return pullableSource.preparedToBePulled();
    }
  }

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

  class NoiseSuppressor extends Base {
    public NoiseSuppressor(PullableSource pullableSource) {
      super(pullableSource);
    }

    @Override
    public AudioRecord preparedToBePulled() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        if (android.media.audiofx.NoiseSuppressor.isAvailable()) {
          android.media.audiofx.NoiseSuppressor noiseSuppressor = android.media.audiofx.NoiseSuppressor
              .create(audioRecord().getAudioSessionId());
          if (noiseSuppressor != null) {
            noiseSuppressor.setEnabled(true);
            Log.i(getClass().getSimpleName(), "NoiseSuppressor ON");
          } else {
            Log.i(getClass().getSimpleName(), "NoiseSuppressor failed :(");
          }
        } else {
          Log.i(getClass().getSimpleName(), "This device don't support NoiseSuppressor");
        }
      } else {
        Log.i(getClass().getSimpleName(),
            "For this effect, Android api should be higher than or equals 16");
      }
      return super.preparedToBePulled();
    }
  }

  class AutomaticGainControl extends Base {
    public AutomaticGainControl(PullableSource pullableSource) {
      super(pullableSource);
    }

    @Override
    public AudioRecord preparedToBePulled() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        if (android.media.audiofx.AutomaticGainControl.isAvailable()) {
          android.media.audiofx.AutomaticGainControl automaticGainControl = android.media.audiofx.AutomaticGainControl
              .create(audioRecord().getAudioSessionId());
          if (automaticGainControl != null) {
            automaticGainControl.setEnabled(true);
            Log.i(getClass().getSimpleName(), "AutomaticGainControl ON");
          } else {
            Log.i(getClass().getSimpleName(), "AutomaticGainControl failed :(");
          }
        } else {
          Log.i(getClass().getSimpleName(), "This device don't support AutomaticGainControl");
        }
      } else {
        Log.i(getClass().getSimpleName(),
            "For this effect, Android api should be higher than or equals 16");
      }
      return super.preparedToBePulled();
    }
  }
}