package zamir.com.smartmeters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.User;

/**
 * Created by engsa on 04/07/2017.
 */

public class BillDetailsActivity extends AppCompatActivity {
    private TextView meterCode, billMonth, billAmount, total_consumption;
    String TAG = "ProfileFragment";
    User user;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        init();
    }

    private void init() {
        Log.e(TAG + " init", "1");
        meterCode = (TextView) findViewById(R.id.meterCode);
        billMonth = (TextView) findViewById(R.id.billMonth);
        billAmount = (TextView) findViewById(R.id.billAmount);
        total_consumption = (TextView) findViewById(R.id.total_consumption);

        billAmount.setText(intent.getStringExtra(Config.KEY__BILL_apartment_bill));
        meterCode.setText(intent.getStringExtra(Config.KEY__BILL_apartment_code));
        total_consumption.setText(intent.getStringExtra(Config.KEY__BILL_total_consumption));
        billMonth.setText(intent.getStringExtra(Config.KEY__BILL_Month));
//        intent.getStringExtra(Config.KEY__BILL_most_used_devices);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
