package zamir.com.smartmeters.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.User;


public class ProfileFragment extends Fragment {

    FirebaseUser firebaseUser;
    private TextView nameTxt, emailTxt, phoneTxt, addressTxt, meterCodeTxt, buildingNumberTxt, apartmentNumberTxt;
    String TAG = "ProfileFragment";
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        init(rootView);
        Log.e(TAG, "1");
        return rootView;
    }

    private void init(View view) {
        Log.e(TAG + " init", "1");
        nameTxt = (TextView) view.findViewById(R.id.user_name);
        phoneTxt = (TextView) view.findViewById(R.id.user_phone);
        emailTxt = (TextView) view.findViewById(R.id.user_email);
        addressTxt = (TextView) view.findViewById(R.id.user_city);
        meterCodeTxt = (TextView) view.findViewById(R.id.meterCode);
        buildingNumberTxt = (TextView) view.findViewById(R.id.buildingNumber);
        apartmentNumberTxt = (TextView) view.findViewById(R.id.apartmentNumber);

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
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_USER, 0);
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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}