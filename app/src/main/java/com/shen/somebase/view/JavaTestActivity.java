package com.shen.somebase.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shen.annotations.Builder;
import com.shen.annotations.Optional;
import com.shen.annotations.Required;
import com.shen.somebase.R;

@Builder
public class JavaTestActivity extends AppCompatActivity {

    @Required
    String name;

    @Required
    int age;

    @Optional
    String describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        ((TextView) findViewById(R.id.javaNameView)).setText(name);
        ((TextView) findViewById(R.id.javaAgeView)).setText(String.valueOf(age));
        ((TextView) findViewById(R.id.javaDesView)).setText(describe);
    }
}
