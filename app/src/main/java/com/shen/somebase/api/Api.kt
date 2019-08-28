package com.shen.somebase.api

import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("v1/fes-search")
    fun search(@Field("keyword") keyword: String,
                        @Field("limit") limit: Int,
                        @Field("user_phone") user_phone: String): Deferred<Any>

    @FormUrlEncoded
    @POST("v1/fes-config")
    fun getAllIndustryList(@Field("user_phone") user_phone: String): Deferred<Any>

    @FormUrlEncoded
    @POST("v1/fe-add-heap-score")
    fun addScore(@Field("task_code") user_phone: String): Deferred<Any>
}