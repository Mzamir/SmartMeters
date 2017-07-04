package zamir.com.smartmeters.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.user_activities.LoginActivity;
import zamir.com.smartmeters.activities.user_activities.ProfileActivity;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.fragments.HomeFragment;
import zamir.com.smartmeters.fragments.BillFragment;
import zamir.com.smartmeters.fragments.ProfileFragment;
import zamir.com.smartmeters.model.User;
import zamir.com.smartmeters.utils.NotificationUtils;

import static zamir.com.smartmeters.activities.SplashActivity.auth;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView userNmae, userEmail, notification;
    String email;
    String registrationToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Log.e("MainActivity", mAuth.toString());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        email = intent.getStringExtra(Config.KEY_EMAIL);
        Log.e(TAG, "BeforeIntent");
        if (intent.getBooleanExtra(Config.FIRST_TIME_TO_LOGIN, false) == true) {
            registrationToken = FirebaseInstanceId.getInstance().getToken();
            Log.e(TAG, registrationToken);
            addRegistrationToken(email, registrationToken);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();
        notification = (TextView) findViewById(R.id.notification);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    notification.setVisibility(View.VISIBLE);
                    notification.setText(message);
                }
            }
        };
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        userEmail = (TextView) header.findViewById(R.id.user_email);
        userNmae = (TextView) header.findViewById(R.id.user_name);
        Log.e("User", "Before");
        User user = getDataFromSharedPrefence();
        if (user != null) {
            Log.e("User", user.getUsername());
            userEmail.setText(user.getEmail().toString());
            userNmae.setText(user.getUsername().toString());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        String title = "Home";
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            title = "Home";
            fragment = new HomeFragment();
        } else if (id == R.id.nav_profile) {
            title = "Profile";
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_consumption) {
            title = "Bills";
            fragment = new BillFragment();
        } else if (id == R.id.nav_signout) {
            signOut();
            return true;
        }
        toolbar.setTitle(title);
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //sign out method
    public void signOut() {
        if (auth != null) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    private User getDataFromSharedPrefence() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_USER, 0);
        String username = pref.getString(Config.KEY_username, null);
        String email = pref.getString(Config.KEY_EMAIL, null);
        String password = pref.getString(Config.KEY_PASSWORD, null);
        String meterCode = pref.getString(Config.KEY_meterCode, null);
        String buildingNumber = pref.getString(Config.KEY_buildingNumber, null);
        String apartmentNumber = pref.getString(Config.KEY_apartmentNumber, null);
        String address = pref.getString(Config.KEY_address, null);
        String updated_at = pref.getString(Config.KEY_updated_at, null);
        String created_at = pref.getString(Config.KEY_created_at, null);
        return new User(email, password, meterCode, buildingNumber, apartmentNumber, username, address);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void addRegistrationToken(final String email, final String registrationToken) {
        //Creating a string request
        StringRequest addTokenRequest = new StringRequest(Request.Method.POST, Config.UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, jsonObject.getString(Config.RESPONSE_MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_registrationToken, registrationToken);

                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(addTokenRequest);
    }
}
