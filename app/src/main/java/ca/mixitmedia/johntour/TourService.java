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

public class TourService extends IntentService implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    private static final String TAG = "TourService";
    private boolean prepared;
    private OnPreparedListener PrepListen;
    private TourServiceControl mBinder = new TourServiceControl();

    private TextView txtDisplay;
    private static Handler handler = new Handler();
    MediaPlayer player;

    public TourService() {
        super("TourService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public void playSong(Uri uri, final OnPreparedListener OPL) {
        if(player != null) {player.release();}
        player = new MediaPlayer();
        player.setOnErrorListener(this);
         //MediaPlayer.create(this, R.raw.intro);


        try {
            player.setDataSource(this,uri);
            player.setOnPreparedListener(OPL);
            player.prepare();


        } catch (Exception e) {
            Log.e(TAG, "wtf",e);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //player.stop();
        //player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {


        Log.e(TAG, "wtf");
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


    public class TourServiceControl extends Binder {
        TourService getService() {
            return TourService.this;
        }
    }

}
