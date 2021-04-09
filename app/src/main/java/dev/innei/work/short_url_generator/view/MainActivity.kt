package dev.innei.work.short_url_generator.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import dev.innei.work.short_url_generator.R


class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = Button(this)
        btn.setOnClickListener(View.OnClickListener { view ->
            (view as Button).text = "clicked"
        })

        val root = findViewById<View>(R.id.root) as LinearLayout
        btn.text = "Pizzazz"

        root.addView(btn)
    }
}