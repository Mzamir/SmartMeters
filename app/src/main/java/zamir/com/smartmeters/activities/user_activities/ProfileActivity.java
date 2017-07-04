package zamir.com.smartmeters.activities.user_activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.User;

/**
 * Created by MahmoudSamir on 4/22/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTxt, emailTxt, phoneTxt, addressTxt, meterCodeTxt, buildingNumberTxt, apartmentNumberTxt;
    String TAG = "ProfileFragment";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        Log.e(TAG + " init", "1");
        nameTxt = (TextView) findViewById(R.id.user_name);
        phoneTxt = (TextView) findViewById(R.id.user_phone);
        emailTxt = (TextView) findViewById(R.id.user_email);
        addressTxt = (TextView) findViewById(R.id.user_city);
        meterCodeTxt = (TextView) findViewById(R.id.meterCode);
        buildingNumberTxt = (TextView) findViewById(R.id.buildingNumber);
        apartmentNumberTxt = (TextView) findViewById(R.id.apartmentNumber);

        user = getDataFromSharedPrefence();
        if (user != null) {
            Log.e("User", user.getUsername());
            nameTxt.setText(user.getUsername().toString());
            emailTxt.setText(user.getEmail().toString());
            meterCodeTxt.setText(user.getMeterCode().toString());
            addressTxt.setText(user.getAddress().toString());
            phoneTxt.setText(user.getBuildingNumber().toString());
            buildingNumberTxt.setText(user.getBuildingNumber().toString());
            apartmentNumberTxt.setText(user.getApartmentNumber().toString());
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}

