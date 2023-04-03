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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_sign_up,tv_login_in_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_2);

        tv_sign_up = findViewById(R.id.tv_sign_up);
        tv_login_in_info = findViewById(R.id.tv_login_in_info);

        clickify(tv_login_in_info, "Log in", new OnClickListener() {
            @Override
            public void onClick() {
            }
        });

        tv_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
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

                Intent intent = new Intent(Welcome2Activity.this, LoginActivity.class);
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