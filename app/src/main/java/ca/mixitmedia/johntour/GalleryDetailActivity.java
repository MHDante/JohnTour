package ca.mixitmedia.johntour;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class GalleryDetailActivity extends AppCompatActivity implements IServiceable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
    }
    private ServiceConnection onServiceConnection;
    private TourService tourService;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            TourService.TourServiceControl binder = (TourService.TourServiceControl) service;
            //get service
            tourService = binder.getService();
            if(onServiceConnection!= null) onServiceConnection.onServiceConnected(name, service);
            else new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onServiceConnection.onServiceConnected(name, service);
                }
            }, 100);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            onServiceConnection.onServiceDisconnected(name);
        }
    };

    public void setOnServiceConnection(ServiceConnection onServiceConnection) {
        this.onServiceConnection = onServiceConnection;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deleteme, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
