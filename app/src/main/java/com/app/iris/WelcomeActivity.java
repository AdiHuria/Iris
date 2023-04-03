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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_start,tv_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(WelcomeActivity.this, R.color.colorGrey));

        tv_start = findViewById(R.id.tv_start);
        tv_skip = findViewById(R.id.tv_skip);

        clickify(tv_skip, "Skip", new OnClickListener() {
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
                startActivity(new Intent(this, ScrollingActivity.class));
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

                Intent intent = new Intent(WelcomeActivity.this, Welcome2Activity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                //   Typeface face = Typeface.createFromAsset(context.getAssets(), "JosefinSans-Bold.ttf");
                //  ds.setTypeface(face);
                ds.setColor(getResources().getColor(R.color.colorDarkBlue2));
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