package com.ezcode.mp.API

import com.ezcode.mp.Util.MPUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    DEV: Eduardo Herrera
    LinkIn: https://www.linkedin.com/in/eduardoherreradeveloper/
    Portfolio: https://soulfade.wixsite.com/eduardoherrera
    Instagram: @edtrip_

    This Class build a client HTTP for consult the WebServices with RetroFit
 */

class APIClient {

    var clientAPI: APIInterface

    init {

        val iLogging = HttpLoggingInterceptor()
        iLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val originalR = chain.request()
            val request = originalR.newBuilder()
            request.addHeader("Accept", "application/json")
            val requestF = request.build()
            chain.proceed(requestF)
        }

        httpClient.connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        httpClient.readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        httpClient.addInterceptor(iLogging)

        val retroClient = Retrofit.Builder()
            .baseUrl(MPUtils.MP_MAIN_SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        clientAPI = retroClient.create(APIInterface::class.java)
    }
}