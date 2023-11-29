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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register2 extends AppCompatActivity {
    private EditText etUsername, etPassword, etPasswordConfirm, etNama;
    private ImageView toggleButton1, toggleButton2;
    private CardView btnRegister;
    private TextView btnTlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        etUsername = findViewById(R.id.emailregistrasi1); //email
        etPassword = findViewById(R.id.password2);  //pass
        etPasswordConfirm = findViewById(R.id.passwordconfirm); //pass con
        etNama = findViewById(R.id.userregistrasi1);  //username
        btnRegister = findViewById(R.id.buttonregister1);  //button regis
        btnTlogin = findViewById(R.id.logintext1);  //text to login class
        toggleButton1 = findViewById(R.id.toggleeyeregister1); //eye1
        toggleButton2 = findViewById(R.id.toggleeyeregister2); //eye2

        btnTlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register2.this, login2.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();
                String nama = etNama.getText().toString();

                if (!(email.isEmpty() || password.isEmpty() || confirm.isEmpty() || nama.isEmpty())) {

                    //check pass = confirm
                    if (password.equals(confirm)) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Db_Contract.urlRegister, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), login2.class));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_email", email);
                                params.put("user_password", confirm);
                                params.put("user_fullname", nama);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        requestQueue.add(stringRequest);
                    } else {
                        // Password dan konfirmasi password tidak sesuai
                        Toast.makeText(getApplicationContext(), "Password mismatch", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

    }
    private void togglePasswordVisibility() {
        if (etPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            etPassword.setTransformationMethod(null);
            toggleButton1.setImageResource(R.drawable.eye);
        }else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleButton1.setImageResource(R.drawable.eye_closed);
        }

        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibilitySecond();
            }
        });
    }
    private void togglePasswordVisibilitySecond() {
        if (etPasswordConfirm.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            etPasswordConfirm.setTransformationMethod(null);
            toggleButton2.setImageResource(R.drawable.eye);
        }else {
            etPasswordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleButton2.setImageResource(R.drawable.eye_closed);
        }
    }
}