package com.example.sion;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static AdapterCustomer adapter;
    private static JsonArrayRequest jsonArrayReq;
    private static List<Customer> items;
    private RecyclerView recycler;

    //private AdapterCustomer adapter;

    private static final String JSON_ARRAY_REQUEST_URL = "http://192.168.43.107:8080/api/customer/findall";//http://192.168.43.137:8080/Pedido/ajax/producto.php?op=listarPollos
    private static final String TAG = "Tab1";
    ProgressDialog progressDialog;

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recycler = findViewById(R.id.recy);
        //recycler.setHasFixedSize(true);

        cargarAdaptador(this);

        items = new ArrayList<>();
        adapter = new AdapterCustomer(this, items);
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        LinearLayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Save.class);
                startActivity(i);

            }
        });

    }
    public static void clearData() {
        adapter.clear();
    }


    public static void cargarAdaptador(Context ctx) {
        String url = "http://192.168.43.107/test-sion/public/api/customer/findall";


        jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("init", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Customer cli = new Customer();
                                cli.setId(jsonObject.getInt("id"));
                                cli.setName(jsonObject.getString("name"));
                                cli.setLastName(jsonObject.getString("last_name"));
                                cli.setAge(Integer.parseInt(jsonObject.getString("age")));
                                cli.setMail(jsonObject.getString("mail"));
                                cli.setNationality(jsonObject.getString("nationality"));
                                cli.setCi(Integer.parseInt(jsonObject.getString("ci")));

                                items.add(cli);
                                adapter.notifyDataSetChanged();
                                Log.d("init", jsonObject.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        //adapter.addAll(items);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Voll", "Error Volley: " + error.getMessage() + " URL: "+JSON_ARRAY_REQUEST_URL);
                        // progressDialog.hide();
                        Toast.makeText(ctx, "Lo sentimos.!, no se pudo conectar con el servidor ", Toast.LENGTH_LONG).show();

                    }
                });
        Conexion.getInstance(ctx).addToRequestQueue(jsonArrayReq, "com.example.sion");

    }

}