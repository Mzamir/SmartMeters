package zamir.com.smartmeters.activities.user_activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.MainActivity;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.User;

/**
 * Created by MahmoudSamir on 2/20/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button btnLogin, btnReset;
    private final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });
    }


    private void init() {
        mAuth = FirebaseAuth.getInstance();
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
    }

    private void startLogin() {
        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            login(email, password);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(Config.FIRST_TIME_TO_LOGIN, false);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void login(final String email, final String password) {
        //Creating a string request
        progressBar.setVisibility(View.VISIBLE);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int state = jsonObject.getInt(Config.RESPONSE_SUCCESS);
                            if (state == 0) {
                                Log.e("Login", "0");
                                Toast.makeText(LoginActivity.this, jsonObject.getString(Config.RESPONSE_MESSAGE), Toast.LENGTH_SHORT).show();
                            } else if (state == 1) {
                                Log.e("Login", "1");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                Log.e("ArrayJson", String.valueOf(jsonArray.length()));
                                JSONObject currentUserObject = jsonArray.getJSONObject(0);
                                Log.e("currentUserObject", currentUserObject.toString());
                                String message = jsonObject.getString(Config.RESPONSE_MESSAGE);
                                Log.e("Login Message", message.toString());
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                //Creating a shared preferences
                                startSignup(email, password);
                                saveToSharedPref(currentUserObject);
                                //Starting MainActivity activity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(Config.FIRST_TIME_TO_LOGIN, true);
                                intent.putExtra(Config.KEY_EMAIL, email);
                                startActivity(intent);
                            }
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
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_EMAIL, email);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(loginRequest);
    }

    private void saveToSharedPref(JSONObject currentUserObject) throws JSONException {
        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //Adding values to editor
        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
        editor.putString(Config.KEY_EMAIL, currentUserObject.getString(Config.KEY_EMAIL));
        editor.putString(Config.KEY_PASSWORD, currentUserObject.getString(Config.KEY_PASSWORD));
        editor.putString(Config.KEY_apartmentNumber, currentUserObject.getString(Config.KEY_apartmentNumber));
        editor.putString(Config.KEY_buildingNumber, currentUserObject.getString(Config.KEY_buildingNumber));
        editor.putString(Config.KEY_address, currentUserObject.getString(Config.KEY_address));
        editor.putString(Config.KEY_meterCode, currentUserObject.getString(Config.KEY_meterCode));
        editor.putString(Config.KEY_username, currentUserObject.getString(Config.KEY_username));
        editor.putString(Config.KEY_created_at, currentUserObject.getString(Config.KEY_created_at));
        editor.putString(Config.KEY_updated_at, currentUserObject.getString(Config.KEY_updated_at));
//        editor.putString(Config.KEY_registrationToken, regToken);
        //Saving values to editor
        editor.commit();
    }

    private void startSignup(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                        } else {

                        }
                    }
                });
    }


}
