package com.whyte.test.di.utils

import android.util.Log
import com.whyte.test.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ServiceInterSeptor : Interceptor, KoinComponent {

    var token: String = ""
    val mSessionManager by inject<SessionManager>()


    fun Token(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if( request.body!= null) {
            val requestBuffer = Buffer()
            request.body!!.writeTo(requestBuffer)
            Log.d("OkHttp", requestBuffer.readUtf8())
        }
        if (request.header("AuthNotNeed") == null) {
            Timber.e("Token=${mSessionManager.token}")
            val token = mSessionManager.token
            token?.let {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            }
        }
        return chain.proceed(request)
    }

}