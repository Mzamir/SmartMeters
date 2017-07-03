package zamir.com.smartmeters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.user_activities.LoginActivity;

/**
 * Created by MahmoudSamir on 2/20/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int WAITTIME = 2000;
    private static final int SLEEPTIME = 1000;
    TextView logoText;
    public static FirebaseAuth.AuthStateListener authListener;
    public static FirebaseAuth auth;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Splash", "onCreate");
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        checkUserState();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // sleep(WAITTIME) ;
                    while (waited < WAITTIME) {
                        sleep(SLEEPTIME);
                        waited += SLEEPTIME;
                    }
                } catch (InterruptedException e) {
                } finally {
                    Log.e("Splash ", "Finally");
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }

    private void checkUserState() {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.e("Splash ", "user null");
                    // user auth state is changed - user is null
                    // launch login activity
                    intent = new Intent(SplashActivity.this, LoginActivity.class);

                } else {
                    Log.e("Splash ", "user not null");
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
