package zamir.com.smartmeters.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.utils.NotificationUtils;

/**
 * Created by MahmoudSamir on 4/10/2017.
 */

public class NotificationActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> notificationAdapter;
    ArrayList<String> notificationArray;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        getSupportActionBar().setTitle("Notifications");
//        setSupportActionBar(toolbar);
        notificationArray = new ArrayList<>();
        listView = (ListView) findViewById(R.id.notification_listview);
        notificationAdapter = new ArrayAdapter<>(this, R.layout.notification_item_list, notificationArray);
        listView.setAdapter(notificationAdapter);
        fillData();
    }

    private void fillData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.e("Notifications", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        database.getReference(Config.SHARED_NOTIFICATIONS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String message = postSnapshot.child("message").getValue().toString();
                    notificationArray.add(message);
                    notificationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        addNotificationToData(message);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // register GCM registration complete receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.REGISTRATION_COMPLETE));
//
//        // register new push message receiver
//        // by doing this, the activity will be notified each time a new message arrives
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.PUSH_NOTIFICATION));
//
//        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(getApplicationContext());
//    }
//
//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }

    private void addNotificationToData(String message) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("notifications").child(mAuth.getCurrentUser().getUid());
        myRef.setValue(message);
    }
}
