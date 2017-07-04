package zamir.com.smartmeters.fragments;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.adapters.BillAdapter;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.BillItem;
import zamir.com.smartmeters.model.User;

/**
 * Created by MahmoudSamir on 3/14/2017.
 */

public class BillFragment extends Fragment {

    RecyclerView billRecyclerView;
    //    ArrayAdapter<BillItem> notificationsAdapter;
    BillAdapter billAdapter;
    ArrayList<BillItem> billItemList = new ArrayList<>();
    String TAG = BillFragment.class.getSimpleName();
    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bill, container, false);
        billRecyclerView = (RecyclerView) view.findViewById(R.id.bill_list_view);
        billAdapter = new BillAdapter(billItemList, getContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        billRecyclerView.setLayoutManager(mLayoutManager);
        billRecyclerView.setItemAnimator(new DefaultItemAnimator());
        billRecyclerView.setAdapter(billAdapter);
        Log.e("BillItem", "Before initiateList");
//        fillData();
        user = getDataFromSharedPrefence();
        if (user != null) {
            getUserConumption(user.getMeterCode());
        }
        Log.e("BillItem", "After initiateList");
        return view;
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

    private void getUserConumption(final String apartment_code) {
        //Creating a string request
        StringRequest addTokenRequest = new StringRequest(Request.Method.POST, Config.GET_USER_CONSUMPTION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getActivity(), jsonObject.getString(Config.RESPONSE_DATA), Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray(Config.RESPONSE_DATA);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.e(TAG, String.valueOf(i));
                                JSONObject currentUserConsumptionObject = jsonArray.getJSONObject(i);
                                billItemList.add(new BillItem(
                                                currentUserConsumptionObject.getString(Config.KEY__BILL_Month),
                                                currentUserConsumptionObject.getString(Config.KEY__BILL_apartment_bill),
                                                currentUserConsumptionObject.getString(Config.KEY__BILL_apartment_code),
                                                currentUserConsumptionObject.getString(Config.KEY__BILL_total_consumption),
                                                currentUserConsumptionObject.getString(Config.KEY__BILL_most_used_devices)
                                        )
                                );
                            }
                            billAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY__BILL_apartment_code, apartment_code);
                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(addTokenRequest);
    }
}
