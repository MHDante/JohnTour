package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ca.mixitmedia.johntour.MediaUtils.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaFragment extends Fragment {

    private static final String TAG = "MediaFragment";
    private MainActivity mainActivity;
    private MediaSeekBar seekBar;
    private ImageButton restartBtn;
    private PlayPauseButton playPauseButton;
    private SubtitleView subsBox;
    private FadingMediaControls fmc;
    private MediaPlayer mp;
    private SurfaceHolder surfaceHolder;

    public MediaFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
        mainActivity.setOnServiceConnection(new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                final TourService tourService = ((TourService.TourServiceControl) service).getService();
                if (tourService.player!=null && tourService.player.isPlaying()){
                    if (mainActivity != null) attachPlayer(tourService.player);
                }else {
                    tourService.playSong(R.raw.intro, new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(final MediaPlayer mp) {
                            TourService t = tourService;
                            if (mainActivity != null) attachPlayer(mp);
                        }
                    });
                }
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

        surfaceHolder = ((SurfaceView) v.findViewById(R.id.video_view)).getHolder();
        seekBar = (MediaSeekBar) v.findViewById(R.id.seek_bar);
        playPauseButton = (PlayPauseButton) v.findViewById(R.id.play_pause_button);
        restartBtn = (ImageButton) v.findViewById(R.id.restart_button);
        subsBox = (SubtitleView) v.findViewById(R.id.subtitle_box);
        fmc = (FadingMediaControls) v.findViewById(R.id.fmc);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public void attachPlayer(final MediaPlayer mp){
        this.mp = mp;
        if (!surfaceHolder.isCreating()) mp.setDisplay(surfaceHolder);
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mp.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mp.setDisplay(null);
            }
        });
        seekBar.setPlayer(mp);
        subsBox.setPlayer(mp);
        subsBox.setSubSource(R.raw.subs_intro, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
        playPauseButton.setPlayer(mp);
        fmc.setPlayer(mp);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

    }



}
