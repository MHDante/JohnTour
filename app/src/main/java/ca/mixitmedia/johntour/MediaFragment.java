package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MediaController;

import ca.mixitmedia.johntour.MediaUtils.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaFragment extends Fragment {

    private static final int ControlsDelay = 2000;
    MediaController mediaController;
    private MainActivity mainActivity;
    private int fadoutCounter;
    private MediaSeekBar seekBar;
    private SurfaceView videoView;
    private ImageButton restartBtn;
    private PlayPauseButton playPauseButton;
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
                final TourService tourService = ((TourService.TourServiceControl) service).getService();
                tourService.playSong(R.raw.intro, new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(final MediaPlayer mp) {
                        MediaFragment.this.mp = mp;
                        mp.setDisplay(videoView.getHolder());
                        videoView.requestLayout();
                        seekBar.setPlayer(mp);
                        subsBox.setPlayer(mp);
                        subsBox.setSubSource(R.raw.subs_intro, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                        playPauseButton.setPlayer(mp);
                    }
                });
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
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showControls();
                return false;
            }
        });
        seekBar = (MediaSeekBar) v.findViewById(R.id.seek_bar);
        playPauseButton = (PlayPauseButton) v.findViewById(R.id.play_pause_button);
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
    }
}
