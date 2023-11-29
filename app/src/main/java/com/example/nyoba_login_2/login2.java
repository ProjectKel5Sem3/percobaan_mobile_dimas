package com.example.nyoba_login_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login2 extends AppCompatActivity {
    private EditText etUsername, etPassword, passwordShow;
    private TextView btnRegister, forgetpassButton;
    private CardView btnLogin;
    private ImageView togglePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

        etUsername = findViewById(R.id.emaillogin1);
        etPassword = findViewById(R.id.password1);
        btnLogin = findViewById(R.id.buttonlogin1);
        btnRegister = findViewById(R.id.signuptext1);
        forgetpassButton = findViewById(R.id.forgotpassword1);
        passwordShow = findViewById(R.id.password1);
        togglePassword = findViewById(R.id.toggleeyelogin1);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login2.this, register2.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_email = etUsername.getText().toString();
                String user_password = etPassword.getText().toString();

                if (!(user_email.isEmpty() || user_password.isEmpty())) {

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
                                    String username = userData.getString("user_email");
                                    String password = userData.getString("user_password");
                                    String alamat = userData.getString("user_fullname");

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
                            params.put("user_email", user_email);
                            params.put("user_password", user_password);
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getApplicationContext(), "Password Atau Username Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        togglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
    }
    private void togglePasswordVisibility() {
        if (passwordShow.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            // Jika password tersembunyi, tampilkan
            passwordShow.setTransformationMethod(null);
            togglePassword.setImageResource(R.drawable.eye); // Ganti dengan gambar mata tertutup
        } else {
            // Jika password terlihat, sembunyikan
            passwordShow.setTransformationMethod(PasswordTransformationMethod.getInstance());
            togglePassword.setImageResource(R.drawable.eye_closed); // Ganti dengan gambar mata terbuka
        }
    }
}