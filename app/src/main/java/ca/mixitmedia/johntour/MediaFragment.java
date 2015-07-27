package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaFragment extends Fragment implements MediaPlayer.OnTimedTextListener {

    private static final int ControlsDelay = 2000;
    MediaController mediaController;
    SeekBar seekBar;
    private MainActivity mainActivity;
    private float seekIncrementAmount;
    private int fadoutCounter;
    private SurfaceView videoView;
    private ImageButton playBtn, restartBtn;
    private Handler mHandler = new Handler();

    private Runnable r;
    private SubtitleView subsBox;

    public MediaFragment() {
    }
    MediaPlayer mp;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
        mainActivity.setOnServiceConnection(new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {


                Uri mediaUri = Uri.parse("android.resource://" + mainActivity.getPackageName() + "/" + R.raw.intro);
                Uri subsUri = Uri.parse("android.resource://" + mainActivity.getPackageName() + "/" + R.raw.subs_intro);

                final TourService tourService = ((TourService.TourServiceControl) service).getService();
                tourService.playSong(mediaUri, new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mp) {

                        MediaFragment.this.mp = mp;
                        mp.setDisplay(videoView.getHolder());
                        videoView.requestLayout();
                        seekBar.setMax(100);
                        subsBox.setPlayer(mp);
                        subsBox.setSubSource(R.raw.subs_intro, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                        playBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mp.isPlaying()) mp.pause();

                                else mp.start();
                            }
                        });
                        seekIncrementAmount = mp.getDuration() / 100f;
                        r = new Runnable() {
                            @Override
                            public void run() {
                                if (mp.isPlaying()) {
                                    int mCurrentPosition = (int) (mp.getCurrentPosition() / seekIncrementAmount);
                                    seekBar.setProgress(mCurrentPosition);
                                }
                                mHandler.postDelayed(this, 250);

                            }
                        };

                        mainActivity.runOnUiThread(r);

                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                if (fromUser) {
                                    mp.seekTo((int) (progress * seekIncrementAmount));
                                }
                            }
                        });
                    }
                });

                //mp.start();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mp.setDisplay(videoView.getHolder());
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media, container, false);

        videoView = (SurfaceView) v.findViewById(R.id.video_view);

        mediaController = new MediaController(mainActivity, false);


        //videoView.setMediaController(mediaController);
        //videoView.start();
//        Drawable newMessage = getResources().getDrawable(R.drawable.alert);
//        videoView.setBackground(newMessage);
        //videoView.setOnPreparedListener(this);


        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showControls();
                return false;
            }
        });
        seekBar = (SeekBar) v.findViewById(R.id.seek_bar);
        playBtn = (ImageButton) v.findViewById(R.id.play_pause_button);
        restartBtn = (ImageButton) v.findViewById(R.id.restart_button);
        subsBox = (SubtitleView) v.findViewById(R.id.subtitle_box);
        return v;
    }

    private void showControls() {

    }




    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
        mHandler.removeCallbacks(r);
    }

    @Override
    public void onTimedText(MediaPlayer mp, TimedText text) {
        subsBox.setText(text.getText());
        Log.e("HAPPEN","\nAAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!\n" +
                "AAAAAAH!");
    }
}
