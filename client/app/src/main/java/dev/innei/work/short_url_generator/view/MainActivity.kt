package dev.innei.work.short_url_generator.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var webView: MWebView

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = MWebView(this)
        setContentView(webView)


    }

//    override fun onStart() {
//        super.onStart()
//        val list = Database.getURLDatabase(applicationContext).urlDao.findAll()
//        webView.emitEventByBus(list, null)
//    }
}
