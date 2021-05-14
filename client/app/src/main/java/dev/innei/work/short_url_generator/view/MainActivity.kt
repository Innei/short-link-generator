package dev.innei.work.short_url_generator.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.fastjson.JSON
import dev.innei.work.short_url_generator.constants.EventType
import dev.innei.work.short_url_generator.model.repository.url.viewmodel.URLViewModel
import dev.innei.work.short_url_generator.utils.observeOnce


class MainActivity : AppCompatActivity() {
    private lateinit var webView: MWebView

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = MWebView(this)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                this@MainActivity.init()
            }
        }
        setContentView(webView)

    }

    fun init() {
        val vm = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(URLViewModel::class.java)
        val urls = vm.getAllUrls()

        urls.observeOnce(Observer { list ->
            webView.emitEventByBus((list), eventName = EventType.FETCH_ALL)
        })
    }

}
