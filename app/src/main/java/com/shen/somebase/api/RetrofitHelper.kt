package com.shen.somebase.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.shen.somebase.util.DebugLog
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class RetrofitHelper private constructor(){
    companion object {
        //by lazy默认线程安全
        val instance: RetrofitHelper by lazy {
            RetrofitHelper()
        }
    }

    val apiService: Api by lazy {
        val client = OkHttpClient().newBuilder()
                .addInterceptor(RetrofitInterceptor())
                .addInterceptor(getInterceptor())
//                .addInterceptor(TokenIntercept())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                //设置拦截器
                .build()

        val retrofit = Retrofit.Builder()
                //设置网络请求的Url地址
                .baseUrl("https://www.baidu.com")
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求适配器，使其支持协程与RxAndroid
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
        retrofit.create(Api::class.java)
    }

    private inner class RetrofitInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val requestBuilder = request.newBuilder()

            if (request.method() == "POST") {
                val builder = FormBody.Builder()
                var body = request.body() as? FormBody
                body?.let {
                    for (i in 0 until it.size()) {
                        builder.add(it.encodedName(i), it.encodedValue(i))
                    }
                }

                body = builder
//                    .add("access_token", Token.getToken())
//                    .add("app_plantform", FinalConstant.PLATFORM)
//                    .add("app_versionname", MetaUtil.getAppVersionName(BaseApplication.getInstance()))
//                    .add("app_versioncode", "${MetaUtil.getAppVersionCode(BaseApplication.getInstance())}")
//                    .add("app_channel",  ChannelUtil.getChannel(BaseApplication.getInstance()))
                    .build()

                request = requestBuilder.post(body).build()
            }

            return chain.proceed(request)
        }
    }

    private inner class TokenIntercept: Interceptor {
        private val TAG = "respond"

        override fun intercept(chain: Interceptor.Chain): Response {
            val oldRequest = chain.request()
//            val url = oldRequest.url().toString()
            var response: Response = chain.proceed(oldRequest)
            val value = response.body()
            val resp = value?.bytes() ?: byteArrayOf()
            val json = String(resp, Charset.forName("UTF-8"))
            try {
                val jsonObject = JSONObject(json)
                val success = jsonObject.getBoolean("success")
                if (!success) {
                    val data = jsonObject.optString("data")

                    var newTokenRequest: Request? = null
                    val requestBuilder = oldRequest.newBuilder()

                    if ("POST" == oldRequest.method()) {
                        if (oldRequest.body() is FormBody) {
                            val builder = FormBody.Builder()
                            var body = oldRequest.body() as FormBody?

                            body?.let {
                                for (i in 0 until it.size()) {
                                    builder.add(it.encodedName(i), it.encodedValue(i))
                                }
                            }

                            body = builder

                                    .add("user_token", "news")
                                    .build()


                            newTokenRequest = requestBuilder.post(body).build()

                        }
                    }

                    newTokenRequest?.let {
                        response = chain.proceed(it)
                    }


                } else {
                    response = response.newBuilder()
                            .body(ResponseBody.create(null, resp))
                            .build()
                }
//
//                val stateCode = jsonObject.optInt("stateCode")
//                response = if (stateCode == 3) {
//                    val data = jsonObject.optString("data")
//                    Log.d(TAG, "token失效，新的token：$data")
//                    //                    DataStorageUtils.saveToken(data);//保存新的token
//                    // token失效，重新执行请求
//                    //                    Request newTokenRequest = addParam(oldRequest);
//                    chain.proceed(oldRequest)
//                } else {
//                    // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
//                    response.newBuilder()
//                            .body(ResponseBody.create(null, resp))
//                            .build()
//                }

            } catch (e: Exception) {
                //ignore this
            }

            return response
        }
    }

    private fun getInterceptor(): Interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        //打印retrofit日志
        DebugLog.logE("RetrofitLog", "retrofitBack = $message")
    }).also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
}