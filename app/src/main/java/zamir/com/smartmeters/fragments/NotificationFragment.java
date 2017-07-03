package zamir.com.smartmeters.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.NotificationActivity;
import zamir.com.smartmeters.adapters.NotificationAdapter;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.Notification;

/**
 * Created by MahmoudSamir on 3/14/2017.
 */

public class NotificationFragment extends Fragment {

    RecyclerView notificationListView;
    //    ArrayAdapter<Notification> notificationsAdapter;
    NotificationAdapter notificationAdapter;
    ArrayList<Notification> notificationList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification, container, false);
        notificationListView = (RecyclerView) view.findViewById(R.id.notification_list_view);
        notificationAdapter = new NotificationAdapter(notificationList, getContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        notificationListView.setLayoutManager(mLayoutManager);
        notificationListView.setItemAnimator(new DefaultItemAnimator());
        notificationListView.setAdapter(notificationAdapter);
        Log.e("Notification", "Before initiateList");
//        fillData();
        initiateList();
        Log.e("Notification", "After initiateList");
        return view;
    }

    public void initiateList() {
        notificationList.add(new Notification("Bill for MAY is 20LE"));
        notificationList.add(new Notification("Bill for JUN is 80LE"));
        notificationList.add(new Notification("You have over consumption for this month.  Please, if there a problem let us know."));
        notificationList.add(new Notification("Bill for July is 95LE"));
        notificationAdapter.notifyDataSetChanged();
    }

    private void fillData() {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference(Config.SHARED_NOTIFICATIONS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.e("Notifications", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()));
//        database.getReference(Config.SHARED_NOTIFICATIONS)
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String message = postSnapshot.child("message").getValue().toString();
                    notificationList.add(new Notification(message));
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
    }
}
