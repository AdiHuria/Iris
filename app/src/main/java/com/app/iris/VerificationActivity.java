package com.app.iris;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.iris.utils.MyUtils;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_back;
    private TextView tv_start;
    public static EditText et_emp_code1, et_emp_code2, et_emp_code3, et_emp_code4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        rl_back = findViewById(R.id.rl_back);
        tv_start = findViewById(R.id.tv_start);

        et_emp_code1 = findViewById(R.id.et_emp_code1);
        et_emp_code2 = findViewById(R.id.et_emp_code2);
        et_emp_code3 = findViewById(R.id.et_emp_code3);
        et_emp_code4 = findViewById(R.id.et_emp_code4);

        et_emp_code1.setTransformationMethod(new MyPasswordTransformationMethod());
        et_emp_code2.setTransformationMethod(new MyPasswordTransformationMethod());
        et_emp_code3.setTransformationMethod(new MyPasswordTransformationMethod());
        et_emp_code4.setTransformationMethod(new MyPasswordTransformationMethod());

        et_emp_code1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_emp_code1.setCursorVisible(true);

                return false; // return is important...
            }
        });


        et_emp_code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    et_emp_code2.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                //Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
            }
        });

        et_emp_code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    et_emp_code3.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                //Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
            }
        });

        et_emp_code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    et_emp_code4.requestFocus();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                //Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
            }
        });

        et_emp_code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    MyUtils.hideKeyboard(VerificationActivity.this, et_emp_code4);
                    et_emp_code1.setCursorVisible(false);
                    et_emp_code4.clearFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                //Log.e("TextWatcherTest", "afterTextChanged:\t" + s.toString());
            }
        });

        et_emp_code2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //check if the right key was pressed
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_DEL) {
                        String text = et_emp_code2.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            et_emp_code1.requestFocus();
                            et_emp_code1.getText().clear();
                        }
                    }
                }
                return false;
            }
        });

        et_emp_code3.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //check if the right key was pressed
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_DEL) {
                        String text = et_emp_code3.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            et_emp_code2.requestFocus();
                            et_emp_code2.getText().clear();
                        }
                    }
                }
                return false;
            }
        });

        et_emp_code4.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    //check if the right key was pressed
                    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_DEL) {
                        String text = et_emp_code4.getText().toString();
                        if (TextUtils.isEmpty(text)) {
                            et_emp_code3.requestFocus();
                            et_emp_code3.getText().clear();
                        }
                    }
                }
                return false;
            }
        });

        rl_back.setOnClickListener(this);
        tv_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_start:
                startActivity(new Intent(this, MainActivity2.class));
                break;
        }
    }

    public static class MyPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private static class PasswordCharSequence implements CharSequence {
            private final CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            @NonNull
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}