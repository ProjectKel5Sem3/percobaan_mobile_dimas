package com.example.nyoba_login_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String username = etUsername.getText().toString();
//                String password = etPassword.getText().toString();
//
//                if (! (username.isEmpty() || password.isEmpty())){
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Db_Contract.urlLogin + "?username=" + username + "&password=" + password, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equals("selamat datang")){
//                                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
//
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            }else{
//                                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    requestQueue.add(stringRequest);
//                }else{
//                    Toast.makeText(getApplicationContext(), "Password Atau Username Salah", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = etUsername.getText().toString();
//                String password = etPassword.getText().toString();
//
//                if (!(username.isEmpty() || password.isEmpty())) {
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogin, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equals("selamat datang")) {
//                                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<>();
//                            params.put("username", username);
//                            params.put("password", password);
//                            return params;
//                        }
//                    };
//
//                    requestQueue.add(stringRequest);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Password Atau Username Salah", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!(username.isEmpty() || password.isEmpty())) {

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    // Menggunakan metode POST
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlLogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                int code = jsonResponse.getInt("code");

                                if (code == 200) {
                                    JSONArray dataArray = jsonResponse.getJSONArray("data");

                                    // Jika Anda hanya mengharapkan satu objek dalam array, Anda bisa mengambil indeks pertama
                                    JSONObject userData = dataArray.getJSONObject(0);

                                    // Handle data sesuai kebutuhan
                                    String userId = userData.getString("id_user");
                                    String username = userData.getString("username");
                                    String password = userData.getString("password");
                                    String alamat = userData.getString("alamat");

                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    // Login gagal
                                    Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Mengirim data sebagai form-data
                            Map<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("password", password);
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Password Atau Username Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}