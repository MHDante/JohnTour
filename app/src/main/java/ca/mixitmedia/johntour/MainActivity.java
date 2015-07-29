package ca.mixitmedia.johntour;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import ca.mixitmedia.johntour.TourService.TourServiceControl;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    public static FragmentManager fragmentManager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    TourService tourService;
    private Intent playIntent;
    private boolean serviceBound = false;
    private ServiceConnection onServiceConnection;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            TourServiceControl binder = (TourServiceControl) service;
            //get service
            tourService = binder.getService();
            if(onServiceConnection!= null) onServiceConnection.onServiceConnected(name, service);
            else new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onServiceConnection.onServiceConnected(name, service);
                }
            }, 100);

            //pass list
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            onServiceConnection.onServiceDisconnected(name);
        }
    };

    static void ChangeDisplayLanguage(String language_code, Context context) {

        String old_lang = context.getResources().getConfiguration().locale.getLanguage();
        if (language_code.equals(old_lang)) return;
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language_code.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lang_code = prefs.getString("language", null);
        ChangeDisplayLanguage(lang_code, this);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        fragmentManager = getSupportFragmentManager();
        mViewPager.setCurrentItem(1);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getApplicationContext().bindService(new Intent(getApplicationContext(), TourService.class), serviceConnection, BIND_AUTO_CREATE);
//
//        if (playIntent == null) {
//            playIntent = new Intent(this, TourService.class);
//            if (!isMyServiceRunning(TourService.class)) startService(playIntent);
//            bindService(playIntent, serviceConnection, Context.BIND_AUTO_CREATE);
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setOnServiceConnection(ServiceConnection onServiceConnection) {
        this.onServiceConnection = onServiceConnection;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return position == 0 ? new GalleryFragment() :
                    position == 1 ? new MediaFragment() :
                            position == 2 ? new MapFragment() : null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_gallery).toUpperCase(l);
                case 1:
                    return getString(R.string.title_media).toUpperCase(l);
                case 2:
                    return getString(R.string.title_location).toUpperCase(l);
            }
            return null;
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy ");
    }


}
