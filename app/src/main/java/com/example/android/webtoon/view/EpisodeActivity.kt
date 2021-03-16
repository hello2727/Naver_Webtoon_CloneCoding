package com.example.android.webtoon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.webtoon.R

class EpisodeActivity : AppCompatActivity() {
    lateinit var roundN : CustomView_webtoonRoundN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        // 초기화
        init()
        giveWebtoonCuts()
    }

    private fun init(){
        roundN = CustomView_webtoonRoundN(this);
        setContentView(roundN)
    }

    private fun giveWebtoonCuts(){
        roundN.renewRV("https://cdn.pixabay.com/photo/2019/05/22/22/03/sky-4222653_960_720.jpg")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}