package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static ca.mixitmedia.johntour.MainActivity.ChangeDisplayLanguage;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Runnable transition = new Runnable() {
        @Override
        public void run() {
            if (!isFirstTime) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();

            } else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                ft.replace(R.id.splash_fragment, new LanguageFragment());
                ft.commit();
            }
            transitioned = true;
        }
    };
    private boolean isFirstTime;
    private boolean transitioned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( MainActivity.isMyServiceRunning(TourService.class, this)){
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        } else setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isFirstTime = prefs.getBoolean("isFirstTime", true);
        if (!transitioned) handler.postDelayed(transition, 1500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(transition);
    }

    public static class LogosFragment extends Fragment {
        private SplashActivity splashActivity;

        public LogosFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            splashActivity = (SplashActivity) activity;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            splashActivity = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_logos, container, false);
        }

    }

    public static class HeadphonesFragment extends Fragment {
        private SplashActivity splashActivity;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashActivity, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        };
        public HeadphonesFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            splashActivity = (SplashActivity) activity;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            splashActivity = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_headphones, container, false);
        }
    }

    public static class LanguageFragment extends Fragment {
        private SplashActivity splashActivity;

        public LanguageFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            splashActivity = (SplashActivity) activity;
        }

        @Override
        public void onDetach() {
            super.onDetach();
            splashActivity = null;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_language, container, false);
            View.OnClickListener c = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String language_code = "EN";
                    if (v.getId() == R.id.english_button) language_code = "en";
                    else if (v.getId() == R.id.french_button) language_code = "fr";
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(splashActivity);
                    prefs.edit()
                            .putString("language", language_code)
                            //.putBoolean("isFirstTime", false)
                            .apply();


                    ChangeDisplayLanguage(language_code, splashActivity);
                    splashActivity.isFirstTime = false;
                    splashActivity.transitioned = false;
                    splashActivity.handler.postDelayed(splashActivity.transition, 2000);
                    FragmentManager manager = splashActivity.getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    ft.replace(R.id.splash_fragment, new HeadphonesFragment());
                    ft.commit();

                }
            };
            v.findViewById(R.id.english_button).setOnClickListener(c);
            v.findViewById(R.id.french_button).setOnClickListener(c);

            return v;
        }


    }

}
