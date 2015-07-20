package ca.mixitmedia.johntour;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean isFirstTime = prefs.getBoolean("isFirstTime", true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirstTime) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                } else {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    ft.replace(R.id.splash_fragment, new LanguageFragment());
                    ft.commit();
                }
            }
        }, 1500);
    }

    public static class LogosFragment extends Fragment {
        public LogosFragment() {
        }

        private SplashActivity splashActivity;

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
        public HeadphonesFragment() {
        }

        private SplashActivity splashActivity;

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
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(splashActivity, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(i);
                }
            }, 500);
            return inflater.inflate(R.layout.fragment_headphones, container, false);
        }

    }

    public static class LanguageFragment extends Fragment {
        public LanguageFragment() {
        }

        private SplashActivity splashActivity;

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
                    if (v.getId() == R.id.english_button) language_code = "EN";
                    else if (v.getId() == R.id.french_button) language_code = "FR";

                    Resources res = splashActivity.getResources();
                    // Change locale settings in the app.
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.locale = new Locale(language_code.toLowerCase());
                    res.updateConfiguration(conf, dm);

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
