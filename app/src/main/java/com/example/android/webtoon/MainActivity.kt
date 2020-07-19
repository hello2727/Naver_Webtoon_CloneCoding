package com.example.android.webtoon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.webtoon.more.more
import com.example.android.webtoon.webtoon.webtoonMain
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(webtoonMain())

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.webtoon -> {
                    loadFragment(webtoonMain())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.recommendedCompletion -> {
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.bestChallenge -> {
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.my -> {
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.more -> {
                    loadFragment(more())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerOfWebtoon, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}