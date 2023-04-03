package com.app.iris;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.iris.model.LoginModel;
import com.app.iris.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_login, tv_forgot_pass;
    private EditText et_email, et_password;
    private List<LoginModel> loginModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.colorLightGreen1));

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_forgot_pass = findViewById(R.id.tv_forgot_pass);
        tv_login = findViewById(R.id.tv_login);

        tv_forgot_pass.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pass:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
            case R.id.tv_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    et_email.setError("Email is required.");
                    et_email.requestFocus();
                } else if (!MyUtils.isValidEmail(email)) {
                    et_email.setError("Email should be valid email address.");
                    et_email.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    et_password.setError("Password is required.");
                    et_password.requestFocus();
                } else {
                    List<LoginModel> loginModelList = MyUtils.getLoginModelList(LoginActivity.this);
                    LoginModel model = new LoginModel();
                    model.setEmail(email);
                    if (loginModelList != null) {
                        int pos = loginModelList.indexOf(model);
                        if (pos > -1) {
                            if (loginModelList.get(pos).getEmail().equals(email) &&
                                    loginModelList.get(pos).getPassword().equals(password)) {
                                String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                                MyUtils.savePreferences(MyUtils.TOKEN, timeStamp,this);

                                startActivity(new Intent(this, MainActivity2.class));
                                finishAffinity();
                            } else {
                                Toast.makeText(this, "Invalid credential", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(this, "Invalid credential", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Invalid credential", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}