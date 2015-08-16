package ca.mixitmedia.johntour;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GalleryDetailActivity extends AppCompatActivity{

    public static final String ITEM_ID = "itemId";
    public int seqID;
    MediaFragment mediaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        // If not already added to the Fragment manager add it. If you don't do this a new Fragment will be added every time this method is called (Such as on orientation change)
        mediaFragment = MediaFragment.newInstance(true, seqID);
        seqID = getIntent().getIntExtra(ITEM_ID, 0);
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mediaFragment).commit();

    }
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            TourService.TourServiceControl binder = (TourService.TourServiceControl) service;
            //get service
            if(mediaFragment!= null) mediaFragment.onServiceConnected(name, service);
            else new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaFragment.onServiceConnected(name, service);
                }
            }, 100);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mediaFragment.onServiceDisconnected(name);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    protected void onStart() {
        getApplicationContext().bindService(new Intent(getApplicationContext(), TourService.class), serviceConnection, BIND_AUTO_CREATE);
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
