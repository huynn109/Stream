package dev.hw.app.streaming.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import dev.hw.app.streaming.R;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

public class StreamFullscreenActivity extends ActionBarActivity  implements
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnInfoListener {

    private String path;
    private VideoView surfaceViewFull;
    private CircularProgressView myProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_fullscreen);

        path = this.getIntent().getStringExtra("path");
        this.addControls();
        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(
                   this,
                    "Please edit VideoBuffer Activity, and set path"
                            + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
        } else {
      /*
       * Alternatively,for streaming media you can use
       * mVideoView.setVideoURI(Uri.parse(URLstring));
       */
            surfaceViewFull.setVideoURI(Uri.parse(path));
            surfaceViewFull.requestFocus();
            surfaceViewFull.setOnInfoListener(this);
            surfaceViewFull.setOnBufferingUpdateListener(this);
            surfaceViewFull.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
        }
    }

    private void addControls() {
        surfaceViewFull = (VideoView) findViewById(R.id.surface_view_full);
        myProgressBar = (CircularProgressView) findViewById(R.id.circular_progress_view_video);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (surfaceViewFull.isPlaying()) {
                    surfaceViewFull.pause();
                    showProgress();
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                surfaceViewFull.start();
                hideProgress();
                break;
        }
        return true;
    }

    /**
     * Show circular progress dialog
     */
    private void showProgress() {
        myProgressBar.setIndeterminate(true);
        myProgressBar.startAnimation();
    }

    /**
     * Hide circular progress dialog
     */
    private void hideProgress() {
        myProgressBar.setIndeterminate(false);
        myProgressBar.resetAnimation();
    }
}
