package com.example.sion;

import android.app.Activity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Save extends AppCompatActivity {

    private static final String TAG = "Fetch";
    private TextView ar;
    private EditText name;
    private EditText lastName;
    private EditText age;
    private EditText mail;
    private EditText nationality;
    private EditText ci;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private Button guardar;
    private Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        cancelar = findViewById(R.id.cancel);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.last_name);
        mail = findViewById(R.id.mail);
        age = findViewById(R.id.age);
        nationality = findViewById(R.id.nat);
        ci = findViewById(R.id.ced);

        guardar = findViewById(R.id.save_us);


    }


    public void guardarUsuario(View v) {
        String URL_INSERT = "http://192.168.43.107/test-sion/public/api/customer/create";//
        //MainActivity main =  new MainActivity();


        String nam = name.getText().toString();
        String last = lastName.getText().toString();
        String ag = age.getText().toString();
        String ma = mail.getText().toString();
        String nat = nationality.getText().toString();
        String cid = ci.getText().toString();

        HashMap<String, String> maps = new HashMap<>();
        maps.put("name", nam);
        maps.put("last_name", last);
        maps.put("age", ag);
        maps.put("mail", ma);
        maps.put("nationality", nat);
        maps.put("ci", cid);

        JSONObject jobject = new JSONObject(maps);
        Log.d(TAG, maps.toString());


        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        URL_INSERT,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String mensaje = response.getString("message");
                                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();

                                    finish();
                                    MainActivity.clearData();
                                    MainActivity.cargarAdaptador(getApplicationContext());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "nose pudo registrar", Toast.LENGTH_LONG).show();

                                }

                                Log.d("Server", response.toString());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );


    }
}