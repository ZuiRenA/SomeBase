package com.shen.somebase.view

import androidx.lifecycle.Observer
import com.shen.annotations.Builder
import com.shen.annotations.Optional
import com.shen.annotations.Required
import com.shen.somebase.R
import com.shen.somebase.coroutine.BaseViewModelActivity
import kotlinx.android.synthetic.main.activity_topic.*

@Builder
class TopicActivity : BaseViewModelActivity<TopicViewModel>() {

    @Required
    lateinit var topicName: String

    @Required
    var topicId = -1

    @Optional(stringValue = "")
    lateinit var topicIntroduction: String

    override fun init() {
        tvTopicName.text = topicName
        tvTopicIntroduction.text = topicIntroduction

        viewModel.topicInfo.observe(this, Observer {

        })

        initNetwork()
    }

    private fun initNetwork() {
        viewModel.getTopicInfo(topicId, this::onError)
    }

    override fun getLayoutId(): Int = R.layout.activity_topic
}
