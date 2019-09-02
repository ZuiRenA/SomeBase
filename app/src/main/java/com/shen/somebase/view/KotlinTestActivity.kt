package com.shen.somebase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shen.somebase.R
import project.shen.annotations.Builder
import project.shen.annotations.Optional
import project.shen.annotations.Required

@Builder
class KotlinTestActivity : AppCompatActivity() {

    @Required
    lateinit var name: String

    @Optional
    var age: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
    }
}
