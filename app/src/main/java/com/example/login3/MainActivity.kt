package com.example.login3

import android.content.Context
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.mwebview)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        cookieManager.setAcceptCookie(true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookieString = CookieManager.getInstance().getCookie(url)
                val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("cookies", cookieString)
                editor.apply()
            }
        }

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val cookieString = sharedPreferences.getString("cookies", "")
        cookieManager.setCookie("https://utserang.ut.ac.id/sp", cookieString)

        webView.loadUrl("https://utserang.ut.ac.id/sp")
    }
}