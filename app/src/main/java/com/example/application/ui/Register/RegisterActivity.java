package com.example.application.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.application.R;
import com.example.application.face.utils.Md5;
import com.example.application.http.HttpUtil;
import com.example.application.http.HttpsUtil;
import com.example.application.ui.login.LoginActivity;
import com.tencent.imsdk.TIMManager;

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText passwordAgain;
    Button registerConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        passwordAgain = findViewById(R.id.register_password_again);
        registerConfirm = findViewById(R.id.register_confirm);

        registerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable_name = username.getText();
                String editable_password = password.getText().toString().trim();
                String editable_password_again = passwordAgain.getText().toString().trim();

                if(editable_password.equals(editable_password_again)){


                    String url="https://120.26.172.16:8443/AndroidTest/registUser?registname="+editable_name+"&password="+ Md5.MD5(editable_password, "utf-8");
                    HttpsUtil.getInstance().get(url, new HttpsUtil.OnRequestCallBack() {
                        @Override
                        public void onSuccess(String s) {
                            System.out.println(s);
                            if(s.equals("user already exist!")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册用户名已存在，请重新填写！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                /*Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);*/
                                finish();
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "两次输入密码不一致！！！请重新输入！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}
