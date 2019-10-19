package com.shen.somebase.view

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.shen.somebase.R
import kotlinx.android.synthetic.main.activity_main.*
import project.shen.dessert_life.factor_dialog.factorDialog

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

        btn3.setOnClickListener {
            factorDialog {
                context = this@MainActivity
                layoutIdRes = R.layout.dialog_loading
            }
        }

    }
}
