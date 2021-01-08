package com.scurab.dipho.android

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.scurab.dipho.android.databinding.ActivityMainBinding
import com.scurab.dipho.android.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private val views: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, HomeFragment())
        }
    }
}
