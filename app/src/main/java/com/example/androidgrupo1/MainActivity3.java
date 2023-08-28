package com.example.androidgrupo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    EditText txtUser, txtTitle, txtBody, txtIdUser;
    Button btnEnviar, btnBuscar, btnEliminar, btnActualizar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        txtUser = findViewById(R.id.txtUser);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        txtIdUser = findViewById(R.id.txtIdUser);

        btnEnviar = findViewById(R.id.btnEnviar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarWs(txtTitle.getText().toString(), txtBody.getText().toString(),txtUser.getText().toString());
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LeerWs(txtIdUser.getText().toString());
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                actualizarWs(txtTitle.getText().toString(), txtBody.getText().toString(),txtUser.getText().toString(), txtIdUser.getText().toString());
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                eliminarWs(txtIdUser.getText().toString());
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LeerWs(final String id){
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txtUser.setText(jsonObject.getString("userId"));
                    txtTitle.setText(jsonObject.getString("title"));
                    txtBody.setText(jsonObject.getString("body"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("Error: ",error.getMessage()) ;
            }

        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    ///////////////////////////////////////////////////////////
    private void enviarWs(final String title, final String body, final String userId){
        String url = "https://jsonplaceholder.typicode.com/posts";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity3.this, "Resultado: " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("Error: ",error.getMessage()) ;
            }

        }){
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("Titulo", title);
                params.put("body", body);
                params.put("userId", userId);
                return  params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    ///////////////////////////////////
    private void actualizarWs(final String title , final String body, final String userId, final String txtIdUser){
        String url = "https://jsonplaceholder.typicode.com/posts/" + txtIdUser;
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity3.this, "Se actualizo correctamente"+response, Toast.LENGTH_SHORT).show(); //Mensaje de confirmacion
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("title",title);
                params.put("body",body);
                params.put("userId",userId);
                return params;
            }

        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    ///////////////
    private void eliminarWs(final String id) {
        String url = "https://jsonplaceholder.typicode.com/posts/"+id;
        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity3.this, "Resultado: " + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }
}