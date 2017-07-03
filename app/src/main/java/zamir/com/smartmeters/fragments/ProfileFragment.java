package zamir.com.smartmeters.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.user_activities.LoginActivity;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.User;

import static zamir.com.smartmeters.activities.SplashActivity.auth;
import static zamir.com.smartmeters.activities.SplashActivity.authListener;


public class ProfileFragment extends Fragment {

    public static User user = new User();
    FirebaseUser firebaseUser;
    private TextView nameTxt, emailTxt, phoneTxt, cityTxt, apartmentCodeTxt;
    private ListView deviceListView;
    private ArrayAdapter devicesAdapter;
    private ArrayList<String> devicesList = new ArrayList<>();
    int devicesListSize = 0;
    String TAG = "ProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        init(rootView);
        Log.e(TAG, "1");
//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                firebaseUser = firebaseAuth.getCurrentUser();
//                if (user == null) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                } else {
//                    Log.e(TAG, "2");
//                    user = getDataFromSharedPrefence();
//                    nameTxt.setText(user.getName());
//                    phoneTxt.setText(user.getPhone());
//                    emailTxt.setText(user.getEmail());
//                    cityTxt.setText(user.getCity());
//                    apartmentCodeTxt.setText(user.getApartmentCode());
//                    Log.e(TAG, "3");
//                }
//            }
//        };
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDeviceDialog deviceDialog = new AddDeviceDialog();
                deviceDialog.show(getActivity().getFragmentManager(), "Add device");
            }
        });
        return rootView;
    }

    private void init(View view) {
        Log.e(TAG + " init", "1");
        nameTxt = (TextView) view.findViewById(R.id.user_name);
        phoneTxt = (TextView) view.findViewById(R.id.user_phone);
        emailTxt = (TextView) view.findViewById(R.id.user_email);
        cityTxt = (TextView) view.findViewById(R.id.user_city);
        apartmentCodeTxt = (TextView) view.findViewById(R.id.user_apartment_code);

        deviceListView = (ListView) view.findViewById(R.id.my_devices);
        devicesAdapter = new ArrayAdapter<>(getActivity(), R.layout.notification_item, devicesList);
        deviceListView.setAdapter(devicesAdapter);
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

//    private User getDataFromSharedPrefence() {
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_USER, 0);
//        String name = pref.getString("name", null);
//        String phone = pref.getString("phone", null);
//        String email = pref.getString("email", null);
//        String city = pref.getString("city", null);
//        String apartmentCode = pref.getString("apartmentCode", null);
//        return new User(name, email, phone, city, apartmentCode);
//    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}