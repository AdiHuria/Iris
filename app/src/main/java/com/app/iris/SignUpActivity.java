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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_start, tv_login_in_info;
    private EditText et_name, et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(SignUpActivity.this, R.color.colorLightGreen1));

        tv_login_in_info = findViewById(R.id.tv_login_in_info);
        tv_start = findViewById(R.id.tv_start);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);


        clickify(tv_login_in_info, "Privacy Policy", new OnClickListener() {
            @Override
            public void onClick() {
            }
        });

        tv_start.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                // String storedEmail = MyUtils.getUserEmail(SignUpActivity.this);
                if (TextUtils.isEmpty(name)) {
                    et_name.setError("Name is required.");
                    et_name.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    et_email.setError("Email is required.");
                    et_email.requestFocus();
                } else if (!MyUtils.isValidEmail(email)) {
                    et_email.setError("Email should be valid email address.");
                    et_email.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    et_password.setError("Password is required.");
                    et_password.requestFocus();
                } else {
                    List<LoginModel> loginModelList = MyUtils.getLoginModelList(SignUpActivity.this);
                    LoginModel model = new LoginModel();
                    model.setEmail(email);
                    if (loginModelList != null) {
                        int pos = loginModelList.indexOf(model);
                        if (pos > -1) {
                            if (!TextUtils.isEmpty(loginModelList.get(pos).getEmail()) && loginModelList.get(pos).getEmail().equalsIgnoreCase(email)) {
                                Toast.makeText(SignUpActivity.this, "This email is already been taken.", Toast.LENGTH_SHORT).show();
                            } else {
                                saveAndContinue(email, password, name, loginModelList);
                            }
                        } else
                            saveAndContinue(email, password, name, loginModelList);
                    } else {
                        saveAndContinue(email, password, name, loginModelList);
                    }
                    /*if (!TextUtils.isEmpty(storedEmail) && storedEmail.equalsIgnoreCase(email)) {
                        Toast.makeText(SignUpActivity.this, "This email is already been taken.", Toast.LENGTH_SHORT).show();
                    } else {
                        LoginModel loginModel = new LoginModel();
                        loginModel.setEmail(email);
                        loginModel.setName(email);
                        loginModel.setPassword(password);
                        loginModelList.add(loginModel);

                        Gson gson = new Gson();
                        String jsonUserServices = gson.toJson(loginModelList);



                        MyUtils.savePreferences(MyUtils.USER_NAME, name, SignUpActivity.this);
                        MyUtils.savePreferences(MyUtils.USER_EMAIL, email, SignUpActivity.this);
                        MyUtils.savePreferences(MyUtils.USER_PASSWORD, password, SignUpActivity.this);
                        startActivity(new Intent(this, LoginActivity.class));
                    }*/

                }

                break;
        }
    }

    private void saveAndContinue(String email, String password, String name, List<LoginModel> loginModelList) {
        if (loginModelList == null)
            loginModelList = new ArrayList<>();
        LoginModel loginModel = new LoginModel();
        loginModel.setEmail(email);
        loginModel.setName(name);
        loginModel.setPassword(password);
        loginModelList.add(loginModel);
        MyUtils.savePreferences(MyUtils.USER_EMAIL,email,SignUpActivity.this);
        MyUtils.savePreferences(MyUtils.USER_NAME,name,SignUpActivity.this);
        MyUtils.saveLoginModelList(SignUpActivity.this, loginModelList);
        startActivity(new Intent(this, LoginActivity.class));
    }

    public interface OnClickListener {
        void onClick();
    }

    public void clickify(final TextView textView, final String clickableText, OnClickListener listener) {

        CharSequence text = textView.getText();
        String string = text.toString();
        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // MyUtils.ButtonClickEffect(v);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //   Typeface face = Typeface.createFromAsset(context.getAssets(), "JosefinSans-Bold.ttf");
                //  ds.setTypeface(face);
                ds.setColor(getResources().getColor(R.color.colorBlue1));
                ds.setUnderlineText(true);
            }
        };

        int start = string.indexOf(clickableText);
        int end = start + clickableText.length();
        if (start == -1) return;

        if (text instanceof Spannable) {
            ((Spannable) text).setSpan(span2, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            SpannableString s = SpannableString.valueOf(text);
            s.setSpan(span2, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(s);
        }

        MovementMethod m = textView.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }


    }
}