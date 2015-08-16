package ca.mixitmedia.johntour;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TourService extends IntentService implements MediaPlayer.OnErrorListener {
    private static final String TAG = "TourService";
    private TourServiceControl mBinder = new TourServiceControl();
    MediaPlayer player;
    SequencePoint seqPt;
    boolean videoMode;

    public TourService() {
        super("TourService");
    }

    public void playSeqPt(SequencePoint sequencePoint, final OnPreparedListener OPL) {
            this.seqPt = sequencePoint;
            if (player!=null) player.release();
            player = new MediaPlayer();
            player.setOnErrorListener(this);

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    startMedia(false, OPL);
                    player.setOnCompletionListener(null);
                }
            });

            startMedia(true, OPL);

    }

    private void startMedia(boolean video, OnPreparedListener OPL){
        try {
        videoMode = video;
        Uri mediaUri = Uri.parse("android.resource://" + getPackageName() + "/" + (videoMode?seqPt.getVideoResID():seqPt.getAudioResID()));
        //videoMode = true;
            player.reset();
        player.setDataSource(this,mediaUri);
        player.setOnPreparedListener(OPL);
        player.prepareAsync();


        } catch (Exception e) {
            Log.e(TAG, "wtf",e);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (!player.isPlaying()) stopSelf();
        return super.onUnbind(intent);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //no-op
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError "+what + " , " + extra);
        return false;
    }

    public class TourServiceControl extends Binder {
        TourService getService() {
            return TourService.this;
        }
    }

}
