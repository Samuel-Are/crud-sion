package com.example.sion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterCustomer extends RecyclerView.Adapter<CustomerViewHolder> {
    private List<Customer> items;
    public Context ctx;


    public AdapterCustomer(Context ctx, List<Customer> items) {
        this.ctx = ctx;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFilter(List<Customer> countryModels) {
        items = new ArrayList<>();
        items.addAll(countryModels);
        notifyDataSetChanged();
    }
    public void dismissItem(int pos){
        this.items.remove(pos);
        this.notifyItemRemoved(pos);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Customer> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card, viewGroup, false);
        return new CustomerViewHolder(v, items);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder viewHolder, int i) {

        //viewHolder.imagen.setImageResource(items.get(i).getImage());

        viewHolder.name.setText(items.get(i).getName());
        viewHolder.lastName.setText(items.get(i).getLastName());
        viewHolder.age.setText("" + items.get(i).getAge());
        viewHolder.mail.setText(items.get(i).getMail());
        viewHolder.nationality.setText(items.get(i).getNationality());
        viewHolder.ci.setText("" + items.get(i).getCi());
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ir = new Intent(ctx, Edit.class);
                ir.putExtra("index", items.get(i).getId());
                ctx.startActivity(ir);
                Log.d("edit", String.valueOf(items.get(i).getId()));
            }
        });
        //viewHolder.edit.setText("" + items.get(i).getId());
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(i, items.get(i).getId());
                Log.d("del", String.valueOf(i));
            }
        });
    }

    public void createDialog(int i, int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
        builder.setMessage("Esta seguro de eliminar?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismissItem(i);
                        delCustomer(index, ctx);
                        Toast.makeText(ctx, "Se elimino correctamente ", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    public void delCustomer(int id, Context ctx) {
        String URL_INSERT = "http://192.168.43.107/test-sion/public/api/customer/delete/ " + id;//
        //MainActivity main =  new MainActivity();

        VolleySingleton.getInstance(ctx).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.DELETE,
                        URL_INSERT,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

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

class CustomerViewHolder extends RecyclerView.ViewHolder{
    List<Customer> proList = new ArrayList<>();

    public TextView name;
    public TextView lastName;
    public TextView mail;
    public TextView age;
    public TextView nationality;
    public TextView ci;
    public ImageView edit;
    public ImageView del;
    public int pos;
    public LinearLayout linearLayout;

    public CustomerViewHolder(View v, final List<Customer> prodList) {

        super(v);

        this.proList = prodList;
        name = (TextView) v.findViewById(R.id.name);
        lastName = (TextView) v.findViewById(R.id.last_name);
        age = (TextView) v.findViewById(R.id.age);
        mail = (TextView) v.findViewById(R.id.mail);
        nationality = (TextView) v.findViewById(R.id.nationality);
        ci = (TextView) v.findViewById(R.id.ci);
        edit =  v.findViewById(R.id.edit);
        del =  v.findViewById(R.id.del);

        linearLayout = v.findViewById(R.id.line);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // final BitmapDrawable bitmapDrawable = (BitmapDrawable) imagen.getDrawable();
                //final Bitmap yourBitmap = bitmapDrawable.getBitmap();
               /* pos = getAdapterPosition();
                Intent intent = new Intent(view.getContext(), Compra.class);
                //intent.putExtra("imagen", yourBitmap);
                intent.putExtra("id", proList.get(pos).getId());
                intent.putExtra("imagen", proList.get(pos).getImagen());
                intent.putExtra("nombre", proList.get(pos).getNombre());
                intent.putExtra("descripcion",proList.get(pos).getDescripcion());
                intent.putExtra("precio", String.valueOf(proList.get(pos).getPrecio()));

                view.getContext().startActivity(intent);*/
            }
        });
    }//
}
