package ca.mixitmedia.johntour;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class TourService extends IntentService {
    private static final String TAG = "TourService";
    private TourServiceControl mBinder = new TourServiceControl();
    private MediaPlayer player;

    public TourService() {
        super("TourService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public void playSong(int resId, final OnPreparedListener OPL) {
        Uri mediaUri = Uri.parse("android.resource://" + getPackageName() + "/" + resId);
        if(player != null) {player.release();}
        player = new MediaPlayer();
        try {
            player.setDataSource(this,mediaUri);
            player.setOnPreparedListener(OPL);
            player.prepare();


        } catch (Exception e) {
            Log.e(TAG, "wtf",e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //no-op
    }

    public class TourServiceControl extends Binder {
        TourService getService() {
            return TourService.this;
        }
    }

}
