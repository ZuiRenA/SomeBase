package com.shen.somebase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.shen.somebase.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            JavaTestActivityBuilder.start(this, 30, "shen", "wow")
        }

        btn2.setOnClickListener {
            startKotlinTestActivity("shen")
        }
    }
}
