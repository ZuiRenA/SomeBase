package com.shen.somebase.view

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shen.somebase.R
import com.shen.somebase.widget.ToastWidget
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
                elementCreator {
                    title = "wow"
                    icon = "https://b-ssl.duitang.com/uploads/item/201604/23/20160423185321_K2ueY.jpeg"
                    iconLoader = { icon, imageView ->
                        Glide.with(this@MainActivity).load(icon).into(imageView)
                    }
                    funcClick = { _, position ->
                        ToastWidget.show("点击了$position")
                    }
                }
                elementCreator {
                    title = "name"
                    icon = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1571575499396&di=5ad5566867a1d0c2abc65e1" +
                            "8c3a71f12&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170301%2Fc2f7fa5a3ddd40b5993d8e9fbe969584_th.jpg"
                    iconLoader = { icon, imageView ->
                        Glide.with(this@MainActivity).load(icon).into(imageView)
                    }
                    funcClick = { _, position ->
                        ToastWidget.show("点击了$position")
                    }
                }
            }.show()
        }

    }
}
