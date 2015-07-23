package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnTimedTextListener {

    private static final int ControlsDelay = 2000;
    MediaController mediaController;
    SeekBar seekBar;
    private MainActivity mainActivity;
    private float seekIncrementAmount;
    private int fadoutCounter;
    private VideoView videoView;
    private ImageButton playBtn, restartBtn;
    private Handler mHandler = new Handler();
    private MediaPlayer mediaPlayer;

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                int mCurrentPosition = (int) (mediaPlayer.getCurrentPosition() / seekIncrementAmount);
                seekBar.setProgress(mCurrentPosition);
            }
            mHandler.postDelayed(this, 250);
            videoView.requestLayout();
        }
    };
    private TextView subsBox;

    public MediaFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
        mainActivity.setOnServiceConnection(new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Uri mediaUri = Uri.parse("android.resource://" + mainActivity.getPackageName() + "/" + R.raw.intro);
                Uri subsUri = Uri.parse("android.resource://" + mainActivity.getPackageName() + "/" + R.raw.intro_subs);

                TourService tourService = ((TourService.TourServiceControl) service).getService();
                tourService.playSong(mediaUri);
                tourService.setOnPreparedListener(MediaFragment.this);
                tourService.mediaPlayer.setDisplay(videoView.getHolder());
                FileDescriptor fd = getResources().openRawResourceFd(R.raw.intro_subs).getFileDescriptor();
                tourService.mediaPlayer.addTimedTextSource(fd,MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                tourService.mediaPlayer.setOnTimedTextListener(MediaFragment.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media, container, false);

        videoView = (VideoView) v.findViewById(R.id.video_view);

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
        subsBox = (TextView) v.findViewById(R.id.subtitle_box);
        return v;
    }

    private void showControls() {

    }

    private File copyFile(String filename){
        File file = new File(mainActivity.getFilesDir(), filename);
        try {
            InputStream in = getResources().openRawResource(R.raw.intro_subs);
            FileOutputStream out = mainActivity.openFileOutput(filename + ".srt", Context.MODE_PRIVATE);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            in.close();
            out.close();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return file;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
        mHandler.removeCallbacks(r);
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        mediaPlayer = mp;
        seekBar.setMax(100);
        seekIncrementAmount = mediaPlayer.getDuration() / 100f;
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
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo((int) (progress * seekIncrementAmount));

                    Log.d("handler", Integer.toString((int) (progress * seekIncrementAmount)));
                }
            }
        });
    }

    @Override
    public void onTimedText(MediaPlayer mp, TimedText text) {
        subsBox.setText(text.getText());
    }
}
