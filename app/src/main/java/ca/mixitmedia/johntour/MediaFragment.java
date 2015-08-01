package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ca.mixitmedia.johntour.MediaUtilViews.*;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaFragment extends Fragment {

    private static final String TAG = "MediaFragment";
    private static final String ARGS_KEY_SEQ_PT_ID = "SeqPtID";
    private static final String ARGS_KEY_HAS_PHOTO = "HasPhoto";
    private static final String ARGS_KEY_HAS_AUDIO = "HasAudio";
    private static final String ARGS_KEY_HAS_VIDEO = "HasVideo";
    private static final String ARGS_KEY_FROM_GALLERY = "FromGallery";
    private Activity mainActivity;
    private MediaSeekBar seekBar;
    private ImageButton restartBtn;
    private PlayPauseButton playPauseButton;
    private SubtitleView subsBox;
    private FadingMediaControls fmc;
    private SurfaceHolder surfaceHolder;
    private static boolean lastAccessWasGallery;

    public MediaFragment() {
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        IServiceable serviceable = (IServiceable) activity;
        mainActivity = activity;
        Bundle args = getArguments();
        final SequencePoint loadedSeqPt;
        final boolean fromGallery = lastAccessWasGallery = args.getBoolean(ARGS_KEY_FROM_GALLERY);
        if(fromGallery) loadedSeqPt = SequencePoint.list.get(args.getInt(ARGS_KEY_SEQ_PT_ID));
        else loadedSeqPt = SeqManager.getCurrentSeqPt();

        serviceable.setOnServiceConnection(new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                final TourService tourService = ((TourService.TourServiceControl) service).getService();
                if ((lastAccessWasGallery == fromGallery) && (tourService.player!=null && tourService.player.isPlaying())){
                    if (mainActivity != null) attachPlayer(tourService.player, tourService);
                }else {
                    tourService.playSeqPt(loadedSeqPt, new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(final MediaPlayer mp) {
                            if (mainActivity != null) attachPlayer(mp, tourService);
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


        SurfaceView surfaceView = ((SurfaceView) v.findViewById(R.id.video_view));
        surfaceHolder = surfaceView.getHolder();
        seekBar = (MediaSeekBar) v.findViewById(R.id.seek_bar);
        playPauseButton = (PlayPauseButton) v.findViewById(R.id.play_pause_button);
        restartBtn = (ImageButton) v.findViewById(R.id.restart_button);
        subsBox = (SubtitleView) v.findViewById(R.id.subtitle_box);
        fmc = (FadingMediaControls) v.findViewById(R.id.fmc);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmc.showControls();
            }
        });
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    public void attachPlayer(final MediaPlayer mp, TourService service){
        if (!surfaceHolder.isCreating()){
            mp.setDisplay(surfaceHolder);

        }
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
        int subsID =service.videoMode?service.seqPt.getVideoSubsResID():service.seqPt.getAudioSubsResID();
        subsBox.setSubSource(subsID, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
        playPauseButton.setPlayer(mp);
        fmc.setPlayer(mp);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(0);
                mp.start();
            }
        });


    }


    public static MediaFragment newInstance(boolean fromGallery, int SeqPtID){//(int MediaID, boolean hasPhoto, boolean hasAudio, boolean hasVideo) {
        MediaFragment result = new MediaFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY_SEQ_PT_ID, SeqPtID);
//        args.putBoolean(ARGS_KEY_HAS_PHOTO, hasPhoto);
//        args.putBoolean(ARGS_KEY_HAS_AUDIO, hasAudio);
//        args.putBoolean(ARGS_KEY_HAS_VIDEO, hasVideo);
        args.putBoolean(ARGS_KEY_FROM_GALLERY, fromGallery);
        result.setArguments(args);
        return result;
    }
}
