package com.shen.somebase.view

import androidx.lifecycle.MutableLiveData
import com.shen.somebase.api.RetrofitHelper
import com.shen.somebase.coroutine.BaseViewModel
import com.shen.somebase.coroutine.ErrorHandler

/*
* created by shen at 2019/9/30 11:42
*/
class TopicViewModel : BaseViewModel() {
    val topicInfo: MutableLiveData<Any> by lazy { MutableLiveData<Any>() }

    fun getTopicInfo(id: Int, error: ErrorHandler) {
        viewModelScopeByErrorCatch(errorHandler = error) {
            topicInfo.value = RetrofitHelper.instance.apiService.getAllIndustryList(user_phone = "").await()
        }
    }
}