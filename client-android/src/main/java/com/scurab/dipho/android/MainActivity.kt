package com.scurab.dipho.android

import PlatformGreetings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.scurab.dipho.android.databinding.ActivityMainBinding
import com.scurab.dipho.common.model.Author
import com.scurab.dipho.common.model.Thread


class MainActivity : AppCompatActivity() {

    private val views: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)

        val auth = Author("1", "test")
        views.textView.text = PlatformGreetings().greetings() + "\n" + auth.name
    }
}
