package com.shen.somebase.coroutine


import androidx.lifecycle.MutableLiveData
import com.shen.somebase.api.RetrofitHelper

class TestViewModel: BaseViewModel() {
    //LiveData, 可持续存活的一个被观察的数据
    val testResult: MutableLiveData<Any> by lazy {
        MutableLiveData<Any>()
    }


    //协程完成网络请求，viewModelScope 使用了 扩展函数,
    //addJobToAutoCancelMap 用了HashMap存储Job， 如果activity被销毁，会结束协程的任务
    //防止内存泄露
    fun show(userPhone: String, errorHandler: ErrorHandler) {
        viewModelScope(errorHandler) {
            testResult.value = RetrofitHelper.instance.apiService.getAllIndustryList(userPhone).await()
        }.addJobToAutoCancelMap()
    }

    //协程完成网络请求的第二种方式
    //使用了google官方方式进行协程作用域绑定
    //但是由于不想导包选用了反射去实现
    fun show2(userPhone: String, errorHandler: ErrorHandler) {
        viewModelScopeByErrorCatch(errorHandler) {
            testResult.value = RetrofitHelper.instance.apiService.getAllIndustryList(userPhone).await()
        }
    }
}