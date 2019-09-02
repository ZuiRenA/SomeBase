package com.shen.somebase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shen.annotations.Builder
import com.shen.annotations.Optional
import com.shen.annotations.Required
import com.shen.somebase.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

@Builder
class KotlinTestActivity : AppCompatActivity() {
    @Required
    lateinit var name: String

    @Optional(intValue = 0)
    var age: Int = -5

    @Optional(stringValue = "describe")
    lateinit var describe: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        ktNameView.text = name
        ktAgeView.text = age.toString()
        ktDesView.text = describe
    }
}
