package com.example.android.webtoon.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.webtoon.R

class EpisodeActivity : AppCompatActivity() {
    val URL : String = "https://comic.naver.com"

    lateinit var roundN : CustomView_webtoonRoundN

    lateinit var link : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        // 초기화
        init()
        // 에피소드 정보 가져오기
        getEpisodeInfo()
        giveWebtoonCuts()
    }

    private fun init(){
        roundN = CustomView_webtoonRoundN(this);
        setContentView(roundN)
    }

    private fun getEpisodeInfo(){
        if(intent.hasExtra("episodeContent")){
            link = intent.getStringExtra("episodeContent")
            Log.d("에피소드 링크", "$link")
        }else{
            Log.d("에피소드 링크", "전달된 데이터가 없습니다.")
        }
    }

    private fun giveWebtoonCuts(){
        roundN.renewRV("https://cdn.pixabay.com/photo/2019/05/22/22/03/sky-4222653_960_720.jpg")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}