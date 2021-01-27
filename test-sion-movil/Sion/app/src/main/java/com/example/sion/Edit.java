package com.example.sion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText age;
    private EditText mail;
    private EditText nationality;
    private EditText ci;
    private Button guardar;
    private Button cancelar;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cancelar = findViewById(R.id.cancel);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.last_name);
        mail = findViewById(R.id.mail);
        age = findViewById(R.id.age);
        nationality = findViewById(R.id.nat);
        ci = findViewById(R.id.ced);

        guardar = findViewById(R.id.save_us);

        index = getIntent().getIntExtra("index", 0);
        Log.d("indesx", "algo esrta" + index);
        getData(index);
    }

    public void getData(int id) {
        String url = "http://192.168.43.107/test-sion/public/api/customer/find/" + id;

        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String id = response.getString("id");
                                    String nam = response.getString("name");
                                    String lastNam = response.getString("last_name");
                                    String mai = response.getString("mail");
                                    String ag = response.getString("age");
                                    String nationalit = response.getString("nationality");
                                    String cid = response.getString("ci");

                                    name.setText(nam);
                                    lastName.setText(lastNam);
                                    age.setText(ag);
                                    mail.setText(mai);
                                    nationality.setText(nationalit);
                                    ci.setText(cid);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "Error Volley: " + error.getMessage());
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

    public void EditCustomer(View v) {
        String URL_INSERT = "http://192.168.43.107/test-sion/public/api/customer/update";//
        //MainActivity main =  new MainActivity();


        String id = String.valueOf(index);
        String nam = name.getText().toString();
        String last = lastName.getText().toString();
        String ag = age.getText().toString();
        String ma = mail.getText().toString();
        String nat = nationality.getText().toString();
        String cid = ci.getText().toString();

        HashMap<String, String> maps = new HashMap<>();
        maps.put("id", id);
        maps.put("name", nam);
        maps.put("last_name", last);
        maps.put("age", ag);
        maps.put("mail", ma);
        maps.put("nationality", nat);
        maps.put("ci", cid);

        JSONObject jobject = new JSONObject(maps);

        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.PUT,
                        URL_INSERT,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    String message = response.getString("message");
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                    finish();
                                    MainActivity.clearData();
                                    MainActivity.cargarAdaptador(getApplicationContext());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                                Log.d("Server", response.toString());

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", "Error Volley: " + error.getMessage());

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