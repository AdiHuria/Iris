package com.app.iris;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_submit, tv_login_in_info;
    private RelativeLayout rl_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(ForgotPasswordActivity.this, R.color.colorLightGreen1));

        tv_login_in_info = findViewById(R.id.tv_login_in_info);
        tv_submit = findViewById(R.id.tv_submit);
        rl_back = findViewById(R.id.rl_back);

        clickify(tv_login_in_info, "Sign Up", new SignUpActivity.OnClickListener() {
            @Override
            public void onClick() {
            }
        });

        rl_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_back:
                onBackPressed();
                break;
                case R.id.tv_submit:
                    startActivity(new Intent(this,VerificationActivity.class));
                break;
        }
    }

    public interface OnClickListener {
        void onClick();
    }

    public void clickify(final TextView textView, final String clickableText, SignUpActivity.OnClickListener listener) {

        CharSequence text = textView.getText();
        String string = text.toString();
        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // MyUtils.ButtonClickEffect(v);

                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
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